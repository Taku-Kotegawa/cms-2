# CMS Spring Boot 3 + MyBatis Version

開発中

## 稼働条件

- java 21 
- postgresql

## 起動方法

- postgresql にデータベースおよびスキーマを準備 (デフォルトでは、データベース=cms, スキーマ=cms)
- application.properties のDB接続設定を編集
- CmsApplication.main() を実行

## 初回ログイン時の設定

- http://localhost:8080/ に接続
- ユーザ名=admin, パスワード=Passw0rd, 管理者としてログインにチェックを入れてログイン
- http://localhost:8080/admin/permission/list にアクセスし、システム管理者(ADMIN)に権限を付与する
- 再ログイン


## 使用モジュール

### Docker Compose Support

アプリケーション実行時にDockerComposeでPostgresを起動する。<br>
DockerComposeSupportでPostgresを起動すると、自動的にDataSourceが登録される。
CurrentSchemaの指定はできない。

```properties
# Docker Composet Support (Postgres起動用)
spring.docker.compose.enabled=true
spring.docker.compose.file=docker/docker-compose.yaml
```


### Liquibase

アプリケーション実行時にDDLを実行する。

```properties
# liquibase設定
spring.liquibase.enabled=true
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.yaml
```


## メモ

postgresに最速でDBに登録する方法
- https://github.com/onozaty/postgresql-copy-helper

