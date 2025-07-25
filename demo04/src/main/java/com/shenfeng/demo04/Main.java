package com.shenfeng.demo04;

/**
 *  java -jar ./demo04/target/demo04-1.0-SNAPSHOT.jar
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(Main.class.getClassLoader().getClass().getName());
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            System.out.println(stackTraceElement);
        }
    }

}
