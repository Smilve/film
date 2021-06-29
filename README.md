# 橘子电影

本项目是一个前后端分离的项目，后端使用SSM框架构建后台接口，前端使用Vue全家桶+ElementUI构建项目，以MySQL作为数据库，为用户提供一个在线购票的平台。

## 后端(film_api、film_api_springboot)

Mybatis整合Redis实现二级缓存；Redtful风格的接口实现；整合Swagger生成接口文档；整合JWT生成Token实现用户登录验证；整合支付宝沙箱模拟真实支付场景并实现支付及退款功能；实现简单的协同过滤算法为用户推荐电影；编写工具类原生实现分页功能；使用Java8的API优化代码；最后使用SpringBoot框架重构后端项目。

## 前端(film_admin、film_user)

使用Vue全家桶+ElementUI构建项目，使用弹出窗口实现登录注册功能，核心业务流程为用户选择电影、点击购票、选择电影院、选择场次、选择座位、点击确认、扫码支付。

## 运行项目

1. 首先`git clone`项目

2. 如果使用film_api，使用idea打开该项目，导入maven依赖，配置tomcat服务器，新建数据库film，运行film.sql文件，将项目mysql和redis的配置文件配置成自己的，使用8080端口运行项目，并将images文件夹复制到编译完的目录下`out.artifacts.film_api`

3. 如果使用film_api_springboot,使用idea打开该项目，导入maven依赖，新建数据库film，运行film.sql文件，将项目mysql和redis的配置文件配置成自己的，使用8080端口运行项目

4. 进入film_admin文件夹下，执行`npm run install`，再执行`npm run serve`，在浏览器窗口输入对应的路径访问项目

5. 进入film_user文件夹下，执行`npm run install`，再执行`npm run serve`，在浏览器窗口输入对应的路径访问项目


* 首页

![image](https://user-images.githubusercontent.com/43447303/123766508-56398a80-d8f9-11eb-8515-56c555b29e23.png)

* 我的订单

![image](https://user-images.githubusercontent.com/43447303/123766560-618cb600-d8f9-11eb-8946-1babcb96bd38.png)
