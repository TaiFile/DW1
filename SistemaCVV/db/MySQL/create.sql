drop database if exists Veiculos;

create database Veiculos;

use Veiculos;

-- Tabela Cliente
CREATE TABLE Cliente (
    id BIGINT AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(64) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    sexo CHAR(1),
    data_nascimento DATE,
    PRIMARY KEY (id),
    UNIQUE (email),
    UNIQUE (cpf)
);

-- Tabela Loja
CREATE TABLE Loja (
    id BIGINT AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(64) NOT NULL,
    cnpj VARCHAR(18) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    PRIMARY KEY (id),
    UNIQUE (email),
    UNIQUE (cnpj)
);

-- Tabela Veiculo
CREATE TABLE Veiculo (
    id BIGINT AUTO_INCREMENT,
    loja_id BIGINT NOT NULL,
    placa VARCHAR(10) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    ano INT NOT NULL,
    quilometragem DECIMAL(10,2) NOT NULL,
    descricao TEXT,
    valor DECIMAL(12,2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (loja_id) REFERENCES Loja(id),
    UNIQUE (placa),
    UNIQUE (chassi)
);

-- Tabela Proposta
CREATE TABLE Proposta (
    id BIGINT AUTO_INCREMENT,
    veiculo_id BIGINT NOT NULL,
    cliente_id BIGINT NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    condicoes_pagamento TEXT NOT NULL,
    data_proposta DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ABERTO',
    PRIMARY KEY (id),
    FOREIGN KEY (veiculo_id) REFERENCES Veiculo(id),
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);