# not_my_fitness_pal Creation of database commands

## MVP

- Terminal setup

```sql
-- type psql in terminal to launch postgresql
CREATE DATABASE not_my_fitness_pal;
```

- Postico setup

```sql
CREATE TABLE people (
id SERIAL PRIMARY KEY,
name VARCHAR(255),
age INT,
height_in_cm DECIMAL(4,1),
weight_in_kg DECIMAL(4,1),
calorie_target INT
);

INSERT INTO people (name, age, height_in_cm, weight_in_kg, calorie_target)
VALUES ('Sarina', 22, 167.0, 58, 2000);
INSERT INTO people (name, age, height_in_cm, weight_in_kg, calorie_target)
VALUES ('Aaron', 23, 175.4, 65.0, 2500);
INSERT INTO people (name, age, height_in_cm, weight_in_kg, calorie_target)
VALUES ('Nasir', 23, 172.0, 64.0, 3000);
INSERT INTO people (name, age, height_in_cm, weight_in_kg, calorie_target)
VALUES ('Suraaj', 23, 170.0, 80.0, 2000);
INSERT INTO people (name, age, height_in_cm, weight_in_kg, calorie_target)
VALUES ('Marcy', 23, 157.0, 47.0, 2000);

CREATE TABLE food_entries (
id SERIAL PRIMARY KEY,
person_id INT REFERENCES people(id) NOT NULL,
name VARCHAR(255),
meal_type VARCHAR(255),
notes TEXT,
calories INT,
week INT,
day VARCHAR(255)
);

INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (1, 'toast', 'BREAKFAST', 'Kingsmill 50/50 bread with butter', 60, 1, 'MONDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (1, 'cereal', 'BREAKFAST', 'Curiously Cinnamon', 400, 1, 'TUESDAY');

INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (3, 'toast', 'BREAKFAST', 'Kingsmill 50/50 bread with nutella', 60, 1, 'MONDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (3, 'cereal', 'BREAKFAST', 'Curiously Cinnamon', 400, 1, 'TUESDAY');

===========================
Adding more entries
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (3, 'tuna & sweetcorn sandwich', 'LUNCH', 'Kingsmill 50/50 bread with tuna, mayo and sweetcorn ', 200, 1, 'MONDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (3, 'greek yoghurt with grapes', 'LUNCH', 'greek honey yoghurt with black grapes', 150, 1, 'TUESDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (3, 'fish and chips', 'DINNER', 'Cod fish and fat chips', 600, 1, 'MONDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (3, 'spaghetti bolognese', 'DINNER', 'Minced meat, tomato sauce', 650, 1, 'TUESDAY');

INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (4, 'cereal', 'BREAKFAST', 'bowl of cornflakes', 350, 1, 'MONDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (4, 'cereal', 'BREAKFAST', 'bowl of cornflakes', 350, 1, 'TUESDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (4, 'sandwich', 'LUNCH', 'cheese and cucumber sandwich with white bread', 400, 1, 'MONDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (4, 'sandwich', 'LUNCH', 'chicken sandwich with white bread', 500, 1, 'TUESDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (4, 'burger and chips', 'DINNER', 'quarter pounder chicken burger with chips', 700, 1, 'MONDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (4, 'fish and chips', 'DINNER', 'Cod fish and fat chips', 600, 1, 'TUESDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (4, 'packet of crisps', 'SNACK', 'thai sweet chilli sensations', 100, 1, 'MONDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (4, 'fridge raiders', 'SNACK', 'chicken fridge raiders', 100, 1, 'TUESDAY');

INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (5, 'cereal and milk', 'BREAKFAST', 'frosties', 250, 1, 'MONDAY'); 
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (5, 'cheese toastie', 'LUNCH', 'cheddar', 500, 1, 'MONDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (5, 'roast pork noodle soup', 'DINNER', 'takeaway', 650, 1, 'MONDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (5, 'avocado on toast', 'BREAKFAST', 'sliced avocado on brown bread', 200, 1, 'TUESDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (5, 'chilli con carne', 'DINNER', 'minced beef and rice', 700, 1, 'TUESDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (5, 'ice cream', 'SNACK', 'vanilla flavour', 200, 1, 'TUESDAY');

INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (2, 'pan au chocolat', 'BREAKFAST', 'Lidl fresh bakery', 150, 1, 'MONDAY'); 
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (2, 'lasagna', 'LUNCH', 'Lidl lasagna', 350, 1, 'MONDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (2, 'lamb biryani', 'DINNER', 'homemade', 600, 1, 'MONDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (2, 'weetabix', 'BREAKFAST', '2-3 pieces with heated milk', 200, 1, 'TUESDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (2, 'bao buns and dumplings', 'LUNCH', 'bao buns and dumplings', 300, 1, 'TUESDAY');
INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
VALUES (2, 'spaghetti bolognese', 'DINNER', 'homemade', 200, 1, 'TUESDAY');
```