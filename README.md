# phase 1

## goal

- 熟悉java语言
- 初步了解spring-boot，mybatis
- 了解git，maven
- 养成良好的编码习惯

## task 1

### description
- 使用spring-boot和mybatis实现一个rest服务
- 包括添加user POST，根据id查询用户GET，根据id删除用户DELETE，根据id更新用户PUT，分页查询用户列表
- 将代码托管到https://git.woda.com

### deadline
- 2020.11.16

-----

## Docker-compose

### build image

```bash
mvn clean package # compile java
cd web && yarn && yarn build && cd .. # compile frontend
docker-compose up --build # build & start docker-compose
```

### test

- 浏览器打开 http://localhost:8888
