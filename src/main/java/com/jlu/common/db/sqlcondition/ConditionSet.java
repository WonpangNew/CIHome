package com.jlu.common.db.sqlcondition;

import java.util.*;

/**
 * Created by Wonpang New on 2016/9/6.
 */
public abstract class ConditionSet {
    private static final long serialVersionUID = -110803228763256709L;
    private Set<Object> values=new HashSet<Object>();
    /**
     * groupBy属性列
     */
    private List<String> groups=new ArrayList<String>();

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public void addGroupBy(String propertyName){
        groups.add(propertyName);
    }

    public ConditionSet(){
    }

    public ConditionSet(String key,Object value){
        put(key, value);
    }

    public ConditionSet(CompareCondition condition){
        this.addCompareCondition(condition);
    }

    /**
     * put并返回当前map，可连续对map进行操作
     * @param key
     * @param value
     * @return
     */
    public final ConditionSet put(String key, Object value) {

        if (value instanceof CompareCondition) {
            this.addCompareCondition((CompareCondition)value);
        }
        else if(value instanceof ConditionSet)
        {
            this.addConditionSet((ConditionSet)value);
        }
        else {
            this.addCompareCondition(new EqualCondition(key, value));
        }
        return this;
    }


    public ConditionSet addCompareCondition(CompareCondition condition){
        values.add(condition);
        return this;
    }

    public ConditionSet addConditionSet(ConditionSet conditionSet){
        //增加自己同类型时，自动打平合并
        if (this.getClass().equals(conditionSet.getClass())) {
            for (Iterator<Object> it = conditionSet.getValues().iterator(); it
                    .hasNext();) {
                Object obj = it.next();
                this.put(null, obj);
            }
        }
        else{
            values.add(conditionSet);
        }
        return this;
    }

    /**
     * 返回所有的集合中的对象信息。普通的列与值通过equal条件返回
     * @return
     */
    public Set<Object> getValues()
    {
        return this.values;

    }

    public int size(){
        return this.values.size();
    }
}
