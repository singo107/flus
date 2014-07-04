package cn.flus.account.core.service;

import cn.flus.account.core.dao.domain.AccountUserEntity;

/**
 * @author zhouxing
 */
public interface AccountUserService {

    /**
     * 读取用户
     * 
     * @param id
     * @return
     */
    AccountUserEntity getById(Integer id);

}
