package br.com.citelsoft.controller;

import br.com.citelsoft.model.Candidato;
import br.com.citelsoft.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;

    @GetMapping()
    public List<Candidato> getAllCandidatos() {
        return candidatoService.findAll();
    }

    @GetMapping("/por-estado")
    public List<Object[]> getCountOfCandidatosByEstado() {
        return candidatoService.countCandidatesByState();
    }

    @GetMapping("/por-faixa-etaria")
    public Map<String, Integer> getCandidatosPorFaixaEtaria() {
        var candidatos = candidatoService.findAll();
        return candidatoService.getImcCandidato(candidatos);
    }
    @GetMapping("/percentual-obeso")
    public List<Object[]> obterPercentualObesosPorSexo() {
       return this.candidatoService.getObesePercentageBySex();
    }
    @GetMapping("/media-idade-sanguineo")
    public List<Object[]> obterMediaIdadePorTipoSanguineo() {
       return this.candidatoService.getAverageAgeByBloodType();
    }
    @GetMapping("/doadores-tipo-sanguineo")
    public List<Object[]> obterDoadoresTipoSanguineo() {
       return this.candidatoService.findDonorsByBloodType();
    }
}
