/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mi7.accounts_keeper.loading;


import com.mi7.accounts_keeper.entity.DataRecord;
import java.util.List;

/**
 *
 * @author mi-7m
 */
public interface LoadFrom {
    
    public List<DataRecord> loadData();
    
}
