package cn.flus.cms.core.dto.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果数据结构封装
 * 
 * @author zhoux 2013-10-18
 * @param <T>
 */
public class PagedList<T> implements Serializable {

    private static final long serialVersionUID = -6043503660626141583L;

    private List<T>           list;                                    // 分页数据存放容器
    private Page              page;                                    // 分页对象

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
        return "PagedList [list=" + list + ", page=" + page + "]";
    }
}
