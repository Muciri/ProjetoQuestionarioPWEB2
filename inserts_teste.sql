-- ROLLBACK

INSERT INTO Participante (nome, email, admin) VALUES
('Murilo', 'murilo@email.com', true),
('Gabriel', 'gabriel@email.com', false),
('Francisco', 'francisco@email.com', false),
('Felipe', 'felipe@email.com', false);

INSERT INTO Corrida (titulo, descricao, tempo_segundos, ativa, participante_id_participante) VALUES
('Corrida de Lógica', 'Teste de raciocínio lógico', 300, true, 1),
('Corrida de Matemática', 'Questões matemáticas básicas', 600, true, 2),
('Corrida de Programação', 'Algoritmos e código', 900, false, 1);

INSERT INTO Pergunta (enunciado, resposta_correta, id_corrida) VALUES
('Quanto é 2 + 2?', 1, 2),
('Qual a saída de um loop for de 0 a 2?', 2, 3),
('Se A = true e B = false, A AND B é?', 0, 1);

-- Pergunta 1
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(1, '3'),
(1, '4'),
(1, '5');

-- Pergunta 2
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(2, '0 1'),
(2, '1 2'),
(2, '0 1 2');

-- Pergunta 3
INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
(3, 'false'),
(3, 'true');

INSERT INTO Resultado (id_participante, id_corrida, pontuacao, data_hora) VALUES
(2, 1, 80.5, '2026-04-03 10:00:00'),
(3, 2, 90.0, '2026-04-03 11:00:00'),
(4, 1, 70.0, '2026-04-03 12:00:00'),
(2, 3, 95.0, '2026-04-03 13:00:00');

SELECT * FROM Participante;
SELECT * FROM Corrida;
SELECT * FROM Pergunta;
SELECT * FROM pergunta_alternativas;
SELECT * FROM Resultado;