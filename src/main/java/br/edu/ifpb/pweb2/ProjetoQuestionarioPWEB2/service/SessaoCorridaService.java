package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Corrida;
import jakarta.servlet.http.HttpSession;


@Service
public class SessaoCorridaService {

    public static final String KEY_CORRIDA   = "corridaAtual";
    public static final String KEY_DECORRIDO = "corridaTempoDecorrido";    
    public static final String KEY_RETOMADA  = "corridaMomentoRetomada";   
    public static final String KEY_ACERTOS   = "corridaAcertos";
    public static final String KEY_INDICE    = "corridaIndicePergunta";
    public static final String KEY_PONTUACAO = "corridaPontuacao";

    public void iniciarCorrida(HttpSession session, Corrida corrida) {
        session.setAttribute(KEY_CORRIDA, corrida);
        session.setAttribute(KEY_DECORRIDO, 0L);
        session.setAttribute(KEY_RETOMADA,  LocalDateTime.now());
        session.setAttribute(KEY_ACERTOS, 0);
        session.setAttribute(KEY_INDICE,  0);
        session.setAttribute(KEY_PONTUACAO, 0);
    }

    public Corrida corridaAtiva(HttpSession session) {
        return (Corrida) session.getAttribute(KEY_CORRIDA);
    }

    public long tempoRestante(HttpSession session) {
        Corrida corrida = corridaAtiva(session);
        if (corrida == null) return 0;
        return Math.max(0, corrida.getTempoSegundos() - getDecorridoTotal(session));
    }

    public void pausar(HttpSession session) {
        if (corridaAtiva(session) == null) return;
        LocalDateTime retomada = (LocalDateTime) session.getAttribute(KEY_RETOMADA);
        if (retomada == null) return; // já está pausada
        session.setAttribute(KEY_DECORRIDO, getDecorridoTotal(session));
        session.removeAttribute(KEY_RETOMADA);
    }

    public void retomar(HttpSession session) {
        if (corridaAtiva(session) == null) return;
        if (session.getAttribute(KEY_RETOMADA) != null) return; // já em andamento
        session.setAttribute(KEY_RETOMADA, LocalDateTime.now());
    }

    private long getDecorridoTotal(HttpSession session) {
        Object dec = session.getAttribute(KEY_DECORRIDO);
        long decorridoSalvo = dec == null ? 0L : (long) dec;
        LocalDateTime retomada = (LocalDateTime) session.getAttribute(KEY_RETOMADA);
        if (retomada == null) {
            return decorridoSalvo;
        }
        return decorridoSalvo + ChronoUnit.SECONDS.between(retomada, LocalDateTime.now());
    }

    public void encerrarCorrida(HttpSession session) {
        session.removeAttribute(KEY_CORRIDA);
        session.removeAttribute(KEY_DECORRIDO);
        session.removeAttribute(KEY_RETOMADA);
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