package com.jlu.common.db.bean;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.9 $
 */
public class PageBean implements java.io.Serializable {
    private static final long serialVersionUID = 693435998917420886L;
    public static final int FIRST_PAGE_NUM = 1;
    private Map<String,Object> parameterMap; //生成分页时所带的参数键值对
    private int currentPage = FIRST_PAGE_NUM; //当前页数
    private int recordsCount = -1; //总记录数
    private Integer maxRecords = 15;; //一次查询的最大记录数
    private int pageCount; // 总页面数
    private Integer wallMaxRecordS = 500; //卡片墙一次查询的最大记录数
    /**
     * Creates a new PageBean object.
     */
    public PageBean() {
    }

    /**
     * Creates a new PageBean object.
     *
     * @param currentPage DOCUMENT ME!
     * @param maxRecords DOCUMENT ME!
     */
    public PageBean(int currentPage, int maxRecords) {
        this.currentPage = currentPage;
        this.maxRecords = maxRecords;
    }
    /**
     * Creates a new PageBean object.
     *
     * @param maxRecords DOCUMENT ME!
     */
    public PageBean(int maxRecords) {
        this.maxRecords = maxRecords;
    }

    /**
     * Creates a new PageBean object.
     *
     * @param currentPage DOCUMENT ME!
     * @param recordsCount DOCUMENT ME!
     * @param params DOCUMENT ME!
     */
    public PageBean(int currentPage, int recordsCount, Map<String,Object> params) {
        this.currentPage = currentPage;
        this.recordsCount = recordsCount;
        this.parameterMap = params;
    }

    /**
     * DOCUMENT ME!
     *
     * @param key DOCUMENT ME!
     * @param value DOCUMENT ME!
     */
    public void putParameter(String key, Object value) {
        if (parameterMap == null) {
            parameterMap = new HashMap<String,Object>();
        }

        parameterMap.put(key, value);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getRecordsCount() {
        return recordsCount;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Integer getMaxRecords() {
        return maxRecords;
    }

    /**
     * 获取总记录数
     *
     * @param currentPage DOCUMENT ME!
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 设置总记录数
     *
     * @param recordsCount DOCUMENT ME!
     */
    public void setRecordsCount(int recordsCount) {
        this.recordsCount = recordsCount;
    }

    /**
     * 一次查询的最大记录数
     *
     * @param parameterMap DOCUMENT ME!
     */
    public void setParameterMap(Map<String,Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    /**
     * 设置一次查询的最大记录数
     *
     * @param maxRecords 记录数
     */
    public void setMaxRecords(Integer maxRecords) {
        this.maxRecords = maxRecords;
    }

    /**
     * DOCUMENT ME!
     */
    public void incrementCurrentPage() {
        this.currentPage++;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("currentPage", currentPage).append("maxRecords", maxRecords)
                .append("recordsCount", recordsCount).append("parameterMap", parameterMap)
                .toString();
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        if(pageCount <= 0){
            pageCount = recordsCount / maxRecords;
            if(recordsCount % maxRecords > 0){
                pageCount += 1;
            }
        }
        return pageCount;
    }

    public Integer getWallMaxRecordS() {
        return wallMaxRecordS;
    }

}

