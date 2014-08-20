package cn.flus.account.core.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.flus.account.core.dao.AccountUserDao;
import cn.flus.account.core.dao.domain.AccountUserEntity;
import cn.flus.account.core.dao.mapper.AccountUserEntityMapper;

/**
 * @author zhouxing
 */
@Service("accountUserDao")
public class AccountUserDaoImpl implements AccountUserDao {

    @Autowired
    private AccountUserEntityMapper accountUserEntityMapper;

    @Override
    public Integer insert(AccountUserEntity entity) {
        accountUserEntityMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public AccountUserEntity get(Integer id) {
        return accountUserEntityMapper.selectByPrimaryKey(id);
    }

    @Override
    public AccountUserEntity getByLoginname(String loginname) {
        return accountUserEntityMapper.selectByLoginname(loginname);
    }

    @Override
    public AccountUserEntity getByEmail(String email) {
        return accountUserEntityMapper.selectByEmail(email);
    }

    @Override
    public AccountUserEntity getByMobile(String mobile) {
        return accountUserEntityMapper.selectByMobile(mobile);
    }
}
