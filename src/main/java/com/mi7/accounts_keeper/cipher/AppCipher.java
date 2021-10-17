
package com.mi7.accounts_keeper.cipher;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

// Класс содержит методы для кодирования и декодирования
public class AppCipher {
    
    private static AppCipher instance = null;
    private String key;
    
    private AppCipher(){
        
    }
    
    public static AppCipher getInstance() {
        if (instance == null) {
            instance = new AppCipher();
        }
        return instance;
    }
    
    public byte [] cryptBytes(int mode, byte[] value) throws Exception {
        
        byte[] seckey = this.key.getBytes();
        SecretKeySpec secretKey = new SecretKeySpec(seckey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        
        cipher.init(mode, secretKey);
        byte[] bytes = cipher.doFinal(value);
        return bytes;
    }
    
    
    public String crypt(int mode, String data, String secret) throws Exception {
        
        byte[] value = data.getBytes();
        byte[] key = secret.getBytes();
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        
        cipher.init(mode, secretKey);
        byte[] bytes = cipher.doFinal(value);
        String val = new String(bytes);
        return  val;
    }
    
    public String crypt(int mode, String data) throws Exception {
        return  crypt(mode, data, this.key);
    }

    public void setKey(String key) {
        this.key = key;
    }
    
}
