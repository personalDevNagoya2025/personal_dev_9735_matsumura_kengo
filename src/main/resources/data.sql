-- users テーブルにデータを挿入するクエリ
INSERT INTO users (id, email, name, password)
VALUES
(0, 'tanaka@aaa.com', '田中太郎', 'test123');


-- tasks テーブルにデータを挿入するクエリ
INSERT INTO tasks (user_id, title)
VALUES
(0, '見積もり');
