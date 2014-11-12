package cn.flus.account.web.utils;

public class TestThreadLocal {

    public static void main(String[] args) throws InterruptedException {

        new Thread() {

            public void run() {
                System.out.println(Thread.currentThread().getName());
            }

        }.start();

        Thread.sleep(2);

        System.out.println(Thread.currentThread().getName());
    }
}
