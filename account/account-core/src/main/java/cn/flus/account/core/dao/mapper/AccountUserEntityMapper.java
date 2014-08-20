package cn.flus.account.core.dao.mapper;

import cn.flus.account.core.dao.domain.AccountUserEntity;

public interface AccountUserEntityMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(AccountUserEntity record);

    int insertSelective(AccountUserEntity record);

    AccountUserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountUserEntity record);

    int updateByPrimaryKey(AccountUserEntity record);

    AccountUserEntity selectByLoginname(String loginname);

    AccountUserEntity selectByEmail(String email);

    AccountUserEntity selectByMobile(String mobile);
}
