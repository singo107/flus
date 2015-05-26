package cn.flus.cms.core.dao;

import java.util.List;

import cn.flus.cms.core.dao.domain.CategoryEntity;

public interface CategoryDao {

    CategoryEntity get(Integer id);

    Integer insert(CategoryEntity entity);

    Integer update(CategoryEntity entity);

    Integer updateDeleteFlag(Integer[] ids);

    List<CategoryEntity> getBySelective(Integer parentId, Integer status);

}
