package cn.flus.viewer.core.service;

/**
 * 应用认证
 * 
 * @author singo
 */
public interface AuthenticationService {

    /**
     * 获取Token
     * 
     * @return
     */
    String fetchAccessToken();
}
