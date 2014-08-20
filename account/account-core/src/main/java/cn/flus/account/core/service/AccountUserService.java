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

    /**
     * 根据登录名读取用户
     * 
     * @param loginname
     * @return
     */
    AccountUserEntity getByLoginname(String loginname);

    /**
     * 注册
     * 
     * @param loginname
     * @param password
     * @return
     */
    AccountUserEntity signup(String loginname, String password);

    /**
     * 判断用户名是否占用
     * 
     * @param loginname
     * @return
     */
    boolean checkExist(String loginname);
}
