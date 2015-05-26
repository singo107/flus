package cn.flus.cms.core.dto;

import java.util.Set;

import cn.flus.cms.core.dao.domain.CategoryEntity;

/**
 * @author zhouxing
 */
public class CategoryNode implements Comparable<CategoryNode> {

    private CategoryEntity    categoryEntity;
    private Set<CategoryNode> childSet;

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public Set<CategoryNode> getChildSet() {
        return childSet;
    }

    public void setChildSet(Set<CategoryNode> childSet) {
        this.childSet = childSet;
    }

    @Override
    public int compareTo(CategoryNode o) {
        int result;
        result = categoryEntity.getWeight().intValue() > o.getCategoryEntity().getWeight().intValue() ? 1 : (categoryEntity.getWeight().intValue() == o.getCategoryEntity().getWeight().intValue() ? 0 : -1);
        if (result == 0) {
            result = categoryEntity.getId().intValue() > o.getCategoryEntity().getId().intValue() ? 1 : (categoryEntity.getId().intValue() == o.getCategoryEntity().getId().intValue() ? 0 : -1);
        }
        return result;
    }
}
