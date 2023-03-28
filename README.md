# 动态定时任务
目的是把动态定时任务弄成一个固定模块，方便以后重用。项目技术：springboot + mybaitsPlus + mysql

## 项目模块引入
- 1、映入jar--->把项目intall到本地maven(mvn install)，然后加入项目dependencys（或者package成jar包，加载jar包也可以）， 下面是映入本地maven包：
~~~~
 <dependency>
    <groupId>com.huazai.test</groupId>
    <artifactId>dynamic-task</artifactId>
    <version>0.0.1-SNAPSHOT</version>
 </dependency>
~~~~

- 2、自定义包扫描，扫描自己的包和本项目的包如：  
因为springboot默认会扫描启动类下的包及所有其子包，如果自定以了扫描，默认扫描就会失效。 
~~~~  
@SpringBootApplication(scanBasePackages = {"自己的包", "com.huazai.test"})  
~~~~

- 如果用了knife4j-openapi2可以把com.huazai.test包加入文档，方便查看，可以单独放一个组，也可以放到其他组，如：
~~~~~~~
      dynamic-task:
        group-name: 定时任务
        api-rule: package
        api-rule-resources:
          - com.huazai.test
~~~~~~~~

注意：调整pom.xml中的库版本和你的项目库版本保持一致，这样不会硬气冲突

## 本项目怎么直接测试
- 去掉 DynamicTaskApplication.java、application.yml的注释
- 在sql目录下把sql表导入数据库，修改数据库连接参数
- 启动项目， [打开knif4j文档](http://localhost:8080/doc.html)
    - 查询所有动态任务
    - 添加动态任务
    - 删除动态任务
    - 修改动态任务
    - 启动或停止任务
    