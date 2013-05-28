/*
 * Copyright 2012-2014 glodon paas All right reserved. This software is the confidential and proprietary information of
 * glodon paas ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with glodon paas.
 */
package cn.flus.taobao.report.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.response.ItemGetResponse;

/**
 * @author zhoux 2013-5-27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring-context-config-test.xml" })
public class CommonsTest {

    @Autowired
    private TaobaoClientInstance taobaoClientInstance;

    @Test
    public void test() {
        TaobaoClient client = taobaoClientInstance.getDefaultClient();
        Long numIid = 1500008207585L;
        ItemGetRequest req = new ItemGetRequest();
        req.setFields("num_iid,title,price");
        req.setNumIid(numIid);
        ItemGetResponse response = null;
        try {
            response = client.execute(req);
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(numIid, response.getItem().getNumIid());
    }
}
