CREATE TABLE product_variants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID NOT NULL REFERENCES products(id),
    size VARCHAR(20),
    color VARCHAR(40),
    sku VARCHAR(60) NOT NULL UNIQUE,
    price NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
    stock_quantity INTEGER NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

COMMENT ON TABLE product_variants IS 'Combinação vendável de um produto (ex: Camiseta Básica, M, Azul). Estoque e preço vivem aqui.';

CREATE INDEX idx_product_variants_product_id ON product_variants(product_id);
