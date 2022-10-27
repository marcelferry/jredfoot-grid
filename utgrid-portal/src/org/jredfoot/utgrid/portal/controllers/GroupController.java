package org.jredfoot.utgrid.portal.controllers;

import java.io.Serializable;
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

import org.jredfoot.utgrid.common.vo.Group;
import org.jredfoot.utgrid.portal.persistence.dao.impl.GroupDAOImpl;
import org.jredfoot.utgrid.portal.persistence.entities.GroupEntity;
import org.jredfoot.utgrid.portal.utils.JsfUtil;
import org.jredfoot.utgrid.portal.utils.PaginationHelper;


import org.jredfoot.utgrid.portal.utils.Utils;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name="groupController")
@SessionScoped
public class GroupController {

	private static Logger logger = LoggerFactory.getLogger(GroupController.class);
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -659531733298999028L;
	private Group current;
    private DataModel<Group> items = null;
    private DataModel<Group> itemsFiltrado = null;   
    private LazyDataModel<Group> lazyItems = null;
    private GroupDAOImpl ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    

    public GroupController() {
    }

    public Group getSelected() {
        if (current == null) {
            current = new Group();
            selectedItemIndex = -1;
        }
        return current;
    }

    private GroupDAOImpl getDAO() {
    	if(ejbFacade == null){
    		ejbFacade = new GroupDAOImpl();
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
                	List<GroupEntity> lista = getDAO().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()});
                    return new ListDataModel(GroupEntity.getDTOList(lista));
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
	        current = (Group)getItems().getRowData();
	    	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    	} else if(lazyItems != null){
    		current = (Group)getLazyItems().getRowData();        
    		selectedItemIndex = getLazyItems().getPageSize() * 10 + getLazyItems().getRowIndex();
    	} else if(itemsFiltrado != null){    	
    		current = (Group)getItemsFiltrado().getRowData();        
    		selectedItemIndex = getItemsFiltrado().getRowIndex();    		
    	}
        return "Detalhes";
    }

    public String prepareCreate() {
        current = new Group();
        selectedItemIndex = -1;
        return "Novo";
    }

    public String create() {
        try {        	
        	
            getDAO().persist(new GroupEntity(current));
            JsfUtil.addSuccessMessage(Utils.getBundle().getString("GroupCreated"));
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
	        current = (Group)getItems().getRowData();
	    	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    	} else if(lazyItems != null){	    	
    		current = (Group)getLazyItems().getRowData();        
    		selectedItemIndex = getLazyItems().getPageSize() * 10 + getLazyItems().getRowIndex();
    	} else if(itemsFiltrado != null){    		
    		current = (Group)getItemsFiltrado().getRowData();        
    		selectedItemIndex = getItemsFiltrado().getRowIndex();    		
    	}
        return "Editar";
    }

    public String update() {
        try {
            getDAO().persist(new GroupEntity(current));
            JsfUtil.addSuccessMessage(Utils.getBundle().getString("GroupUpdated"));
            return "Detalhes";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Utils.getBundle().getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
    	if(items != null){
	        current = (Group)getItems().getRowData();
	    	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    	} else if(lazyItems != null){
    		current = (Group)getLazyItems().getRowData();        
    		selectedItemIndex = getLazyItems().getPageSize() * 10 + getLazyItems().getRowIndex();
    	} else if(itemsFiltrado != null){    		
    		current = (Group)getItemsFiltrado().getRowData();        
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
            getDAO().remove(new GroupEntity(current));
            JsfUtil.addSuccessMessage(Utils.getBundle().getString("GroupDeleted"));
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
        	GroupEntity ge = getDAO().findRange(new int[]{selectedItemIndex, selectedItemIndex+1}).get(0);
            current = ge.getDTO();
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
        	List<GroupEntity> lista = getDAO().findAll();
            items = new ListDataModel(GroupEntity.getDTOList(lista));
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

    
    public LazyDataModel<Group> getLazyItems() {
    	if(lazyItems == null){
	    	lazyItems = new LazyDataModel<Group>() {  
		    	@Override 
		    	public List<Group> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {    
		    		List<GroupEntity> lazy = new ArrayList<GroupEntity>();
		    		lazy = getDAO().findRange(new int[]{first, first+pageSize});      
		    		return GroupEntity.getDTOList(lazy);  
		
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
    	//return "/group/Filtrar";
    	return "/group/Listar";
    }


    public SelectItem[] getItemsAvailableSelectMany() {
    	List<GroupEntity> list = getDAO().findAll();
        return JsfUtil.getSelectItems(GroupEntity.getDTOList(list), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
    	List<GroupEntity> list = getDAO().findAll();
        return JsfUtil.getSelectItems(GroupEntity.getDTOList(list), true);
    } 
    
    @FacesConverter(forClass=Group.class)
    public static class GroupControllerConverter implements Converter, Serializable {
    	
		/**
		 * 
		 */
		private static final long serialVersionUID = -3763513562405079305L;

		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GroupController controller = (GroupController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "groupController");
            GroupEntity ge = controller.getDAO().getById(getKey(value));
            return ge.getDTO();
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
            if (object instanceof Group) {
            	Group o = (Group) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+ Group.class.getName());
            }
        }
    }
   
}
