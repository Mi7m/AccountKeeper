
package com.mi7.accounts_keeper;

import com.mi7.accounts_keeper.cipher.AppCipher;
import com.mi7.accounts_keeper.entity.DataRecord;
import com.mi7.accounts_keeper.loading.LoadFrom;
import com.mi7.accounts_keeper.loading.Loader;
import com.mi7.accounts_keeper.saving.SaveMode;
import com.mi7.accounts_keeper.saving.SaveTo;
import com.mi7.accounts_keeper.saving.SaveType;
import com.mi7.accounts_keeper.saving.Saver;
import java.util.List;
import javax.crypto.Cipher;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    
    
    @RequestMapping("/")
    public String login(){
        return "login";
    }
    
    @RequestMapping("/loginOK")
    public String loginOK(@RequestParam("key") String key, @RequestParam("password") String password, Model model){
        
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        //key 2100 pass 0021
        String storedPass = ">яQ;«EБд·Eх	h¬mх:щ““\n" +
                                        "З¶ы•ЌЎлб";
        try {
            // Временное упрощение для простых 4-значных ключей и паролей ->
            password = password + password + password + password;
            key = key + key + key + key;
            
            //<----------------------
            AppCipher appCipher = AppCipher.getInstance();
            String encodedPassword = appCipher.crypt(Cipher.ENCRYPT_MODE, password, key);

            if (encodedPassword.equals(storedPass)) {
                // Login ok
                appCipher.setKey(password);
                return "redirect:/main";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("errorstatus", "incorrect key or password");
        return "login";
    }
   
    @RequestMapping("/main")
    public String showMainView(Model model) {
        
        DataSet dataSet = DataSet.getInstance();
        
        List<DataRecord> dataRecords = dataSet.getViewDataRecords();
        model.addAttribute("dataRecords", dataRecords);
        model.addAttribute("filename", dataSet.getStatus());
        model.addAttribute("saveMode", new SaveMode());
        
        return "main";
        
    }
    
    
    @RequestMapping("/addRecord")
    public String addData(Model model) {
        model.addAttribute("record", new DataRecord());
        return "edit-record";
    }
    
    @RequestMapping("/editRecord")
    public String editData(@RequestParam("id") long id, Model model) {
        DataRecord record = DataSet.getInstance().getDataRecordById(id);
        model.addAttribute("record", record);
        return "edit-record";
    }
    
    @RequestMapping("/editOK")
    public String saveData(@ModelAttribute("record") DataRecord editedRecord){
        
        DataSet.getInstance().updateData(editedRecord);
        DataSet.getInstance().clearSearch();
        return "redirect:/main";
    }
    
    @RequestMapping("/editCancel")
    public String cancelEdit(){
        return "redirect:/main";
    }
    
    @RequestMapping("/deleteRecord")
    public String editData(@RequestParam("id") long id) {
        DataSet.getInstance().deleteRecordById(id);
        return "redirect:/main";
    }
    
    @RequestMapping("/reloadData")
    public String reloadData() {
        DataSet.getInstance().reload();
        return "redirect:/main";
    }
    
    @RequestMapping("/search")
    public String searchData(@RequestParam("SearchText") String searchString) {
        DataSet.getInstance().createSearchList(searchString);
        return "redirect:/main";
    }
    
    
    @RequestMapping("/saveData")
    public String saveData(@ModelAttribute("saveMode") SaveMode saveMode) {
        
        Enum<SaveType> saveType = SaveType.valueOf(saveMode.getSaveModeName());
        try {
            SaveTo saveTo = Saver.getSaver(saveType);
            saveTo.save(DataSet.getInstance().getDataRecords());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/main";
    }
}
