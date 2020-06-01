## 商品销售信息系统（增删改查CRUD）——demo（大二下学期课堂作业）
### 技术栈：
#### spring boot + spring data jpa + thymeleaf + lombok + maven + SQL server
### 启动步骤：
#### 一、克隆本项目到本地
#### 二、用IDEA打开本项目
#### 三、修改 src\main\resources\application.yml
```yaml
    #指定数据库
    url: jdbc:sqlserver://127.0.0.1:1433;DatabaseName=<数据库名>
    #填写自己的账户密码
    username: root
    password: root
```

#### 四、启动项目

#### 五、在数据库中执行 trigger.sql

#### 六、浏览器中输入 http://localhost:8080/ 访问

![](https://t1.picb.cc/uploads/2020/05/24/koEQbJ.png)

![](https://t1.picb.cc/uploads/2020/05/24/koKv6g.png)