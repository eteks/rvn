/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 *
 * @author ETS-4
 */
public class FilePath {
    
    public static File getPath() throws UnsupportedEncodingException {
        String path = FilePath.class.getClassLoader().getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        String pathArr[] = fullPath.split("classes/");
        fullPath = pathArr[0];
        return new File(fullPath).getParentFile();
    }
}
