package cn.flus.account.core.dao;

import cn.flus.account.core.dao.domain.AccountUserEntity;

/**
 * @author singo
 */
public interface AccountUserDao {

    public Integer insert(AccountUserEntity entity);

    public AccountUserEntity get(Integer id);

    public AccountUserEntity getByLoginname(String loginname);

    public AccountUserEntity getByEmail(String email);

    public AccountUserEntity getByMobile(String mobile);

}
