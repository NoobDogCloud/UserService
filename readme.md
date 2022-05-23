# 一般用户系统设计

### 系统设计

```
用户密码加密:
    md5(password + salt)
    
用户登录鉴权:
    jwt为基础
    增加0时区天为秘钥组成部分,防止时区不同导致的时间戳不同
    同时增加秘钥校验失败,自动向前滚动一天,自动刷新机制.确保非连续登录秘钥被破解
```

---

### 模型设计

```
模型名: user
表名称: user
    普通用户表
```

| 字段名 | 业务类型 | 数据类型 | 默认值 | 错误补位值 | 多对象 | 说明 |
| - | - | - | - | - | - | - |
| user_id | public | id | | | | 用户名 |
| avatar | public | url | | | | 用户头像url地址 |
| nick_name | public | string | | | | 用户昵称 |
| title | public | string | | | | 用户抬头 |
| password | mask | password | | | | 用户密码 |
| salt | mask | string | | | | 用户秘钥 |
| group_id | public | string | | | | 用户组ID(角色id),用户角色由应用管理设计 |