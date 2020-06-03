package com.project.ccts.service.common;

import java.util.Collection;

/**
 *
 * This in a interface with must common CRUD operations.
 * Implements this interface in all services that need a CRUD.
 *
 * */
public interface ICrudOperation<T, ID> {
    /**
     * Function to create a new instance of an object on the persistence layer.
     *
     * @param entity the object that going to be persisted
     * @return entity type - the created object on the database
     */
    T createOrUpdate(T entity);

    /**
     * Function to delete a instance of an object on the persistence layer.
     *
     * @param id entity id that going to be deleted
     */
    void deleteById(ID id);

    /**
     * Function to delete a instance of an object on the persistence layer.
     *
     * @param entity - the object that going to be delete
     */
    void delete(T entity);

    /**
     * Function to get all instances of an object on the persistence layer.
     *
     * @return Collection<entity> - collection of all the entities on db
     */
    Collection<T> findAll();

    /**
     * Find an instance of the entity by primary key.
     *
     * @param id primary key
     * @return entity type - entity if exists
     *         Null - if not
     */
    T findById(ID id) throws Exception;
}

