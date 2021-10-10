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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.crypto.Cipher;
import javax.sound.midi.Patch;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Sava data to yandex disk
public class SaveToYandexDisk implements SaveTo {
    
    // Основной метод класса
    @Override
    public void save() {
       
        // Создаем json из коллекции
        JSONObject json = DataSet.getInstance().createJSON();
        // Создание файла
        File file = createFile(json);
        // Получение ссылки на загрузку
        String hRef = getUploadLink();
        // Загрузка файла
        if (file != null) uploadFile(file, hRef);
    }
    
    // Создание файла, запись в него данных, шифрование
    private File createFile(JSONObject json) {
        
        Path temp = null;
        try {
            // Create file with data
            temp = Files.createTempFile(null, null);
            File file = temp.toFile();
            file.deleteOnExit();
            
            try(FileOutputStream fos = new FileOutputStream(file)) {
                byte[] b = AppCipher.getInstance().cryptBytes(Cipher.ENCRYPT_MODE, json.toString().getBytes("UTF-8"));
                fos.write(b);
            }
            return file;
          }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Получение ссылки на загрузку файла
    private String getUploadLink() {
        String href = null;
        
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        AppConfig appConfig = context.getBean("appConfig", AppConfig.class);
        
        
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            
            URIBuilder uriBuilder = new URIBuilder(appConfig.getUploadUri());
            uriBuilder.setParameter("path", appConfig.getFileName());
            uriBuilder.setParameter("overwrite", "true");
            
            HttpGet request = new HttpGet(uriBuilder.build());
            request.addHeader("Authorization", appConfig.getToken());
            
            try (CloseableHttpResponse response = client.execute(request)) {
             
                HttpEntity entity = response.getEntity();
            
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    JSONObject json = new JSONObject(result);
                    href = json.getString("href");
                    System.out.println(href);
                }
            }    
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return href;
    }
    
    
    // Загрузка файла
    private void uploadFile(File file, String href) {
    
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut(href);
            HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addBinaryBody("file", file, ContentType.create("application/json"), file.getName())
                .build();

            System.out.println(httpPut.getRequestLine());
            httpPut.setEntity(httpEntity);
            try (CloseableHttpResponse response = client.execute(httpPut)) {
                System.out.println(response);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
