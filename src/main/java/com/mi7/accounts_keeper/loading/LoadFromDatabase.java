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
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.crypto.Cipher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author mi-7m
 */
public class LoadFromDatabase implements LoadFrom {

    @Override
    public List<DataRecord> loadData() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
            
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            AppConfig appConfig = context.getBean("appConfig", AppConfig.class);
            String dbConnect = appConfig.getDbConnect();
            String dbUser = appConfig.getDbUser();
            String dbPassword = AppCipher.getInstance().crypt(Cipher.DECRYPT_MODE, appConfig.getDbPassword());
            
            try (Connection connection = DriverManager.getConnection(dbConnect
                                                                    ,dbUser
                                                                    ,dbPassword);
                 Statement statement = connection.createStatement();) {
            
                
                ResultSet result = statement.executeQuery("SELECT acc_data FROM maindata where id = 1");
                result.next();
                Blob blob = result.getBlob("acc_data");
                byte[] crypted = blob.getBytes(1, (int) blob.length());
                byte[] decrypted = AppCipher.getInstance().cryptBytes(Cipher.DECRYPT_MODE, crypted);
                
                return DataSet.parseData(new String(decrypted));
                
            
            }
        }
        catch (Exception e) {
            LogWriter.getInstance().write("db loader", e.getMessage());
        }
        return null;
    }
    
    
}
