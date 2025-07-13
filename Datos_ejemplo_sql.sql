-- Desactivamos temporalmente las claves foráneas
SET FOREIGN_KEY_CHECKS = 0;

-- Limpiamos datos previos si queremos evitar conflictos
TRUNCATE TABLE preparacion;
TRUNCATE TABLE ingrediente_receta;
TRUNCATE TABLE integrante;
TRUNCATE TABLE receta;
TRUNCATE TABLE ingrediente;
TRUNCATE TABLE familia;

SET FOREIGN_KEY_CHECKS = 1;

-- Insertar familias
INSERT INTO familia (activo, fecha_alta, nombre_familia) VALUES
(1, '2024-01-15', 'Familia García'),
(1, '2024-03-10', 'Familia López'),
(1, '2024-06-22', 'Familia Martínez'),
(1, '2024-09-05', 'Familia Pérez'),
(1, '2024-12-01', 'Familia Sánchez');

-- Insertar ingredientes
INSERT INTO ingrediente (activo, descripcion, nombre, unidad_base, calorias_por_unidad) VALUES
(1, 'Fruta tropical rica en vitamina C', 'Piña', 'unidad', 50),
(1, 'Verdura verde muy nutritiva', 'Espinaca', 'gramo', 23),
(1, 'Legumbre rica en proteínas', 'Lentejas', 'gramo', 35),
(1, 'Carne magra', 'Pollo', 'gramo', 110),
(1, 'Aceite vegetal saludable', 'Aceite de oliva', 'ml', 90);

-- Insertar recetas
INSERT INTO receta (activo, descripcion, nombre, calorias_totales) VALUES
(1, 'Plato principal con pollo, arroz y verduras', 'Pollo al horno', 450),
(1, 'Ensalada fresca con espinacas, almendras y piña', 'Ensalada Tropical', 200),
(1, 'Desayuno completo con avena, banana y almendras', 'Desayuno Energético', 300),
(1, 'Guiso casero con lentejas y verduras', 'Guiso de Lentejas', 280);

-- Insertar relaciones Ingrediente-Receta
INSERT INTO ingrediente_receta (activo, cantidad, calorias_por_porcion, receta_id, ingrediente_id) VALUES
(1, 200, 220, 1, 1),
(1, 150, 56, 1, 2),
(1, 100, 23, 1, 3),
(1, 150, 15, 2, 2),
(1, 200, 41, 2, 4);

-- Insertar integrantes
INSERT INTO integrante (apellido, dni, domicilio, fecha_nacimiento, nombre, ocupacion, activo, familia_id) VALUES
('García', 12345678, 'Av. Libertad 123', '1990-05-12', 'Carlos', 'Empleado', 1, 1),
('García', 87654321, 'Av. Libertad 123', '1992-08-22', 'María', 'Docente', 1, 1),
('López', 22334455, 'Calle Falsa 456', '1985-01-10', 'Javier', 'Estudiante', 1, 2);

-- Insertar preparaciones
INSERT INTO preparacion (activo, cantidad_raciones, fecha, receta_id) VALUES
(1, 4, '2025-07-10', 1),
(1, 2, '2025-07-11', 2),
(1, 3, '2025-07-11', 3);