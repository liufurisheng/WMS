# WMS
基于springboot+layui的wms仓库后台管理系统
1：springboot+mybatis+layui实现的wms系统
2：tomcat服务，启动类WmsLiufuApplication启动，默认登录：localhost:8030/login(可随意更改)

3：登录分为仓管和共同管理员：初始登录分别为：（添加用户时请注意别重名用shiro加盐加密）
           用户名：用户       密码：123123 
           用户名：管理员     密码：123123

4：使用于：供应商，顾客，仓库，商品四张基础表格加仓库管理员表，供应商的商品入库，顾客的商品订购（出库），
     仓库库存查询，人员分配和权限管理，保存着商品出库记录和商品入库记录（只保存成功的操作），
     系统记录保存着操作方法，操作人员，入参，结果，操作时间，ip;

5：
     用户和管理员的权限不同，可以用管理员在仓管管理员前端页面更改role，
     本系统实现了基本的crud操作，
     多表联查，外键的简单使用，
     批量删除，分类，条件，日期，模糊查询，
     添加用户在用户密码上用shiro加盐加密，
     表单的导入数据库与从数据库导出表单xlsx格式，
     日志用aop加注解实现写入数据库,记录有关数据增删改和用户登录操作；
     还有些简单的错误异常code的返回封装；//只用了简单的，
     前端使用layui，table需要封装的格式固定，用Result<T>封装；
     前端部分用ajax请求，回显数据
     //（一些修改删除操作我开始时用了后端跳转，返回结果类型没用Map<String,Object> 请多包涵，有时间慢慢完善安全权限管理部分）

![图片](https://user-images.githubusercontent.com/74752638/115820544-9b41cc00-a433-11eb-82d7-5845d9787a43.png)
![图片](https://user-images.githubusercontent.com/74752638/115820555-a3017080-a433-11eb-8f2c-a7df2c34f706.png)
![图片](https://user-images.githubusercontent.com/74752638/115820598-bc0a2180-a433-11eb-90f1-33ea08693a47.png)
