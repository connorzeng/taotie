package com.connor.taotie.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    public static void readLine(String path) throws IOException {

        String line = null;

        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }



}
