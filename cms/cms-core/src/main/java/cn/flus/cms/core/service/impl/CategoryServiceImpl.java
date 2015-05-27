package cn.flus.cms.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.flus.cms.core.dao.CategoryDao;
import cn.flus.cms.core.dao.domain.CategoryEntity;
import cn.flus.cms.core.enums.CategoryStatus;
import cn.flus.cms.core.enums.YesOrNo;
import cn.flus.cms.core.exceptions.CategoryNotFoundException;
import cn.flus.cms.core.service.CategoryService;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    // 根节点的父亲ID
    private static final Integer ROOT_PATENT_ID = 0;

    @Autowired
    private CategoryDao          categoryDao;

    @Override
    public CategoryEntity add(String name, Integer parentId, Boolean allowTopic, Boolean topicAudit,
                              Boolean allowReply, Boolean replyAudit, Integer weight, Long creatorId, String creator) {

        // 判断参数
        Assert.hasText(name);
        Assert.notNull(allowTopic);
        Assert.notNull(allowReply);
        Assert.notNull(topicAudit);
        Assert.notNull(replyAudit);
        Assert.notNull(creatorId);
        Assert.hasText(creator);

        // 设置根节点的父亲ID
        if (parentId == null || parentId <= 0) {
            parentId = ROOT_PATENT_ID;
        }

        // 判断父节点是否存在，并计算当前节点的深度
        Integer depth = 0;
        CategoryEntity parentCategory = null;
        if (parentId > 0) {
            parentCategory = get(parentId);
            if (parentCategory == null) {
                throw new CategoryNotFoundException("category not found, id=" + parentId);
            }
            depth = parentCategory.getDepth() + 1;
        }

        // 组装实体
        CategoryEntity category = new CategoryEntity();
        category.setParentId(parentId);
        category.setDepth(depth);
        category.setName(name);
        category.setStatus(CategoryStatus.ENABLE.getValue());
        if (allowTopic) {
            category.setAllowTopic(YesOrNo.YES.getCode());
        } else {
            category.setAllowTopic(YesOrNo.NO.getCode());
        }
        if (allowReply) {
            category.setAllowReply(YesOrNo.YES.getCode());
        } else {
            category.setAllowReply(YesOrNo.NO.getCode());
        }
        if (topicAudit) {
            category.setTopicAudit(YesOrNo.YES.getCode());
        } else {
            category.setTopicAudit(YesOrNo.NO.getCode());
        }
        if (replyAudit) {
            category.setReplyAudit(YesOrNo.YES.getCode());
        } else {
            category.setReplyAudit(YesOrNo.NO.getCode());
        }
        category.setWeight(weight);
        category.setCreatorId(creatorId);
        category.setCreator(creator);
        category.setCreateTime(new Date());
        category.setDeleteFlag(YesOrNo.NO.getCode());

        // 插入
        Integer id = categoryDao.insert(category);
        category.setId(id);
        return category;
    }

    @Override
    public CategoryEntity update(Integer id, String name, Integer parentId, Integer status, Boolean allowTopic,
                                 Boolean topicAudit, Boolean allowReply, Boolean replyAudit, Integer weight) {
        // 判断参数
        Assert.notNull(id);

        // 读取待修改的节点
        CategoryEntity category = get(id);
        if (category == null) {
            throw new CategoryNotFoundException("category not found, id=" + id);
        }

        // 修改名称
        if (StringUtils.isNotEmpty(name)) {
            category.setName(name);
        }

        // 修改父节点
        if (parentId != null && parentId != category.getParentId()) {

            // 设置根节点的父亲ID
            if (parentId <= 0) {
                parentId = ROOT_PATENT_ID;
            }

            // 判断父节点是否存在，并计算当前节点的深度
            Integer depth = 0;
            CategoryEntity parentCategory = null;
            if (parentId > 0) {
                parentCategory = get(parentId);
                if (parentCategory == null) {
                    throw new CategoryNotFoundException("category not found, id=" + parentId);
                }
                depth = parentCategory.getDepth() + 1;
            }

            // 当前节点的子孙节点的深度全部修改
            Integer depthIncrease = depth - category.getDepth();
            updateChildrenCategoryDepth(id, depthIncrease);

            category.setParentId(parentId);
            category.setDepth(depth);
        }

        // 修改状态
        if (status != null && CategoryStatus.parse(status) != null) {
            category.setStatus(status);
        }

        // 修改评论状态位
        if (allowTopic != null) {
            if (allowTopic) {
                category.setAllowTopic(YesOrNo.YES.getCode());
            } else {
                category.setAllowTopic(YesOrNo.NO.getCode());
            }
        }
        if (allowReply != null) {
            if (allowReply) {
                category.setAllowReply(YesOrNo.YES.getCode());
            } else {
                category.setAllowReply(YesOrNo.NO.getCode());
            }
        }
        if (topicAudit != null) {
            if (topicAudit) {
                category.setTopicAudit(YesOrNo.YES.getCode());
            } else {
                category.setTopicAudit(YesOrNo.NO.getCode());
            }
        }
        if (replyAudit != null) {
            if (replyAudit) {
                category.setReplyAudit(YesOrNo.YES.getCode());
            } else {
                category.setReplyAudit(YesOrNo.NO.getCode());
            }
        }

        // 修改排序权重
        if (weight != null) {
            category.setWeight(weight);
        }

        // 修改
        categoryDao.update(category);
        return category;
    }

    /**
     * 修改子孙节点的深度
     * 
     * @param parentId
     * @param depthIncrease
     */
    private void updateChildrenCategoryDepth(Integer parentId, Integer depthIncrease) {

        // 读取父亲节点所有的子孙节点
        List<Integer> ids = new ArrayList<Integer>();
        getChildrenCategoryIds(parentId, ids);

        // 批量更新
        CategoryEntity category = null;
        for (Integer id : ids) {
            category = get(id);
            category.setDepth(category.getDepth() + depthIncrease);
            categoryDao.update(category);
        }
    }

    private void getChildrenCategoryIds(Integer parentId, List<Integer> ids) {
        List<CategoryEntity> list = getByParentId(parentId, false);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (CategoryEntity e : list) {
            ids.add(e.getId());
            getChildrenCategoryIds(e.getId(), ids);
        }
    }

    @Override
    public CategoryEntity get(Integer id) {
        return categoryDao.get(id);
    }

    @Override
    public List<CategoryEntity> getAllSorted(boolean filterDisable) {

        // 从数据库中获取所有栏目
        List<CategoryEntity> categoryList = getAll(filterDisable);
        if (null == categoryList) {
            return null;
        }

        // 排序
        List<CategoryEntity> resultList = new ArrayList<CategoryEntity>();
        sortCategoryList(categoryList, resultList, 0);
        return resultList;
    }

    /**
     * 获取所有栏目
     * 
     * @param filterDisable
     * @return
     */
    private List<CategoryEntity> getAll(boolean filterDisable) {
        if (filterDisable) {
            return categoryDao.getBySelective(null, CategoryStatus.ENABLE.getValue());
        }
        return categoryDao.getBySelective(null, null);
    }

    /**
     * 递归排序
     * 
     * @param source
     * @param dest
     * @param parentId
     */
    private void sortCategoryList(List<CategoryEntity> source, List<CategoryEntity> dest, Integer parentId) {

        // 取出父ID为parentId的栏目并排序
        List<CategoryEntity> temp = new ArrayList<CategoryEntity>();
        for (CategoryEntity e : source) {
            if (parentId.equals(e.getParentId())) {
                temp.add(e);
            }
        }
        Collections.sort(temp);

        // 递归
        for (CategoryEntity e : temp) {
            dest.add(e);
            sortCategoryList(source, dest, e.getId());
        }
    }

    @Override
    public List<CategoryEntity> getByParentId(Integer parentId, boolean filterDisable) {
        if (filterDisable) {
            return categoryDao.getBySelective(parentId, CategoryStatus.ENABLE.getValue());
        }
        return categoryDao.getBySelective(parentId, null);
    }

    @Override
    public void delete(Integer[] ids) {
        categoryDao.updateDeleteFlag(ids);
    }

    @Override
    public void delete(Integer id) {
        delete(new Integer[] { id });
    }

}
