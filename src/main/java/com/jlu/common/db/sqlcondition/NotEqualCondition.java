package com.jlu.common.db.sqlcondition;

/**
 * Created by Wonpang New on 2016/9/6.
 */
public class NotEqualCondition extends CompareCondition{
    /**
     * Creates a new NotEqualCondition object.
     *
     * @param propertyName DOCUMENT ME!
     * @param value DOCUMENT ME!
     */
    public NotEqualCondition(String propertyName, Object value) {
        super(propertyName, value);
    }
}
