package cn.flus.account.core.dao;

import cn.flus.account.core.dao.domain.AccountUserEntity;

/**
 * @author singo
 */
public interface AccountUserDao {

    Integer insert(AccountUserEntity entity);

    AccountUserEntity get(Integer id);

    AccountUserEntity getByLoginname(String loginname);

    AccountUserEntity getByEmail(String email);

    AccountUserEntity getByMobile(String mobile);

}
