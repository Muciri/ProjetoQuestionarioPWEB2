-- Dados de teste para a apresentação, lembrar de por never no proprietis p n ficar sobrescrevendo

-- Cenários cobertos:
--   A) Corridas ATIVAS COM perguntas E COM resultados (UC07, UC08, UC09, ranking)
--   B) Corridas ATIVAS COM perguntas SEM resultados   (UC08 limpo / participante novo)
--   C) Corridas ATIVAS SEM perguntas                  (UC04 / cadastrar perguntas)
--   D) Corridas INATIVAS                              (UC02/UC03 sem afetar o lobby)
--   E) Participantes com pontuação e SEM pontuação    (UC09 destaque + aviso)

TRUNCATE TABLE resultado, pergunta_alternativas, pergunta, corrida, participante
    RESTART IDENTITY CASCADE;

-- PARTICIPANTES
--   ids 1–4: equipe (Murilo = admin)
--   ids 5–7: participantes COM pontuação
--   ids 8–9: participantes SEM pontuação (testar aviso do UC09)

INSERT INTO Participante (nome, email, admin) VALUES
('Murilo',     'murilo@email.com',     true),
('Gabriel',    'gabriel@email.com',    false),
('Francisco',  'francisco@email.com',  false),
('Felipe',     'felipe@email.com',     false),
('Ana',        'ana@email.com',        false),
('Lucas',      'lucas@email.com',      false),
('Bianca',     'bianca@email.com',     false),
('Joao',       'joao@email.com',       false),
('Maria',      'maria@email.com',      false);

-- CORRIDAS
--   ids 1–5: ATIVAS com perguntas    (cenários A e B)
--   id  6  : INATIVA com perguntas   (cenário D)
--   ids 7–8: ATIVAS SEM perguntas    (cenário C — listar/criar perguntas)
--   id  9  : INATIVA SEM perguntas   (cenário D limpo)

INSERT INTO Corrida (titulo, descricao, tempo_segundos, ativa, participante_id_participante) VALUES
-- ATIVAS com perguntas
('Corrida de Lógica',         'Teste de raciocínio lógico com questões de verdadeiro/falso e dedução.', 300, true, 1),
('Corrida de Matemática',     'Questões matemáticas básicas e interpretação numérica.',                 300, true, 1),
('Corrida de Programação',    'Conceitos de algoritmos, laços, variáveis e Java.',                      420, true, 1),
('Corrida de Web',            'Perguntas sobre HTML, CSS, HTTP e Spring MVC.',                          420, true, 1),
('Corrida de Banco de Dados', 'Questões sobre SQL, tabelas, chaves e relacionamentos.',                 420, true, 1),
-- INATIVA com perguntas
('Corrida Antiga Inativa',    'Corrida antiga mantida apenas como exemplo inativo.',                    300, false, 1),
-- ATIVAS sem perguntas (para UC04 — admin cadastra perguntas)
('Corrida Vazia de Português','Corrida ativa SEM perguntas — usar para cadastrar perguntas (UC04).',    300, true, 1),
('Corrida Vazia de História', 'Corrida ativa SEM perguntas — segunda opção para cadastro de perguntas.', 240, true, 1),
-- INATIVA sem perguntas
('Rascunho Inativo',          'Corrida inativa e sem perguntas — útil para UC02/UC03 sem efeitos.',     180, false, 1);

-- PERGUNTAS
--   Regra: cada corrida ativa popular possui 3 perguntas somando 10 pontos
--   Fácil = 2, média = 3, difícil = 5

INSERT INTO Pergunta (enunciado, resposta_correta, pontos, id_corrida) VALUES
-- Corrida 1: Lógica
('Se A = true e B = false, A AND B é?',                                                                 0, 2, 1),
('Qual alternativa representa uma proposição lógica?',                                                  1, 3, 1),
('Se todo aluno estuda e Felipe é aluno, então Felipe estuda. Esse raciocínio é exemplo de:',           2, 5, 1),

-- Corrida 2: Matemática
('Quanto é 2 + 2?',                                                                                     1, 2, 2),
('Quanto é 12 dividido por 3?',                                                                         2, 3, 2),
('Se x = 5, quanto vale 2x + 3?',                                                                       3, 5, 2),

-- Corrida 3: Programação
('Qual palavra-chave é usada para criar uma classe em Java?',                                           1, 2, 3),
('Qual estrutura é mais adequada para repetir um bloco enquanto uma condição for verdadeira?',          2, 3, 3),
('Qual a saída de um loop for de 0 a 2 imprimindo o valor de i?',                                       2, 5, 3),

