package cn.flus.account.core.dao;

import cn.flus.account.core.dao.domain.AccountUserEntity;

/**
 * @author zhouxing
 */
public interface AccountUserDao {

    public Integer insert(AccountUserEntity entity);

    public AccountUserEntity get(Integer id);
}
