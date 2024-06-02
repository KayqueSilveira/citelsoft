package br.com.citelsoft.utils;

import br.com.citelsoft.model.Candidato;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class Utils {

    public Map<String, Integer> calcularImcPorFaixaEtaria(List<Candidato> listCandidatos) {
        var mapCandidatos = agruparPorFaixaEtaria(listCandidatos);
        Map<String, Integer> mediaIMCPorFaixaEtaria = new TreeMap<>();

        for (Map.Entry<String, List<Candidato>> entry : mapCandidatos.entrySet()) {
            String faixaEtaria = entry.getKey();
            List<Candidato> candidatos = entry.getValue();

            int somaIMC = 0;
            int mediaIMC = 0;
            for (Candidato candidato : candidatos) {
                somaIMC += candidato.calcularImc();
            }

            if(somaIMC != 0) {
                 mediaIMC = somaIMC / candidatos.size();
            }
            mediaIMCPorFaixaEtaria.put(faixaEtaria, mediaIMC);
        }
        return mediaIMCPorFaixaEtaria;
    }

    private Map<String, List<Candidato>> agruparPorFaixaEtaria(List<Candidato> candidatos) {
        int menorIdade = Integer.MAX_VALUE;
        for (Candidato candidato : candidatos) {
            int idade = calcularIdade(candidato.getDataNasc());
            if (idade < menorIdade) {
                menorIdade = idade;
            }
        }

        int inicioFaixa = calcularFaixaInicio(menorIdade);
        int fimFaixa = calcularFaixaFim(menorIdade);

        Map<String, List<Candidato>> candidatosPorFaixa = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            int faixaInicio = inicioFaixa + i * 10;
            int faixaFim = fimFaixa + i * 10;
            candidatosPorFaixa.put(faixaInicio + " a " + faixaFim + " anos", new ArrayList<>());
        }

        for (Candidato candidato : candidatos) {
            int idade = calcularIdade(candidato.getDataNasc());
            int faixaIndex = calcularFaixaInicio(idade);
            int faixaFinal = calcularFaixaFim(idade);
            String faixaEtaria = faixaIndex + " a " + faixaFinal + " anos";
            candidatosPorFaixa.get(faixaEtaria).add(candidato);
        }

        Map<String, List<Candidato>> mapaOrdenado = new TreeMap<>(candidatosPorFaixa);


        return mapaOrdenado;
    }

    private int calcularIdade(LocalDate dataNascimento) {
        LocalDate agora = LocalDate.now();
        return Period.between(dataNascimento, agora).getYears();
    }


    private int calcularFaixaInicio(int idade) {
        int ultimoDigito = idade % 10;
        int faixaInicial = 0;

        if (ultimoDigito == 0) {
            return faixaInicial = idade - 9;
        }
        else{
            if(ultimoDigito == 1){
                return faixaInicial = idade;
            }
            else {
                while (idade % 10 != 1) {
                    idade--;
                }
                faixaInicial = idade;
                return faixaInicial;
            }
        }
    }

    private int calcularFaixaFim(int idade) {
        int ultimoDigito = idade % 10;
        int faixaFinal = 0;

        if (ultimoDigito == 0) {
            return faixaFinal = idade;
        }
        else{
            if(ultimoDigito == 1){
                return faixaFinal = idade + 9;
            }
            else {
                while (idade % 10 != 1) {
                    idade--;
                }
                return faixaFinal = idade + 9;
            }
        }
    }
}
