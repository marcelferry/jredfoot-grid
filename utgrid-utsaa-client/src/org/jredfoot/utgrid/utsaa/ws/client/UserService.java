package org.jredfoot.utgrid.utsaa.ws.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.jredfoot.utgrid.common.vo.User;
import org.jredfoot.utgrid.common.ws.cfx.ClientPasswordCallback;
import org.jredfoot.utgrid.utsaa.ws.IUserService;

@WebServiceClient(name="UserService", targetNamespace="http://ws.utsaa.utgrid.jredfoot.org", 
		wsdlLocation="http://localhost:8080/utgrid-utsaa/UserService?wsdl")
public class UserService extends Service {
	
	private final static URL WSDL_LOCATION;
    private final static QName GRIDNODE_IMPL_SERVICE = new QName("http://ws.utsaa.utgrid.jredfoot.org", "UserService");
    private final static QName GRIDNODE_IMPL_PORT = new QName("http://ws.utsaa.utgrid.jredfoot.org", "UserServicePort");

    static{
    	URL url = null;
        try {
            url = new URL("http://localhost:8080/utgrid-utsaa/UserService?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

	protected UserService(URL wsdlDocumentLocation, QName serviceName) {
		super(wsdlDocumentLocation, serviceName);
	}
	
	public UserService() {
		super(WSDL_LOCATION, GRIDNODE_IMPL_SERVICE);
	}
	
	/**
     * 
     * @return
     *     returns AddNumbersImpl
     */
    @WebEndpoint(name = "UserServicePort")
    public IUserService getUserServicePort() {
        return (IUserService)super.getPort(GRIDNODE_IMPL_PORT, IUserService.class);
    }
    
    public IUserService getGridNodeService(User usuario){
    	
    	IUserService service = getUserServicePort();
    	
    	// Seguranca
        Client client = ClientProxy.getClient(service);
        Endpoint cxfEndpoint = client.getEndpoint();
        
        Map<String,Object> outProps = new HashMap<String,Object>();

        outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        outProps.put(WSHandlerConstants.USER, "rafaelliu");
        outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);

        // for hashed password use:
        //properties.setProperty(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST); 
        //outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);

        // Callback used to retrive password for given user.
        outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientPasswordCallback.class.getName());
        
        WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
        cxfEndpoint.getOutInterceptors().add(wssOut);
        cxfEndpoint.getOutInterceptors().add(new SAAJOutInterceptor());
        
        return service;
    }
	
    public static void main(String[] args) {
    	System.out.println("inicio");
		UserService gns = new UserService();
		System.out.println("E ai?");
	//	gns.getUserServicePort().listGridNodes();
		System.out.println("fim");
	}
    
}
