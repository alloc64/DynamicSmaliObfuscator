.class public Lnet/dynamico/Crypt;
.super Ljava/lang/Object;
.source "Crypt.java"


# static fields
.field private static final AES:[B

.field private static final BASE:I = 0x40

.field private static final UTF_8:[B

.field private static charPositionMap:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Ljava/lang/Character;",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation
.end field

.field private static final cipherType:Ljava/lang/String;

.field private static encryptionKey:Ljava/lang/Object;

.field private static final encryptionKeyPrefix:[B

.field private static final secretKeySpecClass:[B


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 20
    const/4 v0, 0x3

    new-array v0, v0, [B

    fill-array-data v0, :array_44

    sput-object v0, Lnet/dynamico/Crypt;->AES:[B

    .line 21
    const/4 v0, 0x5

    new-array v0, v0, [B

    fill-array-data v0, :array_4a

    sput-object v0, Lnet/dynamico/Crypt;->UTF_8:[B

    .line 23
    const/16 v0, 0x14

    new-array v0, v0, [B

    fill-array-data v0, :array_52

    invoke-static {v0}, Lnet/dynamico/Crypt;->createString([B)Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lnet/dynamico/Crypt;->cipherType:Ljava/lang/String;

    .line 24
    const/4 v0, 0x6

    new-array v0, v0, [B

    fill-array-data v0, :array_60

    sput-object v0, Lnet/dynamico/Crypt;->encryptionKeyPrefix:[B

    .line 25
    const/16 v0, 0x1f

    new-array v0, v0, [B

    fill-array-data v0, :array_68

    sput-object v0, Lnet/dynamico/Crypt;->secretKeySpecClass:[B

    .line 35
    const/16 v0, 0x1a

    new-array v0, v0, [B

    fill-array-data v0, :array_7c

    invoke-static {v0}, Lnet/dynamico/Crypt;->setEncryptionKey([B)V

    .line 36
    const/16 v0, 0x80

    new-array v0, v0, [B

    fill-array-data v0, :array_8e

    invoke-static {v0}, Lnet/dynamico/Crypt;->setBase64Charset([B)V

    .line 38
    return-void

    .line 20
    nop

    :array_44
    .array-data 1
        0x41t
        0x45t
        0x53t
    .end array-data

    .line 21
    :array_4a
    .array-data 1
        0x55t
        0x54t
        0x46t
        0x2dt
        0x38t
    .end array-data

    .line 23
    nop

    :array_52
    .array-data 1
        0x41t
        0x45t
        0x53t
        0x2ft
        0x43t
        0x42t
        0x43t
        0x2ft
        0x50t
        0x4bt
        0x43t
        0x53t
        0x35t
        0x50t
        0x41t
        0x44t
        0x44t
        0x49t
        0x4et
        0x47t
    .end array-data

    .line 24
    :array_60
    .array-data 1
        0x68t
        0x61t
        0x6dt
        0x72t
        0x61t
        0x6bt
    .end array-data

    .line 25
    nop

    :array_68
    .array-data 1
        0x6at
        0x61t
        0x76t
        0x61t
        0x78t
        0x2et
        0x63t
        0x72t
        0x79t
        0x70t
        0x74t
        0x6ft
        0x2et
        0x73t
        0x70t
        0x65t
        0x63t
        0x2et
        0x53t
        0x65t
        0x63t
        0x72t
        0x65t
        0x74t
        0x4bt
        0x65t
        0x79t
        0x53t
        0x70t
        0x65t
        0x63t
    .end array-data

    .line 35
    :array_7c
    .array-data 1
        0x45t
        0x70t
        0x53t
        0x45t
        0x35t
        0x75t
        0x44t
        0x67t
        0x62t
        0x6ct
        0x62t
        0x55t
        0x56t
        0x76t
        0x39t
        0x67t
        0x62t
        0x34t
        0x57t
        0x49t
        0x71t
        0x5at
        0x46t
        0x48t
        0x69t
        0x57t
    .end array-data

    .line 36
    nop

    :array_8e
    .array-data 1
        -0x31t
        -0x54t
        -0x31t
        -0x53t
        -0x31t
        -0x52t
        -0x31t
        -0x51t
        -0x31t
        -0x50t
        -0x31t
        -0x4ft
        -0x31t
        -0x4et
        -0x31t
        -0x4dt
        -0x31t
        -0x4ct
        -0x31t
        -0x4bt
        -0x31t
        -0x4at
        -0x31t
        -0x49t
        -0x31t
        -0x48t
        -0x31t
        -0x47t
        -0x31t
        -0x46t
        -0x31t
        -0x45t
        -0x31t
        -0x44t
        -0x31t
        -0x43t
        -0x31t
        -0x42t
        -0x31t
        -0x41t
        -0x30t
        -0x80t
        -0x30t
        -0x7ft
        -0x30t
        -0x7et
        -0x30t
        -0x7dt
        -0x30t
        -0x7ct
        -0x30t
        -0x7bt
        -0x30t
        -0x7at
        -0x30t
        -0x79t
        -0x30t
        -0x78t
        -0x30t
        -0x77t
        -0x30t
        -0x76t
        -0x30t
        -0x75t
        -0x30t
        -0x74t
        -0x30t
        -0x73t
        -0x30t
        -0x72t
        -0x30t
        -0x71t
        -0x30t
        -0x70t
        -0x30t
        -0x6ft
        -0x30t
        -0x6et
        -0x30t
        -0x6dt
        -0x30t
        -0x6ct
        -0x30t
        -0x6bt
        -0x30t
        -0x6at
        -0x30t
        -0x69t
        -0x30t
        -0x68t
        -0x30t
        -0x67t
        -0x30t
        -0x66t
        -0x30t
        -0x65t
        -0x30t
        -0x64t
        -0x30t
        -0x63t
        -0x30t
        -0x62t
        -0x30t
        -0x61t
        -0x30t
        -0x60t
        -0x30t
        -0x5ft
        -0x30t
        -0x5et
        -0x30t
        -0x5dt
        -0x30t
        -0x5ct
        -0x30t
        -0x5bt
        -0x30t
        -0x5at
        -0x30t
        -0x59t
        -0x30t
        -0x58t
        -0x30t
        -0x57t
        -0x30t
        -0x56t
        -0x30t
        -0x55t
    .end array-data
.end method

.method public constructor <init>()V
    .registers 1

    .prologue
    .line 17
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private static convertStringToByteArrayNotation(Ljava/lang/String;)Ljava/lang/String;
    .registers 5
    .param p0, "input"    # Ljava/lang/String;

    .prologue
    .line 200
    sget-object v3, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {p0, v3}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object v2

    .line 202
    .local v2, "val":[B
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v3, "{ "

    invoke-direct {v1, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 204
    .local v1, "s":Ljava/lang/StringBuilder;
    const/4 v0, 0x0

    .local v0, "i":I
    :goto_e
    array-length v3, v2

    if-ge v0, v3, :cond_23

    .line 206
    aget-byte v3, v2, v0

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 208
    array-length v3, v2

    add-int/lit8 v3, v3, -0x1

    if-ge v0, v3, :cond_20

    .line 209
    const-string v3, ", "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 204
    :cond_20
    add-int/lit8 v0, v0, 0x1

    goto :goto_e

    .line 212
    :cond_23
    const-string v3, " };"

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 214
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    return-object v3
.end method

.method private static createString([B)Ljava/lang/String;
    .registers 2
    .param p0, "bytes"    # [B

    .prologue
    .line 140
    new-instance v0, Ljava/lang/String;

    invoke-direct {v0, p0}, Ljava/lang/String;-><init>([B)V

    return-object v0
.end method

.method private static decode(Ljava/lang/String;)[B
    .registers 11
    .param p0, "base64String"    # Ljava/lang/String;

    .prologue
    .line 111
    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v5

    int-to-double v6, v5

    const-wide/high16 v8, 0x4018000000000000L    # 6.0

    mul-double/2addr v6, v8

    const-wide/high16 v8, 0x4020000000000000L    # 8.0

    div-double/2addr v6, v8

    invoke-static {v6, v7}, Ljava/lang/Math;->floor(D)D

    move-result-wide v6

    double-to-int v5, v6

    new-array v1, v5, [B

    .line 113
    .local v1, "decodedBytes":[B
    const/4 v3, 0x2

    .line 114
    .local v3, "missingBits":I
    const/4 v4, 0x0

    .line 116
    .local v4, "stringPos":I
    const/4 v2, 0x0

    .local v2, "i":I
    :goto_15
    array-length v5, v1

    if-ge v2, v5, :cond_66

    .line 118
    sget-object v5, Lnet/dynamico/Crypt;->charPositionMap:Ljava/util/HashMap;

    invoke-virtual {p0, v4}, Ljava/lang/String;->charAt(I)C

    move-result v6

    invoke-static {v6}, Ljava/lang/Character;->valueOf(C)Ljava/lang/Character;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/Integer;

    invoke-virtual {v5}, Ljava/lang/Integer;->intValue()I

    move-result v5

    int-to-byte v5, v5

    aput-byte v5, v1, v2

    .line 119
    aget-byte v5, v1, v2

    shl-int/2addr v5, v3

    int-to-byte v5, v5

    aput-byte v5, v1, v2

    .line 120
    sget-object v5, Lnet/dynamico/Crypt;->charPositionMap:Ljava/util/HashMap;

    add-int/lit8 v6, v4, 0x1

    invoke-virtual {p0, v6}, Ljava/lang/String;->charAt(I)C

    move-result v6

    invoke-static {v6}, Ljava/lang/Character;->valueOf(C)Ljava/lang/Character;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/Integer;

    invoke-virtual {v5}, Ljava/lang/Integer;->intValue()I

    move-result v5

    int-to-byte v0, v5

    .line 121
    .local v0, "b":B
    rsub-int/lit8 v5, v3, 0x6

    shr-int v5, v0, v5

    int-to-byte v0, v5

    .line 122
    aget-byte v5, v1, v2

    or-int/2addr v5, v0

    int-to-byte v5, v5

    aput-byte v5, v1, v2

    .line 124
    add-int/lit8 v5, v3, 0x2

    rem-int/lit8 v3, v5, 0x8

    .line 126
    if-nez v3, :cond_61

    .line 128
    add-int/lit8 v4, v4, 0x1

    .line 129
    add-int/lit8 v3, v3, 0x2

    .line 132
    :cond_61
    add-int/lit8 v4, v4, 0x1

    .line 116
    add-int/lit8 v2, v2, 0x1

    goto :goto_15

    .line 135
    .end local v0    # "b":B
    :cond_66
    return-object v1
.end method

.method public static decrypt(I)I
    .registers 2
    .param p0, "encrypted"    # I

    .prologue
    .line 42
    invoke-static {p0}, Ljava/lang/Integer;->reverse(I)I

    move-result v0

    return v0
.end method

.method public static decrypt(Ljava/lang/String;)Ljava/lang/String;
    .registers 5
    .param p0, "encrypted"    # Ljava/lang/String;

    .prologue
    .line 47
    if-eqz p0, :cond_9

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v2

    const/4 v3, 0x1

    if-ge v2, v3, :cond_a

    .line 63
    .end local p0    # "encrypted":Ljava/lang/String;
    :cond_9
    :goto_9
    return-object p0

    .line 52
    .restart local p0    # "encrypted":Ljava/lang/String;
    :cond_a
    :try_start_a
    invoke-static {p0}, Lnet/dynamico/Crypt;->decode(Ljava/lang/String;)[B

    move-result-object v2

    invoke-static {v2}, Lnet/dynamico/Crypt;->decrypt([B)[B

    move-result-object v0

    .line 54
    .local v0, "data":[B
    if-eqz v0, :cond_22

    .line 55
    new-instance p0, Ljava/lang/String;

    .end local p0    # "encrypted":Ljava/lang/String;
    invoke-direct {p0, v0}, Ljava/lang/String;-><init>([B)V
    :try_end_19
    .catch Ljava/lang/Exception; {:try_start_a .. :try_end_19} :catch_1a

    goto :goto_9

    .line 57
    .end local v0    # "data":[B
    :catch_1a
    move-exception v1

    .line 59
    .local v1, "e":Ljava/lang/Exception;
    sget-boolean v2, Lnet/dynamico/BuildConfig;->DEBUG:Z

    if-eqz v2, :cond_22

    .line 60
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    .line 63
    :cond_22
    const/4 p0, 0x0

    goto :goto_9
.end method

.method private static decrypt([B)[B
    .registers 11
    .param p0, "data"    # [B

    .prologue
    const/4 v7, 0x0

    .line 74
    if-eqz p0, :cond_7

    array-length v6, p0

    const/4 v8, 0x1

    if-ge v6, v8, :cond_9

    :cond_7
    move-object v6, v7

    .line 106
    :goto_8
    return-object v6

    .line 79
    :cond_9
    :try_start_9
    invoke-static {p0}, Ljava/nio/ByteBuffer;->wrap([B)Ljava/nio/ByteBuffer;

    move-result-object v0

    .line 81
    .local v0, "byteBuffer":Ljava/nio/ByteBuffer;
    invoke-virtual {v0}, Ljava/nio/ByteBuffer;->getInt()I

    move-result v5

    .line 83
    .local v5, "ivLength":I
    sget-boolean v6, Lnet/dynamico/BuildConfig;->DEBUG:Z

    if-eqz v6, :cond_2f

    .line 85
    const/16 v6, 0xc

    if-lt v5, v6, :cond_1d

    const/16 v6, 0x10

    if-le v5, v6, :cond_2f

    .line 86
    :cond_1d
    new-instance v6, Ljava/lang/IllegalArgumentException;

    const-string v8, "Invalid IV."

    invoke-direct {v6, v8}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v6
    :try_end_25
    .catch Ljava/lang/Exception; {:try_start_9 .. :try_end_25} :catch_25

    .line 100
    .end local v0    # "byteBuffer":Ljava/nio/ByteBuffer;
    .end local v5    # "ivLength":I
    :catch_25
    move-exception v3

    .line 102
    .local v3, "e":Ljava/lang/Exception;
    sget-boolean v6, Lnet/dynamico/BuildConfig;->DEBUG:Z

    if-eqz v6, :cond_2d

    .line 103
    invoke-virtual {v3}, Ljava/lang/Exception;->printStackTrace()V

    :cond_2d
    move-object v6, v7

    .line 106
    goto :goto_8

    .line 89
    .end local v3    # "e":Ljava/lang/Exception;
    .restart local v0    # "byteBuffer":Ljava/nio/ByteBuffer;
    .restart local v5    # "ivLength":I
    :cond_2f
    :try_start_2f
    new-array v4, v5, [B

    .line 90
    .local v4, "iv":[B
    invoke-virtual {v0, v4}, Ljava/nio/ByteBuffer;->get([B)Ljava/nio/ByteBuffer;

    .line 92
    invoke-virtual {v0}, Ljava/nio/ByteBuffer;->remaining()I

    move-result v6

    new-array v2, v6, [B

    .line 93
    .local v2, "cipherText":[B
    invoke-virtual {v0, v2}, Ljava/nio/ByteBuffer;->get([B)Ljava/nio/ByteBuffer;

    .line 95
    sget-object v6, Lnet/dynamico/Crypt;->cipherType:Ljava/lang/String;

    invoke-static {v6}, Ljavax/crypto/Cipher;->getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;

    move-result-object v1

    .line 96
    .local v1, "cipher":Ljavax/crypto/Cipher;
    const/4 v8, 0x2

    sget-object v6, Lnet/dynamico/Crypt;->encryptionKey:Ljava/lang/Object;

    check-cast v6, Ljava/security/Key;

    new-instance v9, Ljavax/crypto/spec/IvParameterSpec;

    invoke-direct {v9, v4}, Ljavax/crypto/spec/IvParameterSpec;-><init>([B)V

    invoke-virtual {v1, v8, v6, v9}, Ljavax/crypto/Cipher;->init(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V

    .line 98
    invoke-virtual {v1, v2}, Ljavax/crypto/Cipher;->doFinal([B)[B
    :try_end_53
    .catch Ljava/lang/Exception; {:try_start_2f .. :try_end_53} :catch_25

    move-result-object v6

    goto :goto_8
.end method

.method public static main([Ljava/lang/String;)V
    .registers 6
    .param p0, "args"    # [Ljava/lang/String;

    .prologue
    .line 188
    const-string v0, ""

    .line 190
    .local v0, "alphabet":Ljava/lang/String;
    const/4 v1, 0x0

    .local v1, "i":I
    :goto_3
    const/16 v2, 0x40

    if-ge v1, v2, :cond_26

    .line 191
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    new-instance v3, Ljava/lang/String;

    add-int/lit16 v4, v1, 0x3ec

    invoke-static {v4}, Ljava/lang/Character;->toChars(I)[C

    move-result-object v4

    invoke-direct {v3, v4}, Ljava/lang/String;-><init>([C)V

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 190
    add-int/lit8 v1, v1, 0x1

    goto :goto_3

    .line 194
    :cond_26
    sget-object v2, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v3, "EpSE5uDgblbUVv9gb4WIqZFHiW"

    invoke-static {v3}, Lnet/dynamico/Crypt;->convertStringToByteArrayNotation(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 195
    sget-object v2, Ljava/lang/System;->out:Ljava/io/PrintStream;

    invoke-static {v0}, Lnet/dynamico/Crypt;->convertStringToByteArrayNotation(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 196
    return-void
.end method

.method private static setBase64Charset([B)V
    .registers 8
    .param p0, "base64CharsetBytes"    # [B

    .prologue
    const/16 v6, 0x40

    .line 166
    new-instance v1, Ljava/lang/String;

    invoke-direct {v1, p0}, Ljava/lang/String;-><init>([B)V

    .line 168
    .local v1, "base64Charset":Ljava/lang/String;
    sget-boolean v3, Lnet/dynamico/BuildConfig;->DEBUG:Z

    if-eqz v3, :cond_19

    .line 170
    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v3

    if-eq v3, v6, :cond_19

    .line 171
    new-instance v3, Ljava/lang/IllegalStateException;

    const-string v4, "The charset must be 64 characters long."

    invoke-direct {v3, v4}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v3

    .line 174
    :cond_19
    invoke-virtual {v1}, Ljava/lang/String;->toCharArray()[C

    move-result-object v0

    .line 175
    .local v0, "base64Chars":[C
    new-instance v3, Ljava/util/HashMap;

    invoke-direct {v3}, Ljava/util/HashMap;-><init>()V

    sput-object v3, Lnet/dynamico/Crypt;->charPositionMap:Ljava/util/HashMap;

    .line 177
    const/4 v2, 0x0

    .local v2, "i":I
    :goto_25
    array-length v3, v0

    if-ge v2, v3, :cond_3a

    .line 178
    sget-object v3, Lnet/dynamico/Crypt;->charPositionMap:Ljava/util/HashMap;

    aget-char v4, v0, v2

    invoke-static {v4}, Ljava/lang/Character;->valueOf(C)Ljava/lang/Character;

    move-result-object v4

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v3, v4, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 177
    add-int/lit8 v2, v2, 0x1

    goto :goto_25

    .line 180
    :cond_3a
    sget-boolean v3, Lnet/dynamico/BuildConfig;->DEBUG:Z

    if-eqz v3, :cond_4e

    .line 182
    sget-object v3, Lnet/dynamico/Crypt;->charPositionMap:Ljava/util/HashMap;

    invoke-virtual {v3}, Ljava/util/HashMap;->size()I

    move-result v3

    if-eq v3, v6, :cond_4e

    .line 183
    new-instance v3, Ljava/lang/IllegalStateException;

    const-string v4, "The charset must contain 64 unique characters."

    invoke-direct {v3, v4}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v3

    .line 185
    :cond_4e
    return-void
.end method

.method private static setEncryptionKey([B)V
    .registers 7
    .param p0, "encryptionKey"    # [B

    .prologue
    .line 147
    :try_start_0
    sget-object v1, Lnet/dynamico/Crypt;->secretKeySpecClass:[B

    invoke-static {v1}, Lnet/dynamico/Crypt;->createString([B)Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v1

    const/4 v2, 0x2

    new-array v2, v2, [Ljava/lang/Class;

    const/4 v3, 0x0

    const-class v4, [B

    aput-object v4, v2, v3

    const/4 v3, 0x1

    const-class v4, Ljava/lang/String;

    aput-object v4, v2, v3

    .line 148
    invoke-virtual {v1, v2}, Ljava/lang/Class;->getDeclaredConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;

    move-result-object v1

    const/4 v2, 0x2

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v5, Lnet/dynamico/Crypt;->encryptionKeyPrefix:[B

    .line 149
    invoke-static {v5}, Lnet/dynamico/Crypt;->createString([B)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-static {p0}, Lnet/dynamico/Crypt;->createString([B)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    sget-object v5, Lnet/dynamico/Crypt;->UTF_8:[B

    invoke-static {v5}, Lnet/dynamico/Crypt;->createString([B)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/String;->getBytes(Ljava/lang/String;)[B

    move-result-object v4

    aput-object v4, v2, v3

    const/4 v3, 0x1

    sget-object v4, Lnet/dynamico/Crypt;->AES:[B

    invoke-static {v4}, Lnet/dynamico/Crypt;->createString([B)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v2, v3

    invoke-virtual {v1, v2}, Ljava/lang/reflect/Constructor;->newInstance([Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    sput-object v1, Lnet/dynamico/Crypt;->encryptionKey:Ljava/lang/Object;
    :try_end_55
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_55} :catch_56

    .line 156
    :cond_55
    :goto_55
    return-void

    .line 151
    :catch_56
    move-exception v0

    .line 153
    .local v0, "e":Ljava/lang/Exception;
    sget-boolean v1, Lnet/dynamico/BuildConfig;->DEBUG:Z

    if-eqz v1, :cond_55

    .line 154
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_55
.end method
