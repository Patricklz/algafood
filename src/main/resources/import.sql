insert into cozinha (nome) values ('Tailandesa');
insert into cozinha (nome) values ('Indiana');

insert into forma_pagamento (descricao) values ('Cartão de Crédito');
insert into forma_pagamento (descricao) values ('Débito');
insert into forma_pagamento (descricao) values ('Ticket refeição/alimentação');

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Gourmet', 10, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Delivery', 9.50, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Tuk Tuk Comida Indiana', 15, 2);


insert into estado (id, nome) values (1, 'Espírito Santo');
insert into estado (id, nome) values (2, 'Rio de Janeiro');
insert into estado (id, nome) values (3, 'São Paulo');

insert into cidade(id, nome, estado_id) values (1, 'Vitória', 1);
insert into cidade(id, nome, estado_id) values (2, 'Rio de Janeiro', 2);
insert into cidade(id, nome, estado_id) values (3, 'São Paulo', 3);

insert into restaurante_forma_pagamento(restaurante_id, forma_pagamento_id) values (1, 1), (1,2), (1,3), (2,3), (3,2), (3,3);





