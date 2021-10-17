package com.mi7.accounts_keeper;

import com.mi7.accounts_keeper.cipher.AppCipher;
import com.mi7.accounts_keeper.entity.DataRecord;
import com.mi7.accounts_keeper.loading.LoadFrom;
import com.mi7.accounts_keeper.loading.LoadType;
import com.mi7.accounts_keeper.loading.Loader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
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
            
            Enum<LoadType> loadType = appConfig.getLoadType();
            if (loadType.equals(LoadType.YANDEX_DISK)) {
                status = appConfig.getFileName();
            }
            else if (loadType.equals(LoadType.LOCAL_DISK)) {
                status = appConfig.getLocalFile();
            }
            else if (loadType.equals(LoadType.DATABASE)) {
                status = appConfig.getDbConnect();
            }
        
            
            
        }
        catch (Exception e) {
            LogWriter.getInstance().write("Data processing", e.getMessage());
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
    
    
    // Создаем объект JSON из основных данных
    public JSONObject createJSON() {
        
        JSONObject json = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        
        for (DataRecord record : this.dataRecords) {
            JSONObject jso = new JSONObject();
            jso.put("id", record.getId());
            jso.put("name", record.getName());
            jso.put("username", record.getUsername());
            jso.put("password", record.getPassword());
            jso.put("comment", record.getComment());
            jso.put("www", record.getWww());
            jSONArray.put(jso);
        }
        
        json.put("Credentials", jSONArray);
        
        return json;
    }
    
    // Преобразовываем JSON в данные
    public static List<DataRecord> parseData(String data) {
        List<DataRecord> dataRecords = new ArrayList<>();
        
        try {
            JSONObject json = new JSONObject(data);
            JSONArray jSONArray = json.getJSONArray("Credentials");
            for (int i = 0; i < jSONArray.length(); i++) {

                JSONObject j = jSONArray.getJSONObject(i);
                DataRecord dataRecord = new DataRecord(
                          j.getLong("id")
                        ,j.getString("name")
                        ,j.getString("username")
                        ,j.getString("password")
                        ,j.getString("comment")
                        ,j.getString("www"));

                dataRecords.add(dataRecord);
            }
        }
        catch (Exception e) {
            LogWriter.getInstance().write("Data processing, parsing", e.getMessage());
        }
        return dataRecords;
    }
    
}
