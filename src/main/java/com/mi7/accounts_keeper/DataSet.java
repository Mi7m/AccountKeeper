package com.mi7.accounts_keeper;

import com.mi7.accounts_keeper.cipher.AppCipher;
import com.mi7.accounts_keeper.entity.DataRecord;
import com.mi7.accounts_keeper.loading.LoadFrom;
import com.mi7.accounts_keeper.loading.Loader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Синглтон. Содержит коллекции с данными, доступные в любом месте программы
// и сервисные методы для оперативных манипуляций с данными
public class DataSet {
    
    private static DataSet instance = null;
    private List<DataRecord> dataRecords;
    private List<DataRecord> viewDataRecords;
    private String status;
    
    
    public static DataSet getInstance() {
        if ( instance == null) {
            instance = new DataSet();
        }
        return instance;
    }
    
    private DataSet(){
        reload();
    }
    
    public void reload() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        AppConfig appConfig = context.getBean("appConfig", AppConfig.class);
        
        try {
            LoadFrom loadFrom = Loader.getLoader(appConfig.getLoadType());
            dataRecords = loadFrom.loadData();
            viewDataRecords = dataRecords;
            status = appConfig.getFileName();
        }
        catch (Exception e) {
            status = e.getMessage();
        }
    }
    

    public List<DataRecord> getDataRecords() {
        return dataRecords;
    }

    public List<DataRecord> getViewDataRecords() {
        return viewDataRecords;
    }

    public String getStatus() {
        return status;
    }

    public void setViewDataRecords(List<DataRecord> viewDataRecords) {
        this.viewDataRecords = viewDataRecords;
    }
    
    public DataRecord getDataRecordById(long id) {
        DataRecord record = null;
        
        for (DataRecord rec : dataRecords) {
            if (rec.getId() == id) {
                record = rec;
                break;
            }
        }
        return record;
    }
    
    // Update and Insert
    public void updateData(DataRecord newDataRecord) {
        
        DataRecord dataRecord = getDataRecordById(newDataRecord.getId());
        
        
        if (dataRecord == null) {
            dataRecord = new DataRecord();
            dataRecord.setId(getNextId());
            dataRecords.add(dataRecord);
        }
        
        dataRecord.setName(newDataRecord.getName());
        dataRecord.setUsername(newDataRecord.getUsername());
        dataRecord.setPassword(newDataRecord.getPassword());
        dataRecord.setComment(newDataRecord.getComment());
        dataRecord.setWww(newDataRecord.getWww());
        
    }
    
    public long getNextId() {
        long maxid = 0;
        for (DataRecord rec : dataRecords) {
            if (rec.getId() > maxid) maxid = rec.getId();
        }
        return ++maxid;
    }
    
    public void deleteRecordById(long id) {
        dataRecords.remove(getDataRecordById(id));
    }
    
    public void createSearchList(String searchString) {
        if (searchString == "" || searchString == null) {
            viewDataRecords = dataRecords;
        }
        else {
            viewDataRecords = new ArrayList<DataRecord>();
            for (DataRecord record : dataRecords) {
                String name = record.getName().toUpperCase();
                if (name.contains(searchString.toUpperCase())) {
                    viewDataRecords.add(record);
                }
            }
        }
    }
    
    public void clearSearch() {
        viewDataRecords = dataRecords;
    }
    
    
}
