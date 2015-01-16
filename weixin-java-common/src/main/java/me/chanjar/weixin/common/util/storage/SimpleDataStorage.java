package me.chanjar.weixin.common.util.storage;

/**
 * 用于存储一些简单的数据的方式，这里用于存储accesstoken和jsapiticket
 * 
 * @author Diablo Wu
 * @date 下午9:52:00
 */
public class SimpleDataStorage {

    private Data data = null;
    
    private StorageStrategy strategy;
    
    public SimpleDataStorage(StorageStrategy strategy){
        this.strategy = strategy;
    }
    
    public String getData(){
        return data.value;
    }
    
    public long getExpired(){
        return data.expired;
    }
    
    public void loadData(boolean force){
        data = strategy.exec(force);
    };
    
    public static class Data {
        public String value;
        public long expired;
    }
}
