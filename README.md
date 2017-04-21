# OSS ARCHETYPE

## 项目介绍

oss是我们为针对公司微服务化的需求提供的一系列库/服务/框架。由于项目体系庞杂，依赖众多，入门使用需要一定的时间。oss archetyle作为我们提供的脚手架工具，开发者可以使用这套工具快速创建接入oss框架的项目。 

基于本项目提供的maven骨架，目前可以生成以下类型的项目:  
+ C/S架构的项目。项目中包含以下内容：
  - 基本的oss dependency
  - 微服务及其client项目。
  - 容器化脚本
  - 生成的项目包含部分样例代码，演示oss的基本使用以及测试（包括单测、集成测试）的demo。
  - 整套的软件工程解决方案
+ 配置类型的项目。用于存放应用的配置信息。项目中包含如下内容:  
  - yml的配置文件;
  - 配置文件格式检查工具;
  - git hooks工具pre-commit;
  - gitlab-ci 脚本及配置。

## maven archetype 介绍
### 生成 maven archetype
本项目是一个maven骨架项目。可以通过`mvn archetype:create-from-project`命令，从现有的项目创建一个新的maven archetype。

    mvn archetype:create-from-project -Dinteractive

### maven archetype references
1. [creating-maven-archetypes-tutorial](http://geekofficedog.blogspot.com/2013/08/creating-maven-archetypes-tutorial.html)
2. [archetype-descriptor](https://maven.apache.org/archetype/archetype-models/archetype-descriptor/archetype-descriptor.html)
3. [spring-mvc-quickstart-archetype](https://github.com/kolorobot/spring-mvc-quickstart-archetype)

## oss-archetype 详细使用文档
具体的工具使用说明，请查看各项目的文档。
1. [oss-archetype-micro-service](oss-archetype-micro-service/)
2. [oss-archetype-micro-service-config](oss-archetype-micro-service-config/)

