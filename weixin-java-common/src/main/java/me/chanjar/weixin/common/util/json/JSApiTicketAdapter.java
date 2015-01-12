package me.chanjar.weixin.common.util.json;

import java.lang.reflect.Type;

import me.chanjar.weixin.common.bean.JSApiTicket;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


/**
 * 
 * @author Diablo Wu 
 * 
 */
public class JSApiTicketAdapter implements JsonDeserializer<JSApiTicket> {

    /* (non-Javadoc)
     * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
     */
    @Override
    public JSApiTicket deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JSApiTicket ticket = new JSApiTicket();
        
        JsonObject accessTokenJsonObject = json.getAsJsonObject();
        if (accessTokenJsonObject.get("ticket") != null && !accessTokenJsonObject.get("ticket").isJsonNull()) {
            ticket.setTickct(GsonHelper.getAsString(accessTokenJsonObject.get("ticket")));
        }
        if (accessTokenJsonObject.get("expires_in") != null && !accessTokenJsonObject.get("expires_in").isJsonNull()) {
            ticket.setExpiresIn(GsonHelper.getAsPrimitiveInt(accessTokenJsonObject.get("expires_in")));
        }        
        if (accessTokenJsonObject.get("errcode") != null && !accessTokenJsonObject.get("errcode").isJsonNull()) {
            ticket.setErrCode(GsonHelper.getAsString(accessTokenJsonObject.get("errcode")));
        }        
        if (accessTokenJsonObject.get("errmsg") != null && !accessTokenJsonObject.get("errmsg").isJsonNull()) {
            ticket.setErrMsg(GsonHelper.getAsString(accessTokenJsonObject.get("errmsg")));
        }        
        
        return ticket;
    }

}
