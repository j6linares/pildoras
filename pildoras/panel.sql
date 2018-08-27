
DROP TABLE IF EXISTS panel;
CREATE TABLE panel (
	id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(8),
	titulo VARCHAR(70),
	entorno VARCHAR(15),
	subtitulo VARCHAR(70),
	fecha DATETIME,
	mensaje VARCHAR(80),
	pagina INT,
	paginas INT,
	PRIMARY KEY (id)
) ENGINE=InnoDB;
INSERT INTO panel (nombre, titulo, entorno, subtitulo, fecha, mensaje, pagina, paginas)
VALUES ("cPanel", "Crear un panel", "paneles", "CRUD paneles (Create)", NOW()
		, "> Teclea la info del panel y pulsa Guardar.", 1, 4);
INSERT INTO panel (nombre, titulo, entorno, subtitulo, fecha, mensaje, pagina, paginas)
VALUES ("rPanel", "Consulta de paneles", "paneles", "CRUD paneles (Read)", NOW()
		, "> La lista de paneles permite la consulta, modificación, eliminación o creación.", 2, 4);
INSERT INTO panel (nombre, titulo, entorno, subtitulo, fecha, mensaje, pagina, paginas)
VALUES ("uPanel", "Editar un panel", "paneles", "CRUD paneles (Update)", NOW()
		, "> Modifica los datos del panel y pulsa Guardar.", 3, 4);
		INSERT INTO panel (nombre, titulo, entorno, subtitulo, fecha, mensaje, pagina, paginas)
VALUES ("dPanel", "Eliminar un panel", "paneles", "CRUD paneles (Delete)", NOW()
		, "> Pulse Eliminar para borrar el panel del repositorio.", 4, 4);