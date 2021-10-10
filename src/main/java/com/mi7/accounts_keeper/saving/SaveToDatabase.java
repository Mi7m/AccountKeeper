/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mi7.accounts_keeper.saving;

import com.mi7.accounts_keeper.AppConfig;
import com.mi7.accounts_keeper.DataSet;
import com.mi7.accounts_keeper.cipher.AppCipher;
import com.mi7.accounts_keeper.entity.DataRecord;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.crypto.Cipher;
import javax.sql.rowset.serial.SerialBlob;
import org.json.JSONObject;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author mi-7m
 */
public class SaveToDatabase implements SaveTo {

    @Override
    public void save() {
        JSONObject json = DataSet.getInstance().createJSON();
        
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        AppConfig appConfig = context.getBean("appConfig", AppConfig.class);
        
        String dbConnect = appConfig.getDbConnect();
        String dbUser = appConfig.getDbUser();
        
        try {
            
            String dbPassword = AppCipher.getInstance().crypt(Cipher.DECRYPT_MODE, appConfig.getDbPassword());
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
            
            try ( Connection connection = DriverManager.getConnection(
                        dbConnect
                        ,dbUser
                        ,dbPassword);
                  PreparedStatement statement = connection.prepareStatement("insert into maindata (id, acc_data) VALUES (1, ?)");
                  Statement deleteStatement = connection.createStatement();){
                
                
                deleteStatement.execute("delete from maindata");
                
                // encrypt data
                byte[] b = AppCipher.getInstance().cryptBytes(Cipher.ENCRYPT_MODE, json.toString().getBytes("UTF-8"));
                
//                DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
                Blob blob = new SerialBlob(b);
                statement.setBlob(1, blob);
                statement.execute();
            }    
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    
}
