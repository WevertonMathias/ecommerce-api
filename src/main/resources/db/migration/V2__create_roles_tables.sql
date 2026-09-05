CREATE TABLE roles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(30) NOT NULL UNIQUE
);

COMMENT ON TABLE roles IS 'Papéis de acesso: ADMIN, CLIENTE';

CREATE TABLE user_roles (
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- Papéis iniciais fixos do sistema
INSERT INTO roles (name) VALUES ('ADMIN'), ('CLIENTE');
