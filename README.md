# SSM_Maven
**概述**
* （1）项目来源自：[https://github.com/liyifeng1994/ssm](https://github.com/liyifeng1994/ssm)

* （2）master主干是原来博主李奕锋的项目，有基本的SSM框架的搭建；

* （3）seckill分支是结合慕课网秒杀项目简单的实现了视频中的秒杀项目

**技术栈**
* Maven
* SpringMVC + Spring + MyBatis

**运行说明**

-- master主干
* （1）创建数据库表，先运行目录src/main/sql/schema.sql（提前先建好库，数据库名ssm）
* （2）修改src/main/resources/jdbc.properties中的连接配置

--seckill分支
* （1）下载代码后，切换分支到seckill
* （2）创建数据库表，运行目录src/main/sql/schema_seckill.sql（提前先建好库，数据库名seckill）
* （3）修改src/main/resources/jdbc.properties中的连接配置

**SSM框架**
* 1、SpringMVC：它用于web层，相当于controller（等价于传统的servlet和struts的action），用来处理用户请求。举个例子，用户在地址栏输入http://网站域名/login ，那么springmvc就会拦截到这个请求，并且调用controller层中相应的方法，（中间可能包含验证用户名和密码的业务逻辑，以及查询数据库操作，但这些都不是springmvc的职责），最终把结果返回给用户，并且返回相应的页面（当然也可以只返回json/xml等格式数据）。springmvc就是做前面和后面过程的活，与用户打交道！！

* 2、Spring：太强大了，以至于我无法用一个词或一句话来概括它。但与我们平时开发接触最多的估计就是IOC容器，它可以装载bean（也就是我们java中的类，当然也包括service dao里面的），有了这个机制，我们就不用在每次使用这个类的时候为它初始化，很少看到关键字new。另外spring的aop，事务管理等等都是我们经常用到的。

* 3、MyBatis：和Hibernate有什么区别？第一，它能自由控制sql，这会让有数据库经验的人编写的代码能够提升数据库访问的效率。第二，它可以使用xml的方式来组织管理我们的sql，因为一般程序出错很多情况下是sql出错，别人接手代码后能快速找到出错地方，甚至可以优化原来写的sql。
