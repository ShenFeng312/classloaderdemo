package com.shenfeng.demo03;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 双亲委派验证
 */
public class Main {
    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //通过调整jar 的顺序 来控制classpath 和 类的加载顺序
        URL jar1 = new File("./demo02/urlclassLoader_jar1/target/urlclassLoader_jar1-1.0-SNAPSHOT.jar").toURL();
        URL jar2 = new File("./demo02/urlclassLoader_jar2/target/urlclassLoader_jar2-1.0-SNAPSHOT.jar").toURL();
        URLClassLoader parentClassLoader = new URLClassLoader(new URL[]{jar2}, Main.class.getClassLoader()){
            @Override
            public String toString() {
                return "parentClassLoader";
            }
        };
        URLClassLoader classLoader = new URLClassLoader(new URL[]{jar1}, parentClassLoader){
            @Override
            public String toString() {
                return "classLoader";
            }
        };
        System.out.println("=======================================================");
        Class<?> aClass = classLoader.loadClass("com.shenfeng.Bean");

        //com.shenfeng.Bean 存在于jar1 和 jar2 由于双亲委派被 parent 加载
        aClass.newInstance();
        System.out.println(aClass.getName() + " load from " + aClass.getClassLoader());

        aClass = classLoader.loadClass("com.shenfeng.BeanB");
        aClass.newInstance();
        System.out.println(aClass.getName() + " load from " + aClass.getClassLoader());

        parentClassLoader = new URLClassLoader(new URL[]{jar1}, Main.class.getClassLoader()){

            @Override
            public String toString() {
                return "parentClassLoader";
            }
        };
        classLoader = new URLClassLoader(new URL[]{jar2}, parentClassLoader){

            @Override
            public String toString() {
                return "classLoader";
            }
        };

        System.out.println("=======================================================");
        aClass = classLoader.loadClass("com.shenfeng.Bean");
        //com.shenfeng.Bean 存在于jar1 和 jar2 由于双亲委派被 parent 加载
        aClass.newInstance();
        System.out.println(aClass.getName() + " load from " + aClass.getClassLoader());
        aClass = classLoader.loadClass("com.shenfeng.BeanB");
        aClass.newInstance();
        System.out.println(aClass.getName() + " load from " + aClass.getClassLoader());





    }
}
