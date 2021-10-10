/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mi7.accounts_keeper.loading;

import com.mi7.accounts_keeper.AppConfig;
import com.mi7.accounts_keeper.DataSet;
import com.mi7.accounts_keeper.cipher.AppCipher;
import com.mi7.accounts_keeper.entity.DataRecord;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class LoadFromYandexDisk implements LoadFrom {

    // Основной метод. 
    @Override
    public List<DataRecord> loadData() {
        
        // Получение ссылки с yandexdisk
        String hRef = getDownloadLink();
        // Скачивание и дешифровывание файла
        String jsonData = downloadData(hRef);
        // Преобразование в коллекцию
        return DataSet.parseData(jsonData);
//        return parseData(jsonData);
        
    }
    
    // Получение ссылки с yandexdisk
    private String getDownloadLink() {
        
        String hRef = null;
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        AppConfig appConfig = context.getBean("appConfig", AppConfig.class);
        
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            
            URIBuilder uriBuilder = new URIBuilder(appConfig.getDownloadUri());
            uriBuilder.setParameter("path", appConfig.getFileName());
            HttpGet request = new HttpGet(uriBuilder.build());
            request.addHeader("Authorization", appConfig.getToken());
            
            try (CloseableHttpResponse response = client.execute(request)) {
                // getting answer from yandex
                HttpEntity entity = response.getEntity();
                
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    hRef = parseResult(result);
            }
                
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return hRef;
    }
    
    // Парсим REST ответ, получаем ссылку
    private String parseResult(String result) {
        JSONObject json = new JSONObject(result);
        return json.getString("href");
    }
    

    // Скачиваем файл в строку, дешифруем
    private String downloadData(String hRef) {
        String data = null;
        try (CloseableHttpClient client = HttpClients.createDefault()){
            
            HttpGet request = new HttpGet(hRef);
            try(CloseableHttpResponse response = client.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    
                    try(InputStream inputStream = entity.getContent()) {
                        
                        int l = (int) entity.getContentLength();
                        
                        byte[] b = new byte[l];
                        inputStream.read(b);
                        
                        byte[] b1 = AppCipher.getInstance().cryptBytes(Cipher.DECRYPT_MODE, b);
                        return new String(b1);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
}
