
USE tutiveinteveinteveinticinco;

-- Recetas de ejemplo
INSERT INTO receta (id, nombre, descripcion, activo)
VALUES (1, 'Guiso de lentejas', 'Un guiso nutritivo ideal para el invierno.', true),
       (2, 'Tarta de verduras', 'Receta saludable con vegetales mixtos.', true);

-- Ingredientes para Guiso de lentejas
INSERT INTO ingrediente_receta (nombre_ingrediente, cantidad, calorias, activo, receta_id)
VALUES ('Lentejas', 0.5, 300, true, 1),
       ('Papa', 0.4, 150, true, 1),
       ('Zanahoria', 0.2, 80, true, 1);

-- Ingredientes para Tarta de verduras
INSERT INTO ingrediente_receta (nombre_ingrediente, cantidad, calorias, activo, receta_id)
VALUES ('Acelga', 0.3, 60, true, 2),
       ('Huevo', 0.2, 120, true, 2),
       ('Queso', 0.2, 250, true, 2);
