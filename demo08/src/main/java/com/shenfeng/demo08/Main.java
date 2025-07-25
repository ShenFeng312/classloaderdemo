package com.shenfeng.demo08;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 类卸载实验
 * 通过 arthas 命令 sc -d <classname> 观察
 */
public class Main {
    private static final Map<String,Class> loadedClass = new HashMap<>();
    private static final Map<String,ClassLoader> classLoaderMap = new HashMap<>();

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("命令列表:");
        System.out.println("  load <className>       加载类");
        System.out.println("  rm class <className>   移除类引用");
        System.out.println("  rm loader <className>  移除类加载器引用");
        System.out.println("  list                   显示所有已加载类");
        System.out.println("  exit                   退出程序");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("退出程序");
                break;
            } else if (input.startsWith("load ")) {
                String className = input.substring("load ".length()).trim();
                File jarFile = new File("./demo02/urlclassLoader_jar2/target/urlclassLoader_jar2-1.0-SNAPSHOT.jar");

                if (!jarFile.exists()) {
                    System.out.println("JAR 文件不存在: " + jarFile.getAbsolutePath());
                    continue;
                }

                URL jarUrl = jarFile.toURI().toURL();
                ClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl}, Main.class.getClassLoader());

                try {
                    Class<?> clazz = classLoader.loadClass(className);
                    loadedClass.put(className, clazz);
                    classLoaderMap.put(className, classLoader);
                    System.out.println("类已加载: " + clazz + " 使用的加载器: " + classLoader);
                } catch (ClassNotFoundException e) {
                    System.out.println("类未找到: " + className);
                }

            } else if (input.startsWith("rm class ")) {
                String className = input.substring("rm class ".length()).trim();
                loadedClass.remove(className);
                System.out.println("已移除类引用: " + className);

            } else if (input.startsWith("rm loader ")) {
                String className = input.substring("rm loader ".length()).trim();
                classLoaderMap.remove(className);
                System.out.println("已移除加载器引用: " + className);

            }  else {
                System.out.println("未知命令: " + input);
            }

            // 强制尝试 GC（只是示意，不能保证卸载）
            System.gc();
        }

        scanner.close();
    }
}
