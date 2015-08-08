package cn.flus.account.web.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.flus.account.core.dao.domain.AccountUserEntity;
import cn.flus.account.core.dto.SigninUser;
import cn.flus.account.core.service.SigninUserService;

/**
 * 登录
 * 
 * @author singo
 */
@Service("signinExecutor")
public class SigninExecutor {

    private static final Logger logger         = LoggerFactory.getLogger(SigninExecutor.class);

    // 记录用户登录的唯一键，存放在Cookie中
    private static final String SIGNIN_USER_CK = "sn_k";

    @Value("${signin.domain}")
    private String              signinDomain;

    @Autowired
    private SigninUserService   signinUserService;

    public SigninUser signin(AccountUserEntity u, HttpServletResponse response) {

        // 拼装登录的用户信息
        SigninUser signinUser = new SigninUser();
        signinUser.setId(u.getId());
        signinUser.setLoginname(u.getLoginname());
        signinUser.setNickname(u.getNickname());
        signinUser.setEmail(u.getEmail());
        signinUser.setMobile(u.getMobile());

        // 生成键值
        String uk = UIdUtils.generate();

        // 持久化登录用户
        signinUserService.put(uk, signinUser);

        // 设置cookie
        Cookie cookie = new Cookie(SIGNIN_USER_CK, uk);
        cookie.setDomain(signinDomain);
        cookie.setPath("/");
        response.addCookie(cookie);

        logger.info(signinUser + ", has signin.");
        return signinUser;
    }

    /**
     * 获取已经登录的用户信息
     * 
     * @param httpRequest
     * @return
     */
    public SigninUser getSignin(HttpServletRequest httpRequest) {
        String uk = CookieUtils.getValue(httpRequest, SIGNIN_USER_CK);
        if (StringUtils.isBlank(uk)) {
            return null;
        }
        return signinUserService.get(uk);
    }
}
