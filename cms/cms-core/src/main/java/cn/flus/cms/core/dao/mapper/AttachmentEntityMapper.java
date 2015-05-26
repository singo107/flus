package cn.flus.cms.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.flus.cms.core.dao.domain.AttachmentEntity;

public interface AttachmentEntityMapper {

    int insert(AttachmentEntity record);

    int updateDeleteFlag(@Param("ids") Integer[] ids);

    int countSelectBySelective(@Param("title") String title, @Param("url") String url);

    List<AttachmentEntity> selectBySelective(@Param("title") String title, @Param("url") String url,
                                             @Param("start") Integer start, @Param("size") Integer size);

}
