-- 各種テーブル削除
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS recipes;
DROP TABLE IF EXISTS reviews;


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
   user_name VARCHAR(20),
   password VARCHAR(20)
);
-- レシピテーブル
CREATE TABLE recipes
(
   id SERIAL PRIMARY KEY,
   category_id INTEGER,
   user_name VARCHAR(20),
   recipe_name VARCHAR(20),
   materials VARCHAR(200), 
   contents VARCHAR(500)

);

CREATE TABLE reviews
(
   id SERIAL PRIMARY KEY,
   recipe_id INTEGER,
   review_name VARCHAR(20),
   comments VARCHAR(500)
);
