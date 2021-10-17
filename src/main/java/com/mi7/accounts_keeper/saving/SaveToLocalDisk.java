/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mi7.accounts_keeper.saving;

import com.mi7.accounts_keeper.AppConfig;
import com.mi7.accounts_keeper.DataSet;
import com.mi7.accounts_keeper.LogWriter;
import com.mi7.accounts_keeper.cipher.AppCipher;
import java.io.FileOutputStream;
import javax.crypto.Cipher;
import org.json.JSONObject;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author mi-7m
 */
public class SaveToLocalDisk implements SaveTo {
    
    private boolean decoded;
    private AppConfig appConfig;

    // Default conctructor for coded data
    public SaveToLocalDisk() {
        this(false);
    }

    public SaveToLocalDisk(boolean decoded) {
        this.decoded = decoded;
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        this.appConfig = context.getBean("appConfig", AppConfig.class);
    }
    
    
    @Override
    public void save() {
        JSONObject json = DataSet.getInstance().createJSON();
        byte[] b = null;
        String fileName = null;
        
        try {
        
            if (decoded) {
                b = json.toString().getBytes("UTF-8");
                fileName = appConfig.getLocalDecryptedFile();
                
            }
            else {
                b = AppCipher.getInstance().cryptBytes(Cipher.ENCRYPT_MODE, json.toString().getBytes("UTF-8"));
                fileName = appConfig.getLocalFile();
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                    fileOutputStream.write(b);
            }
        }
        catch (Exception e) {
            LogWriter.getInstance().write("Save to local disk", e.getMessage());
        }
    }
}
