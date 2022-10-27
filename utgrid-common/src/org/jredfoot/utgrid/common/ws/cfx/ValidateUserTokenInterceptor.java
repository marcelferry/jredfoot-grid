package org.jredfoot.utgrid.common.ws.cfx;

import java.util.Vector;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.ws.security.WSSecurityEngineResult;
import org.apache.ws.security.WSUsernameTokenPrincipal;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.handler.WSHandlerResult;

public class ValidateUserTokenInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	public ValidateUserTokenInterceptor(String s) {
		super(s);
	}

	public void handleMessage(SoapMessage message) throws Fault {
		boolean userTokenValidated = false;
		Vector result = (Vector) message
				.getContextualProperty(WSHandlerConstants.RECV_RESULTS);
		for (int i = 0; i < result.size(); i++) {
			WSHandlerResult res = (WSHandlerResult) result.get(i);
			for (int j = 0; j < res.getResults().size(); j++) {
				WSSecurityEngineResult secRes = (WSSecurityEngineResult) res
						.getResults().get(j);
				WSUsernameTokenPrincipal principal = (WSUsernameTokenPrincipal) secRes
						.get(WSSecurityEngineResult.TAG_PRINCIPAL);

//				if (!principal.isPasswordDigest()
//						|| principal.getNonce() == null
//						|| principal.getPassword() == null
//						|| principal.getCreatedTime() == null) {
//					throw new RuntimeException("Invalid Security Header");
//				} else {
					userTokenValidated = true;
//				}

			}
		}
		if (!userTokenValidated) {
			throw new RuntimeException("Security processing failed");
		}
	}
}
