package br.com.citelsoft.service;

import br.com.citelsoft.model.Candidato;
import br.com.citelsoft.model.CandidatoDTO;
import br.com.citelsoft.repository.CandidatoRepository;
import br.com.citelsoft.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CandidatoService {
    @Autowired
    private CandidatoRepository repository;

    @Autowired
    private Utils utils;

    public List<Candidato> findAll() {
        return repository.findAll();
    }

    public List<Object[]> countCandidatesByState() {
        return repository.countCandidatesByState();
    }

    public Map<String, Integer> getImcCandidato(List<Candidato> candidatos) {
        return utils.calcularImcPorFaixaEtaria(candidatos);
    }

    public List<Object[]> getObesePercentageBySex() {
        return this.repository.getObesePercentageBySex();
    }

    public List<Object[]> getAverageAgeByBloodType() {
        return this.repository.getAverageAgeByBloodType();
    }
    public List<Object[]> findDonorsByBloodType() {
        return this.repository.findDonorsByBloodType();
    }

}
