package com.jlu.common.db.sqlcondition;

/**
 * Created by Wonpang New on 2016/9/6.
 */
public class GreaterCondition extends CompareCondition {
    /**
     * Creates a new GreaterCondition object.
     *
     * @param propertyName DOCUMENT ME!
     * @param value DOCUMENT ME!
     */
    public GreaterCondition(String propertyName, Object value) {
        super(propertyName, value);
    }
}
