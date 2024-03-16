# CMS Spring Boot 2 + MyBatis Version

## 稼働条件

- java 17
- postgresql

## 起動方法

- postgresql にデータベースおよびスキーマを準備 (デフォルトでは、データベース=cms, スキーマ=cms)
- application.properties のDB接続設定を編集
- CmsApplication.main() を実行

## 初回ログイン時の設定

- http://localhost:8080/ に接続
- ユーザ名=admin, パスワード=demo, 管理者としてログインにチェックを入れてログイン
- http://localhost:8080/admin/permission/list にアクセスし、システム管理者(ADMIN)に権限を付与する
- 再ログイン

