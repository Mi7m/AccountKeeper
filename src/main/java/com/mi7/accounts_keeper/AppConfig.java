/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mi7.accounts_keeper;

import com.mi7.accounts_keeper.loading.LoadType;
import org.springframework.context.annotation.Scope;

public class AppConfig {
    
    private String token;
    private Enum<LoadType> loadType;
    private String stringLoadType;
    private String fileName;
    private String downloadUri;
    private String uploadUri;
    private String password;
    

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Enum<LoadType> getLoadType() {
        return LoadType.valueOf(stringLoadType);
    }

    public String getStringLoadType() {
        return stringLoadType;
    }

    public void setStringLoadType(String stringLoadType) {
        this.stringLoadType = stringLoadType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    public String getUploadUri() {
        return uploadUri;
    }

    public void setUploadUri(String uploadUri) {
        this.uploadUri = uploadUri;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

        
    
   
}
