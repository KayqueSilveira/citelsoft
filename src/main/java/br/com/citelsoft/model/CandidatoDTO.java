package br.com.citelsoft.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidatoDTO {

    private String nome;
    private String cpf;
    private String rg;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("data_nasc")
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
    @JsonProperty("telefone_fixo")
    private String telefoneFixo;
    private String celular;
    private double altura;
    private int peso;
    @JsonProperty("tipo_sanguineo")
    private String tipoSanguineo;

    public CandidatoDTO convertToDTO(Candidato candidato) {
        return CandidatoDTO.builder()
                .nome(candidato.getNome())
                .cpf(candidato.getCpf())
                .rg(candidato.getRg())
                .dataNasc(candidato.getDataNasc())
                .sexo(candidato.getSexo())
                .mae(candidato.getMae())
                .pai(candidato.getPai())
                .email(candidato.getEmail())
                .cep(candidato.getCep())
                .endereco(candidato.getEndereco())
                .numero(candidato.getNumero())
                .bairro(candidato.getBairro())
                .cidade(candidato.getCidade())
                .estado(candidato.getEstado())
                .telefoneFixo(candidato.getTelefoneFixo())
                .celular(candidato.getCelular())
                .altura(candidato.getAltura())
                .peso(candidato.getPeso())
                .tipoSanguineo(candidato.getTipoSanguineo())
                .build();
    }

    public List<CandidatoDTO> convertListCandidatoToListDTO(List<Candidato> candidatos) {
        return candidatos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
