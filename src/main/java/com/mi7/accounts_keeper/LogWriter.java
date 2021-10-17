/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mi7.accounts_keeper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author mi-7m
 */
public class LogWriter {
    
    private static LogWriter instance;
    private String logFile;

    private LogWriter() {
        
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        AppConfig appConfig = context.getBean("appConfig", AppConfig.class);
        logFile = appConfig.getLogFile();
    }
    
    public static LogWriter getInstance() {
        if (instance == null) {
            instance = new LogWriter();
        }
        return instance;
    }
    
    public void write(String block, String err) {
        
        try (
                FileWriter fileWriter = new FileWriter(logFile, true); ) {
//                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(logFile, true));) {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss").format(Calendar.getInstance().getTime());
            String log = block + ";" + timeStamp + ";" + err + "\r\n";
            fileWriter.write(log);
        }
        catch (Exception e) {
            System.exit(0);
        }
    }
    
}
