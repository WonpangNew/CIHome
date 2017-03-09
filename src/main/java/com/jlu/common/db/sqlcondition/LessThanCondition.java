package com.jlu.common.db.sqlcondition;

/**
 * Created by Wonpang New on 2016/9/6.
 */
public class LessThanCondition extends CompareCondition{
    /**
     * Creates a new LessThanCondition object.
     *
     * @param propertyName DOCUMENT ME!
     * @param value DOCUMENT ME!
     */
    public LessThanCondition(String propertyName, Object value) {
        super(propertyName, value);
    }
}
