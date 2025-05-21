-- 各種テーブル削除
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS tasks;

-- users テーブルを作成するクエリ
CREATE TABLE users (
id SERIAL PRIMARY KEY,
email text,
name text,
password text
);

-- tasks テーブルを作成するクエリ
CREATE TABLE tasks (
id SERIAL PRIMARY KEY,
user_id INTEGER,
title text,
closing_date DATE,
memo text,
deleted BOOLEAN DEFAULT FALSE,
categories_id text
);

-- categoriesテーブル
CREATE TABLE categories
(
	id SERIAL PRIMARY KEY,
	name TEXT
);