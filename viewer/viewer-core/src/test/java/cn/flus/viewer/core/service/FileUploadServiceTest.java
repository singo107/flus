package cn.flus.viewer.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:test-spring-context-config.xml" })
public class FileUploadServiceTest {

    @Autowired
    private FileUploadService fileUploadService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test1() {

        File file = new File("e:\\tmp\\01.nwd");
        FileInputStream f = null;
        try {

            f = new FileInputStream(file);
            String urn = fileUploadService.uploadAndConvert(f, file.getName());
            System.out.println(urn);

            fileUploadService.fetchConvertProgress(urn);

            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            fileUploadService.fetchConvertProgress(urn);

            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            fileUploadService.fetchConvertProgress(urn);

            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            fileUploadService.fetchConvertProgress(urn);

        } catch (FileNotFoundException e) {
        } finally {
            if (f != null) {
                try {
                    f.close();
                } catch (IOException e) {
                }
            }
        }
    }

    // @Test
    public void test2() {
        String urn = "dXJuOmFkc2sub2JqZWN0czpvcy5vYmplY3Q6c2luZ28vMTIucnZ0";
        int progress = fileUploadService.fetchConvertProgress(urn);
        System.out.println(progress);
    }
}
