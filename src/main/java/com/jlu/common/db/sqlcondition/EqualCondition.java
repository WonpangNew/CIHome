package com.jlu.common.db.sqlcondition;

/**
 * Created by Wonpang New on 2016/9/6.
 */
public class EqualCondition extends CompareCondition{

    public EqualCondition(String propertyName, Object value) {
        super(propertyName, value);
    }
}
