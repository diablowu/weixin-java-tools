package me.chanjar.weixin.cp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpReloadMemConfigStorage;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;

public class AccessTokenTestHolder {
    
    private static final WxAccessToken _TOKEN = new WxAccessToken();
    
    static{
        try{
            
          WxCpConfigStorage storage = WxCpReloadMemConfigStorage.get();
          WxCpService wxCpService = new WxCpServiceImpl();
          wxCpService.setWxCpConfigStorage(storage);

            
            File tk = new File(System.getProperty("user.home") + "/.access_token");
            if(!tk.exists()){
                tk.createNewFile();
            }
            FileInputStream fis = new FileInputStream(tk);
            List<String> lines = IOUtils.readLines(fis);
            fis.close();
            long ts = System.currentTimeMillis();
            if(lines!=null && !lines.isEmpty() && 
                    !StringUtils.isEmpty(lines.get(0).trim()) && 
                    !StringUtils.isEmpty(lines.get(1).trim())){
                        String _t = lines.get(0).trim();
                        Long expired = Long.parseLong(lines.get(1).trim());
                        if(ts>= expired){
                            wxCpService.accessTokenRefresh();
                            _TOKEN.setAccessToken(storage.getAccessToken());
                            _TOKEN.setExpiresIn(storage.getExpiresIn());
                            List<String> _newlines = new ArrayList<String>(2);
                            _newlines.add(storage.getAccessToken());
                            _newlines.add(String.valueOf(ts + storage.getExpiresIn() * 1000));
                            FileOutputStream fos = new FileOutputStream(tk);
                            IOUtils.writeLines(_newlines, "\n", fos);
                            fos.close();
                            System.out.println("refresh access token");
                        }else{
                            _TOKEN.setAccessToken(_t);
                            _TOKEN.setExpiresIn(7200);
                        }
                    }else{
                        wxCpService.accessTokenRefresh();
                        _TOKEN.setAccessToken(storage.getAccessToken());
                        _TOKEN.setExpiresIn(storage.getExpiresIn());
                        List<String> _newlines = new ArrayList<String>(2);
                        _newlines.add(storage.getAccessToken());
                        _newlines.add(String.valueOf(ts + storage.getExpiresIn() * 1000));
                        FileOutputStream fos = new FileOutputStream(tk);
                        IOUtils.writeLines(_newlines, "\n", fos);
                        fos.close();
                        System.out.println("refresh access token");
                    }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public static WxAccessToken getToken(){
        return _TOKEN;
    }
    
    
    public static void main(String[] args) {
        WxAccessToken token = AccessTokenTestHolder.getToken();
        
        System.out.println(token.getAccessToken());
    }
    
    

}
