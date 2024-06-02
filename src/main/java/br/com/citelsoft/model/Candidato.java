package br.com.citelsoft.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String rg;
    private LocalDate dataNasc;
    private String sexo;
    private String mae;
    private String pai;
    private String email;
    private String cep;
    private String endereco;
    private int numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String telefoneFixo;
    private String celular;
    private double altura;
    private int peso;
    private String tipoSanguineo;

    public Candidato convertToEntity(CandidatoDTO dto) {
        Candidato candidato = Candidato.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .rg(dto.getRg())
                .dataNasc(dto.getDataNasc())
                .sexo(dto.getSexo())
                .mae(dto.getMae())
                .pai(dto.getPai())
                .email(dto.getEmail())
                .cep(dto.getCep())
                .endereco(dto.getEndereco())
                .numero(dto.getNumero())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .telefoneFixo(dto.getTelefoneFixo())
                .celular(dto.getCelular())
                .altura(dto.getAltura())
                .peso(dto.getPeso())
                .tipoSanguineo(dto.getTipoSanguineo())
                .build();
        return candidato;
    }

    public List<Candidato> convertListDTOToListEntity(List<CandidatoDTO> candidatoDTOS) {
        return candidatoDTOS.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    public double calcularImc() {
        double imc = this.peso / (this.altura * this.altura);
        return imc;
    }
}
