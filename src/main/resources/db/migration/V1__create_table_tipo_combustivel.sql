CREATE TABLE tipo_combustivel (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    preco_litro NUMERIC NOT NULL
);