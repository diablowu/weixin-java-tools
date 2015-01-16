package me.chanjar.weixin.cp.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpSpringServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpDepart;

public class TokenTestServlet extends HttpServlet {


    /**
     * 
     */
    private static final long serialVersionUID = -1483802105204593468L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        WxCpService wxCpService = new WxCpSpringServiceImpl();
        wxCpService.setWxCpConfig(null);
        
        try {
            resp.setContentType("text/plain;charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            PrintWriter p = resp.getWriter();
            List<WxCpDepart> l = wxCpService.departGet();
            for (WxCpDepart wxCpDepart : l) {
                p.write(wxCpDepart.getName());
                p.write("\n");
            }
            p.flush();
            p.close();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        super.doGet(req, resp);
    }

}
