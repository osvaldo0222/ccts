package com.project.ccts.service.common;

import com.project.ccts.util.logger.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Implementation of the basic CRUD operations.
 *
 * @param <T> Entity class type
 * @param <ID> Primary key type
 */
@Transactional
public abstract class AbstractCrud<T, ID> implements ICrudOperation<T, ID> {

    public abstract JpaRepository<T, ID> getDao();

    @Override
    public T createOrUpdate(T entity) {
        try {
            entity = getDao().save(entity);
        }catch (Exception e){
            Logger.getInstance().getLog(getClass()).error(String.format("Error creating or updating entity - Exception message: %s", e.getMessage()));
        }
        return entity;
    }

    @Override
    public Collection<T> createAll(Collection<T> entities) {
        try {
            entities = getDao().saveAll(entities);
        } catch (Exception e) {
            Logger.getInstance().getLog(getClass()).error(String.format("Error creating or updating entity - Exception message: %s", e.getMessage()));
        }
        return entities;
    }

    @Override
    public void deleteById(ID id) {
        try {
            T entity = findById(id);
            if (entity != null) {
                getDao().delete(entity);
            }
        } catch (Exception e) {
            Logger.getInstance().getLog(getClass()).error(String.format("Error deleting entity - %s", e.getMessage()));
        }
    }

    @Override
    public void delete(T entity) {
        deleteById((ID) getCampValue(entity));
    }

    @Override
    public Collection<T> findAll() {
        return getDao().findAll();
    }

    @Override
    public T findById(ID id) throws Exception {
        if (id == null) {
            throw new Exception("ID cannot be null");
        }

        return getDao().findById(id).orElse(null);
    }

    /**
     * Function that returns the field annotated as the entity ID.
     *
     * @param entity
     * @return Field as object if it has a @Id or Null if not
     */
    private Object getCampValue(T entity){
        if(entity == null){
            return null;
        }

        for(Field f : entity.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(Id.class)) {
                try {
                    f.setAccessible(true);
                    Object campValue = f.get(entity);
                    return campValue;
                } catch (Exception e) {
                    Logger.getInstance().getLog(getClass()).error(String.format("The object doesn't have an ID value - %s", e.getMessage()));
                }
            }
        }

        return null;
    }
}
