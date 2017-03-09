package com.jlu.common.db.sqlcondition;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Wonpang New on 2016/9/6.
 */
public class OrderCondition {
    private static final long serialVersionUID = 131982036485132446L;
    private String propertyName;
    private boolean ascOrder;

    /**
     * Creates a new OrderCondition object.
     */
    public OrderCondition() {
    }

    /**
     * 只有子类或本包可以使用,子类需说明orderby顺序
     *
     * @param property String
     */
    protected OrderCondition(String property) {
        this.propertyName = property;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param propertyName DOCUMENT ME!
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isAscOrder() {
        return ascOrder;
    }

    /**
     * DOCUMENT ME!
     *
     * @param ascOrder DOCUMENT ME!
     */
    public void setAscOrder(boolean ascOrder) {
        this.ascOrder = ascOrder;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("propertyName", this.propertyName)
                .append("ascOrder", this.ascOrder).toString();
    }
}
