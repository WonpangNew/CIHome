# CIHome

一个基于开源技术的持续集成平台，集成 GitHub、Jenkins 等工具，提供代码管理、自动化构建、部署流水线展示等功能。

![CIHome](./CIHome.png)

## 项目简介

基于当下的开源技术，本项目采用 GitHub 作为代码管理工具，Jenkins 作为持续集成工具，并自己搭建 MQ、FTP 服务分别用来进行数据通信和产出存储。核心服务 CIHome 作为任务调度工具并提供部署流水线的展示。

GitHub 作为开源的代码管理工具提供了丰富的 API 供开发者调用，其中 GitHook 作为监听代码提交的钩子更是为本项目提供了有力的帮助，可以很方便的监听代码提交信息。而 Jenkins 所提倡的插件化机制也使得我们自己开发个性化的 hpi 用于本项目中。对于 Apache 所提供的 MQ 服务则可以提供数据关联的功能，并基于 FTP 协议开发新的服务作为产品库。

## 技术栈

### 后端框架
- **Spring Framework 4.2.7** - 核心框架
- **Spring MVC** - Web MVC 框架
- **Hibernate 4.2.7** - ORM 框架
- **Velocity** - 模板引擎

### 数据库
- **MySQL** - 关系型数据库
- **C3P0** - 数据库连接池
- **Commons DBCP** - 数据库连接池

### 集成工具
- **Jenkins Client** - Jenkins 集成
- **GitHub API** - GitHub 集成
- **Apache MQ** - 消息队列
- **FTP** - 文件传输协议

### 工具库
- **Jackson / Gson** - JSON 处理
- **Apache HttpClient** - HTTP 客户端
- **Logback** - 日志框架
- **Commons Lang** - 工具类库

## 功能模块

### 1. 用户管理模块 (user)
- 用户登录认证
- 用户信息管理
- 权限控制

### 2. GitHub 集成模块 (github)
- GitHub 仓库管理
- 代码提交记录同步
- GitHub Hook 监听
- 分支管理

### 3. 分支管理模块 (branch)
- 分支信息展示
- 分支类型管理
- 分支操作

### 4. 编译构建模块 (compile)
- 构建任务管理
- 构建状态跟踪
- 构建详情查看
- 构建历史记录

### 5. Jenkins 集成模块 (jenkins)
- Jenkins 任务触发
- 构建状态同步
- Jenkins API 调用

### 6. 发布管理模块 (release)
- 发布任务创建
- 发布状态管理
- 发布详情查看
- 发布历史记录

### 7. 流水线模块 (pipeline)
- CI/CD 流水线展示
- 流水线构建管理
- 流水线状态跟踪

## 项目结构

```
CIHome/
├── src/main/java/com/jlu/
│   ├── branch/          # 分支管理
│   ├── cihome/          # 首页控制器
│   ├── common/          # 公共工具类
│   │   ├── cookies/     # Cookie 工具
│   │   ├── db/          # 数据库相关
│   │   ├── utils/       # 工具类
│   │   └── web/         # Web 拦截器
│   ├── compile/         # 编译构建
│   ├── github/          # GitHub 集成
│   ├── jenkins/         # Jenkins 集成
│   ├── pipeline/        # 流水线管理
│   ├── release/         # 发布管理
│   └── user/            # 用户管理
├── .github/             # GitHub Actions 配置
├── azure-pipelines.yml  # Azure Pipelines 配置
├── pom.xml              # Maven 配置文件
└── README.md            # 项目说明文档
```

## 快速开始

### 环境要求
- JDK 1.7 或以上
- Maven 3.x
- MySQL 5.x
- Jenkins Server
- Git

### 安装步骤

1. **克隆项目**
```bash
git clone https://github.com/WonpangNew/CIHome.git
cd CIHome
```

2. **配置数据库**
- 创建 MySQL 数据库
- 配置数据库连接信息（在配置文件中）

3. **配置 Jenkins**
- 配置 Jenkins 服务器地址
- 配置 Jenkins 认证信息

4. **配置 GitHub**
- 配置 GitHub API Token
- 设置 GitHub Webhook

5. **编译项目**
```bash
mvn clean install
```

6. **运行项目**
```bash
mvn jetty:run
```

项目将在 http://localhost:8888 启动

## 配置说明

### 数据库配置
在 Spring 配置文件中配置数据库连接信息：
- 数据库 URL
- 用户名和密码
- 连接池参数

### Jenkins 配置
配置 Jenkins 服务器信息：
- Jenkins URL
- 用户名和 API Token
- 任务名称

### GitHub 配置
配置 GitHub 集成信息：
- GitHub API Token
- Webhook Secret
- 仓库信息

## API 接口

项目提供 RESTful API 接口，主要包括：

- `/user/*` - 用户管理接口
- `/github/*` - GitHub 集成接口
- `/branch/*` - 分支管理接口
- `/compile/*` - 编译构建接口
- `/jenkins/*` - Jenkins 集成接口
- `/release/*` - 发布管理接口
- `/pipeline/*` - 流水线接口

## 开发指南

### 代码规范
- 遵循 Java 编码规范
- 使用有意义的变量和方法命名
- 添加必要的注释

### 分支管理
- `main` - 主分支
- `develop` - 开发分支
- `feature/*` - 功能分支
- `hotfix/*` - 热修复分支

### 提交规范
- feat: 新功能
- fix: 修复 bug
- docs: 文档更新
- style: 代码格式调整
- refactor: 重构
- test: 测试相关
- chore: 构建工具或辅助工具的变动

## CI/CD

项目配置了多个 CI/CD 平台：

- **GitHub Actions** - 自动化测试和构建
- **Azure Pipelines** - Azure DevOps 集成
- **Maven** - 构建和依赖管理

## 贡献指南

欢迎贡献代码！请遵循以下步骤：

1. Fork 本仓库
2. 创建您的特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交您的更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启一个 Pull Request

## 许可证

本项目采用 [MIT License](LICENSE)

## 联系方式

如有问题或建议，请提交 Issue 或 Pull Request。

---

**注意**: 本项目仅供学习和研究使用。
