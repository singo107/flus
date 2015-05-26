package cn.flus.cms.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.flus.cms.core.dao.domain.CategoryEntity;

public interface CategoryEntityMapper {

    int insert(CategoryEntity record);

    CategoryEntity selectByPrimaryKey(Integer id);

    List<CategoryEntity> selectBySelective(@Param("parentId") Integer parentId, @Param("status") Integer status);

    int updateByPrimaryKeySelective(CategoryEntity record);

    int updateDeleteFlag(@Param("ids") Integer[] ids);
}
