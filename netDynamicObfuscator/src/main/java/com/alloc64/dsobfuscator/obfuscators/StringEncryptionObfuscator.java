/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.obfuscators;

import com.alloc64.dsobfuscator.crypt.CryptUtils;
import com.alloc64.dsobfuscator.encoders.BaseEncoder;
import com.alloc64.dsobfuscator.proguard.ProguardDictionary;
import com.alloc64.dsobfuscator.smali.model.SmaliMethod;
import com.alloc64.apktools.apk.SmaliFile;
import com.alloc64.apktools.smali.Smali;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class StringEncryptionObfuscator extends SmaliClassInjectingObfuscator
{
    private static final String encryptionKeyPrefix = "hamrak";

    private SecretKey encryptionKey;
    private BaseEncoder baseEncode;

    private SmaliMethod decryptStringMethod;

    public StringEncryptionObfuscator(ProguardDictionary proguardDictionary)
    {
        super(proguardDictionary);
    }

    @Override
    protected String name()
    {
        return "StringEncryptionObfuscator";
    }

    public void setEncryptionKey(String encryptionKey)
    {
        final String finalEncryptionKey = encryptionKeyPrefix + encryptionKey;

        if (finalEncryptionKey.length() != 32)
            throw new IllegalStateException("encryptionKey must have exactly 32 (32 - " + encryptionKeyPrefix.length() + " prefix length) characters.");

        this.encryptionKey = new SecretKeySpec(finalEncryptionKey.getBytes(StandardCharsets.UTF_8), "AES");
    }

    public void setEncoderAlphabet(String encoderAlphabet)
    {
        this.baseEncode = new BaseEncoder(encoderAlphabet);
    }

    private void validate()
    {
        if(encryptionKey == null)
            throw new IllegalStateException("encryptionKey must be set.");

        if(baseEncode == null)
            throw new IllegalStateException("encoderAlphabet must be set.");
    }

    @Override
    public void obfuscate(int fileNumber, SmaliFile smaliFile, List<String> lines) throws Exception
    {
        validate();

        String className = null;

        // Line numbers where a static string is declared.
        List<Integer> staticStringIndex = new ArrayList<>();

        // Names of the static strings.
        List<String> staticStringName = new ArrayList<>();

        // Values of the static strings.
        List<String> staticStringValue = new ArrayList<>();

        int directMethodsLine = -1;
        int staticConstructorLine = -1;

        // Line numbers where a constant string is declared.
        List<Integer> stringIndex = new ArrayList<>();

        // Registers containing the constant strings.
        List<String> stringRegister = new ArrayList<>();

        //Values of the constant strings.
        List<String> stringValue = new ArrayList<>();

        int currentLocalCount = 0;

        for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++)
        {
            String line = lines.get(lineNumber);

            if (className == null)
            {
                Matcher classMatch = classPattern.matcher(line);

                if (classMatch.matches())
                {
                    className = classMatch.group(CLASS_NAME);
                    continue;
                }
            }

            if (line.startsWith("# direct methods"))
            {
                directMethodsLine = lineNumber;
                continue;
            }

            if (line.startsWith(".method static constructor <clinit>()V"))
            {
                staticConstructorLine = lineNumber;
                continue;
            }

            Matcher staticStringMatch = Smali.staticStringPattern.matcher(line);

            if (staticStringMatch.matches())
            {
                String value = staticStringMatch.group(Smali.STRING_VALUE);

                if (!StringUtils.isEmpty(value))
                {
                    String name = staticStringMatch.group(Smali.STRING_NAME);

                    staticStringIndex.add(lineNumber);
                    staticStringName.add(name);
                    staticStringValue.add(value);
                }
            }

            // We are iterating the lines in order, so each time we enter a
            // method we'll find the declaration with the number of local
            // registers available. When we'll encounter a constant string later
            // in the body of the method, we'll look at its register value and if
            // it's greater than 15 we won't encrypt it (the invoke instruction
            // that we need later won't take registers with values greater
            // than 15).
            Matcher match = Smali.localsPattern.matcher(line);

            if (match.matches())
            {
                currentLocalCount = Integer.parseInt(match.group(Smali.LOCAL_COUNT));
                continue;
            }

            // If the constant string has a register v0-v15 we can proceed with
            // the encryption, but if it uses a p<number> register, before
            // encrypting we have to check if <number> + locals <= 15.

            Matcher stringMatch = Smali.constStringPattern.matcher(line);

            if (stringMatch.matches())
            {
                String value = stringMatch.group(Smali.STRING_VALUE);
                String register = stringMatch.group(Smali.REGISTER);

                String regType = register.substring(0, 1);
                Integer regNumber = Integer.parseInt(register.substring(1, register.length()));

                if ((regType.equals("v") && regNumber <= 15) || (regType.equals("p") && (regNumber + currentLocalCount + 1) <= 15))
                {
                    // A non empty string was found in a register <= 15.
                    stringIndex.add(lineNumber);
                    stringRegister.add(register);
                    stringValue.add(value);
                }
            }

            // Const string encryption.

            for (int i = 0; i < stringIndex.size(); i++)
            {
                Integer lineNumber0 = stringIndex.get(i);

                String register = stringRegister.get(i);
                String value = stringValue.get(i);
                String encryptedValue = encryptString(value);

                // System.out.printf("Encrypted const string \"%s\" to new value %s\n", value, encryptedValue);

                String newValue = String.format("\tconst-string/jumbo %s, \"%s\"\n"
                                + "\n\tinvoke-static {%s}, " + decryptStringMethod.getSignature().getCallSignature() + "\n"
                                + "\n\tmove-result-object %s\n",
                        register,
                        encryptedValue,
                        register,
                        register);

                lines.set(lineNumber0, newValue);

                addEncryptedString(value);
            }

            // Static string encryption.

            String staticStringEncryptionCode = "";

            for (int i = 0; i < staticStringIndex.size(); i++)
            {
                Integer lineNumber0 = staticStringIndex.get(i);

                String stringName = staticStringName.get(i);
                String value = staticStringValue.get(i);
                String encryptedValue = encryptString(value);

                String line0 = lines.get(lineNumber0);

                // System.out.printf("Encrypted static string %s=\"%s\" to new value %s\n", stringName, value, encryptedValue);

                // Remove the original initialization.
                lines.set(lineNumber0, line0.split(" = ")[0] + "\n");

                // Initialize the static string from an encrypted string.

                staticStringEncryptionCode +=
                        String.format("\tconst-string/jumbo v0, \"%s\"\n"
                                        + "\n\tinvoke-static {v0}, " + decryptStringMethod.getSignature().getCallSignature() + "\n"
                                        + "\n\tmove-result-object v0\n"
                                        + "\n\tsput-object v0, %s->"
                                        + "%s:Ljava/lang/String;\n\n",
                                encryptedValue,
                                className,
                                stringName);

                addEncryptedString(value);
            }

            if (staticConstructorLine != -1)
            {
                // Add static string encryption to the existing static constructor.

                Matcher m = Smali.localsPattern.matcher(lines.get(staticConstructorLine + 1));

                if (m.matches())
                {
                    // At least one register is needed.

                    Integer localCount = Integer.valueOf(m.group(Smali.LOCAL_COUNT));

                    if (localCount == 0)
                        lines.set(staticConstructorLine + 1, "\t.locals 1\n");

                    lines.set(staticConstructorLine + 2, "\n" + staticStringEncryptionCode);
                }
            }
            else if(!StringUtils.isEmpty(staticStringEncryptionCode))
            {
                // Add a new static constructor for the static string encryption.

                int newConstructorLine;

                if (directMethodsLine != -1)
                    newConstructorLine = directMethodsLine;
                else
                    newConstructorLine = lines.size() - 1;

                lines.set(newConstructorLine,
                        String.format(
                                "%s\n" +
                                        ".method static constructor <clinit>()V\n" +
                                        "\t.locals 1\n\n" +
                                        "%s" +
                                        "\treturn-void\n" +
                                        ".end method\n\n",
                                lines.get(newConstructorLine),
                                staticStringEncryptionCode
                        )
                );
            }

            write(smaliFile, lines);
        }
    }

    private String encryptString(String input) throws Exception
    {
        input = org.apache.commons.text.StringEscapeUtils.unescapeJava(input);

        return baseEncode.encode(CryptUtils.encrypt(encryptionKey, input.getBytes(StandardCharsets.UTF_8)));
    }

    private void addEncryptedString(String value)
    {
        increaseOccurrenceCount();
    }

    public void setDecryptStringMethod(SmaliMethod decryptStringMethod)
    {
        this.decryptStringMethod = decryptStringMethod;
    }
}
