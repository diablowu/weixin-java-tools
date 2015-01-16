package me.chanjar.weixin.cp.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.chanjar.weixin.common.util.AccessTokenHolder;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.common.util.storage.SimpleDataStorage;
import me.chanjar.weixin.common.util.storage.SimpleDataStorage.Data;
import me.chanjar.weixin.common.util.storage.StorageStrategy;
import me.chanjar.weixin.cp.api.WxCpConfig;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于文件的存储设备，第一行为数据，第二行为失效时间戳
 * 
 * @author Diablo Wu
 * @date 下午9:50:38
 */
public class FileSimpleDataStrategy implements StorageStrategy {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSimpleDataStrategy.class);
    
    private String filePath;
    private WxCpConfig config;
    
    public FileSimpleDataStrategy(String filePath, WxCpConfig config){
        this.filePath = filePath;
        this.config = config;
    }
        

    @Override
    public SimpleDataStorage.Data exec(boolean force) {
        long current = System.currentTimeMillis();
        long newExpiredTs = current + 7200000;
        Data data = new Data();
        File storageFile = new File(this.filePath);
        if(force){//十分忽略过期策略直接刷新
            try {
                
                data.value = writeAndUpdate(newExpiredTs,storageFile);
                data.expired = newExpiredTs;
                LOGGER.info("本地文件存储[{}]强制更新成功", this.filePath);
                return data;
            } catch (IOException e) {
                LOGGER.error("更新本地存储文件[{}]时出错",this.filePath,e);
                return null;
            }
        }
        
        try{
            if (!storageFile.exists()) {
                storageFile.createNewFile();
            }
            FileInputStream fis = new FileInputStream(storageFile);
            List<String> lines = IOUtils.readLines(fis);
            fis.close();
            
            if (lines != null && !lines.isEmpty()
                    && !StringUtils.isEmpty(lines.get(0).trim())
                    && !StringUtils.isEmpty(lines.get(1).trim())) {
                
                String _token = lines.get(0).trim();
                long _expired = Long.parseLong(lines.get(1).trim());
                
                if (current >= _expired) {// 如果存储过期
                    data.value = writeAndUpdate(newExpiredTs, storageFile);
                    data.expired = newExpiredTs;
                    LOGGER.info("当前时间超过本地文件存储[{}]中过期时间，直接更新成功", this.filePath);
                } else {
                    // 直接使用
                    data.expired = _expired;
                    data.value = _token;
                    LOGGER.info("[{}]本地文件存储读取成功", this.filePath);
                }
            } else {
                data.value = writeAndUpdate(newExpiredTs, storageFile);
                data.expired = newExpiredTs;
                LOGGER.info("[{}]本地文件存储读取失败，直接更新成功", this.filePath);
            }
        }catch(IOException e){
            LOGGER.error("[{}]本地文件存储写入时出错",this.filePath,e);
        }
        return data;
    }
    
    /**
     * 请求更新数据并返回
     * @param expired 新的过期时间戳
     * @param storageFile 存储文件
     * @return
     * @throws IOException
     */
    private String writeAndUpdate(long expired, File storageFile) throws IOException{
        String token = AccessTokenHolder.requestToken(
                config.getCorpId(), config.getCorpSecret(),
                AccessTokenHolder.TokenType.CP);
        // 更新存储文件
        List<String> _newlines = new ArrayList<String>(2);
        _newlines.add(token);
        _newlines.add(String.valueOf(expired));
        FileOutputStream fos = new FileOutputStream(storageFile);
        IOUtils.writeLines(_newlines, "\n", fos);
        fos.close();
        return token;
    }

}
