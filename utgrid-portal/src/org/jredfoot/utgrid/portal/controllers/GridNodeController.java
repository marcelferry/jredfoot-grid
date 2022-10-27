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

import org.jredfoot.utgrid.common.node.GridNode;
import org.jredfoot.utgrid.portal.facade.GridNodeFacade;
import org.jredfoot.utgrid.portal.utils.JsfUtil;
import org.jredfoot.utgrid.portal.utils.PaginationHelper;
import org.jredfoot.utgrid.portal.utils.Utils;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name="gridNodeController")
@SessionScoped
public class GridNodeController {

    /**
	 * 
	 */
	private static final long serialVersionUID = -659531733298999028L;
	private GridNode current;
    private DataModel<GridNode> items = null;
    private DataModel<GridNode> itemsFiltrado = null;   
    private LazyDataModel<GridNode> lazyItems = null;
    private GridNodeFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public GridNodeController() {
    }

    public GridNode getSelected() {
        if (current == null) {
            current = new GridNode();
            selectedItemIndex = -1;
        }
        return current;
    }

    private GridNodeFacade getFacade() {
    	if(ejbFacade == null){
    		ejbFacade = new GridNodeFacade();
    	}
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(20) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @SuppressWarnings("unchecked")
				@Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()}));
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
	        current = (GridNode)getItems().getRowData();
	    	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    	} else if(lazyItems != null){
    		current = (GridNode)getLazyItems().getRowData();        
    		selectedItemIndex = getLazyItems().getPageSize() * 10 + getLazyItems().getRowIndex();
    	} else if(itemsFiltrado != null){    	
    		current = (GridNode)getItemsFiltrado().getRowData();        
    		selectedItemIndex = getItemsFiltrado().getRowIndex();    		
    	}
        return "Detalhes";
    }

    public String prepareCreate() {
        current = new GridNode();
        selectedItemIndex = -1;
        return "Novo";
    }

    public String create() {
        try {        	
            getFacade().persist(current);
            JsfUtil.addSuccessMessage(Utils.getBundle().getString("GroupCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Utils.getBundle().getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
    	if(items != null){
	        current = (GridNode)getItems().getRowData();
	    	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    	} else if(lazyItems != null){	    	
    		current = (GridNode)getLazyItems().getRowData();        
    		selectedItemIndex = getLazyItems().getPageSize() * 10 + getLazyItems().getRowIndex();
    	} else if(itemsFiltrado != null){    		
    		current = (GridNode)getItemsFiltrado().getRowData();        
    		selectedItemIndex = getItemsFiltrado().getRowIndex();    		
    	}
        return "Editar";
    }

    public String update() {
        try {
            getFacade().persist(current);
            JsfUtil.addSuccessMessage(Utils.getBundle().getString("GroupUpdated"));
            return "Detalhes";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Utils.getBundle().getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
    	if(items != null){
	        current = (GridNode)getItems().getRowData();
	    	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    	} else if(lazyItems != null){
    		current = (GridNode)getLazyItems().getRowData();        
    		selectedItemIndex = getLazyItems().getPageSize() * 10 + getLazyItems().getRowIndex();
    	} else if(itemsFiltrado != null){    		
    		current = (GridNode)getItemsFiltrado().getRowData();        
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
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(Utils.getBundle().getString("GroupDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Utils.getBundle().getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count-1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex+1}).get(0);
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
            items = new ListDataModel(getFacade().findAll());
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

    
    public LazyDataModel<GridNode> getLazyItems() {
    	if(lazyItems == null){
	    	lazyItems = new LazyDataModel<GridNode>() {  
		    	@Override 
		    	public List<GridNode> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {    
		    		List<GridNode> lazy = new ArrayList<GridNode>();
		    		lazy = getFacade().findRange(new int[]{first, first+pageSize});      
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
	    	lazyItems.setRowCount(getFacade().count());
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
    	return "/gridnode/Listar";
    }


    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getFacade().findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getFacade().findAll(), true);
    } 
    
    @FacesConverter(forClass=GridNode.class)
    public static class GridControllerConverter implements Converter, Serializable {


		/**
		 * 
		 */
		private static final long serialVersionUID = -2108084709282497069L;

		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GridNodeController controller = (GridNodeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "gridNodeController");
            return controller.getFacade().getById(getKey(value));
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
            if (object instanceof GridNode) {
            	GridNode o = (GridNode) object;
                return getStringKey(o.getNodeId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+ GridNode.class.getName());
            }
        }

    }

}
