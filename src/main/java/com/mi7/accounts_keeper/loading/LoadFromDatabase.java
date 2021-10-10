/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mi7.accounts_keeper.loading;

import com.mi7.accounts_keeper.cipher.AppCipher;
import com.mi7.accounts_keeper.entity.DataRecord;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.crypto.Cipher;

/**
 *
 * @author mi-7m
 */
public class LoadFromDatabase implements LoadFrom {

    @Override
    public List<DataRecord> loadData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void load() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_keeper","user","user");
            
            Statement sta = connection.createStatement();
            ResultSet result = sta.executeQuery("SELECT acc_data FROM maindata where id = 1");
            result.next();
            Blob blob = result.getBlob("acc_data");
            byte[] b = blob.getBytes(1, (int) blob.length());
            byte[] b1 = AppCipher.getInstance().cryptBytes(Cipher.DECRYPT_MODE, b);
            String s = new String(b1);
            System.out.println(b);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