-- Corrida 4: Web
('Qual tag HTML é usada para criar um link?',                                                           0, 2, 4),
('Qual método HTTP é mais adequado para enviar dados de um formulário de cadastro?',                    1, 3, 4),
('No padrão MVC, qual camada é responsável por receber a requisição e chamar a lógica necessária?',     2, 5, 4),

-- Corrida 5: Banco de Dados
('Qual comando SQL é usado para buscar dados de uma tabela?',                                           0, 2, 5),
('Qual tipo de chave identifica unicamente um registro em uma tabela?',                                 1, 3, 5),
('Em um relacionamento 1:N, onde normalmente fica a chave estrangeira?',                                2, 5, 5),

-- Corrida 6: Inativa (exemplo de UC06 com pergunta única)
('Pergunta de exemplo da corrida inativa.',                                                             0, 2, 6);

-- ALTERNATIVAS

-- Pergunta 1 (Lógica)
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(1, 'false'), (1, 'true'), (1, 'depende');

-- Pergunta 2
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(2, 'Uma frase sem sentido'),
(2, 'Uma afirmação que pode ser verdadeira ou falsa'),
(2, 'Um número decimal');

-- Pergunta 3
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(3, 'Composição'), (3, 'Herança'), (3, 'Dedução lógica');

-- Pergunta 4 (Matemática)
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(4, '3'), (4, '4'), (4, '5');

-- Pergunta 5
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(5, '2'), (5, '3'), (5, '4');

-- Pergunta 6
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(6, '10'), (6, '11'), (6, '12'), (6, '13');

-- Pergunta 7 (Programação)
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(7, 'function'), (7, 'class'), (7, 'object');

-- Pergunta 8
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(8, 'if'), (8, 'switch'), (8, 'while');

-- Pergunta 9
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(9, '1 2 3'), (9, '0 1'), (9, '0 1 2');

-- Pergunta 10 (Web)
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(10, '<a>'), (10, '<link>'), (10, '<href>');

-- Pergunta 11
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(11, 'GET'), (11, 'POST'), (11, 'DELETE');

-- Pergunta 12
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(12, 'Model'), (12, 'View'), (12, 'Controller');

-- Pergunta 13 (BD)
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(13, 'SELECT'), (13, 'INSERT'), (13, 'DELETE');

-- Pergunta 14
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(14, 'Chave estrangeira'), (14, 'Chave primária'), (14, 'Chave composta');

-- Pergunta 15
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(15, 'Na tabela do lado 1'),
(15, 'Em uma tabela separada obrigatoriamente'),
(15, 'Na tabela do lado N');

-- Pergunta 16 (corrida inativa)
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(16, 'Alternativa correta'), (16, 'Alternativa incorreta');

-- RESULTADOS - cenário A (corridas realizadas)
--   Participantes 5 (Ana), 6 (Lucas), 7 (Bianca) já jogaram várias corridas
--   Participantes 2 (Gabriel), 3 (Francisco), 4 (Felipe) jogaram algumas
--   Participantes 8 (Joao) e 9 (Maria) NÃO têm pontuação — aviso UC09
--   Cada corrida tem várias linhas para o ranking parecer disputado

INSERT INTO Resultado (id_participante, id_corrida, pontuacao, data_hora) VALUES
-- Corrida 1: Lógica
(2, 1,  7.0, '2026-04-03 10:00:00'),
(3, 1, 10.0, '2026-04-03 10:20:00'),
(4, 1,  5.0, '2026-04-03 10:40:00'),
(5, 1,  8.0, '2026-04-04 09:10:00'),
(6, 1,  6.0, '2026-04-04 09:30:00'),

-- Corrida 2: Matemática
(2, 2,  8.0, '2026-04-03 11:00:00'),
(3, 2,  6.0, '2026-04-03 11:20:00'),
(5, 2, 10.0, '2026-04-03 11:40:00'),
(6, 2,  5.0, '2026-04-04 10:00:00'),
(7, 2,  9.0, '2026-04-04 10:20:00'),

-- Corrida 3: Programação
(4, 3,  7.0, '2026-04-03 12:00:00'),
(5, 3,  5.0, '2026-04-03 12:20:00'),
(6, 3, 10.0, '2026-04-03 12:40:00'),
(7, 3,  8.0, '2026-04-04 11:00:00'),

-- Corrida 4: Web
(2, 4,  6.0, '2026-04-03 13:00:00'),
(4, 4, 10.0, '2026-04-03 13:20:00'),
(6, 4,  8.0, '2026-04-03 13:40:00'),
(7, 4,  7.0, '2026-04-04 12:00:00'),

-- Corrida 5: Banco de Dados
(3, 5,  5.0, '2026-04-03 14:00:00'),
(5, 5,  7.0, '2026-04-03 14:20:00'),
(6, 5, 10.0, '2026-04-03 14:40:00'),
(7, 5,  9.0, '2026-04-04 13:00:00');
