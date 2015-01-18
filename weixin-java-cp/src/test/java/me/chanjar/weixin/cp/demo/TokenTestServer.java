package me.chanjar.weixin.cp.demo;

import me.chanjar.weixin.cp.demo.servlet.TestInitServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class TokenTestServer {

    public static void main(String[] args) throws Exception {


        
        Server server = new Server(8000);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        ServletHandler sh = new ServletHandler();
        sh.addServletWithMapping(new ServletHolder(new TestInitServlet()), "/ff");
        
        server.start();
        server.join();
    }
}
