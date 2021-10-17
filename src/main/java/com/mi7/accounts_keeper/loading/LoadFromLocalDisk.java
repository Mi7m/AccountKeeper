/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mi7.accounts_keeper.loading;

import com.mi7.accounts_keeper.AppConfig;
import com.mi7.accounts_keeper.DataSet;
import com.mi7.accounts_keeper.LogWriter;
import com.mi7.accounts_keeper.cipher.AppCipher;
import com.mi7.accounts_keeper.entity.DataRecord;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.crypto.Cipher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author mi-7m
 */
public class LoadFromLocalDisk implements LoadFrom {

    @Override
    public List<DataRecord> loadData() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        AppConfig appConfig = context.getBean("appConfig", AppConfig.class);
        String fileName = appConfig.getLocalFile();
        
        try {
            
            Path path = Paths.get(fileName);
            byte[] crypted = Files.readAllBytes(path);
            byte[] decrypted = AppCipher.getInstance().cryptBytes(Cipher.DECRYPT_MODE, crypted);
                
            return DataSet.parseData(new String(decrypted));
        }
        catch (Exception e) {
            LogWriter.getInstance().write("load from local disk", e.getMessage());
        }
        return null;
    }
    
}
