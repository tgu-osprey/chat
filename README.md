# 鱼鹰聊天

一个基于 Vue3 + SpringBoot 的匿名聊天应用

## 项目介绍

### 基本功能
从底层代码实现完全的匿名聊天

### 扩展功能
从整活的角度出发，发展新鲜的功能

#### 彩蛋
设置关键词触发彩蛋，如一个表情包，一段音频等

#### 技能包
使用技能包，改变聊天环境，如将新的聊天消息分词并随机翻译为英文

## 技术栈

### 前端
- Vue 3
- Vite 4
- Ant Design Vue
- Pinia
- Vue Router

### 后端
- Spring Boot
- MyBatis
- MySQL
- Redis
- WebSocket

### 部署
- Docker
- Nginx
- Supervisor

## 环境要求

- Node.js >= 18
- npm >= 9
- Java 17+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.6+

## 快速开始

### Docker一键部署

``` docker
docker run -d -p 5173:5173 -p 9079:9079 --name ospreychat ospreychat:latest
```

### 前端运行

1. 进入前端目录
```bash
cd osprey_fe
```

2. 安装依赖
```bash
npm install
```

3. 启动开发服务器
```bash
npm run dev
```

### 后端运行

1. 进入后端目录
```bash
cd osprey_be
```

2. 配置数据库
   - 创建数据库：`fish_backfonted`
   - 执行初始化脚本：`init_database.sql`
   - 修改 `src/main/resources/application.yml` 中的数据库和 Redis 配置

3. 编译并运行
```bash
mvn clean package
java -jar target/osprey_be-0.0.1-SNAPSHOT.jar
```

## 项目结构

```
Osprey Chat/
├── osprey_be/          # 后端项目
│   ├── src/            # 源代码
│   ├── pom.xml         # Maven 配置
│   └── build.sh        # 构建脚本
├── osprey_fe/          # 前端项目
│   ├── src/            # 源代码
│   ├── package.json    # NPM 配置
│   └── vite.config.js  # Vite 配置
├── dockerfile          # Docker 配置
├── nginx.conf          # Nginx 配置
├── supervisor.conf     # Supervisor 配置
├── init_database.sql   # 数据库初始化脚本
└── mysql-init.sql      # MySQL 初始化脚本
```

## 功能特性

- ✅ 匿名聊天
- ✅ 随机用户名
- ✅ WebSocket 实时通信
- ✅ 敏感词过滤
- ✅ 彩蛋系统
- ✅ 技能包系统
- ✅ Docker 一键部署
- ✅ 支持多平台镜像

## 许可证

MIT License

## 贡献

欢迎提交 Issue 和 Pull Request！
