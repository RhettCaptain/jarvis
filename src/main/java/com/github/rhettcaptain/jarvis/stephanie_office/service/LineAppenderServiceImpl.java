package com.github.rhettcaptain.jarvis.stephanie_office.service;

import com.github.rhettcaptain.jarvis.stephanie_office.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

@Slf4j
public class LineAppenderServiceImpl {
    public static void main(String args[]){
        String inFile = "/home/rhett/Downloads/crm1128";
        String outFile = "/home/rhett/Downloads/crm1128(;)";
        String element = ";;";
        LineAppenderServiceImpl service = new LineAppenderServiceImpl();
        for(int i=1;i<=5;i++){
            service.appendElement(inFile+"/Group "+i+".csv",outFile+"/Group "+i+".csv",element);
        }
        System.out.println("---Finished---");
    }

    /**
     *
     * @param inPath
     * @param outPath
     * @param element
     */
    public void appendElement(String inPath, String outPath, String element){
        BufferedReader br = FileUtil.getReader(inPath);
        BufferedWriter bw = FileUtil.getWriter(outPath);
        br.lines().forEach(line -> {
            try{
                bw.write(line);
                bw.write(element);
                bw.write(System.lineSeparator());
            } catch (IOException e){
                log.error("error when append", e);
            }
        });
        try {
            br.close();
            bw.close();
        } catch (IOException e) {
            log.error("error when close", e);
        }
    }
}
