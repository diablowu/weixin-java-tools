package me.chanjar.weixin.common.api;

/**
 * 接收到的消息的事件类型
 * @author Diablo Wu
 * @Date 2015年1月19日 下午5:29:56
 */
public enum InEventType {
    subscribe, 
    unsubscribe, 
    SCAN, 
    LOCATION, 
    CLICK, 
    VIEW, 
    MASSSENDJOBFINISH, 
    scancode_push, 
    scancode_waitmsg, 
    pic_sysphoto, 
    pic_photo_or_album, 
    pic_weixin, 
    location_select, 
    TEMPLATESENDJOBFINISH
}
