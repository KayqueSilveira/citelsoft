package br.com.citelsoft.service;

import br.com.citelsoft.model.Candidato;
import br.com.citelsoft.model.CandidatoDTO;
import br.com.citelsoft.repository.CandidatoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DataLoader {

    @Autowired
    private CandidatoRepository repository;

    @PostConstruct
    public void init() {
        loadJsonData();
    }

    @Scheduled(cron = "0 59 23 * * ?")
    public void verificarCandidatos() {
        log.info("Iniciando verificação dos candidatos às 23h.");

        LocalDate hoje = LocalDate.now();
        LocalDate amanha = hoje.plusDays(1);

        List<Candidato> candidatos = repository.findAll();
        log.debug("Total de candidatos encontrados: {}", candidatos.size());

        for (Candidato candidato : candidatos) {
            LocalDate dataNasc = candidato.getDataNasc();
            LocalDate proximoAniversario = dataNasc.plusYears(70);

            if (!proximoAniversario.isAfter(amanha)) {
                log.info("Candidato com ID {} fará 70 anos em breve. Removendo do banco de dados.", candidato.getId());
                repository.delete(candidato);
            }
        }

        log.info("Verificação dos candidatos concluída.");
    }

    private void loadJsonData() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        TypeReference<List<CandidatoDTO>> typeReference = new TypeReference<List<CandidatoDTO>>() {};
        try (InputStream inputStream = getClass().getResourceAsStream("/candidatos.json")) {
            if (inputStream == null) {
                log.error("Arquivo candidatos.json não encontrado no pacote resources.");
                return;
            }

            List<CandidatoDTO> candidatoDTOs = mapper.readValue(inputStream, typeReference);
            List<Candidato> candidatos = candidatoDTOs.stream()
                    .map(dto -> new Candidato().convertToEntity(dto))
                    .filter(this::isCandidatoElegivel)
                    .filter(this::isCandidatoUnico)
                    .collect(Collectors.toList());
            repository.saveAll(candidatos);
            log.info("Candidatos elegíveis salvos no banco de dados!");
        } catch (IOException e) {
            log.error("Não foi possível salvar os candidatos: " + e.getMessage());
        }
    }

    private boolean isCandidatoElegivel(Candidato candidato) {
        LocalDate hoje = LocalDate.now();
        Period idade = Period.between(candidato.getDataNasc(), hoje);
        return idade.getYears() >= 16 && idade.getYears() <= 69 && candidato.getPeso() > 50;
    }

    private boolean isCandidatoUnico(Candidato candidato) {
        Optional<Candidato> existente = repository.findByCpf(candidato.getCpf());
        return existente.isEmpty();
    }
}
