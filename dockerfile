# 使用Ubuntu 22.04 LTS作为基础镜像
FROM ubuntu:22.04

# 设置环境变量，避免交互提示，配置中文编码，设置时区
ENV DEBIAN_FRONTEND=noninteractive \
    LANG=C.UTF-8 \
    LC_ALL=C.UTF-8 \
    TZ=Asia/Shanghai \
    MYSQL_ROOT_PASSWORD=123456 \
    MYSQL_DATABASE=fish_backfonted

# 设置工作目录
WORKDIR /app

# 1. 替换Ubuntu国内源 + 安装所有基础依赖 + 清理缓存
RUN sed -i 's/archive.ubuntu.com/mirrors.aliyun.com/g' /etc/apt/sources.list && \
    sed -i 's/security.ubuntu.com/mirrors.aliyun.com/g' /etc/apt/sources.list && \
    apt update && apt install -y --no-install-recommends \
    openjdk-17-jdk \
    mysql-server \
    redis-server \
    supervisor \
    curl \
    net-tools \
    nginx && \
    apt clean && rm -rf /var/lib/apt/lists/*

# 2. 安装Node.js 20.x和npm，配置国内源
RUN curl -fsSL https://deb.nodesource.com/setup_20.x | bash - && \
    apt install -y nodejs && \
    npm config set registry https://registry.npmmirror.com/ && \
    apt clean && rm -rf /var/lib/apt/lists/*

# 3. 配置MySQL
RUN mkdir -p /var/run/mysqld && \
    chown -R mysql:mysql /var/run/mysqld && \
    chmod 777 /var/run/mysqld && \
    # 修改MySQL配置允许远程访问
    sed -i 's/^bind-address\s*=.*/bind-address = 0.0.0.0/' /etc/mysql/mysql.conf.d/mysqld.cnf && \
    # 修复MySQL数据目录权限
    chown -R mysql:mysql /var/lib/mysql && \
    chmod 755 /var/lib/mysql

# 4. 复制初始化脚本
COPY ./init_database.sql /app/init_database.sql
COPY ./mysql-init.sql /app/mysql-init.sql

# 5. 配置Nginx
RUN rm -rf /etc/nginx/sites-enabled/default /etc/nginx/sites-available/default && \
    mkdir -p /var/log/nginx/anonymous-chat && \
    chown -R www-data:www-data /var/log/nginx/anonymous-chat

# 复制Nginx配置
COPY ./nginx.conf /etc/nginx/conf.d/anonymous-chat.conf
RUN nginx -t

# 6. 配置Supervisor
COPY ./supervisor.conf /etc/supervisor/conf.d/supervisord.conf

# 7. 复制并部署后端代码
RUN mkdir -p /app/osprey_be
COPY ./osprey_be/target/osprey_be-0.0.1-SNAPSHOT.jar /app/osprey_be/osprey_be.jar

# 8. 复制并构建前端代码
# 先从宿主机复制整个 osprey_fe 目录到容器的 /app 目录下
COPY ./osprey_fe /app/osprey_fe

# 然后在容器内执行安装和构建
WORKDIR /app/osprey_fe
RUN npm install && \
    npm run build && \
    # 将构建产物复制到 Nginx 静态目录
    cp -r dist/* /var/www/html/ && \
    # 清理 node_modules 减小镜像体积
    rm -rf node_modules

# 回到工作目录
WORKDIR /app
# 9. 暴露端口
EXPOSE 80 3306 6379 9079

# 10. 健康检查
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
    CMD curl -f http://localhost/ || curl -f http://localhost:9079/actuator/health || exit 1

# 11. 启动supervisor
WORKDIR /app
CMD ["/usr/bin/supervisord", "-n", "-c", "/etc/supervisor/conf.d/supervisord.conf"]