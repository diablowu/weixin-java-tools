package me.chanjar.weixin.common.message;


public interface WxMessageDispatcher<In,Out> {

    
    public Out process(final In message);
}
