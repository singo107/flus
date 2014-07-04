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
        return accountUserEntityMapper.insert(entity);
    }

    @Override
    public AccountUserEntity get(Integer id) {
        return accountUserEntityMapper.selectByPrimaryKey(id);
    }
}
