UPDATE food_item SET nome = nomel WHERE nome IS NULL AND nomel IS NOT NULL;

ALTER TABLE food_item DROP COLUMN nomel;