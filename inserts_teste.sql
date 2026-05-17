-- PARTICIPANTES
INSERT INTO Participante (nome, email, admin) VALUES
('Murilo', 'murilo@email.com', true),
('Gabriel', 'gabriel@email.com', false),
('Francisco', 'francisco@email.com', false),
('Felipe', 'felipe@email.com', false),
('Ana', 'ana@email.com', false),
('Lucas', 'lucas@email.com', false);


-- CORRIDAS
INSERT INTO Corrida (titulo, descricao, tempo_segundos, ativa, participante_id_participante) VALUES
('Corrida de Lógica', 'Teste de raciocínio lógico com questões de verdadeiro/falso e dedução.', 300, true, 1),
('Corrida de Matemática', 'Questões matemáticas básicas e interpretação numérica.', 300, true, 2),
('Corrida de Programação', 'Conceitos de algoritmos, laços, variáveis e Java.', 420, true, 1),
('Corrida de Web', 'Perguntas sobre HTML, CSS, HTTP e Spring MVC.', 420, true, 1),
('Corrida de Banco de Dados', 'Questões sobre SQL, tabelas, chaves e relacionamentos.', 420, true, 1),
('Corrida Antiga Inativa', 'Corrida antiga mantida apenas como exemplo inativo.', 300, false, 1);


-- PERGUNTAS
-- Regra: cada corrida ativa possui 3 perguntas somando 10 pontos.
-- Fácil = 2 pontos, média = 3 pontos, difícil = 5 pontos.

INSERT INTO Pergunta (enunciado, resposta_correta, pontos, id_corrida) VALUES
-- Corrida 1: Lógica
('Se A = true e B = false, A AND B é?', 0, 2, 1),
('Qual alternativa representa uma proposição lógica?', 1, 3, 1),
('Se todo aluno estuda e Felipe é aluno, então Felipe estuda. Esse raciocínio é exemplo de:', 2, 5, 1),

-- Corrida 2: Matemática
('Quanto é 2 + 2?', 1, 2, 2),
('Quanto é 12 dividido por 3?', 2, 3, 2),
('Se x = 5, quanto vale 2x + 3?', 3, 5, 2),

-- Corrida 3: Programação
('Qual palavra-chave é usada para criar uma classe em Java?', 1, 2, 3),
('Qual estrutura é mais adequada para repetir um bloco enquanto uma condição for verdadeira?', 2, 3, 3),
('Qual a saída de um loop for de 0 a 2 imprimindo o valor de i?', 2, 5, 3),

-- Corrida 4: Web
('Qual tag HTML é usada para criar um link?', 0, 2, 4),
('Qual método HTTP é mais adequado para enviar dados de um formulário de cadastro?', 1, 3, 4),
('No padrão MVC, qual camada é responsável por receber a requisição e chamar a lógica necessária?', 2, 5, 4),

-- Corrida 5: Banco de Dados
('Qual comando SQL é usado para buscar dados de uma tabela?', 0, 2, 5),
('Qual tipo de chave identifica unicamente um registro em uma tabela?', 1, 3, 5),
('Em um relacionamento 1:N, onde normalmente fica a chave estrangeira?', 2, 5, 5),

-- Corrida 6: Inativa
('Pergunta de exemplo da corrida inativa.', 0, 2, 6);


-- ALTERNATIVAS

-- Pergunta 1
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(1, 'false'),
(1, 'true'),
(1, 'depende');

-- Pergunta 2
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(2, 'Uma frase sem sentido'),
(2, 'Uma afirmação que pode ser verdadeira ou falsa'),
(2, 'Um número decimal');

-- Pergunta 3
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(3, 'Composição'),
(3, 'Herança'),
(3, 'Dedução lógica');

-- Pergunta 4
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(4, '3'),
(4, '4'),
(4, '5');

-- Pergunta 5
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(5, '2'),
(5, '3'),
(5, '4');

-- Pergunta 6
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(6, '10'),
(6, '11'),
(6, '12'),
(6, '13');

-- Pergunta 7
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(7, 'function'),
(7, 'class'),
(7, 'object');

-- Pergunta 8
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(8, 'if'),
(8, 'switch'),
(8, 'while');

-- Pergunta 9
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(9, '1 2 3'),
(9, '0 1'),
(9, '0 1 2');

-- Pergunta 10
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(10, '<a>'),
(10, '<link>'),
(10, '<href>');

-- Pergunta 11
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(11, 'GET'),
(11, 'POST'),
(11, 'DELETE');

-- Pergunta 12
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(12, 'Model'),
(12, 'View'),
(12, 'Controller');

-- Pergunta 13
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(13, 'SELECT'),
(13, 'INSERT'),
(13, 'DELETE');

-- Pergunta 14
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(14, 'Chave estrangeira'),
(14, 'Chave primária'),
(14, 'Chave composta');

-- Pergunta 15
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(15, 'Na tabela do lado 1'),
(15, 'Em uma tabela separada obrigatoriamente'),
(15, 'Na tabela do lado N');

-- Pergunta 16
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(16, 'Alternativa correta'),
(16, 'Alternativa incorreta');


-- RESULTADOS DE TESTE
-- Como cada corrida ativa vale no máximo 10 pontos, os resultados ficam entre 0 e 10.

INSERT INTO Resultado (id_participante, id_corrida, pontuacao, data_hora) VALUES
(2, 1, 7.0, '2026-04-03 10:00:00'),
(3, 1, 10.0, '2026-04-03 10:20:00'),
(4, 1, 5.0, '2026-04-03 10:40:00'),

(2, 2, 8.0, '2026-04-03 11:00:00'),
(3, 2, 6.0, '2026-04-03 11:20:00'),
(5, 2, 10.0, '2026-04-03 11:40:00'),

(4, 3, 7.0, '2026-04-03 12:00:00'),
(5, 3, 5.0, '2026-04-03 12:20:00'),
(6, 3, 10.0, '2026-04-03 12:40:00'),

(2, 4, 6.0, '2026-04-03 13:00:00'),
(4, 4, 10.0, '2026-04-03 13:20:00'),
(6, 4, 8.0, '2026-04-03 13:40:00'),

(3, 5, 5.0, '2026-04-03 14:00:00'),
(5, 5, 7.0, '2026-04-03 14:20:00'),
(6, 5, 10.0, '2026-04-03 14:40:00');


SELECT * FROM Participante;
SELECT * FROM Corrida;
SELECT * FROM Pergunta;
SELECT * FROM pergunta_alternativas;
SELECT * FROM Resultado;