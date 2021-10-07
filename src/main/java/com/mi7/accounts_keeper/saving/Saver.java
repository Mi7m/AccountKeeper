/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mi7.accounts_keeper.saving;

/**
 *
 * @author mi-7m
 */
public final class Saver {
    
    public static SaveTo getSaver(Enum<SaveType> saveType) throws Exception {
        
        if (saveType == SaveType.YANDEX_DISK) {
            return new SaveToYandexDisk();
        }
        else if (saveType == SaveType.DATABASE) {
            return new SaveToDatabase();
        }
        else if (saveType == SaveType.LOCAL_DISK) {
            return new SaveToLocalDisk();
        }
        else if (saveType == SaveType.UNCODED_FILE_ON_LOCAL_DISK) {
            return new SaveToLocalDisk(true);
        }
        else {
            throw new Exception();
        }
        
    }
    
}
