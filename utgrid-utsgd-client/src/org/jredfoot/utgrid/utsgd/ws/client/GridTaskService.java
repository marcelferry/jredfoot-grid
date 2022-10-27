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
import org.jredfoot.utgrid.utsgd.ws.IGridTaskService;

@WebServiceClient(name="GridTaskService", targetNamespace="http://ws.utsgd.utgrid.jredfoot.org", 
		wsdlLocation="http://localhost:8080/utgrid-utsgd/GridTaskService?wsdl")
public class GridTaskService extends Service {
	
	private final static URL WSDL_LOCATION;
    private final static QName GRIDTASK_IMPL_SERVICE = new QName("http://ws.utsgd.utgrid.jredfoot.org", "GridTaskService");
    private final static QName GRIDTASK_IMPL_PORT = new QName("http://ws.utsgd.utgrid.jredfoot.org", "GridTaskServicePort");

    static{
    	URL url = null;
        try {
            url = new URL("http://localhost:8080/utgrid-utsgd/GridTaskService?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

	protected GridTaskService(URL wsdlDocumentLocation, QName serviceName) {
		super(wsdlDocumentLocation, serviceName);
	}
	
	public GridTaskService() {
		super(WSDL_LOCATION, GRIDTASK_IMPL_SERVICE);
	}
	
	/**
     * 
     * @return
     *     returns AddNumbersImpl
     */
    @WebEndpoint(name = "GridTaskServicePort")
    public IGridTaskService getGridTaskServicePort() {
        return (IGridTaskService)super.getPort(GRIDTASK_IMPL_PORT, IGridTaskService.class);
    }
    
    public IGridTaskService getGridNodeService(User usuario){
    	
    	IGridTaskService service = getGridTaskServicePort();
    	
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
		GridTaskService gns = new GridTaskService();
		System.out.println("E ai?");
		gns.getGridTaskServicePort().listGridTasks();
		System.out.println("fim");
	}
    
}
