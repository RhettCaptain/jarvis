package com.github.rhettcaptain.jarvis.stephanie_office.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class FileUtil {
    public static BufferedReader getReader(String path){
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(new File(path)));
        } catch (IOException e){
            log.error("Fail to get reader", e);
        }
        return br;
    }

    public static BufferedWriter getWriter(String path){
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(new File(path)));
        } catch (IOException e){
            log.error("Fail to get writer", e);
        }
        return bw;
    }

    public static void writeFile(String path, String cont){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
            bw.write(cont);
            bw.close();
        } catch (IOException e){
            log.error("Fail to write", e);
        }
    }
}
