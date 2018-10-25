package com.zjg.dev.util;

import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Copyright CHJ
 * @Author HUANGP
 * @Date 2018年4月19日
 * @Desc 分页工具
 */
public class PageUtils<T> implements Serializable {

    public final static String PARAMS_KEY_PAGE_NO = "page";
    public final static String PARAMS_KEY_PAGE_LIMIT = "rows";
    public final static String PARAMS_KEY_PAGE_OFFSET = "offset";
    public final static String PARAMS_KEY_VO_SEARCH_MODE = "vo";
    public final static int DEFAULT_PAGE_SIZE = 20;
    public final static int DEFAULT_PAGE = 1;
    private static final long serialVersionUID = 3297445200287541862L;

    // 一页显示的记录数
    /**
     * The limit.
     */
    private int pageSize = DEFAULT_PAGE_SIZE;

    // 记录总数
    /**
     * The total rows.
     */
    private long totalRows;

    // 当前页码
    /**
     * The page no.
     */
    private int pageNo = DEFAULT_PAGE;

    // 结果集存放List
    /**
     * The result list.
     */
    private List<T> resultList;

    @Setter
    private T query;

    public PageUtils(int pageSize, long totalRows, int pageNo, List<T> resultList) {
        super();
        this.pageSize = (pageSize == 0 ? DEFAULT_PAGE_SIZE : pageSize);
        this.totalRows = totalRows;
        this.pageNo = (pageNo == 0 ? DEFAULT_PAGE : pageNo);
        this.resultList = resultList;
    }

    public PageUtils() {
    }

    public static <T> PageUtils<T> getInstance(int pageNo, int pageSize) {
        PageUtils<T> pageUtils = new PageUtils<T>();
        pageUtils.pageNo = pageNo;
        pageUtils.pageSize = pageSize;
        return pageUtils;
    }

    public static <T> PageUtils<T> getInstance(int pageNo, int pageSize, T query) {
        PageUtils<T> pageUtils = new PageUtils<T>();
        pageUtils.pageNo = pageNo;
        pageUtils.pageSize = pageSize;
        pageUtils.query = query;
        return pageUtils;
    }

    /**
     * Gets the result list.
     *
     * @return the result list
     */
    public List<T> getResultList() {
        return resultList;
    }

    /**
     * Sets the result list.
     *
     * @param resultList the new result list
     */
    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    // 计算总页数

    /**
     * Gets the total pages.
     *
     * @return the total pages
     */
    public long getTotalPages() {
        long totalPages;
        if (totalRows % pageSize == 0) {
            totalPages = totalRows / pageSize;
        } else {
            totalPages = (totalRows / pageSize) + 1;
        }
        return totalPages;
    }

    /**
     * Gets the total rows.
     *
     * @return the total rows
     */
    public long getTotalRows() {
        return totalRows;
    }

    /**
     * Sets the total rows.
     *
     * @param totalRows the new total rows
     */
    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    /**
     * Gets the offset.
     *
     * @return the offset
     */
    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }

    /**
     * Gets the end index.
     *
     * @return the end index
     */
    public long getEndIndex() {
        if (getOffset() + pageSize > totalRows) {
            return totalRows;
        } else {
            return getOffset() + pageSize;
        }
    }

    /**
     * Gets the page no.
     *
     * @return the page no
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * Sets the page no.
     *
     * @param pageNo the new page no
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public boolean hasNextPage() {
        boolean hasNextPage = false;
        if ((pageNo * pageSize) < totalRows) {
            hasNextPage = true;
        }
        return hasNextPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
