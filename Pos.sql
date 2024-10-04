CREATE DATABASE Pos;

USE Pos;

CREATE TABLE Contador (
    contadorF INT,
    contadorL INT
);

CREATE TABLE Cliente (
    id VARCHAR(10) NOT NULL,
    nombre VARCHAR(10) NOT NULL,
    telefono VARCHAR(10) NOT NULL,
    email VARCHAR(30) NOT NULL,
    descuento FLOAT,
    PRIMARY KEY (id)
);

CREATE TABLE Cajero (
    id VARCHAR(10) NOT NULL,
    nombre VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Categoria (
    id VARCHAR(10) NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Producto (
    codigo VARCHAR(10) NOT NULL,
    descripcion VARCHAR(30) NOT NULL,
    unidadMedida VARCHAR(20),
    precioUnitario DOUBLE,
    existencia INT,
    categoria VARCHAR(10),
    PRIMARY KEY (codigo)
);

ALTER TABLE Producto 
ADD CONSTRAINT fk_categoria 
FOREIGN KEY (categoria) REFERENCES Categoria(id);

CREATE TABLE Factura (
    numero VARCHAR(10) NOT NULL,
    cliente VARCHAR(10) NOT NULL,
    cajero VARCHAR(10) NOT NULL,
    fecha DATE NOT NULL,
    PRIMARY KEY (numero)
);

ALTER TABLE Factura 
ADD CONSTRAINT fk_cliente 
FOREIGN KEY (cliente) REFERENCES Cliente(id),
ADD CONSTRAINT fk_cajero 
FOREIGN KEY (cajero) REFERENCES Cajero(id);

CREATE TABLE Linea (
    codigo VARCHAR(10) NOT NULL,
    producto VARCHAR(10) NOT NULL,
    factura VARCHAR(10) NOT NULL,
    cantidad INT NOT NULL,
    descuento FLOAT NOT NULL,
    PRIMARY KEY (codigo)
);

ALTER TABLE Linea 
ADD CONSTRAINT fk_producto 
FOREIGN KEY (producto) REFERENCES Producto(codigo),
ADD CONSTRAINT fk_factura 
FOREIGN KEY (factura) REFERENCES Factura(numero);

INSERT INTO Contador (contadorF, contadorL) VALUES (1, 1);

INSERT INTO Categoria (id, nombre) VALUES ('001', 'Aguas');
INSERT INTO Categoria (id, nombre) VALUES ('002', 'Dulces');
INSERT INTO Categoria (id, nombre) VALUES ('003', 'Aceites');
INSERT INTO Categoria (id, nombre) VALUES ('004', 'Vinos');
INSERT INTO Categoria (id, nombre) VALUES ('006', 'Frutas');
