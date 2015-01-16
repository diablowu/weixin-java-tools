package me.chanjar.weixin.common.util.storage;

/**
 * 存储方式
 * 
 * @author Diablo Wu
 * @date 下午9:53:52
 */
public interface StorageStrategy {

    /**
     * 自定义数据加载方式
     * @param force 是否忽略存储的过期策略直接更新
     * @return
     */
    public SimpleDataStorage.Data exec(boolean force);
}
