/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mi7.accounts_keeper.saving;

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

/**
 *
 * @author mi-7m
 */
public class SaveToDatabase implements SaveTo {

    @Override
    public void save() {
        JSONObject json = DataSet.getInstance().createJSON();
        try {
            byte[] b = AppCipher.getInstance().cryptBytes(Cipher.ENCRYPT_MODE, json.toString().getBytes("UTF-8"));
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_keeper","user","user");
            PreparedStatement statement = connection.prepareStatement("insert into maindata (id, acc_data) VALUES (1, ?)");
            Blob blob = new SerialBlob(b);
            statement.setBlob(1, blob);
            statement.execute();
            statement.close();
            connection.close();
            
            load();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
