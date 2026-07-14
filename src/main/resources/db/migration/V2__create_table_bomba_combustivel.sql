CREATE TABLE bomba_combustivel (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    tipo_combustivel_id BIGINT NOT NULL REFERENCES tipo_combustivel(id)
);