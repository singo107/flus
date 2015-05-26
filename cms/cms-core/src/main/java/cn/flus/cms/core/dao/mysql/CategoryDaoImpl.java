package cn.flus.cms.core.dao.mysql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.flus.cms.core.dao.CategoryDao;
import cn.flus.cms.core.dao.domain.CategoryEntity;
import cn.flus.cms.core.dao.mapper.CategoryEntityMapper;

@Service("categoryDao")
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private CategoryEntityMapper categoryEntityMapper;

    @Override
    public CategoryEntity get(Integer id) {
        return categoryEntityMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer insert(CategoryEntity entity) {
        categoryEntityMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public Integer update(CategoryEntity entity) {
        return categoryEntityMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public Integer updateDeleteFlag(Integer[] ids) {
        return categoryEntityMapper.updateDeleteFlag(ids);
    }

    @Override
    public List<CategoryEntity> getBySelective(Integer parentId, Integer status) {
        return categoryEntityMapper.selectBySelective(parentId, status);
    }

}
