-- カテゴリーテーブルデータ
INSERT INTO categories(category_name) VALUES('和食');
INSERT INTO categories(category_name) VALUES('洋食');
INSERT INTO categories(category_name) VALUES('中華');
INSERT INTO categories(category_name) VALUES('イタリアン');
INSERT INTO categories(category_name) VALUES('ドリンク');
INSERT INTO categories(category_name) VALUES('デザート、菓子');
INSERT INTO categories(category_name) VALUES('その他');
-- users テーブルにデータを挿入
INSERT INTO users(user_name, password) VALUES ('田中太郎', 'test123');
INSERT INTO users(user_name, password) VALUES ('鈴木一郎', 'test456');

-- recipes テーブルにデータを挿入
INSERT INTO recipes (category_id, user_name, recipe_name, materials, contents) VALUES(2, '田中太郎', 'ハンバーグ', 'ひき肉、玉ねぎ、パン粉、牛乳、塩、コショウ', '材料を混ぜて、捏ねて、焼く'); 
INSERT INTO recipes (category_id, user_name, recipe_name, materials, contents) VALUES(1, '鈴木一郎', '寿司', '刺身、米、お酢、砂糖、塩', '米、お酢、砂糖、塩を混ぜて酢飯を作り、刺身と握る');

INSERT INTO reviews(recipe_id,review_name,comments) VALUES(1,'匿名１','とても良かったです！');
INSERT INTO reviews(recipe_id,review_name,comments) VALUES(2,'匿名２','とても良かったです！');