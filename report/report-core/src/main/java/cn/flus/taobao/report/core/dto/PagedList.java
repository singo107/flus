package cn.flus.taobao.report.core.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 分页结果数据结构封装
 * 
 * @author zhoux 2013-5-6
 * @param <T>
 */
public class PagedList<T> implements Serializable {

    private static final long serialVersionUID = 6120119383140852450L;
    private List<T>           list;                                   // 分页数据存放容器
    private Page              page;                                   // 分页对象

    public PagedList() {
    }

    public PagedList(List<T> list, Page page) {
        this.list = list;
        this.page = page;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
