/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mi7.accounts_keeper.loading;

/**
 В зависимости от настроек системы возвращает класс-загрузчик, который будет поставлять данные
 */
public final class Loader {
    
    public static LoadFrom getLoader(Enum<LoadType> loadType) throws Exception {
        
        if (loadType == LoadType.DATABASE) return new LoadFromDatabase();
        else if (loadType == LoadType.LOCAL_DISK) return new LoadFromLocalDisk();
        else if (loadType == LoadType.YANDEX_DISK) return new LoadFromYandexDisk();
        else throw new Exception();
        
    }
    
}
