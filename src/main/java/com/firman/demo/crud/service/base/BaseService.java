package com.firman.demo.crud.service.base;

import com.firman.demo.crud.repository.base.BaseRepository;
import com.firman.demo.crud.repository.base.Selections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@Transactional
public class BaseService<T> {

    @Autowired
    private BaseRepository baseRepository;

    @SuppressWarnings("rawtypes")
    private Class clazz;

    @SuppressWarnings({ "unchecked" })
    private <S extends T> Class<S> getClazz() {
        if (this.clazz == null) {
            Type genType = this.getClass().getGenericSuperclass();
            if (!(genType instanceof ParameterizedType)) {
                return null;
            }
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            if (!(params[0] instanceof Class)) {
                return null;
            }
            return (Class<S>) params[0];
        }
        return this.clazz;
    }

    @Transactional(readOnly = true)
    public T find(Long id) {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.find(getClazz(), id);
    }

    @Transactional(readOnly = true)
    public T find(Example<T> example) {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.find(getClazz(), example);
    }

    @Transactional(readOnly = true)
    public T find(Example<T> example, Selections<T> selections) {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.find(getClazz(), example, selections);
    }

    @Transactional(readOnly = true)
    public T find(T entity) {
        return find(Example.of(entity));
    }

    @Transactional(readOnly = true)
    public T find(T entity, Selections<T> selections) {
        return find(Example.of(entity), selections);
    }

    @Transactional(readOnly = true)
    public T find(Specification<T> spec) {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.find(getClazz(), spec);
    }

    @Transactional(readOnly = true)
    public T find(Specification<T> spec, Selections<T> selections) {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.find(getClazz(), spec, selections);
    }

    @Transactional(readOnly = true)
    public T find(Long id, LockModeType lockMode) {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.find(getClazz(), id, lockMode);
    }

    @Transactional(readOnly = true)
    public T getReference(Long id) {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.getReference(getClazz(), id);
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.findAll(getClazz());
    }

    @Transactional(readOnly = true)
    public List<T> findAll(Selections<T> selections) {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.findAll(getClazz(), selections);
    }

    @Transactional(readOnly = true)
    public List<T> findAll(Sort sort) {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.findAll(getClazz(), sort);
    }

    @Transactional(readOnly = true)
    public List<T> findAll(Selections<T> selections, Sort sort) {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.findAll(getClazz(), selections, sort);
    }

    @Transactional(readOnly = true)
    public <S extends T> List<S> findAll(Example<S> example) {
        return baseRepository.findAll(example);
    }

    @Transactional(readOnly = true)
    public <S extends T> List<S> findAll(Example<S> example, Selections<S> selections) {
        return baseRepository.findAll(example, selections);
    }

    @Transactional(readOnly = true)
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return baseRepository.findAll(example, sort);
    }

    @Transactional(readOnly = true)
    public <S extends T> List<S> findAll(Example<S> example, Selections<S> selections, Sort sort) {
        return baseRepository.findAll(example, selections, sort);
    }

    @Transactional(readOnly = true)
    public <S extends T> List<S> findAll(Specification<S> spec) {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.findAll(getClazz(), spec);
    }

    @Transactional(readOnly = true)
    public <S extends T> List<S> findAll(Specification<S> spec, Sort sort) {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.findAll(getClazz(), spec, sort);
    }

    @Transactional(readOnly = true)
    public <S extends T> List<S> findAll(Specification<S> spec, Selections<S> selections) {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.findAll(getClazz(), spec, selections);
    }

    @Transactional(readOnly = true)
    public <S extends T> List<S> findAll(Specification<S> spec, Selections<S> selections, Sort sort) {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.findAll(getClazz(), spec, selections, sort);
    }

    public <S extends T> S save(S entity) {
        if (getClazz() == null) {
            return null;
        }
        return baseRepository.save(getClazz(), entity);
    }

    public <S extends T> List<S> save(Iterable<S> entities) {
        List<S> result = new ArrayList<S>();
        if (entities == null) {
            return result;
        }
        for (S entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    public <S extends T> S saveAndFlush(S entity) {
        S result = save(entity);
        flush();
        return result;
    }

    public void delete(Long id) {
        T entity = find(id);
        if (entity != null) {
            baseRepository.delete(entity);
        }
    }

    public void delete(T entity) {
        baseRepository.delete(entity);
    }

    public void detach(T entity) {
        baseRepository.detach(entity);
    }

    @Transactional(readOnly = true)
    public void refresh(T entity) {
        baseRepository.refresh(entity);
    }

    @Transactional(readOnly = true)
    public void refresh(T entity, LockModeType lockMode) {
        baseRepository.refresh(entity, lockMode);
    }

    @Transactional(readOnly = true)
    public void refresh(T entity, Map<String, Object> properties) {
        baseRepository.refresh(entity, properties);
    }

    @Transactional(readOnly = true)
    public void refresh(T entity, LockModeType lockMode, Map<String, Object> properties) {
        baseRepository.refresh(entity, lockMode, properties);
    }

    public void lock(T entity, LockModeType lockMode) {
        baseRepository.lock(entity, lockMode);
    }

    public LockModeType getLockMode(T entity) {
        return baseRepository.getLockMode(entity);
    }

    @Transactional(readOnly = true)
    public Long count(T entity) {
        if (getClazz() == null) {
            return 0L;
        }
        return baseRepository.count(getClazz(), Example.of(entity));
    }

    @Transactional(readOnly = true)
    public Long count(Specification<T> spec) {
        if (getClazz() == null) {
            return 0L;
        }
        return baseRepository.count(getClazz(), spec);
    }

    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        if (getClazz() == null) {
            return false;
        }
        return baseRepository.exists(getClazz(), id);
    }

    @Transactional(readOnly = true)
    public boolean exists(T entity) {
        if (getClazz() == null) {
            return false;
        }
        return baseRepository.exists(getClazz(), Example.of(entity));
    }

    @Transactional(readOnly = true)
    public boolean exists(Specification<T> spec) {
        if (getClazz() == null) {
            return false;
        }
        return baseRepository.exists(getClazz(), spec);
    }

    public void flush() {
        baseRepository.flush();
    }

    public int update(Long id, T entity) {
        if (getClazz() == null) {
            return 0;
        }
        return baseRepository.update(getClazz(), id, entity);
    }

}
