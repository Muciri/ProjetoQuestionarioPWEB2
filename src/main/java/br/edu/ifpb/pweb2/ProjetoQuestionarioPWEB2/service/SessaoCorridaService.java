package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Corrida;
import jakarta.servlet.http.HttpSession;


@Service
public class SessaoCorridaService {

    public static final String KEY_CORRIDA   = "corridaAtual";
    public static final String KEY_INICIO    = "corridaInicio";
    public static final String KEY_ACERTOS   = "corridaAcertos";
    public static final String KEY_INDICE    = "corridaIndicePergunta";
    public static final String KEY_PONTUACAO = "corridaPontuacao";

    // Felipe tu vai controlar a corrida ativa, tempo restante, acertos e índice da pergunta atual na sessão do usuário. 
    // Eu fiz essa classe para centralizar toda a lógica de controle da corrida na sessão, 
    // evitando que tenhamos código duplicado espalhado pelos controllers.

    public void iniciarCorrida(HttpSession session, Corrida corrida) {
        session.setAttribute(KEY_CORRIDA, corrida);
        session.setAttribute(KEY_INICIO,  LocalDateTime.now());
        session.setAttribute(KEY_ACERTOS, 0);
        session.setAttribute(KEY_INDICE,  0);
        session.setAttribute(KEY_PONTUACAO, 0);
    }

    public Corrida corridaAtiva(HttpSession session) {
        return (Corrida) session.getAttribute(KEY_CORRIDA);
    }

    public long tempoRestante(HttpSession session) {
        Corrida corrida = corridaAtiva(session);
        LocalDateTime inicio = (LocalDateTime) session.getAttribute(KEY_INICIO);
        if (corrida == null || inicio == null) return 0;
        long decorrido = ChronoUnit.SECONDS.between(inicio, LocalDateTime.now());
        return Math.max(0, corrida.getTempoSegundos() - decorrido);
    }

    public void encerrarCorrida(HttpSession session) {
        session.removeAttribute(KEY_CORRIDA);
        session.removeAttribute(KEY_INICIO);
        session.removeAttribute(KEY_ACERTOS);
        session.removeAttribute(KEY_INDICE);
        session.removeAttribute(KEY_PONTUACAO);
    }


    public int getAcertos(HttpSession session) {
        Object v = session.getAttribute(KEY_ACERTOS);
        return v == null ? 0 : (int) v;
    }

    public void incrementarAcertos(HttpSession session) {
        session.setAttribute(KEY_ACERTOS, getAcertos(session) + 1);
    }

    public int getPontuacao(HttpSession session) {
        Object valor = session.getAttribute(KEY_PONTUACAO);
        return valor == null ? 0 : (int) valor;
    }

    public void somarPontuacao(HttpSession session, Integer pontos) {
        int pontosDaPergunta = pontos == null ? 0 : pontos;
        int pontuacaoAtual = getPontuacao(session);
        session.setAttribute(KEY_PONTUACAO, pontuacaoAtual + pontosDaPergunta);
    }

    // --- Índice da pergunta atual (Felipe controla) ---

    public int getIndicePergunta(HttpSession session) {
        Object v = session.getAttribute(KEY_INDICE);
        return v == null ? 0 : (int) v;
    }

    public void avancarPergunta(HttpSession session) {
        session.setAttribute(KEY_INDICE, getIndicePergunta(session) + 1);
    }
}