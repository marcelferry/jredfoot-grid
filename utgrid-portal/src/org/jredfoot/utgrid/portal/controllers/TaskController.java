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

import org.jredfoot.utgrid.common.vo.Task;
import org.jredfoot.utgrid.common.vo.TaskState;
import org.jredfoot.utgrid.portal.facade.GridTaskServiceFacade;
import org.jredfoot.utgrid.portal.utils.JsfUtil;
import org.jredfoot.utgrid.portal.utils.PaginationHelper;
import org.jredfoot.utgrid.portal.utils.Utils;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name="taskController")
@SessionScoped
public class TaskController {
	

	private static Logger logger = LoggerFactory.getLogger(TaskController.class);
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -659531733298999028L;
	private Task current;
    private DataModel<Task> items = null;
    private DataModel<Task> itemsFiltrado = null;   
    private LazyDataModel<Task> lazyItems = null;
    private GridTaskServiceFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public TaskController() {
    }

    public Task getSelected() {
        if (current == null) {
            current = new Task();
            selectedItemIndex = -1;
        }
        return current;
    }

    private GridTaskServiceFacade getDAO() {
    	if(ejbFacade == null){
    		ejbFacade = new GridTaskServiceFacade();
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
	        current = (Task)getItems().getRowData();
	    	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    	} else if(lazyItems != null){
    		current = (Task)getLazyItems().getRowData();        
    		selectedItemIndex = getLazyItems().getPageSize() * 10 + getLazyItems().getRowIndex();
    	} else if(itemsFiltrado != null){    	
    		current = (Task)getItemsFiltrado().getRowData();        
    		selectedItemIndex = getItemsFiltrado().getRowIndex();    		
    	}
        return "Detalhes";
    }

    public String prepareCreate() {
        current = new Task();
        selectedItemIndex = -1;
        return "Novo";
    }

    public String create() {
        try {
        	// Conectar no WebService e realizar o cadastro do usuario na base
        	Task u = getDAO().persist(current);
            JsfUtil.addSuccessMessage(Utils.getBundle().getString("TaskCreated"));
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
	        Task tarefa = (Task)getItems().getRowData();
        	Task u = getDAO().getById(tarefa.getTaskId());
        	Utils.copyProperties(tarefa, u );
	        current = tarefa;
	        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    	} else if(lazyItems != null){	    	
    		Task usuario = (Task)getLazyItems().getRowData();
        	Task u = getDAO().getById(usuario.getTaskId());
        	Utils.copyProperties(usuario, u);
	        current = usuario;
    		selectedItemIndex = getLazyItems().getPageSize() * 10 + getLazyItems().getRowIndex();
    	} else if(itemsFiltrado != null){    		
    		Task usuario = (Task)getItemsFiltrado().getRowData();       
        	Task u = getDAO().getById(usuario.getTaskId());
        	Utils.copyProperties(usuario, u);
	        current = usuario;
    		selectedItemIndex = getItemsFiltrado().getRowIndex();    		
    	}
        return "Editar";
    }

    public String update() {
        try {
            getDAO().update(current);
            JsfUtil.addSuccessMessage(Utils.getBundle().getString("TaskUpdated"));
            return "Detalhes";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, Utils.getBundle().getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
    	if(items != null){
	        current = (Task)getItems().getRowData();
	    	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    	} else if(lazyItems != null){
    		current = (Task)getLazyItems().getRowData();        
    		selectedItemIndex = getLazyItems().getPageSize() * 10 + getLazyItems().getRowIndex();
    	} else if(itemsFiltrado != null){    		
    		current = (Task)getItemsFiltrado().getRowData();        
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
            JsfUtil.addSuccessMessage(Utils.getBundle().getString("TaskDeleted"));
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

    
    @SuppressWarnings("serial")
	public LazyDataModel<Task> getLazyItems() {
    	if(lazyItems == null){
	    	lazyItems = new LazyDataModel<Task>() {  
		    	@Override 
		    	public List<Task> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {    
		    		List<Task> lazy = new ArrayList<Task>();
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
    	//return "/user/Filtrar";
    	return "/task/Listar";
    }


    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getDAO().findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getDAO().findAll(), true);
    } 
    
	public List<SelectItem> getTaskStates() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (TaskState type : TaskState.values()) {
			items.add(new SelectItem(type, type.toString()));
		}
		return items;
	}
    
    @FacesConverter(forClass=Task.class)
    public static class TaskControllerConverter implements Converter, Serializable {


		/**
		 * 
		 */
		private static final long serialVersionUID = -1534408555655858715L;

		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TaskController controller = (TaskController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userController");
            return controller.getDAO().getById(value);
        }


        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Task) {
            	Task o = (Task) object;
                return o.getTaskId();
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+ Task.class.getName());
            }
        }

    }
	
	
	
	public String prepararEnvioTarefa(){
		return null;
	}
	
	public String prepararListaTarefasAndamento(){
		return null;
	}
	
	public String prepararListaTarefasConcluidas(){
		return null;
	}
}
