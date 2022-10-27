package org.jredfoot.utgrid.common.persistence.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jredfoot.utgrid.common.persistence.dao.GenericDao;
import org.jredfoot.utgrid.common.persistence.entities.GridEntity;

@SuppressWarnings("serial")
public abstract class GenericDAOImpl<T1 extends GridEntity<T2>, T2> implements GenericDao<T1, T2> {

	protected EntityManager entityManager;
	protected Class<T1> domainClass;

	@SuppressWarnings("unchecked")
	public GenericDAOImpl(){
		this.domainClass = (Class<T1>) 
				((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public Class<T1> getDomainClass() {
		return domainClass;
	}
	
	@Override
    public T2 persist(T1 entity) {
    	try{
    		entityManager = createEntityManager();
    		entityManager.getTransaction().begin();
		    if(entity.getId() == null){
		        entityManager.persist(entity);
		    } else {
		        entityManager.merge(entity);
		    }
		    entityManager.getTransaction().commit();
		    return entity.getId();
    	} catch (Exception e) {
    		if(entityManager != null && entityManager.getTransaction().isActive()){
    			entityManager.getTransaction().rollback();
    		}
    		throw new RuntimeException(e);
		} finally {
			if(entityManager != null) {
				entityManager.close();
			}
		}
    }    
    
	@Override
    public void remove(T1 entity) {
    	try{
    		entityManager = createEntityManager();
    		entityManager.getTransaction().begin();
    		
	    	entityManager.merge(entity);
	        entityManager.remove(entity);
	        entityManager.getTransaction().commit();
    	} catch (Exception e) {
    		if(entityManager != null && entityManager.getTransaction().isActive()){
    			entityManager.getTransaction().rollback();
    		}
    		throw new RuntimeException(e);
		} finally {
			if(entityManager != null) {
				entityManager.close();
			}
		}

   	}
    
    @SuppressWarnings("unchecked")
    public List<T1> findAll() {
    	try{
    		entityManager = createEntityManager();
	    	List<T1> lista = entityManager.createQuery("select f from " + getDomainClass().getName() + " f").getResultList();
	        return lista;
    	}catch (Exception e) {
    		throw new RuntimeException(e);
		} finally {
			if(entityManager != null) {
				entityManager.close();
			}
		}

    }

    @SuppressWarnings("unchecked")
	public List<T1> findRange(int[] range) {
    	try{
    		entityManager = createEntityManager();
	    	Query q = entityManager.createQuery("select f from " + getDomainClass().getName() + " f");
	        q.setMaxResults(range[1] - range[0]);
	        q.setFirstResult(range[0]);
	        List<T1> lista = q.getResultList();
	        return lista;
    	} catch (Exception e) {
    		throw new RuntimeException(e);
		} finally {
			if(entityManager != null) entityManager.close();
		}
    }
    
	@Override
	public int count() {
    	try{
    		entityManager = createEntityManager();
    		return ((Long)entityManager.createQuery("SELECT count(g) FROM " + getDomainClass().getName() + " g").getSingleResult()).intValue();
    	} catch (Exception e) {
    		throw new RuntimeException(e);
		} finally {
			if(entityManager != null) entityManager.close();
		}

    }

	@SuppressWarnings("unchecked")
	@Override
	public List<T1> findByProperty(String propertyName,
			Object value) {
    	try{
    		entityManager = createEntityManager();

			Query q = entityManager.createQuery("SELECT g FROM " + getDomainClass().getName() + " g where g." + propertyName + " = ?");
			q.setParameter(1, value);
			return (List<T1>)q.getResultList();	
    	} catch (Exception e) {
    		throw new RuntimeException(e);
		} finally {
			if(entityManager != null) entityManager.close();
		}

	}

	@Override
	public T1 getById(T2 id) {
    	try{
    		entityManager = createEntityManager();
			return (T1) entityManager.find(getDomainClass(), id);
    	} catch (Exception e) {
    		throw new RuntimeException(e);
		} finally {
			if(entityManager != null) entityManager.close();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T1> list() {
    	try{
    		entityManager = createEntityManager();

			return (List<T1>)entityManager.createQuery("SELECT g FROM " + getDomainClass().getName() + " g").getResultList();
    	} catch (Exception e) {
    		throw new RuntimeException(e);
		} finally {
			if(entityManager != null) entityManager.close();
		}

	}
    
}
