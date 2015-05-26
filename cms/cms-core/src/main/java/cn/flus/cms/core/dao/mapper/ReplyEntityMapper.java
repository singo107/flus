package cn.flus.cms.core.dao.mapper;

import cn.flus.cms.core.dao.domain.ReplyEntity;

public interface ReplyEntityMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ReplyEntity record);

    int insertSelective(ReplyEntity record);

    ReplyEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReplyEntity record);

    int updateByPrimaryKeyWithBLOBs(ReplyEntity record);

    int updateByPrimaryKey(ReplyEntity record);
}
