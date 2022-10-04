INSERT INTO restaurante (id, cep, complemento, nome) VALUES
(1L, '0000001', 'Complemento Endereço Restaurante 1', 'Restaurante 1'),
(2L, '0000002', 'Complemento Endereço Restaurante 2', 'Restaurante 2');

INSERT INTO cliente (id, cep, complemento, nome) VALUES
(1L, '0000001', 'Complemento Endereço Cliente 1', 'Cliente 1');

INSERT INTO produto (id, nome, valor_unitario, disponivel, restaurante_id) VALUES
(1L, 'Produto 1', 5.0, true, 1L),
(2L, 'Produto 2', 6.0, true, 2L),
(3L, 'Produto 3', 7.0, true, 2L);

INSERT INTO sacola (id, forma_pagamento, fechado, valor_total, cliente_id) VALUES
(1L, 0, false, 0.0, 1L);