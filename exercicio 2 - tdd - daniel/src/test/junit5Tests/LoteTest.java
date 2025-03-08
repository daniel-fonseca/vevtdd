package test.junit5Tests;

import static org.junit.jupiter.api.Assertions.*;
import model.Ingresso;
import model.Lote;
import model.TipoIngresso;
import org.junit.jupiter.api.*;
import test.helper.ShowTestHelper;

import java.util.Arrays;
import java.util.List;

@Tag("unit")
@DisplayName("Testes de Unidade - Lote")
class LoteTest {

    private Lote lote;

    @BeforeEach
    void setup() {
        lote = ShowTestHelper.criarLoteValido();
    }

    @Test
    @DisplayName("Deve criar um lote e validar percentuais corretamente")
    void deveCriarLoteEValidarPercentuaisCorretos() {
        assertEquals(10, lote.getIngressos().size());
    }

    @Test
    @DisplayName("Deve lançar erro quando ingressos VIP forem excessivos")
    void deveFalharQuandoIngressosVIPForemExcessivos() {
        List<Ingresso> ingressos = Arrays.asList(
                new Ingresso(1, TipoIngresso.NORMAL, 10.0),
                new Ingresso(7, TipoIngresso.NORMAL, 10.0),
                new Ingresso(2, TipoIngresso.VIP, 20.0),
                new Ingresso(3, TipoIngresso.VIP, 20.0),
                new Ingresso(4, TipoIngresso.VIP, 20.0),
                new Ingresso(5, TipoIngresso.VIP, 20.0),
                new Ingresso(6, TipoIngresso.VIP, 20.0),
                new Ingresso(8, TipoIngresso.NORMAL, 10.0),
                new Ingresso(9, TipoIngresso.NORMAL, 10.0),
                new Ingresso(10, TipoIngresso.NORMAL, 10.0)
        );

        assertThrows(IllegalArgumentException.class, () -> new Lote(1, 0.10, ingressos));
    }

    @Test
    @DisplayName("Deve vender ingresso disponível corretamente")
    void deveVenderIngressoDisponivel() {
        double precoVendido = lote.venderIngresso(1);
        assertEquals(9.0, precoVendido);
        assertTrue(lote.getIngressosMap().get(1).isVendido());
    }

    @Test
    @DisplayName("Deve lançar erro ao vender um ingresso inexistente")
    void deveLancarExcecaoParaIngressoInexistente() {
        assertThrows(IllegalArgumentException.class, () -> lote.venderIngresso(999));
    }

    @Test
    @DisplayName("Deve lançar erro ao vender um ingresso já vendido")
    void deveLancarExcecaoParaIngressoJaVendido() {
        lote.venderIngresso(1);
        assertThrows(IllegalStateException.class, () -> lote.venderIngresso(1));
    }
}
