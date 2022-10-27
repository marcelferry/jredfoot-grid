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

import org.jredfoot.utgrid.common.vo.Organization;
import org.jredfoot.utgrid.portal.persistence.dao.impl.OrganizationDAOImpl;
import org.jredfoot.utgrid.portal.persistence.entities.OrganizationEntity;
import org.jredfoot.utgrid.portal.utils.JsfUtil;
import org.jredfoot.utgrid.portal.utils.PaginationHelper;
import org.jredfoot.utgrid.portal.utils.Utils;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name="organizationController")
@SessionScoped
public class OrganizationController {

    /**
	 * 
	 */
	private static final long serialVersionUID = -659531733298999028L;
	private OrganizationEntity current;
    private DataModel<OrganizationEntity> items = null;
    private DataModel<OrganizationEntity> itemsFiltrado = null;   
    private LazyDataModel<OrganizationEntity> lazyItems = null;
    private OrganizationDAOImpl ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public OrganizationController() {
    }

    public OrganizationEntity getSelected() {
        if (current == null) {
            current = new OrganizationEntity();
            selectedItemIndex = -1;
        }
        return current;
    }

    private OrganizationDAOImpl getDAO() {
    	if(ejbFacade == null){
    		ejbFacade = new OrganizationDAOImpl();
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
	        current = (OrganizationEntity)getItems().getRowData();
	    	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    	} else if(lazyItems != null){
    		current = (OrganizationEntity)getLazyItems().getRowData();        
    		selectedItemIndex = getLazyItems().getPageSize() * 10 + getLazyItems().getRowIndex();
    	} else if(itemsFiltrado != null){    	
    		current = (OrganizationEntity)getItemsFiltrado().getRowData();        
    		selectedItemIndex = getItemsFiltrado().getRowIndex();    		
    	}
        return "Detalhes";
    }

    public String prepareCreate() {
        current = new OrganizationEntity();
        selectedItemIndex = -1;
        return "Novo";
    }

    public String create() {
        try {        	
            getDAO().persist(current);
            JsfUtil.addSuccessMessage(Utils.getBundle().getString("OrganizacaoCriado"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Utils.getBundle().getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
    	if(items != null){
	        current = (OrganizationEntity)getItems().getRowData();
	    	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    	} else if(lazyItems != null){	    	
    		current = (OrganizationEntity)getLazyItems().getRowData();        
    		selectedItemIndex = getLazyItems().getPageSize() * 10 + getLazyItems().getRowIndex();
    	} else if(itemsFiltrado != null){    		
    		current = (OrganizationEntity)getItemsFiltrado().getRowData();        
    		selectedItemIndex = getItemsFiltrado().getRowIndex();    		
    	}
        return "Editar";
    }

    public String update() {
        try {
            getDAO().persist(current);
            JsfUtil.addSuccessMessage(Utils.getBundle().getString("OrganizacaoAtualizado"));
            return "Detalhes";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Utils.getBundle().getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
    	if(items != null){
	        current = (OrganizationEntity)getItems().getRowData();
	    	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    	} else if(lazyItems != null){
    		current = (OrganizationEntity)getLazyItems().getRowData();        
    		selectedItemIndex = getLazyItems().getPageSize() * 10 + getLazyItems().getRowIndex();
    	} else if(itemsFiltrado != null){    		
    		current = (OrganizationEntity)getItemsFiltrado().getRowData();        
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
            getDAO().remove(current);
            JsfUtil.addSuccessMessage(Utils.getBundle().getString("OrganizacaoExcluido"));
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
            current = getDAO().findRange(new int[]{selectedItemIndex, selectedItemIndex+1}).get(0);
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

    
    public LazyDataModel<OrganizationEntity> getLazyItems() {
    	if(lazyItems == null){
	    	lazyItems = new LazyDataModel<OrganizationEntity>() {  
		    	@Override 
		    	public List<OrganizationEntity> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {    
		    		List<OrganizationEntity> lazy = new ArrayList<OrganizationEntity>();
		    		lazy = getDAO().findRange(new int[]{first, first+pageSize});      
		    		return lazy;  
		
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
    	//return "/organization/Filtrar";
    	return "/organization/Listar";
    }


    public SelectItem[] getItemsAvailableSelectMany() {
    	List<OrganizationEntity> list = getDAO().findAll();
        return JsfUtil.getSelectItems(OrganizationEntity.getDTOList(list), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
    	List<OrganizationEntity> list = getDAO().findAll();
        return JsfUtil.getSelectItems(OrganizationEntity.getDTOList(list), true);
    } 
    
    @FacesConverter(forClass=Organization.class)
    public static class OrganizationControllerConverter implements Converter, Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -9094619329989260516L;

		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OrganizationController controller = (OrganizationController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "organizationController");
            OrganizationEntity entity = controller.getDAO().getById(getKey(value)); 
            return entity.getDTO();
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
            if (object instanceof Organization) {
            	Organization o = (Organization) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+ OrganizationEntity.class.getName());
            }
        }

    }    
}
