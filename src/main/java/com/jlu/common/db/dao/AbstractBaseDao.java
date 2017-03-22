package com.jlu.common.db.dao;

import com.jlu.common.db.bean.PageBean;
import com.jlu.common.db.sqlcondition.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Wonpang New on 2016/9/6.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractBaseDao<C> implements IBaseDao<C>{
    /**
     * 当参数集合大于500时，将自动转换为join
     */
    private static final int MAX_PARAM_COLLECTION_SIZE = 500;

    protected Class<C> entityClass;
    protected String entityClassName;

    @Resource
    protected HibernateTemplate hibernateTemplate;

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public AbstractBaseDao() {
        // 通过范型反射，获取在子类中定义的entityClass.
        this.entityClass = (Class<C>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.entityClassName = entityClass.getSimpleName();
    }

    /**
     * 保存对象
     * @param object
     */
    public void save(C object) {
        hibernateTemplate.save(object);
    }

    /**
     * 删除对象
     * @param object
     */
    public void delete(C object) {
        hibernateTemplate.delete(object);
        hibernateTemplate.flush();
    }

    /**
     * 更新对象
     * @param object
     */
    public void update(C object) {
        hibernateTemplate.update(object);
    }

    /**
     * 保存或更新对象
     * @param object
     */
    public void saveOrUpdate(C object) {
        hibernateTemplate.saveOrUpdate(object);
    }

    /**
     * 批量删除
     * @param objects
     */
    public void deleteBatch(Collection<C> objects) {
        hibernateTemplate.deleteAll(objects);
        hibernateTemplate.flush();
    }

    /**
     * 批量保存或更新
     *
     * @param objects
     */
    public void saveOrUpdateAll(Collection<C> objects) {
        for (C object: objects) {
            hibernateTemplate.saveOrUpdate(object);
        }
    }

    /**
     * 通过id查找
     * @param id
     * @return
     */
    public C findById(Serializable id) {
        return (C)hibernateTemplate.get(entityClass, id);
    }

    /**
     * 根据属性查找，属性对应的值可以支持含有like条件
     * @param conditionSet
     * @return Collection
     */
    public List<C> findByProperties(ConditionSet conditionSet) {
        return findByProperties(conditionSet, new ArrayList<OrderCondition>());
    }

    /**
     * 根据属性查找,并指定排序列，属性对应的值可以支持含有like条件
     *
     * @param properties
     *            Map
     * @param orderByProperties
     *            OrderBy[] 需要排序的属性名，
     * @return Collection
     */
    public List<C> findByProperties(final ConditionSet properties, final List<OrderCondition> orderByProperties) {
        return (List) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException{
                Criteria crit = convertProperties2Criteria(properties, session);
                fillOrderBy(crit, orderByProperties);
                return crit.list();
            }
        });
    }

    /**
     * 根据属性查找,并指定排序列，属性对应的值可以支持含有like条件
     *
     * @param properties
     *            Map
     * @param orderByProperty OrderBy 需要排序的属性名
     * @return Collection
     */
    public List<C> findByProperties(final ConditionSet properties, final OrderCondition orderByProperty) {
        List<OrderCondition> orderConditions = new ArrayList<OrderCondition>();
        if (orderByProperty != null) {
            orderConditions.add(orderByProperty);
        }
        return findByProperties(properties, orderConditions);
    }

    /**
     * 查询数据库指定的条目
     *
     * @param properties
     * @param orderByProperties
     * @param offset 开始index
     * @param limit 最大数目
     * @return
     */
    public List<C> findHeadByProperties(final ConditionSet properties, final List<OrderCondition> orderByProperties,
                                        final int offset, final int limit) {
        return (List)  hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                if(limit <= 0){
                    return new ArrayList<C>();
                }
                Criteria crit = convertProperties2Criteria(properties, session);
                crit.setFirstResult(offset > 0 ? offset : 0).setMaxResults(limit);
                if (orderByProperties != null) {
                    for (OrderCondition orderBy : orderByProperties) {
                        Order order = null;
                        if (orderBy instanceof DescOrder)
                            order = Order.desc(orderBy.getPropertyName());
                        else
                            order = Order.asc(orderBy.getPropertyName());
                        crit.addOrder(order);
                    }
                }
                return crit.list();
            }
        });
    }

    /**
     * 本类使用，属性排序
     *
     * @param crit
     *            Criteria
     * @param orderByProperties
     *            OrderBy[]
     */
    private void fillOrderBy(Criteria crit, List<OrderCondition> orderByProperties) {
        if (orderByProperties != null) {
            for (OrderCondition orderBy : orderByProperties) {
                Order order = null;
                if (orderBy instanceof DescOrder)
                    order = Order.desc(orderBy.getPropertyName());
                else
                    order = Order.asc(orderBy.getPropertyName());
                crit.addOrder(order);
            }
        }

    }

    /**
     * 构造查询所有条件 一层
     *
     * @param properties
     * @param session
     * @return
     */
    private Criteria convertProperties2Criteria(ConditionSet properties, Session session) {
        Criteria crit = session.createCriteria(entityClass);
        if (properties != null) {
            crit.add(constructCriteriaValue(null, properties));
            List<String> groupBys = properties.getGroups();
            if (groupBys != null && !groupBys.isEmpty()) {
                for (String propGroupBy : groupBys) {
                    crit.setProjection(Projections.groupProperty(propGroupBy));
                }
            }
        }
        return crit;
    }

    /**
     * 构造查询条件 二层
     * @param propertyName
     * @param propValue
     * @return
     */
    private Criterion constructCriteriaValue(String propertyName, Object propValue) {
        Criterion crit = null;
        if (propValue == null) {
            crit = Restrictions.isNull(propertyName);
        } else if (propValue instanceof String && ((String) propValue).indexOf("%") != -1) {
            // 属性值是字符串类型的并且含有%,用like匹配
            crit = Restrictions.like(propertyName, propValue);
        } else if (propValue instanceof Collection) {
            Collection valueCollection = (Collection) propValue;
            if (valueCollection.isEmpty()) {
                crit = Restrictions.isNull(propertyName);
            } else {
                crit = Restrictions.in(propertyName, valueCollection);
            }
        } else if (propValue instanceof Object[]) {
            Object[] values = (Object[]) propValue;
            if (values.length == 0) {
                crit = Restrictions.isNull(propertyName);
            } else {
                crit = Restrictions.in(propertyName, values);
            }
            crit = Restrictions.in(propertyName, (Object[]) propValue);
        } else if (propValue instanceof CompareCondition) {
            CompareCondition condition = (CompareCondition) propValue;
            if (propertyName == null)
                propertyName = condition.getPropertyName();
            if (propValue instanceof EqualCondition) {
                crit = constructCriteriaValue(propertyName, condition.getValue());
            } else if (propValue instanceof NotEqualCondition) {
                Object notValue = condition.getValue();
                if (notValue == null) {
                    crit = Restrictions.isNotNull(propertyName);
                } else {
                    crit = Restrictions.ne(propertyName, notValue);
                }
            } else if (propValue instanceof NotInCondition) {
                crit = Restrictions.not(Restrictions.in(propertyName, (Collection)condition.getValue()));
            } else if (propValue instanceof GreaterCondition)
                crit = Restrictions.gt(propertyName, condition.getValue());
            else if (propValue instanceof LessThanCondition)
                crit = Restrictions.lt(propertyName, condition.getValue());
            else if (propValue instanceof BetweenCondition)
                crit = Restrictions.between(propertyName, condition.getMinValue(), condition.getMaxValue());
            else if (propValue instanceof LessOrEqualCondition) {
                crit = Restrictions.le(propertyName, condition.getValue());
            } else if (propValue instanceof GreaterOrEqualCondition) {
                crit = Restrictions.ge(propertyName, condition.getValue());
            }
        }
        else if (propValue instanceof ConditionSet) {
            ConditionSet propMap = (ConditionSet) propValue;
            List<Criterion> ccIt = new ArrayList<Criterion>();
            for (Iterator<Object> it = propMap.getValues().iterator(); it.hasNext();) {
                Object condition = it.next();
                ccIt.add(constructCriteriaValue(null, condition));
            }
            if (ccIt.size() > 1) {
                if (propValue instanceof ConditionAndSet) {
                    crit = Restrictions.and(ccIt.get(0), ccIt.get(1));
                    for (int i = 2; i < ccIt.size(); i++) {
                        crit = Restrictions.and(crit, ccIt.get(i));
                    }
                } else {
                    crit = Restrictions.or(ccIt.get(0), ccIt.get(1));
                    for (int i = 2; i < ccIt.size(); i++) {
                        crit = Restrictions.or(crit, ccIt.get(i));
                    }
                }
            } else if (ccIt.size() == 1) {
                crit = ccIt.get(0);
            }
        } else
            crit = Restrictions.eq(propertyName, propValue);
        return crit;
    }

    public List queryByHQL(final String hql, final Map<String, Object> queryParam, final String totalCountHql,
                           final PageBean... pageParam) {
        return (List) hibernateTemplate.execute(new org.springframework.orm.hibernate4.HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                if (queryParam != null)
                    query = setParams(query, queryParam);
                if (pageParam.length == 1 && pageParam[0] != null) {
                    if (totalCountHql != null)// 如果带了需要查询总数的HQL，查询总数设置到pagebean中
                    {
                        Query countQuery = session.createQuery(totalCountHql);
                        if (queryParam != null)
                            countQuery = setParams(countQuery, queryParam);
                        Integer count = Integer.parseInt(countQuery.uniqueResult() + "");
                        pageParam[0].setRecordsCount(count);
                    }
                    int start = pageParam[0].getCurrentPage() - 1;
                    start = start >= 0 ? start : 0;
                    query.setFirstResult(start * pageParam[0].getMaxRecords()).setMaxResults(
                            pageParam[0].getMaxRecords());
                }
                return query.list();
            }
        });
    }

    private Query setParams(Query resultQuery, Map queryParam) {
        // 设置查询参数
        for (Map.Entry<String, Object> entry : (Set<Map.Entry<String, Object>>) queryParam.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Collection) {
                Collection collection = (Collection) value;
                if (collection.size() < MAX_PARAM_COLLECTION_SIZE) {
                    resultQuery.setParameterList(key, (Collection) value);
                } else {
                    String collectionStr = "'" + StringUtils.join(collection, "','") + "'";
                    resultQuery.setParameter(key, collectionStr);
                }
            }else if(value instanceof Object[]){
                resultQuery.setParameterList(key, (Object[])value);
            } else {
                resultQuery.setParameter(key, value);
            }

        }
        return resultQuery;
    }
}
