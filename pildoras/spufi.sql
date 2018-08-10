CREATE TABLE mitabla (
	nombre VARCHAR(45) NOT NULL,
	apellidos VARCHAR(45) NOT NULL
) 
;
SELECT * FROM mitabla
;
INSERT INTO mitabla (nombre, apellidos) 
 VALUES ('Julian', 'Garcia Linares')
 ;
UPDATE mitabla SET apellidos = 'García Linares' 
 WHERE nombre = 'Julian'
 ;
DELETE FROM mitabla 
 WHERE nombre = 'Julian';