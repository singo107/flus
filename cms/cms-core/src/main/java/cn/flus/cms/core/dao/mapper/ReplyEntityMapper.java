package cn.flus.cms.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.flus.cms.core.dao.domain.ReplyEntity;

public interface ReplyEntityMapper {

    int insert(ReplyEntity record);

    List<ReplyEntity> selectByOrderdTopicId(@Param("topicId") Integer topicId, @Param("status") Integer status,
                                            @Param("start") Integer start, @Param("size") Integer size);

    int countSelectByOrderdTopicId(@Param("topicId") Integer topicId, @Param("status") Integer status);

    int updateStatus(@Param("ids") Integer[] ids, @Param("status") Integer status);

    int updateRecommend(@Param("ids") Integer[] ids, @Param("recommend") String recommend);

    int updateDeleteFlag(@Param("ids") Integer[] ids);

}
