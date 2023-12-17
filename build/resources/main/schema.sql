create table if not exists articles (id serial primary key, url VARCHAR(255) not null, title VARCHAR(255) not null, is_article BOOLEAN, top_image_url VARCHAR(255), additional_details JSONB)
----
--create table if not exists article_contents (id INTEGER primary key, article_id INTEGER, content text not null, constraint fk_article foreign key (article_id) references articles(id))
--
