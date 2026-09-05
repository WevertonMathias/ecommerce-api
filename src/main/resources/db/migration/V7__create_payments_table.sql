CREATE TABLE payments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL UNIQUE REFERENCES orders(id) ON DELETE CASCADE,
    method VARCHAR(20) NOT NULL
        CHECK (method IN ('CREDIT_CARD', 'PIX', 'BOLETO')),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING'
        CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
    amount NUMERIC(10, 2) NOT NULL CHECK (amount >= 0),
    paid_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

COMMENT ON TABLE payments IS 'Um pagamento por pedido (UNIQUE em order_id). Simulado — sem integração com gateway real.';
