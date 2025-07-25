# SpringBoot fat jar 启动原理
spring-boot-maven-plugin 会将所有依赖和本项目的类打包到一个fatjar中。
默认情况下spring-boot-maven-plugin 在打包时会自动找到SpringBoot 启动类。并写入信息 我们也可以手动指定  
解压打包后的jar 包我们可以找到这样一份清单文件
```shell
.
├── BOOT-INF
│   ├── classes
│   │   └── com
│   │       └── shenfeng
│   │           └── demo04
│   │               └── Main.class
│   ├── classpath.idx
│   ├── layers.idx
│   └── lib
│       ├── mysql-connector-j-8.4.0.jar
│       ├── protobuf-java-3.25.1.jar
│       └── spring-boot-jarmode-layertools-3.0.0.jar
├── META-INF
│   ├── MANIFEST.MF
│   └── maven
│       └── com.shenfeng
│           └── demo04
│               ├── pom.properties
│               └── pom.xml
└── org
    └── springframework
      ... 这里省略springboot 相关的类内容
```
其中 `classpath.idx` 描述了springboot fatjar 中的classpath 信息 springboot 的类加载器由他来决定如何按顺序加载类（见demo02 中的URL 顺序调整  原理是一样的）  
传统的URLClassLoader 并不能加载springboot 的fatjar springboot 在这里做了一个hack

```shell
Manifest-Version: 1.0
Archiver-Version: Plexus Archiver
Created-By: Apache Maven 3.6.3
Built-By: shenfeng
Build-Jdk: 17.0.14
Main-Class: org.springframework.boot.loader.JarLauncher
Start-Class: com.shenfeng.demo04.Main
Spring-Boot-Version: 3.0.0
Spring-Boot-Classes: BOOT-INF/classes/
Spring-Boot-Lib: BOOT-INF/lib/
Spring-Boot-Classpath-Index: BOOT-INF/classpath.idx
Spring-Boot-Layers-Index: BOOT-INF/layers.idx
```

观察 `META-INF/MANIFEST.MF` 文件我们发现主类被改成了`org.springframework.boot.loader.JarLauncher`
内部创建了一个springboot自己实现的fatjar的类加载器   





 