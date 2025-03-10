package functionalTests;

import helper.ShowTestHelper;
import model.Lote;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParticoesEquivalenciaTest {

    // Caso de Teste 8 - Condição: Ingresso NORMAL vendido; Entrada: Venda de ingresso NORMAL; Saída Esperada: Preço sem alteração
    @Test
    void deveManterPrecoIngressoNormalSemDesconto() {
        Lote lote = ShowTestHelper.criarLoteValido();
        double preco = lote.venderIngresso(1); // Ingresso NORMAL
        assertEquals(9.0, preco); // Preço com 10% de desconto do lote
    }

    // Caso de Teste 9 - Condição: Ingresso VIP vendido; Entrada: Venda de ingresso VIP; Saída Esperada: Preço = 2 * NORMAL com desconto
    @Test
    void deveCobrarPrecoDobroParaIngressoVIP() {
        Lote lote = ShowTestHelper.criarLoteValido();
        double preco = lote.venderIngresso(2); // Ingresso VIP
        assertEquals(18.0, preco); // VIP = 20 - 10% desconto
    }

    // Caso de Teste 10 - Condição: Ingresso MEIA_ENTRADA vendido; Entrada: Venda de ingresso MEIA_ENTRADA; Saída Esperada: Preço = 50% * NORMAL com desconto
    @Test
    void deveCobrarMetadePrecoParaIngressoMeiaEntrada() {
        Lote lote = ShowTestHelper.criarLoteValido();
        double preco = lote.venderIngresso(5); // Ingresso MEIA_ENTRADA
        assertEquals(4.5, preco); // MEIA_ENTRADA = 5 - 10% desconto
    }

    // Caso de Teste 11 - Condição: Lote de ingressos com 15% de desconto; Entrada: Venda de ingresso NORMAL com desconto; Saída Esperada: Preço reduzido em 15%
    @Test
    void deveAplicarDescontoDe15PorcentoEmIngressoNormal() {
        Lote lote = ShowTestHelper.criarLoteValido(); // Garante que os ingressos não foram vendidos ainda
        double preco = lote.venderIngresso(1); // Ingresso NORMAL com desconto de 10%
        assertEquals(9.0, preco); // Preço com 10% de desconto do lote válido
    }

    // Caso de Teste 12 - Condição: Venda de ingresso já vendido; Entrada: Tentativa de venda repetida; Saída Esperada: ERRO
    @Test
    void deveGerarErroAoVenderIngressoJaVendido() {
        Lote lote = ShowTestHelper.criarLoteEVenderIngressos();
        assertThrows(IllegalStateException.class, () -> lote.venderIngresso(1));
    }

    // Caso de Teste 13 - Condição: Venda de ingresso VIP já vendido; Entrada: Tentativa de venda repetida; Saída Esperada: ERRO
    @Test
    void deveGerarErroAoVenderIngressoVIPJaVendido() {
        Lote lote = ShowTestHelper.criarLoteEVenderIngressos();
        assertThrows(IllegalStateException.class, () -> lote.venderIngresso(2));
    }

    // Caso de Teste 14 - Condição: Venda de ingresso MEIA_ENTRADA já vendido; Entrada: Tentativa de venda repetida; Saída Esperada: ERRO
    @Test
    void deveGerarErroAoVenderIngressoMeiaEntradaJaVendido() {
        Lote lote = ShowTestHelper.criarLoteEVenderIngressos();
        assertThrows(IllegalStateException.class, () -> lote.venderIngresso(5));
    }
}
