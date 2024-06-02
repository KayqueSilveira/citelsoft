package br.com.citelsoft.service;

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
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DataLoaderTest {

    @InjectMocks
    private DataLoader dataLoader;
    @Mock
    private CandidatoRepository repository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Deve retornar true, quando candidato estiver acima de 50kg e a idade for superior a 16 anos e inferior a 70 anos")
    public void deveRetornarTrueQuandoQuandoDadosCorretos() throws Exception {
        Candidato candidato = new Candidato();
        candidato.setDataNasc(LocalDate.of(1985, 6, 15));
        candidato.setPeso(60);

        assertTrue(getIsCandidatoElegivel(candidato));
    }

    @Test
    @DisplayName("Deve retornar false, quando idade for menor que 16 anos")
    public void deveRetornarFalseQuandoIdadeMenorQueEsperado() throws Exception {
        Candidato candidato = new Candidato();
        candidato.setDataNasc(LocalDate.of(2010, 3, 20));
        candidato.setPeso(70);

        assertFalse(getIsCandidatoElegivel(candidato));
    }

    @Test
    @DisplayName("Deve retornar false, quando idade é superior a 69 anos")
    public void deveRetornarFalseQuandoIdadeMaiorQueEsperado() throws Exception {
        Candidato candidato = new Candidato();
        candidato.setDataNasc(LocalDate.of(1940, 8, 10));
        candidato.setPeso(55);

        assertFalse(getIsCandidatoElegivel(candidato));
    }

    @Test
    @DisplayName("Deve retornar false quando o peso do candidato é menor do que 50kg")
    public void deveRetornarFalseQuandoPesoInferiorEsperado() throws Exception {
        Candidato candidato = new Candidato();
        candidato.setDataNasc(LocalDate.of(1990, 2, 5));
        candidato.setPeso(45);

        assertFalse(getIsCandidatoElegivel(candidato));
    }

    @Test
    @DisplayName("Deve retornar true, quando o usuario nao exister no banco de dados atraves do cpf")
    public void testIsCandidatoUnico() throws Exception {
        Candidato candidato = new Candidato();
        candidato.setCpf("12345678900");

        assertTrue(getIsCandidatoUnico(candidato));
    }

    @Test
    @DisplayName("Deve retornar false, quando o candidato estiver salvo no banco de dados")
    public void testIsCandidatoUnico_CpfExistente() throws Exception {
        Candidato candidato = new Candidato();
        candidato.setCpf("98765432100");
        when(repository.findByCpf(candidato.getCpf())).thenReturn(Optional.of(candidato));
        assertFalse(getIsCandidatoUnico(candidato));
    }

    private boolean getIsCandidatoUnico(Candidato candidato) throws Exception {
        Method method = DataLoader.class.getDeclaredMethod("isCandidatoUnico", Candidato.class);
        method.setAccessible(true);
        return (boolean) method.invoke(dataLoader, candidato);
    }

    private boolean getIsCandidatoElegivel(Candidato candidato) throws Exception {
        Method method = DataLoader.class.getDeclaredMethod("isCandidatoElegivel", Candidato.class);
        method.setAccessible(true);
        return (boolean) method.invoke(dataLoader, candidato);
    }

    private void getLoadJsonData() throws Exception {
        Method method = DataLoader.class.getDeclaredMethod("loadJsonData");
        method.setAccessible(true);
        method.invoke(dataLoader);
    }
}
