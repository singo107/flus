/*
 * Copyright 2012-2014 glodon paas All right reserved. This software is the confidential and proprietary information of
 * glodon paas ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with glodon paas.
 */
package cn.flus.taobao.report.core;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ProductGetRequest;
import com.taobao.api.response.ProductGetResponse;

/**
 * @author zhoux 2013-5-27
 */
public class MiscTest {

    // 沙箱环境调用地址
    private static String url    = "http://gw.api.tbsandbox.com/router/rest";

    private static String appkey = "21520195";
    private static String secret = "59581aff0f62e2f73cb6dd3d186da224";

    public static void testUserGet() {
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        ProductGetRequest req = new ProductGetRequest();
        req.setFields("product_id,outer_id");
        req.setProductId(2000037442647L);
        try {
            ProductGetResponse response = client.execute(req);
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MiscTest.testUserGet();
    }

}
