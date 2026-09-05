CREATE TABLE orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE'
        CHECK (status IN ('PENDENTE', 'PAGO', 'ENVIADO', 'ENTREGUE', 'CANCELADO')),
    total_amount NUMERIC(10, 2) NOT NULL CHECK (total_amount >= 0),

    -- Endereço de entrega (embutido: um endereço por pedido, sem tabela separada)
    recipient_name VARCHAR(150) NOT NULL,
    street VARCHAR(200) NOT NULL,
    number VARCHAR(20) NOT NULL,
    complement VARCHAR(100),
    neighborhood VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state CHAR(2) NOT NULL,
    zip_code VARCHAR(9) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

COMMENT ON TABLE orders IS 'Pedidos. Status segue máquina de estados: PENDENTE -> PAGO -> ENVIADO -> ENTREGUE, ou CANCELADO (só antes de ENVIADO)';

CREATE INDEX idx_orders_user_id ON orders(user_id);

CREATE TABLE order_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_variant_id UUID NOT NULL REFERENCES product_variants(id),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    unit_price NUMERIC(10, 2) NOT NULL CHECK (unit_price >= 0),
    subtotal NUMERIC(10, 2) NOT NULL CHECK (subtotal >= 0)
);

COMMENT ON COLUMN order_items.unit_price IS 'Snapshot do preço no momento da compra. Nunca deve refletir mudanças futuras no preço da variante.';

CREATE INDEX idx_order_items_order_id ON order_items(order_id);
