package com.shenfeng.classloaderdemo.demo02;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Main {
    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        URL[] urls = new URL[2];
        //通过调整jar 的顺序 来控制classpath 和 类的加载顺序
        urls[1] = new File("./demo02/urlclassLoader_jar1/target/urlclassLoader_jar1-1.0-SNAPSHOT.jar").toURL();
        urls[0] = new File("./demo02/urlclassLoader_jar2/target/urlclassLoader_jar2-1.0-SNAPSHOT.jar").toURL();
        URLClassLoader classLoader = new URLClassLoader(urls, Main.class.getClassLoader());
        Class<?> aClass = classLoader.loadClass("com.shenfeng.Bean");
        aClass.newInstance();
        System.out.println(classLoader);
    }
}
