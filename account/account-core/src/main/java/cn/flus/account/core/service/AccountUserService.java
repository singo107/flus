package cn.flus.account.core.service;

import cn.flus.account.core.dao.domain.AccountUserEntity;

/**
 * 账号
 * 
 * @author singo
 */
public interface AccountUserService {

    /**
     * 读取用户的详细信息
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
     * 密码校验
     * 
     * @param loginname
     * @param password
     * @return
     */
    boolean checkPassword(String loginname, String password);

    /**
     * 密码校验
     * 
     * @param entity
     * @param password
     * @return
     */
    boolean checkPassword(AccountUserEntity entity, String password);

    /**
     * 判断用户名是否占用
     * 
     * @param loginname
     * @return
     */
    boolean checkExist(String loginname);
}
