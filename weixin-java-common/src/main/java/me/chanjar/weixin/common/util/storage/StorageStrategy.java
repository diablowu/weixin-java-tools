package me.chanjar.weixin.common.util.storage;

public interface StorageStrategy {

    public SimpleDataStorage.Data exec(boolean force);
}
