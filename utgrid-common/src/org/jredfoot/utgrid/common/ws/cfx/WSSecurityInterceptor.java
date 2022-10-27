package org.jredfoot.utgrid.common.ws.cfx;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;

public class WSSecurityInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
 
    public WSSecurityInterceptor() {
        super(Phase.PRE_PROTOCOL);
    }
    public WSSecurityInterceptor(String s) {
        super(Phase.PRE_PROTOCOL);
    }

    public void handleMessage(SoapMessage message) throws Fault {
 
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        props.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
        props.put(WSHandlerConstants.PW_CALLBACK_REF, new ServerPasswordCallback());
 
        WSS4JInInterceptor wss4jInHandler = new WSS4JInInterceptor(props);
        ValidateUserTokenInterceptor userTokenInterceptor = new ValidateUserTokenInterceptor(Phase.POST_PROTOCOL);
 
        message.getInterceptorChain().add(wss4jInHandler);
        message.getInterceptorChain().add(new SAAJInInterceptor());
        message.getInterceptorChain().add(userTokenInterceptor);
    }
}
