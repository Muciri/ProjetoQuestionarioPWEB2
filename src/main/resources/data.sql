-- Dados de teste para a apresentação.
-- Para parar de executar no boot: spring.sql.init.mode=never

TRUNCATE TABLE resultado, pergunta_alternativas, pergunta, corrida, participante
    RESTART IDENTITY CASCADE;

-- participantes: Admin (admin), Alice/Bob/Carol/Diego (participantes comuns)
INSERT INTO participante (nome, email, admin) VALUES
    ('Admin', 'admin@ifpb.edu.br',  true),
    ('Alice', 'alice@exemplo.com',  false),
    ('Bob',   'bob@exemplo.com',    false),
    ('Carol', 'carol@exemplo.com',  false),
    ('Diego', 'diego@exemplo.com',  false);

-- corrida 1: sem perguntas (inativa)
-- corrida 2: com perguntas, ativa, já tem resultados no ranking
-- corrida 3: com perguntas, ativa, sem resultados (para jogar ao vivo)
-- corrida 4: com perguntas, inativa (para editar/excluir)
INSERT INTO corrida (titulo, descricao, tempo_segundos, ativa) VALUES
    ('Nova Corrida (Sem Perguntas)',
     'Corrida recém-cadastrada, aguardando perguntas para ser ativada.',
     60, false),

    ('Fundamentos de Java',
     'Teste seus conhecimentos sobre os fundamentos da linguagem Java.',
     60, true),

    ('Lógica de Programação',
     'Desafios clássicos de lógica, algoritmos e estruturas de dados.',
     90, true),

    ('Banco de Dados Básico',
     'Perguntas sobre SQL e modelagem relacional.',
     120, false);

-- resposta_correta = índice da alternativa correta (começa em 0)

-- corrida 2: Fundamentos de Java
INSERT INTO pergunta (enunciado, resposta_correta, pontos, id_corrida) VALUES
    ('Qual palavra-chave é usada para herança em Java?',   1, 10, 2),
    ('O que é encapsulamento em POO?',                     0, 15, 2),
    ('Qual é o tipo de retorno do método main() em Java?', 2, 10, 2);

INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
    (1, 'implements'), (1, 'extends'), (1, 'inherits'), (1, 'super');

INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
    (2, 'Ocultar detalhes internos e expor interface pública'),
    (2, 'Herdar métodos de outra classe'),
    (2, 'Criar múltiplos objetos iguais'),
    (2, 'Chamar métodos estáticos');

INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
    (3, 'int'), (3, 'String'), (3, 'void'), (3, 'boolean');

-- corrida 3: Lógica de Programação
INSERT INTO pergunta (enunciado, resposta_correta, pontos, id_corrida) VALUES
    ('Se x=10 e y=3, qual é o resultado de x % y?',              0, 10, 3),
    ('Quantas vezes o laço for(int i=0; i<5; i++) é executado?', 1, 10, 3),
    ('Qual estrutura de dados segue a ordem FIFO?',               3, 15, 3);

INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
    (4, '1'), (4, '3'), (4, '0'), (4, '2');

INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
    (5, '4'), (5, '5'), (5, '6'), (5, 'Infinito');

INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
    (6, 'Pilha'), (6, 'Árvore'), (6, 'Grafo'), (6, 'Fila');

-- corrida 4: Banco de Dados Básico
INSERT INTO pergunta (enunciado, resposta_correta, pontos, id_corrida) VALUES
    ('Qual comando SQL é usado para consultar dados?', 0, 10, 4),
    ('O que significa a sigla DDL em SQL?',            2, 15, 4);

INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
    (7, 'SELECT'), (7, 'INSERT'), (7, 'UPDATE'), (7, 'DELETE');

INSERT INTO pergunta_alternativas (id_pergunta, alternativa) VALUES
    (8, 'Data Download Language'),
    (8, 'Data Distribution Layer'),
    (8, 'Data Definition Language'),
    (8, 'Database Debug Log');

-- resultados da corrida 2 (ranking já populado)
INSERT INTO resultado (id_participante, id_corrida, pontuacao, data_hora) VALUES
    (2, 2, 35.0, NOW() - INTERVAL '2 days'),
    (3, 2, 10.0, NOW() - INTERVAL '1 day'),
    (4, 2, 25.0, NOW() - INTERVAL '3 hours'),
    (2, 3, 35.0, NOW() - INTERVAL '12 hours');
