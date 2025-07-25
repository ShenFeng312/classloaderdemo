package com.shenfeng.demo07;

import java.util.HashMap;
import java.util.Map;

/**
 *  java --add-opens java.base/java.util=ALL-UNNAMED -javaagent:./agent/target/agent-1.0-SNAPSHOT.jar -jar ./demo07/target/demo07-1.0-SNAPSHOT.jar
 *  动态字节码修改。常见于skyWalking pinpoint 等链路追踪工具
 */
public class Main {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("hello", "world");
    }
}
