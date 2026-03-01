-- MySQL初始化脚本：设置root密码和权限
-- 注意：这个脚本需要在MySQL启动后执行

-- 设置root用户密码（MySQL 8.0语法）
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '123456';

-- 刷新权限
FLUSH PRIVILEGES;

-- 授予root用户所有权限
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' WITH GRANT OPTION;

-- 刷新权限
FLUSH PRIVILEGES;