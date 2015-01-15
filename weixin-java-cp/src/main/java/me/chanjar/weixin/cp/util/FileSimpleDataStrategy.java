package me.chanjar.weixin.cp.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import me.chanjar.weixin.common.util.AccessTokenHolder;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.common.util.storage.SimpleDataStorage;
import me.chanjar.weixin.common.util.storage.SimpleDataStorage.Data;
import me.chanjar.weixin.common.util.storage.StorageStrategy;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpReloadMemConfigStorage;

public class FileSimpleDataStrategy implements StorageStrategy {
    
    private String filePath;
    
    public FileSimpleDataStrategy(String filePath){
        this.filePath = filePath;
    }
        

    @Override
    public SimpleDataStorage.Data exec(boolean force) {
        WxCpConfigStorage storage = WxCpReloadMemConfigStorage.get();
        
        long cts = System.currentTimeMillis();
        long ts = cts + 7200000;
        Data data = new Data();
        File tk = new File(this.filePath);
        if(force){
            try {
                data.value = writeAndUpdate(storage,ts,tk);
                data.expired = ts;
                return data;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        try{
            if (!tk.exists()) {
                tk.createNewFile();
            }
            FileInputStream fis = new FileInputStream(tk);
            List<String> lines = IOUtils.readLines(fis);
            fis.close();
            if (lines != null && !lines.isEmpty()
                    && !StringUtils.isEmpty(lines.get(0).trim())
                    && !StringUtils.isEmpty(lines.get(1).trim())) {
                String _t = lines.get(0).trim();
                Long expired = Long.parseLong(lines.get(1).trim());
                if (cts >= expired) {// 如果存储过期
                    data.value = writeAndUpdate(storage,ts,tk);
                    data.expired = ts;
                    System.out.println("获取新的value使用");
                } else {
                    System.out.println("从file中读取value使用");
                    // 直接使用
                    data.expired = ts;
                    data.value = _t;
                }
            } else {
                data.value = writeAndUpdate(storage,ts,tk);
                data.expired = ts;
                System.out.println("获取新的value使用");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    
    private String writeAndUpdate(final WxCpConfigStorage storage,long expired, File tk) throws IOException{
        String token = AccessTokenHolder.requestToken(
                storage.getCorpId(), storage.getCorpSecret(),
                AccessTokenHolder.TokenType.CP);
        // 更新存储文件
        List<String> _newlines = new ArrayList<String>(2);
        _newlines.add(token);
        _newlines.add(String.valueOf(expired));
        FileOutputStream fos = new FileOutputStream(tk);
        IOUtils.writeLines(_newlines, "\n", fos);
        fos.close();
        return token;
    }

}
