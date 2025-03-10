package junit5Tests;

import static org.junit.jupiter.api.Assertions.*;
import model.Lote;
import org.junit.jupiter.api.*;
import helper.ShowTestHelper;

@Tag("unit")
@DisplayName("Testes de Unidade - Partições de Equivalência")
class ParticoesEquivalenciaTest {

    private Lote lote;

    @BeforeEach
    void setup() {
        lote = ShowTestHelper.criarLoteValido();
    }

    @Test
    @DisplayName("Deve manter preço de ingresso NORMAL sem desconto")
    void deveManterPrecoIngressoNormalSemDesconto() {
        double preco = lote.venderIngresso(1);
        assertEquals(9.0, preco);
    }

    @Test
    @DisplayName("Deve cobrar preço do ingresso VIP corretamente")
    void deveCobrarPrecoDobroParaIngressoVIP() {
        double preco = lote.venderIngresso(2);
        assertEquals(18.0, preco);
    }

    @Test
    @DisplayName("Deve cobrar metade do preço para ingresso MEIA_ENTRADA")
    void deveCobrarMetadePrecoParaIngressoMeiaEntrada() {
        double preco = lote.venderIngresso(5);
        assertEquals(4.5, preco);
    }

    @Test
    @DisplayName("Deve gerar erro ao vender ingresso já vendido")
    void deveGerarErroAoVenderIngressoJaVendido() {
        Lote loteVendido = ShowTestHelper.criarLoteEVenderIngressos();
        assertThrows(IllegalStateException.class, () -> loteVendido.venderIngresso(1));
    }
}
