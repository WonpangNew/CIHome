package com.jlu.common.db.sqlcondition;

/**
 * Created by Wonpang New on 2016/9/6.
 */
public class CompareCondition {
    private String propertyName;
    private Object minValue;
    private Object maxValue;
    private Object value;

    /**
     * Creates a new CompareCondition object.
     *
     * @param propertyName DOCUMENT ME!
     */
    protected CompareCondition(String propertyName, Object...objs) {
        this.propertyName = propertyName;

        switch (objs.length) {
            case 1:
                value = objs[0];

                break;

            case 2:
                minValue = objs[0];
                maxValue = objs[1];

                break;

            default:
                throw new IllegalArgumentException("构建器参数个数不支持！");
        }
    }

    public Object getMaxValue() {
        return maxValue;
    }

    public Object getMinValue() {
        return minValue;
    }

    public Object getValue() {
        return value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setMaxValue(Object maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(Object minValue) {
        this.minValue = minValue;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((maxValue == null) ? 0 : maxValue.hashCode());
        result = prime * result
                + ((minValue == null) ? 0 : minValue.hashCode());
        result = prime * result
                + ((propertyName == null) ? 0 : propertyName.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CompareCondition other = (CompareCondition) obj;
        if (maxValue == null) {
            if (other.maxValue != null)
                return false;
        } else if (!maxValue.equals(other.maxValue))
            return false;
        if (minValue == null) {
            if (other.minValue != null)
                return false;
        } else if (!minValue.equals(other.minValue))
            return false;
        if (propertyName == null) {
            if (other.propertyName != null)
                return false;
        } else if (!propertyName.equals(other.propertyName))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
}
