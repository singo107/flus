package cn.flus.taobao.report.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;

/**
 * @author zhoux 2013-5-28
 */
@Service("taobaoClientInstance")
public class TaobaoClientInstance {

    @Value("${taobao.api.url}")
    private String url;

    @Value("${taobao.api.appkey}")
    private String appkey;

    @Value("${taobao.api.secret}")
    private String secret;

    /**
     * 获取默认的淘宝客户端
     * 
     * @return
     */
    public TaobaoClient getDefaultClient() {
        return new DefaultTaobaoClient(url, appkey, secret);
    }
}
