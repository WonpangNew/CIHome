package com.jlu.common.db.sqlcondition;

/**
 * Created by Wonpang New on 2016/9/6.
 */
public class AscOrder extends OrderCondition{
    private static final long serialVersionUID = -2923948637027588374L;

    /**
     * Creates a new AscOrder object.
     *
     * @param propertyName DOCUMENT ME!
     */
    public AscOrder(String propertyName) {
        super(propertyName);
        super.setAscOrder(true);
    }

    /**
     * Creates a new AscOrder object.
     */
    public AscOrder() {
    }
}
