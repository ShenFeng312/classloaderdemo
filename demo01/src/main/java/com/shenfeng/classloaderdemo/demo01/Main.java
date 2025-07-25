package com.shenfeng.classloaderdemo.demo01;

import com.mysql.cj.jdbc.Driver;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        System.out.println("打印classLoader 和 父classLoader");

        ClassLoader classLoader = Driver.class.getClassLoader();
        while (classLoader != null) {
            System.out.println(classLoader);


            classLoader = classLoader.getParent();
        }

        System.out.println("=============================================");

        System.out.println("ClassLoader.getSystemClassLoader():"+  ClassLoader.getSystemClassLoader());

        System.out.println("ClassLoader.getPlatformClassLoader():"+ ClassLoader.getPlatformClassLoader());

        System.out.println("====================打印类路径==================");
        /**
         * jdk9 开始appClassLoader 不再是URLClassLoader
         */
        Field ucpField = null;
        ClassLoader appClassLoader = Driver.class.getClassLoader();
        Class<?> cl = appClassLoader.getClass();
        while (cl != null) {
            try {
                ucpField = cl.getDeclaredField("ucp");
                ucpField.setAccessible(true);
                break;
            } catch (NoSuchFieldException e) {
                cl = cl.getSuperclass(); // 向上找
            }
        }

        if (ucpField != null) {
            Object ucp = ucpField.get(appClassLoader);
            Field pathField = ucp.getClass().getDeclaredField("path");
            pathField.setAccessible(true);
            @SuppressWarnings("unchecked")
            java.util.List<URL> urls = (java.util.List<URL>) pathField.get(ucp);
            for (URL url : urls) {
                System.out.println(url);
            }
        } else {
            System.out.println("无法找到 ucp 字段");
        }
    }
}
