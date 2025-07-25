# 说明
此demo 展示了classLoader 的 双亲委派关系。
以及查找顺序 和classpath 关系。

一下是idea 启动时的命令我们可以看到 idea 指定了classpath 和我们的主类
```shell
/home/shenfeng/.sdkman/candidates/java/17.0.14-amzn/bin/java --add-opens java.base/jdk.internal.loader=ALL-UNNAMED 
-javaagent:/home/shenfeng/.cache/JetBrains/RemoteDev/dist/06246dafc225c_ideaIU-2025.1.3/lib/idea_rt.jar=43785 
-Dfile.encoding=UTF-8 
-classpath /home/shenfeng/github/classloaderdemo/demo01/target/classes:/home/shenfeng/.m2/repository/com/mysql/mysql-connector-j/8.4.0/mysql-connector-j-8.4.0.jar:/home/shenfeng/.m2/repository/com/google/protobuf/protobuf-java/3.25.1/protobuf-java-3.25.1.jar 
com.shenfeng.classloaderdemo.demo01.Main
```
如果我们想要直接通过jar包运行可以配置打包插件将依赖打到jar 包中 类似的插件有 `springboot` 的打包插件还有 `maven-shade-plugin`

**特别注意: java -jar 的方式是不支持 -classpath 的 他只允许运行单个jar 包**