package cn.flus.account.core.dao.mapper;

import cn.flus.account.core.dao.domain.AccountUserEntity;

public interface AccountUserEntityMapper {

    int insert(AccountUserEntity record);

    AccountUserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountUserEntity record);

    AccountUserEntity selectByLoginname(String loginname);

    AccountUserEntity selectByEmail(String email);

    AccountUserEntity selectByMobile(String mobile);
}
