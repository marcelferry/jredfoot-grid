package org.jredfoot.utgrid.portal.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.jredfoot.utgrid.common.vo.User;

public class Utils {
	
	private static final Logger log = Logger.getLogger(Utils.class.getName());		
	
	public static ResourceBundle bundle;

	public static ResourceBundle getBundle() {
		try {
			if (bundle == null) {
				Locale locale = FacesContext.getCurrentInstance().getViewRoot()
						.getLocale();
				bundle = ResourceBundle.getBundle("resources/Bundle", locale);
			}
			return bundle;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		}
	}

	public static void copyProperties(Object dest, Object orig) {
    
		try{
            // Validate existence of the specified beans
            if (dest == null) {
                throw new IllegalArgumentException
                        ("No destination bean specified");
            }
            if (orig == null) {
                throw new IllegalArgumentException("No origin bean specified");
            }
    
            // Copy the properties, converting as necessary
            if (orig instanceof DynaBean) {
                DynaProperty[] origDescriptors =
                    ((DynaBean) orig).getDynaClass().getDynaProperties();
                for (int i = 0; i < origDescriptors.length; i++) {
                    String name = origDescriptors[i].getName();
                    // Need to check isReadable() for WrapDynaBean
                    // (see Jira issue# BEANUTILS-61)
                    if (BeanUtilsBean.getInstance().getPropertyUtils().isReadable(orig, name) &&
                    	BeanUtilsBean.getInstance().getPropertyUtils().isWriteable(dest, name)) {
                        Object value = ((DynaBean) orig).get(name);
                        BeanUtilsBean.getInstance().copyProperty(dest, name, value);
                    }
                }
            } else if (orig instanceof Map) {
                Iterator entries = ((Map) orig).entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry entry = (Map.Entry) entries.next();
                    String name = (String)entry.getKey();
                    if (BeanUtilsBean.getInstance().getPropertyUtils().isWriteable(dest, name)) {
                    	BeanUtilsBean.getInstance().copyProperty(dest, name, entry.getValue());
                    }
                }
            } else /* if (orig is a standard JavaBean) */ {
                PropertyDescriptor[] origDescriptors =
                		BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptors(orig);
                for (int i = 0; i < origDescriptors.length; i++) {
                    String name = origDescriptors[i].getName();
                    if ("class".equals(name)) {
                        continue; // No point in trying to set an object's class
                    }
                    String metodo = "get" + name.substring(0,1).toUpperCase() + name.substring(1, name.length());  
                    Method metodoJava = orig.getClass().getMethod(metodo, null); 
                    if (BeanUtilsBean.getInstance().getPropertyUtils().isReadable(orig, name) &&
                    		BeanUtilsBean.getInstance().getPropertyUtils().isWriteable(dest, name) &&
                    		metodoJava.invoke(orig) != null) {
                        try {
                            Object value =
                            		BeanUtilsBean.getInstance().getPropertyUtils().getSimpleProperty(orig, name);
                            BeanUtilsBean.getInstance().copyProperty(dest, name, value);
                        } catch (NoSuchMethodException e) {
                            // Should not happen
                        }
                    }
                }
            }
    
        
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	 public static <D> D getInstance(Class<D> _class)
	    {
	        try
	        {
	            return _class.newInstance();
	        }
	        catch (Exception _ex)
	        {
	            _ex.printStackTrace();
	        }
	        return null;
	    }
	
	static class Nova1 {
		int id;
		public int getId() {
			return id;
		}
		
		public void setId(int id) {
			this.id = id;
		}
	}
	
	public static void main(String[] args) {
		Nova1 n = new Utils.Nova1();
		Nova1 n1 = new Utils.Nova1();
		
		Utils.copyProperties(n, n1);
	}
	
}
