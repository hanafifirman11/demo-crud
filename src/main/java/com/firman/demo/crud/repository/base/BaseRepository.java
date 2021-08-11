package com.firman.demo.crud.repository.base;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Slf4j
@Repository("baseRepository")
public class BaseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public <T> T find(Class<T> clazz, Long id) {
        return entityManager.find(clazz, id);
    }

    public <T> T find(Class<T> clazz, Example<T> example) {
        try {
            return getQuery(new ExampleSpecification<T>(example), example.getProbeType(), (Sort) null).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public <T> T find(Class<T> clazz, Example<T> example, Selections<T> selections) {
        try {
            return getQuery(new ExampleSpecification<T>(example), selections, example.getProbeType(), (Sort) null).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public <T> T find(Class<T> clazz, Specification<T> spec) {
        try {
            return getQuery(spec, clazz, (Sort) null).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public <T> T find(Class<T> clazz, Specification<T> spec, Selections<T> selections) {
        try {
            return getQuery(spec, selections, clazz, (Sort) null).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public <T> T find(Class<T> clazz, Long id, LockModeType lockMode) {
        return entityManager.find(clazz, id, lockMode);
    }

    public <T> T getReference(Class<T> clazz, Long id) {
        return entityManager.getReference(clazz, id);
    }

    public <T> List<T> findAll(Class<T> clazz) {
        return getQuery(null, clazz, (Sort) null).getResultList();
    }

    public <T> List<T> findAll(Class<T> clazz, Selections<T> selections) {
        return getQuery(null, selections, clazz, (Sort) null).getResultList();
    }

    public <T> List<T> findAll(Class<T> clazz, Sort sort) {
        return getQuery(null, clazz, sort).getResultList();
    }

    public <T> List<T> findAll(Class<T> clazz, Selections<T> selections, Sort sort) {
        return getQuery(null, selections, clazz, sort).getResultList();
    }

    public <T> List<T> findAll(Example<T> example) {
        Sort sort = Sort.by(Direction.ASC, getIdName(example.getProbeType()));

        return getQuery(new ExampleSpecification<T>(example), example.getProbeType(), sort).getResultList();
    }

    public <T> List<T> findAll(Example<T> example, Selections<T> selections) {
        Sort sort = Sort.by(Direction.ASC, getIdName(example.getProbeType()));
        return getQuery(new ExampleSpecification<T>(example), selections, example.getProbeType(), sort).getResultList();
    }

    public <T> List<T> findAll(Example<T> example, Sort sort) {
        return getQuery(new ExampleSpecification<T>(example), example.getProbeType(), sort).getResultList();
    }

    public <T> List<T> findAll(Example<T> example, Selections<T> selections, Sort sort) {
        return getQuery(new ExampleSpecification<T>(example), selections, example.getProbeType(), sort).getResultList();
    }

    public <T> List<T> findAll(Class<T> clazz, Specification<T> spec) {
        return getQuery(spec, clazz, (Sort) null).getResultList();
    }

    public <T> List<T> findAll(Class<T> clazz, Specification<T> spec, Sort sort) {
        return getQuery(spec, clazz, sort).getResultList();
    }

    public <T> List<T> findAll(Class<T> clazz, Specification<T> spec, Selections<T> selections) {
        return getQuery(spec, selections, clazz, (Sort) null).getResultList();
    }

    public <T> List<T> findAll(Class<T> clazz, Specification<T> spec, Selections<T> selections, Sort sort) {
        return getQuery(spec, selections, clazz, sort).getResultList();
    }

    public <T> void persist(T entity) {
        entityManager.persist(entity);
    }

    public <T> T merge(T entity) {
        return entityManager.merge(entity);
    }

    public <T> T save(Class<T> clazz, T entity) {
        Object val = getIdValue(clazz, entity);
        if (val == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    public <T> void remove(T entity) {
        entityManager.remove(entity);
    }

    public <T> void delete(T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    public <T> void detach(T entity) {
        entityManager.detach(entity);
    }

    public <T> void refresh(T entity) {
        entityManager.refresh(entity);
    }

    public <T> void refresh(T entity, LockModeType lockMode) {
        entityManager.refresh(entity, lockMode);
    }

    public <T> void refresh(T entity, Map<String, Object> properties) {
        entityManager.refresh(entity, properties);
    }

    public <T> void refresh(T entity, LockModeType lockMode, Map<String, Object> properties) {
        entityManager.refresh(entity, lockMode, properties);
    }

    public <T> void lock(T entity, LockModeType lockMode) {
        entityManager.lock(entity, lockMode);
    }

    public <T> LockModeType getLockMode(T entity) {
        return entityManager.getLockMode(entity);
    }

    public <T> Long count(Class<T> clazz, Example<T> example) {
        return executeCountQuery(getCountQuery(new ExampleSpecification<T>(example), clazz));
    }

    public <T> Long count(Class<T> clazz, Specification<T> spec) {
        return executeCountQuery(getCountQuery(clazz, spec));
    }

    public <T> boolean exists(Class<T> clazz, Long id) {
        String idName = getIdName(clazz);
        String ql = QueryUtils.getExistsQueryString(clazz.getSimpleName(), "*", Arrays.asList(idName));
        ql = ql.substring(0, ql.length() - 10);
        TypedQuery<Long> query = entityManager.createQuery(ql, Long.class);
        query.setParameter(idName, id);
        return query.getSingleResult() >= 1L;
    }

    public <T> boolean exists(Class<T> clazz, Example<T> example) {
        return executeCountQuery(getCountQuery(new ExampleSpecification<T>(example), clazz)) >= 1L;
    }

    public <T> boolean exists(Class<T> clazz, Specification<T> spec) {
        return executeCountQuery(getCountQuery(spec, clazz)) >= 1L;
    }

    public void flush() {
        entityManager.flush();
    }

    public <T> int update(Class<T> clazz, Long id, T entity) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<T> updateQuery = cb.createCriteriaUpdate(clazz);
        Root<T> root = updateQuery.from(clazz);
        // update
        buildUpdate(clazz, updateQuery, entity);
        // where
        Predicate predicate = cb.conjunction();
        List<Expression<Boolean>> expressions = predicate.getExpressions();
        expressions.add(cb.equal(root.get(getIdName(clazz)), id));
        updateQuery.where(predicate);
        // executeUpdate
        return entityManager.createQuery(updateQuery).executeUpdate();
    }

    @SuppressWarnings("rawtypes")
    public List getList(String jpql, Object... args) {
        Query query = entityManager.createQuery(jpql);
        int i = 1;
        for (Object obj : args) {
            query.setParameter(i++, obj);
        }
        return query.getResultList();
    }

    @SuppressWarnings("rawtypes")
    public List getList(String sql, ResultHandler resultHandler, Object... args) {
        Query query = entityManager.createNativeQuery(sql);
        int i = 1;
        for (Object obj : args) {
            query.setParameter(i++, obj);
        }
        @SuppressWarnings("unchecked")
        List<Object[]> result = query.getResultList();
        // return
        return resultHandler.getResult(result);
    }

    @SuppressWarnings("rawtypes")
    public List getList(String jpql, int startPosition, int maxResult, Object... args) {
        Query query = entityManager.createQuery(jpql);
        int i = 1;
        for (Object obj : args) {
            query.setParameter(i++, obj);
        }
        if (startPosition >= 0) {
            query.setFirstResult(startPosition);
        }
        if (maxResult > 0) {
            query.setMaxResults(maxResult);
        }
        return query.getResultList();
    }

    @SuppressWarnings("rawtypes")
    public List getList(String sql, int startPosition, int maxResult, ResultHandler resultHandler, Object... args) {
        Query query = entityManager.createNativeQuery(sql);
        int i = 1;
        for (Object obj : args) {
            query.setParameter(i++, obj);
        }
        if (startPosition >= 0) {
            query.setFirstResult(startPosition);
        }
        if (maxResult > 0) {
            query.setMaxResults(maxResult);
        }
        @SuppressWarnings("unchecked")
        List<Object[]> result = query.getResultList();
        return resultHandler.getResult(result);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void queryByPage(PageBean pageBean, Object... args) {
        pageBean.init();
        int startPosition = (pageBean.getNumber()) * pageBean.getSize();
        int maxResult = pageBean.getSize();
        List list = this.getList(pageBean.getQl(), startPosition, maxResult, args);
        pageBean.setContent(list);
        Long count = this.count(pageBean.getCountQl(), false, args);
        if (null != count) {
            pageBean.setTotalElements(count);
        }
        if (pageBean.getTotalElements() > 0) {
            int totalPages = pageBean.getTotalElements().intValue() / pageBean.getSize();
            int remainder = pageBean.getTotalElements().intValue() % pageBean.getSize();
            if (remainder > 0) {
                totalPages++;
            }
            pageBean.setTotalPages(totalPages);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void queryByPage(PageBean pageBean, ResultHandler resultHandler, Object... args) {
        pageBean.init();
        int startPosition = (pageBean.getNumber()) * pageBean.getSize();
        int maxResult = pageBean.getSize();
        List list = this.getList(pageBean.getQl(), startPosition, maxResult, resultHandler, args);
        pageBean.setContent(list);
        Long count = this.count(pageBean.getCountQl(), true, args);
        if (count != null) {
            pageBean.setTotalElements(count);
        }
        if (pageBean.getTotalElements() > 0) {
            int totalPages = pageBean.getTotalElements().intValue() / pageBean.getSize();
            int remainder = pageBean.getTotalElements().intValue() % pageBean.getSize();
            if (remainder > 0) {
                totalPages++;
            }
            pageBean.setTotalPages(totalPages);
        }
    }

    private Long count(String ql, boolean nativeQuery, Object... args) {
        Query query = null;
        if (nativeQuery) {
            query = entityManager.createNativeQuery(ql);
        } else {
            query = entityManager.createQuery(ql);
        }

        int i = 1;
        for (Object obj : args) {
            query.setParameter(i++, obj);
        }

        return ((Number) query.getSingleResult()).longValue();
    }

    private <T> TypedQuery<T> getQuery(Specification<T> spec, Class<T> domainClass, Sort sort) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(domainClass);

        Root<T> root = applySpecificationToCriteria(spec, domainClass, query);
        query.select(root);

        if (sort != null) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return entityManager.createQuery(query);
    }

    private <T> TypedQuery<T> getQuery(Specification<T> spec, Selections<T> selections, Class<T> domainClass, Sort sort) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(domainClass);

        Root<T> root = applySpecificationToCriteria(spec, domainClass, query);
        query.multiselect(selections.toSelection(root));

        if (sort != null) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return entityManager.createQuery(query);
    }

    private <S, U extends T, T> Root<U> applySpecificationToCriteria(Specification<U> spec, Class<U> domainClass, CriteriaQuery<S> query) {
        Assert.notNull(query, "query is not null");
        Assert.notNull(domainClass, "domainClass is not null");
        Root<U> root = query.from(domainClass);

        if (spec == null) {
            return root;
        }

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }

    private static class ExampleSpecification<T> implements Specification<T> {
        private final Example<T> example;

        public ExampleSpecification(Example<T> example) {

            Assert.notNull(example, "Example must not be null!");
            this.example = example;
        }

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            return QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
        }
    }

    private <T> String getIdName(Class<T> clazz) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(clazz);
        Root<T> root = query.from(clazz);
        SingularAttribute<? super T, ?> id = root.getModel().getId(root.getModel().getIdType().getJavaType());
        Member member = id.getJavaMember();
        if (member instanceof Field) {
            Field field = (Field) member;
            return field.getName();
        } else {
            return null;
        }
    }

    private <T> Object getIdValue(Class<T> clazz, T entity) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(clazz);
        Root<T> root = query.from(clazz);
        SingularAttribute<? super T, ?> id = root.getModel().getId(root.getModel().getIdType().getJavaType());
        Member member = id.getJavaMember();
        if (member instanceof Field) {
            Field field = (Field) member;
            if (!Modifier.isPublic(field.getDeclaringClass().getModifiers()) || !Modifier.isPublic(field.getModifiers())) {
                field.setAccessible(true);
            }
            try {
                Object val = field.get(entity);
                return val;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    private <T> void buildUpdate(Class<T> clazz, CriteriaUpdate<T> updateQuery, T entity) {
        try {
            BeanInfo bi = Introspector.getBeanInfo(clazz, Object.class);
            PropertyDescriptor[] pd = bi.getPropertyDescriptors();

            for (PropertyDescriptor propertyDescriptor : pd) {
                // getter
                Method readMethod = propertyDescriptor.getReadMethod();
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }

                Object val = null;
                try {
                    val = readMethod.invoke(entity);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

                if (val != null) {
                    String name = propertyDescriptor.getName();
                    updateQuery.set(name, val);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private <T> TypedQuery<Long> getCountQuery(Class<T> clazz, Specification<T> spec) {
        return getCountQuery(spec, clazz);
    }

    private <T> TypedQuery<Long> getCountQuery(Specification<T> spec, Class<T> domainClass) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<T> root = applySpecificationToCriteria(spec, domainClass, query);

        if (query.isDistinct()) {
            query.select(builder.countDistinct(root));
        } else {
            query.select(builder.count(root));
        }

        // Remove all Orders the Specifications might have applied
        query.orderBy(Collections.<Order> emptyList());

        return entityManager.createQuery(query);
    }

    private static Long executeCountQuery(TypedQuery<Long> query) {
        Assert.notNull(query, "query is not null");

        List<Long> totals = query.getResultList();
        Long total = 0L;

        for (Long element : totals) {
            total += element == null ? 0 : element;
        }

        return total;
    }

}
