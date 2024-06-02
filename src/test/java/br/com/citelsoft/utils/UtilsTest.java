package br.com.citelsoft.utils;

import br.com.citelsoft.model.Candidato;
import br.com.citelsoft.repository.CandidatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {

    @Mock
    private CandidatoRepository repository;

    @InjectMocks
    private Utils utils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Deve calcular IMC por faixa etária quando passar uma lista de candidatos")
    @Test
    void deveCalcularImcQuandoPassarListaCandidato() throws Exception {
        Map<String, Integer> resultado = utils.calcularImcPorFaixaEtaria(candidatoList());

        Map<String, Integer> candidatosPorFaixa = new HashMap<>();
        candidatosPorFaixa.put("31 a 40 anos", 23 );

        assertEquals(candidatosPorFaixa.get("31 a 40 anos"), resultado.get("31 a 40 anos"));
    }

    @Test
    @DisplayName("Deve calcular a idade corretamente quando passado um LocalDate")
    void testCalcularIdade() throws Exception {
        // Execute o método que você deseja testar
        try {
            int idadeCalculada = getCalcularIdade(LocalDate.of(1990, 1, 1));
            assertEquals(34, idadeCalculada);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Faixa inicio deve retornar sempre com a segunda casa decial 1")
    void testCalcularFaixaInicio() throws Exception {
        try {
            int faixaInicioCalculada = getCalcularFaixaInicio(25);
            assertEquals(21, faixaInicioCalculada);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Faixa fim deve retornar sempre com a segunda casa decimal 0")
    void testCalcularFaixaFim() throws Exception {
        try {
            int faixaFimCalculada = getCalcularFaixaFim(25);
            assertEquals(30, faixaFimCalculada);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @DisplayName("Deve classificar a faixa etaria com base na idade dos candidatos")
    void testAgruparPorFaixaEtaria() throws Exception {
        try {
            Map<String, List<Candidato>> resultado = getAgruparPorFaixaEtaria(candidatoList());

            assertEquals(2, resultado.get("31 a 40 anos").size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getCalcularIdade(LocalDate dataNascimento) throws Exception {
        Method method = Utils.class.getDeclaredMethod("calcularIdade", LocalDate.class);
        method.setAccessible(true);
        return (int) method.invoke(utils, dataNascimento);
    }

    private int getCalcularFaixaInicio(int idade) throws Exception {
        Method method = Utils.class.getDeclaredMethod("calcularFaixaInicio", int.class);
        method.setAccessible(true);
        return (int) method.invoke(utils, idade);
    }
    private int getCalcularFaixaFim(int idade) throws Exception {
        Method method = Utils.class.getDeclaredMethod("calcularFaixaFim", int.class);
        method.setAccessible(true);
        return (int) method.invoke(utils, idade);
    }
    private Map<String, List<Candidato>> getAgruparPorFaixaEtaria(List<Candidato> candidatos) throws Exception {
        Method method = Utils.class.getDeclaredMethod("agruparPorFaixaEtaria", List.class);
        method.setAccessible(true);
        return (Map<String, List<Candidato>>) method.invoke(utils, candidatos);
    }
    public static List<Candidato> candidatoList() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Candidato candidato1 = Candidato.builder()
                .nome("Milena Analu Pires")
                .cpf("775.256.099-50")
                .rg("44.084.541-5")
                .dataNasc(LocalDate.parse("23/05/1964", formatter))
                .sexo("Feminino")
                .mae("Isadora Marli")
                .pai("Noah Severino César Pires")
                .email("mmilenaanalupires@keffin.com.br")
                .cep("39801-678")
                .endereco("Rua Kurt W. Rothe")
                .numero(675)
                .bairro("Castro Pires")
                .cidade("Teófilo Otoni")
                .estado("MG")
                .telefoneFixo("(33) 3611-4613")
                .celular("(33) 98481-0191")
                .altura(1.53)
                .peso(80)
                .tipoSanguineo("O-")
                .build();

        Candidato candidato2 = Candidato.builder()
                .nome("Marcos Vinicius Kevin Samuel Santos")
                .cpf("024.934.035-68")
                .rg("10.701.456-7")
                .dataNasc(LocalDate.parse("07/09/1992", formatter))
                .sexo("Masculino")
                .mae("Isabella Andrea")
                .pai("Lorenzo Marcos André Santos")
                .email("marcosviniciuskevinsamuelsantos_@dhl.com")
                .cep("49091-043")
                .endereco("Rua Manoel de Oliveira França")
                .numero(634)
                .bairro("Jardim Centenário")
                .cidade("Aracaju")
                .estado("SE")
                .telefoneFixo("(79) 2686-2642")
                .celular("(79) 99666-0063")
                .altura(1.92)
                .peso(95)
                .tipoSanguineo("O-")
                .build();

        Candidato candidato3 = Candidato.builder()
                .nome("Noah Severino Freitas")
                .cpf("745.405.478-10")
                .rg("13.501.191-7")
                .dataNasc(LocalDate.parse("27/03/1991", formatter))
                .sexo("Masculino")
                .mae("Silvana Bárbara")
                .pai("Tiago Manoel Kaique Freitas")
                .email("nnoahseverinofreitas@tjrj.jus.br")
                .cep("38411-338")
                .endereco("Rua José Cunha Chaves")
                .numero(880)
                .bairro("Jardim Colina")
                .cidade("Uberlândia")
                .estado("MG")
                .telefoneFixo("(34) 2903-7300")
                .celular("(34) 98476-5223")
                .altura(1.93)
                .peso(84)
                .tipoSanguineo("A+")
                .build();

        return Arrays.asList(candidato1, candidato2, candidato3);
    }

}