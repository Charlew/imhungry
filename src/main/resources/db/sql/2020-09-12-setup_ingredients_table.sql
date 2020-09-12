create table ingredients(time timestamp, name text);

create index "ingredients_name" ON ingredients("name");