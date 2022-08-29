/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/


package com.alloc64.dsobfuscator.proguard;

import com.alloc64.apktools.res.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ResourceAware
{
    protected static InputStream getResource(String resourceName) throws Exception
    {
        ClassPathResource resource = new ClassPathResource(resourceName);

        if (!resource.exists())
            throw new FileNotFoundException("Resource file does not exists:" + resourceName);

        return resource.getInputStream();
    }
}
