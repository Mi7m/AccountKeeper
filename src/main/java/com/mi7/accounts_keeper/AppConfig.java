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
    private String dbPassword;
    private String dbUser;
    private String dbConnect;
    private String dbDecodedPass;
    private String localFile;
    private String localDecryptedFile;
    private String logFile;

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }
    

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

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPass) {
        this.dbPassword = dbPass;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbConnect() {
        return dbConnect;
    }

    public void setDbConnect(String dbConnect) {
        this.dbConnect = dbConnect;
    }

    public String getDbDecodedPass() {
        return dbDecodedPass;
    }

    public void setDbDecodedPass(String dbDecodedPass) {
        this.dbDecodedPass = dbDecodedPass;
    }

    public String getLocalFile() {
        return localFile;
    }

    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }

    public String getLocalDecryptedFile() {
        return localDecryptedFile;
    }

    public void setLocalDecryptedFile(String localDecryptedFile) {
        this.localDecryptedFile = localDecryptedFile;
    }

    
}
