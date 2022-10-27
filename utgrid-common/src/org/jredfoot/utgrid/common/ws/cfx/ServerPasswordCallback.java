package org.jredfoot.utgrid.common.ws.cfx;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class ServerPasswordCallback implements CallbackHandler  {
 
   @Override
   public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
 
        String password = null;
        if (pc.getIdentifier().equals("rafaelliu")) {
           password = "abc123";
        }
        if (password == null || !password.equals(pc.getPassword())) {
           throw new IOException("wrong password");
        }
   }
 
}