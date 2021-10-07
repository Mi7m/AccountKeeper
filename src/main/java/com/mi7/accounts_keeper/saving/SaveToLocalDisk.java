/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mi7.accounts_keeper.saving;

import com.mi7.accounts_keeper.entity.DataRecord;
import java.util.List;

/**
 *
 * @author mi-7m
 */
public class SaveToLocalDisk implements SaveTo {
    
    private boolean uncoded;

    // Default conctructor for coded data
    public SaveToLocalDisk() {
        this(false);
    }

    public SaveToLocalDisk(boolean uncoded) {
        this.uncoded = uncoded;
    }
    
    
    
    @Override
    public void save(List<DataRecord> dataRecords) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
