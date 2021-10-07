/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mi7.accounts_keeper;

import com.mi7.accounts_keeper.loading.LoadType;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

// Синглтон, содержит параметры приложения, полученные из properties
public final class ProjectConfig {
    private static ProjectConfig instance;
    private String token;
    private Enum<LoadType> loadType;
    private String fileName;
    private String downloadUri;
    private String uploadUri;
    
    private ProjectConfig() {
        
       try (InputStream inputStream = new FileInputStream("application.properties"))  {
            
            Properties properties = new Properties();
            properties.load(inputStream);
            token = properties.getProperty("token");
            fileName = properties.getProperty("file");
            downloadUri = properties.getProperty("get");
            uploadUri = properties.getProperty("set");
            loadType = LoadType.valueOf(properties.getProperty("loadtype"));
            
        }
        catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public static ProjectConfig getInstance() {
        if (instance == null) instance = new ProjectConfig();
        return instance;
    }

    public String getToken() {
        return token;
    }

    public Enum<LoadType> getLoadType() {
        return loadType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public String getUploadUri() {
        return uploadUri;
    }
    
    
}
