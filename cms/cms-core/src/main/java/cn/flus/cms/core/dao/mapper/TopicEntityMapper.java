package cn.flus.cms.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.flus.cms.core.dao.domain.TopicEntity;

public interface TopicEntityMapper {

    int insert(TopicEntity record);

    TopicEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TopicEntity record);

    int updateDeleteFlag(@Param("ids") Integer[] ids);

    int countSelectByOrderdCategoryId(@Param("categoryId") Integer appKey, @Param("status") Integer status);

    List<TopicEntity> selectByOrderdCategoryId(@Param("categoryId") Integer categoryId,
                                               @Param("status") Integer status, @Param("orderFiled") String orderFiled,
                                               @Param("orderType") String orderType, @Param("start") Integer start,
                                               @Param("size") Integer size);
}
