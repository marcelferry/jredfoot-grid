package org.jredfoot.utgrid.common.persistence.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.jredfoot.utgrid.common.persistence.entities.GridEntity;

/**
 *
 * @author marcelo.ferreira
 */
public interface GenericDao<T1 extends GridEntity<?>, T2> extends Serializable {

	EntityManager createEntityManager();

    T2 persist(T1 entity);

    void remove(T1 entity);

    T1 getById(T2 id);

    int count();

    List<T1> list() ;

    List<T1> findByProperty(String propertyName, Object value);
    
    Class<T1> getDomainClass();
    
}
