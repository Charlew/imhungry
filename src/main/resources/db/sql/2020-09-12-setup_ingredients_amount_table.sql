create table ingredients_amount(time timestamp, ingredient text, amount int);

create index "ingredients_amount_ingredient" ON ingredients_amount("ingredient");