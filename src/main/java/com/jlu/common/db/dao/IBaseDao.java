package com.jlu.common.db.dao;

import com.jlu.common.db.sqlcondition.ConditionSet;
import com.jlu.common.db.sqlcondition.OrderCondition;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by Wonpang New on 2016/9/6.
 *
 * 数据库基本操作接口
 */
@SuppressWarnings("rawtypes")
public interface IBaseDao<C> {

    /**
     * 保存对象
     * @param object
     */
    void save(C object);

    /**
     * 删除对象
     * @param object
     */
    void delete(C object);

    /**
     * 更新对象
     * @param object
     */
    void update(C object);

    /**
     * 保存或更新对象
     * @param object
     */
    void saveOrUpdate(C object);

    /**
     * 批量删除
     * @param objects
     */
    void deleteBatch(Collection<C> objects);

    /**
     * 批量保存或更新
     *
     * @param objects
     */
    void saveOrUpdateAll(Collection<C> objects);

    /**
     * 通过id查找
     * @param id
     * @return
     */
    C findById(Serializable id);

    /**
     * 根据属性查找，属性对应的值可以支持含有like条件
     * @param conditionSet
     * @return Collection
     */
    List<C> findByProperties(ConditionSet conditionSet);

    /**
     * 根据属性查找,并指定排序列，属性对应的值可以支持含有like条件，以及是com.baidu.iit.dao.sqlcondition下的对象。
     *
     * @param properties
     *            Map
     * @param orderByProperties
     *            OrderBy[] 需要排序的属性名，
     * @return Collection
     */
    List<C> findByProperties(ConditionSet properties, List<OrderCondition> orderByProperties);

    /**
     * 根据属性查找,并指定排序列，属性对应的值可以支持含有like条件，以及是com.baidu.iit.dao.sqlcondition下的对象。
     *
     * @param properties
     *            Map
     * @param orderByProperty OrderBy 需要排序的属性名
     * @return Collection
     */
    List<C> findByProperties(ConditionSet properties, OrderCondition orderByProperty);

    /**
     * 查询数据库指定的条目
     *
     * @param properties
     * @param orderByProperties
     * @param offset 开始index
     * @param limit 最大数目
     * @return
     */
    List<C> findHeadByProperties(final ConditionSet properties, final List<OrderCondition> orderByProperties, final int offset, final int limit);
}
