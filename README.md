### 第7周作业
#### 使用 Spring Boot 来实现一个整合 Gitee 或者 Github OAuth2 认证
> gitee-client模块是集成gitee第三方登陆。
> 登陆逻辑：
> * GiteeLoginFilter用于拦截除了根目录以外的所有请求，拦截当前cookie是否有已经English过的用户信息，如果有则继续，没有的话则去跳转到gitee进行授权登陆，当gitee授权之后会回调地址/gitee/callback，/gitee/callback用户根据code获取用户的token等信息，并且跳转到/user/info路径下，/user/info会根据用户token获取用户信息。