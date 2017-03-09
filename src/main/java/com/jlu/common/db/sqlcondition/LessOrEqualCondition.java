package com.jlu.common.db.sqlcondition;

/**
 * Created by Wonpang New on 2016/9/6.
 */
public class LessOrEqualCondition extends CompareCondition {
    /**
     * Creates a new LessOrEqualCondition object.
     *
     * @param propertyName DOCUMENT ME!
     * @param value DOCUMENT ME!
     */
    public LessOrEqualCondition(String propertyName, Object value) {
        super(propertyName, value);
    }
}
