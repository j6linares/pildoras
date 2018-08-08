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