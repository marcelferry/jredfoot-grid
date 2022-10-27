package org.jredfoot.utgrid.portal.controllers;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanUtils;
import org.jredfoot.utgrid.common.vo.User;
import org.jredfoot.utgrid.common.vo.UserState;
import org.jredfoot.utgrid.portal.facade.UserServiceFacade;
import org.jredfoot.utgrid.portal.persistence.dao.impl.UserDAOImpl;
import org.jredfoot.utgrid.portal.persistence.entities.UserEntity;
import org.jredfoot.utgrid.portal.utils.JsfUtil;
import org.jredfoot.utgrid.portal.utils.PaginationHelper;
import org.jredfoot.utgrid.portal.utils.Utils;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.print.resources.serviceui;

@ManagedBean(name="userController")
@SessionScoped
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -659531733298999028L;
	private User current;
    private DataModel<User> items = null;
    private DataModel<User> itemsFiltrado = null;   
    private LazyDataModel<User> lazyItems = null;
    private UserDAOImpl ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public UserController() {
    }

    public User getSelected() {
        if (current == null) {
            current = new User();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UserDAOImpl getDAO() {
    	if(ejbFacade == null){
    		ejbFacade = new UserDAOImpl();
    	}
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(20) {

                @Override
                public int getItemsCount() {
                    return getDAO().count();
                }

                @SuppressWarnings("unchecked")
				@Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getDAO().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "Listar";
    }

    public String prepareView() {
    	if(items != null){
	        current = (User)getItems().getRowData();
	    	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    	} else if(lazyItems != null){
    		current = (User)getLazyItems().getRowData();        
    		selectedItemIndex = getLazyItems().getPageSize() * 10 + getLazyItems().getRowIndex();
    	} else if(itemsFiltrado != null){    	
    		current = (User)getItemsFiltrado().getRowData();        
    		selectedItemIndex = getItemsFiltrado().getRowIndex();    		
    	}
        return "Detalhes";
    }

    public String prepareCreate() {
        current = new User();
        selectedItemIndex = -1;
        return "Novo";
    }

    public String create() {
        try {
        	// Conectar no WebService e realizar o cadastro do usuario na base
        	UserServiceFacade facade = new UserServiceFacade();
        	User u = facade.registerUser(current);
            getDAO().persist(new UserEntity(current));
            JsfUtil.addSuccessMessage(Utils.getBundle().getString("UserCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Utils.getBundle().getString("PersistenceErrorOccured"));
            logger.error(Utils.getBundle().getString("PersistenceErrorOccured"));
            e.printStackTrace();
            return null;
        }
    }

    public String prepareEdit() {
    	if(items != null){
	        User usuario = (User)getItems().getRowData();
	        UserServiceFacade facade = new UserServiceFacade();
        	User u = facade.getUser(usuario.getUsername());
        	Utils.copyProperties(usuario, u );
	        current = usuario;
	        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    	} else if(lazyItems != null){	    	
    		User usuario = (User)getLazyItems().getRowData();
    		UserServiceFacade facade = new UserServiceFacade();
        	User u = facade.getUser(usuario.getUsername());
        	Utils.copyProperties(usuario, u);
	        current = usuario;
    		selectedItemIndex = getLazyItems().getPageSize() * 10 + getLazyItems().getRowIndex();
    	} else if(itemsFiltrado != null){    		
    		User usuario = (User)getItemsFiltrado().getRowData();       
    		UserServiceFacade facade = new UserServiceFacade();
        	User u = facade.getUser(usuario.getUsername());
        	Utils.copyProperties(usuario, u);
	        current = usuario;
    		selectedItemIndex = getItemsFiltrado().getRowIndex();    		
    	}
        return "Editar";
    }

    public String update() {
        try {
            getDAO().persist(new UserEntity(current));
            JsfUtil.addSuccessMessage(Utils.getBundle().getString("UserUpdated"));
            return "Detalhes";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Utils.getBundle().getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
    	if(items != null){
	        current = (User)getItems().getRowData();
	    	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    	} else if(lazyItems != null){
    		current = (User)getLazyItems().getRowData();        
    		selectedItemIndex = getLazyItems().getPageSize() * 10 + getLazyItems().getRowIndex();
    	} else if(itemsFiltrado != null){    		
    		current = (User)getItemsFiltrado().getRowData();        
    		selectedItemIndex = getItemsFiltrado().getRowIndex();    		
    	}         	
        performDestroy();
        recreateModel();
        return "Listar";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "Detalhes";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "Listar";
        }
    }

    private void performDestroy() {
        try {
            getDAO().remove(new UserEntity(current));
            JsfUtil.addSuccessMessage(Utils.getBundle().getString("UserDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Utils.getBundle().getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getDAO().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count-1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
        	UserEntity ue = getDAO().findRange(new int[]{selectedItemIndex, selectedItemIndex+1}).get(0);
            current = ue.getDTO();
        }
    }

    @SuppressWarnings("unchecked")
	public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }
    
    public DataModel getAllItems() {
        if (items == null) {
            items = new ListDataModel(getDAO().findAll());
        }
        return items;
    }     
    
    @SuppressWarnings("unchecked")
    public DataModel getItemsFiltrado(){
        if (itemsFiltrado == null) {
            //filter();
        }
        return itemsFiltrado;
    }    

    
    @SuppressWarnings("serial")
	public LazyDataModel<User> getLazyItems() {
    	if(lazyItems == null){
	    	lazyItems = new LazyDataModel<User>() {  
		    	@Override 
		    	public List<User> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {    
		    		List<UserEntity> lazy = new ArrayList<UserEntity>();
		    		lazy = getDAO().findRange(new int[]{first, first+pageSize});      
		    		List<User> lista = new ArrayList<User>();
		    		lista = UserEntity.getDTOList(lazy);
		    		return lista;  
		
		    	}
		    	@Override
		    	public void setRowIndex(int rowIndex) {
		    		if(getPageSize() == 0){
		    			setPageSize(1);
		    		}
		    		super.setRowIndex(rowIndex);
		    	}

	    	}; 
	    	lazyItems.setRowCount(getDAO().count());
    	}
    	return lazyItems;
    }
    
    private void recreateModel() {
        items = null;
        lazyItems = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "Listar";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "Listar";
    }
    
    public String prepareFilter(){
    	recreateModel();
    	//filter();
    	//return "/user/Filtrar";
    	return "/user/Listar";
    }


    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getDAO().findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getDAO().findAll(), true);
    } 
    
	public List<SelectItem> getUserStates() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (UserState type : UserState.values()) {
			items.add(new SelectItem(type, type.toString()));
		}
		return items;
	}
    
    @FacesConverter(forClass=User.class)
    public static class UserControllerConverter implements Converter, Serializable {


		/**
		 * 
		 */
		private static final long serialVersionUID = -1534408555655858715L;

		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserController controller = (UserController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userController");
            return controller.getDAO().getById(getKey(value));
        }

        Long getKey(String value) {
            Long key;
            key = Long.parseLong( value );
            return key;
        }

        String getStringKey(Long value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof UserEntity) {
            	UserEntity o = (UserEntity) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+ UserEntity.class.getName());
            }
        }

    }

   
}
