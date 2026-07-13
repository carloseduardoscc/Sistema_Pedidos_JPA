-- ==========================
-- CLIENTS
-- ==========================

INSERT INTO auth.client_tb
(id, client_id, client_secret, redirect_uri, scope)
VALUES ('11111111-1111-1111-1111-111111111111',
        'meu-client',
        '$2a$10$G8VcNoiN8ifZB7WM6nDyVeLJ7hR03tZgpaZ.Dz8qzI2EQz/YPmnxi',
        'http://localhost:8080/authorized',
        'ADMIN'
       );

-- ==========================
-- USUÁRIOS
-- ==========================

INSERT INTO auth.identidade_tb
(id, ativo, data_cadastro, data_atualizacao, email, senha, roles, version)
VALUES ('11111111-1111-1111-1111-111111111111',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'carlos@email.com',
        '$2a$10$rZjDpBft/x0vXoJykBwfBuj8.I6BxnfBJWM4W6NldVH5APb8gBN7q',
        ARRAY['ADMIN'],
        0),
       ('22222222-2222-2222-2222-222222222222',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'maria@email.com',
        '$2a$10$rZjDpBft/x0vXoJykBwfBuj8.I6BxnfBJWM4W6NldVH5APb8gBN7q',
        ARRAY['CLIENTE'],
        0),
       ('33333333-3333-3333-3333-333333333333',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'joao@email.com',
        '$2a$10$rZjDpBft/x0vXoJykBwfBuj8.I6BxnfBJWM4W6NldVH5APb8gBN7q',
        ARRAY['CLIENTE'],
        0),
       ('44444444-4444-4444-4444-444444444444',
        FALSE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'ana@email.com',
        '$2a$10$rZjDpBft/x0vXoJykBwfBuj8.I6BxnfBJWM4W6NldVH5APb8gBN7q',
        ARRAY['CLIENTE'],
        0),
       ('55555555-5555-5555-5555-555555555555',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'pedro.entregador@email.com',
        '$2a$10$rZjDpBft/x0vXoJykBwfBuj8.I6BxnfBJWM4W6NldVH5APb8gBN7q',
        ARRAY['ENTREGADOR'],
        0),
       ('66666666-6666-6666-6666-666666666666',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'lucas.lojista@email.com',
        '$2a$10$rZjDpBft/x0vXoJykBwfBuj8.I6BxnfBJWM4W6NldVH5APb8gBN7q',
        ARRAY['LOJISTA'],
        0);