package org.jredfoot.utgrid.utsgd.ws.client;

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
import org.jredfoot.utgrid.utsgd.ws.IGridNodeService;

@WebServiceClient(name="GridNodeService", targetNamespace="http://ws.utsgd.utgrid.jredfoot.org", 
		wsdlLocation="http://localhost:8080/utgrid-utsgd/GridNodeService?wsdl")
public class GridNodeService extends Service {
	
	private final static URL WSDL_LOCATION;
    private final static QName GRIDNODE_IMPL_SERVICE = new QName("http://ws.utsgd.utgrid.jredfoot.org", "GridNodeService");
    private final static QName GRIDNODE_IMPL_PORT = new QName("http://ws.utsgd.utgrid.jredfoot.org", "GridNodeServicePort");

    static{
    	URL url = null;
        try {
            url = new URL("http://localhost:8080/utgrid-utsgd/GridNodeService?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

	protected GridNodeService(URL wsdlDocumentLocation, QName serviceName) {
		super(wsdlDocumentLocation, serviceName);
	}
	
	public GridNodeService() {
		super(WSDL_LOCATION, GRIDNODE_IMPL_SERVICE);
	}
	
	/**
     * 
     * @return
     *     returns AddNumbersImpl
     */
    @WebEndpoint(name = "GridNodeServicePort")
    public IGridNodeService getGridNodeServicePort() {
        return (IGridNodeService)super.getPort(GRIDNODE_IMPL_PORT, IGridNodeService.class);
    }
    
    public IGridNodeService getGridNodeService(User usuario){
    	
    	IGridNodeService service = getGridNodeServicePort();
    	
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
		GridNodeService gns = new GridNodeService();
		System.out.println("E ai?");
		gns.getGridNodeServicePort().listGridNodes();
		System.out.println("fim");
	}
    
}
