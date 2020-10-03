insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

insert into forma_pagamento (descricao) values ('Cartão de Crédito');
insert into forma_pagamento (descricao) values ('Débito');
insert into forma_pagamento (descricao) values ('Ticket refeição/alimentação');

insert into restaurante (nome, taxa_frete, cozinha_id, forma_pagamento_id) values ('Thai Gourmet', 10, 1, 1);
insert into restaurante (nome, taxa_frete, cozinha_id, forma_pagamento_id) values ('Thai Delivery', 9.50, 1, 2);
insert into restaurante (nome, taxa_frete, cozinha_id, forma_pagamento_id) values ('Tuk Tuk Comida Indiana', 15, 2, 3);

