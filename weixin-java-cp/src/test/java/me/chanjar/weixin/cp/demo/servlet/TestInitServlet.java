package me.chanjar.weixin.cp.demo.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.util.AccessTokenHolder;
import me.chanjar.weixin.common.util.AccessTokenHolder.TokenType;
import me.chanjar.weixin.common.util.storage.SimpleDataStorage;
import me.chanjar.weixin.common.util.storage.StorageStrategy;
import me.chanjar.weixin.cp.api.WxCpConfig;
import me.chanjar.weixin.cp.util.FileSimpleDataStrategy;

public class TestInitServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 8026939601123557587L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        WxCpConfig config = new WxCpConfig("mp-config.xml");
        String filePath = System.getProperty("user.home")+ "/.access_token";
        StorageStrategy ss = new FileSimpleDataStrategy(filePath,config);
        SimpleDataStorage sds = new SimpleDataStorage(ss);
        sds.loadData(true);
        AccessTokenHolder.load(sds.getData(), 7200,TokenType.CP);
        super.doGet(req, resp);
    }

    @Override
    public void init(ServletConfig c) throws ServletException {
        WxCpConfig config = new WxCpConfig("mp-config.xml");
        String filePath = System.getProperty("user.home")+ "/.access_token";
        StorageStrategy ss = new FileSimpleDataStrategy(filePath,config);
        SimpleDataStorage sds = new SimpleDataStorage(ss);
        sds.loadData(false);
        AccessTokenHolder.load(sds.getData(), 7200,TokenType.CP);
        super.init(c);
    }
}
