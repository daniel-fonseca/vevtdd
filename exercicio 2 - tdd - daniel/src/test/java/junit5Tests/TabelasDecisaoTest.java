package junit5Tests;

import static org.junit.jupiter.api.Assertions.*;

import model.Ingresso;
import model.Lote;
import org.junit.jupiter.api.*;
import helper.ShowTestHelper;
import helper.IngressoFactory;

import java.util.List;

@Tag("unit")
@DisplayName("Testes de Unidade - Baseados em Tabelas de Decisão")
class TabelasDecisaoTest {

    @Test
    @DisplayName("Deve cobrar preço normal sem desconto")
    void deveCobrarPrecoPadraoParaIngressoNormalSemDesconto() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(70, 10, 20); // 70% NORMAL, 10% MEIA, 20% VIP
        Lote lote = new Lote(1, 0.0, ingressos);
        double preco = lote.venderIngresso(61);
        assertEquals(10.0, preco);
    }

    @Test
    @DisplayName("Deve aplicar desconto corretamente no ingresso VIP")
    void deveAplicarDescontoEmIngressoVIP() {
        Lote lote = ShowTestHelper.criarLoteValido();
        double preco = lote.venderIngresso(2);
        assertEquals(18.0, preco, 0.001);
    }

    @Test
    @DisplayName("Deve aplicar desconto corretamente no ingresso MEIA_ENTRADA")
    void deveAplicarDescontoEmIngressoMeiaEntrada() {
        Lote lote = ShowTestHelper.criarLoteValido();
        double preco = lote.venderIngresso(5);
        assertEquals(4.5, preco, 0.001);
    }

    @Test
    @DisplayName("Deve gerar erro para ingresso já vendido")
    void deveGerarErroParaIngressoIndisponivel() {
        Lote lote = ShowTestHelper.criarLoteEVenderIngressos();
        assertThrows(IllegalStateException.class, () -> lote.venderIngresso(1));
    }
}
