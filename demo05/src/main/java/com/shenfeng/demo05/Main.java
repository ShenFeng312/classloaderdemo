package com.shenfeng.demo05;


import com.shenfeng.Bean;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException {
        System.out.println("替换classLoader 之前====================");
        Class<?> aClass = Class.forName("com.shenfeng.Bean");
        System.out.println(aClass.getClassLoader());
        Bean bean = new Bean();
        System.out.println(bean.getClass().getClassLoader());


        //classLoader 跳过了 AppClassLoader
        URLClassLoader classLoader = new URLClassLoader(new URL[]{
                new File("./demo02/urlclassLoader_jar1/target/urlclassLoader_jar1-1.0-SNAPSHOT.jar").toURL()
        }, Main.class.getClassLoader().getParent());
        Thread.currentThread().setContextClassLoader(classLoader);
        System.out.println("替换classLoader 之后====================");
        aClass = Class.forName("com.shenfeng.Bean");
        System.out.println(aClass.getClassLoader());
        bean = new Bean();
        System.out.println(bean.getClass().getClassLoader());
        /**
         * 替换当前线程上下文并不会改变类的加载选择。直接申明的类加载 如import 的
         * 如 Bean bean1 = new Bean();
         * 或者 Class.forName("com.shenfeng.Bean"); 会选择当前 Class<?> caller = Reflection.getCallerClass();类的类加载器进行加载
         * 但有些spi 或者特定行为的编码一般选择Thread.currentThread().getContextClassLoader() 所以最好保证一致
         */

    }
}
