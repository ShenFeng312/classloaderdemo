package com.shenfeng.demo06;

import org.apache.dubbo.common.compiler.support.JdkCompiler;

public class Main {
    public static void main(String[] args) throws Throwable {
        //除了传统的类加载 JDK 也能运行时编译并加载类 比如groovy 脚本类
        //下面是用的dubbo的JdkCompiler 编译生成java 类的实现 在一些比较动态又高性能的场景会用到。但实现各有差异。
        // 比如presto的一些算子聚合 udf 函数生成 dubbo的spi 拓展
        JdkCompiler jdkCompiler =new JdkCompiler();
        Class<?> aClass = jdkCompiler.doCompile(null, "com.shenfeng.Bean", "package com.shenfeng;\n" +
                "\n" +
                "public class Bean {\n" +
                "    static {\n" +
                "        System.out.println(\"this is from jdkCompiler\");\n" +
                "    }\n" +
                "}");
        aClass.newInstance();
    }
}
