-- テーブル削除
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS task;

-- テーブル生成

-- categoryテーブル
CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    category VARCHAR(255)
);

-- taskテーブル
CREATE TABLE task (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);


-- Insert into "category" table
INSERT INTO category (category) VALUES ('ToDo');
INSERT INTO category (category) VALUES ('作業中');
INSERT INTO category (category) VALUES ('確認待ち');
INSERT INTO category (category) VALUES ('完了');

-- Insert into "task" table
INSERT INTO task (title, category_id) VALUES ('買い物リストを作成する', 1);
INSERT INTO task (title, category_id) VALUES ('部屋の掃除をする', 1);
INSERT INTO task (title, category_id) VALUES ('レポートを提出する', 1);
INSERT INTO task (title, category_id) VALUES ('家計簿をつける', 1);
INSERT INTO task (title, category_id) VALUES ('散歩に行く', 1);
