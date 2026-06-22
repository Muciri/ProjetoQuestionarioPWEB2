package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.exception.PerguntaNaoEncontradaException;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Corrida;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Pergunta;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.CorridaRepository;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.PerguntaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class PerguntaService {

    private PerguntaRepository perguntaRepository;
    private CorridaRepository corridaRepository;

    public PerguntaService(PerguntaRepository perguntaRepository, CorridaRepository corridaRepository) {
        this.perguntaRepository = perguntaRepository;
        this.corridaRepository = corridaRepository;
    }

    public List<Pergunta> getPerguntas() {
        return perguntaRepository.findAll();
    }

    public List<Pergunta> getPerguntasPorCorrida(Long corridaId) {
        return perguntaRepository.findByCorridaId(corridaId);
    }

    public List<Corrida> getCorridas() {
        return corridaRepository.findAll();
    }

    public Pergunta getPerguntaById(Long id) {
        return perguntaRepository.findById(id)
                .orElseThrow(() -> new PerguntaNaoEncontradaException(id));
    }

    public Pergunta createPergunta(Pergunta pergunta) {
        return perguntaRepository.save(pergunta);
    }

    public Pergunta updatePergunta(Long id, Pergunta pergunta) {
        getPerguntaById(id);
        pergunta.setId(id);
        return perguntaRepository.save(pergunta);
    }

    public void deletePerguntaById(Long id) {
        getPerguntaById(id);
        perguntaRepository.deleteById(id);
    }

    public String salvarImagem(MultipartFile arquivo) throws IOException {
        String pastaAbsoluta = System.getProperty("user.dir") + "/src/main/resources/static/uploads/perguntas/";

        Path pasta = Paths.get(pastaAbsoluta);
        if (!Files.exists(pasta)) {
            Files.createDirectories(pasta);
        }

        String extensao = obterExtensao(arquivo.getOriginalFilename());
        String nomeArquivo = UUID.randomUUID().toString() + extensao;

        Path destino = pasta.resolve(nomeArquivo);
        Files.write(destino, arquivo.getBytes());

        return "/uploads/perguntas/" + nomeArquivo;
    }

    public void excluirImagem(String imagemPath) {
        if (imagemPath == null || imagemPath.isBlank()) return;
        try {
            String pastaAbsoluta = System.getProperty("user.dir") + "/src/main/resources/static";
            Path arquivo = Paths.get(pastaAbsoluta + imagemPath);
            Files.deleteIfExists(arquivo);
        } catch (IOException e) {
            System.err.println("Erro ao excluir imagem: " + e.getMessage());
        }
    }

    private String obterExtensao(String nomeOriginal) {
        if (nomeOriginal == null || !nomeOriginal.contains(".")) return ".png";
        return nomeOriginal.substring(nomeOriginal.lastIndexOf(".")).toLowerCase();
    }
}