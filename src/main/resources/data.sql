-- users テーブルにデータを挿入するクエリ
INSERT INTO users (id, email, name, password)
VALUES
(0, 'tanaka@aaa.com', '田中太郎', 'test123');


-- tasks テーブルにデータを挿入するクエリ
INSERT INTO tasks (user_id, title, closing_date,memo,categories_id)
VALUES
(0, '見積もり','2025-12-31','案件に適した見積もりを取る','高');

--categoriesテーブルデータ
INSERT INTO categories(name) VALUES('高');
INSERT INTO categories(name) VALUES('中');
INSERT INTO categories(name) VALUES('低');