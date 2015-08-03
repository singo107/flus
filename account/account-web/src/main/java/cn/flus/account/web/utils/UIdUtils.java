package cn.flus.account.web.utils;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

/**
 * 唯一ID生成器
 * 
 * @author singo
 */
public class UIdUtils {

    /**
     * 使用JAVA的UUID
     * 
     * @return
     */
    public static String generate() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }
}
