package cn.flus.account.core.service;

import cn.flus.account.core.entity.AccountUserEntity;

/**
 * 调用用户
 * 
 * @author zhouxing 2013-5-2
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
