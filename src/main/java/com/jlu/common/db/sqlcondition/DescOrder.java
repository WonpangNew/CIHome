package com.jlu.common.db.sqlcondition;

/**
 * Created by Wonpang New on 2016/9/6.
 */
public class DescOrder extends OrderCondition {
    private static final long serialVersionUID = -2473406369581360611L;

    /**
     * Creates a new DescOrder object.
     *
     * @param propertyName DOCUMENT ME!
     */
    public DescOrder(String propertyName) {
        super(propertyName);
        super.setAscOrder(false);
    }

    /**
     * Creates a new DescOrder object.
     */
    public DescOrder() {
    }
}
