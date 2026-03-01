#!/bin/bash

# 打包Spring Boot项目脚本

echo "开始打包项目..."
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo "==================== 打包成功 ===================="
    echo "jar文件位置: target/AnonymousChat-0.0.1-SNAPSHOT.jar"
    ls -lh target/*.jar
    echo "=================================================="
    echo ""
    echo "运行命令: java -jar target/AnonymousChat-0.0.1-SNAPSHOT.jar"
else
    echo "==================== 打包失败 ===================="
    exit 1
fi
