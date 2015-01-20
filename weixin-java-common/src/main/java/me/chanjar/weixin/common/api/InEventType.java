package me.chanjar.weixin.common.api;

/**
 * 接收到的消息的事件类型
 * @author Diablo Wu
 * @Date 2015年1月19日 下午5:29:56
 */
public enum InEventType {
    
    /** 订阅 */
    subscribe,
    
    /** 取消订阅 */
    unsubscribe,
    
    /** 扫描 */
    SCAN, 
    
    /** 上报位置 */
    LOCATION,
    
    /** 菜单点击 */
    CLICK, 
    
    /** 菜单页面链接 */
    VIEW, 
    
    /** 群发消息完成 */
    MASSSENDJOBFINISH,
    
    /** 二维码扫描 */
    scancode_push,
    
    /** 二维码扫描等待 */
    scancode_waitmsg, 
    
    /**弹出系统拍照发图 */
    pic_sysphoto, 
    
    /** 弹出拍照或者相册发图 */
    pic_photo_or_album, 
    
    /** 弹出微信相册发图器 */
    pic_weixin, 
    
    /** 地理位置选择器*/
    location_select,
    
    /** 模板消息发送完成 */
    TEMPLATESENDJOBFINISH,
    
    /** 用户进入应用*/
    enter_agent
}
