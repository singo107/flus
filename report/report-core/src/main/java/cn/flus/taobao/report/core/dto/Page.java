package cn.flus.taobao.report.core.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 分页实体
 * 
 * @author zhoux 2013-5-6
 */
public class Page implements Serializable {

    private static final long serialVersionUID  = -4173962911612597911L;

    // 默认每页显示条目数
    private static final int  DEFAULT_PAGE_SIZE = 5;

    private int               pageNo            = 1;                    // 页码
    private int               pageSize          = DEFAULT_PAGE_SIZE;    // 每页显示数量
    private int               totalCount        = -1;                   // 总记录数

    public Page() {
    }

    public Page(int pageSize) {
        this.pageSize = pageSize;
    }

    public Page(int pageNo, int pageSize) {
        if (pageNo > 0) {
            this.pageNo = pageNo;
        }
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }
    }

    /**
     * 获得当前页的页号,序号从1开始,默认为1
     * 
     * @return
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1
     * 
     * @param pageNo
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
        if (pageNo < 1) {
            this.pageNo = 1;
        }
    }

    /**
     * 获得每页显示数量
     * 
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页的记录数量
     * 
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始
     * 
     * @return
     */
    public int getStartIndex() {
        return (pageNo - 1) * pageSize;
    }

    /**
     * 获得总记录数, 默认值为-1
     * 
     * @return
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数
     * 
     * @param totalCount
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        // 判断指定的页码数是否大于最大页数，如果大于，则设置当前页为最后页
        int totalPages = getTotalPages();
        if (pageNo > totalPages) {
            setPageNo(totalPages);
        }
    }

    /**
     * 根据pageSize与totalCount计算总页数, 默认值为-1
     * 
     * @return
     */
    public int getTotalPages() {
        if (totalCount < 0) {
            return -1;
        }
        int totalPages = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            totalPages++;
        }
        return totalPages;
    }

    /**
     * 是否还有下一页
     * 
     * @return
     */
    public boolean hasNext() {
        return (pageNo + 1 <= getTotalPages());
    }

    /**
     * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号
     * 
     * @return
     */
    public int getNextPage() {
        if (hasNext()) {
            return pageNo + 1;
        } else {
            return pageNo;
        }
    }

    /**
     * 是否还有上一页
     * 
     * @return
     */
    public boolean hasPre() {
        return (pageNo - 1 >= 1);
    }

    /**
     * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号
     * 
     * @return
     */
    public int getPrePage() {
        if (hasPre()) {
            return pageNo - 1;
        } else {
            return pageNo;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
