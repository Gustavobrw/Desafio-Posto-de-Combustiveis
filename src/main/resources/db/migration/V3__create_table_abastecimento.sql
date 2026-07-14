CREATE TABLE abastecimento (
    id BIGSERIAL PRIMARY KEY,
    data TIMESTAMP NOT NULL,
    valor_total NUMERIC,
    litros NUMERIC NOT NULL,
    bomba_combustivel_id BIGINT REFERENCES bomba_combustivel(id)
);