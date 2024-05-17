-- 各種テーブル削除
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS recipes;


-- カテゴリーテーブル
CREATE TABLE categories
(
   id SERIAL PRIMARY KEY,
   category_name VARCHAR(20)
);

-- ユーザーテーブル
CREATE TABLE users
(
   id SERIAL PRIMARY KEY,
   user_name VARCHAR(50),
   password VARCHAR(50)
);
-- レシピテーブル
CREATE TABLE recipes
(
   id SERIAL PRIMARY KEY,
   category_id INTEGER,
   user_id INTEGER,
   recipe_name VARCHAR(30),
   materials VARCHAR(200), 
   contents VARCHAR(500)

);
