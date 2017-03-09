package com.jlu.common.db.sqlcondition;

/**
 * Created by Wonpang New on 2016/9/6.
 */
public class NotInCondition extends CompareCondition {

    /**
     * Creates a new NotInCondition object.
     *
     * @param propertyName DOCUMENT ME!
     */
    public NotInCondition(String propertyName, Object obj) {
        super(propertyName, obj);
    }
}
