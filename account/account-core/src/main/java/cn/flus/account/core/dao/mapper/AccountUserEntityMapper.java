package cn.flus.account.core.dao.mapper;

import cn.flus.account.core.dao.domain.AccountUserEntity;

/**
 * @author zhouxing
 */
public interface AccountUserEntityMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(AccountUserEntity record);

    int insertSelective(AccountUserEntity record);

    AccountUserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountUserEntity record);

    int updateByPrimaryKey(AccountUserEntity record);
}
