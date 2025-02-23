package test.functionalTests;

import test.helper.IngressoFactory;
import model.Ingresso;
import model.Lote;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class ParticoesEquivalenciaTest {

    // Caso de Teste 8 - Condição: Ingresso NORMAL vendido; Entrada: Venda de ingresso NORMAL; Saída Esperada: Preço sem alteração
    @Test
    void deveManterPrecoIngressoNormalSemDesconto() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(100, 0, 0); // 100% NORMAL
        Lote lote = new Lote(1, 0.0, ingressos);
        double preco = lote.venderIngresso(1);
        assertEquals(10.0, preco); // Preço sem alteração
    }

    // Caso de Teste 9 - Condição: Ingresso VIP vendido; Entrada: Venda de ingresso VIP; Saída Esperada: Preço = 2 * NORMAL
    @Test
    void deveCobrarPrecoDobroParaIngressoVIP() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(0, 0, 100); // 100% VIP
        Lote lote = new Lote(1, 0.0, ingressos);
        double preco = lote.venderIngresso(1);
        assertEquals(20.0, preco); // VIP = 2 * NORMAL
    }

    // Caso de Teste 10 - Condição: Ingresso MEIA_ENTRADA vendido; Entrada: Venda de ingresso MEIA_ENTRADA; Saída Esperada: Preço = 50% * NORMAL
    @Test
    void deveCobrarMetadePrecoParaIngressoMeiaEntrada() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(0, 100, 0); // 100% MEIA_ENTRADA
        Lote lote = new Lote(1, 0.0, ingressos);
        double preco = lote.venderIngresso(1);
        assertEquals(5.0, preco); // MEIA_ENTRADA = 50% * NORMAL
    }

    // Caso de Teste 11 - Condição: Lote de ingressos com 15% de desconto; Entrada: Venda de ingresso NORMAL com desconto; Saída Esperada: Preço reduzido em 15%
    @Test
    void deveAplicarDescontoDe15PorcentoEmIngressoNormal() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(100, 0, 0); // 100% NORMAL
        Lote lote = new Lote(1, 0.15, ingressos);
        double preco = lote.venderIngresso(1);
        assertEquals(8.5, preco); // 10.00 - 15% desconto
    }

    // Caso de Teste 12 - Condição: Venda de ingresso já vendido; Entrada: Tentativa de venda repetida; Saída Esperada: ERRO
    @Test
    void deveGerarErroAoVenderIngressoJaVendido() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(100, 0, 0); // 100% NORMAL
        Lote lote = new Lote(1, 0.0, ingressos);
        lote.venderIngresso(1);
        assertThrows(IllegalStateException.class, () -> lote.venderIngresso(1));
    }

    // Caso de Teste 13 - Condição: Venda de ingresso VIP já vendido; Entrada: Tentativa de venda repetida; Saída Esperada: ERRO
    @Test
    void deveGerarErroAoVenderIngressoVIPJaVendido() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(0, 0, 100); // 100% VIP
        Lote lote = new Lote(1, 0.0, ingressos);
        lote.venderIngresso(1);
        assertThrows(IllegalStateException.class, () -> lote.venderIngresso(1));
    }

    // Caso de Teste 14 - Condição: Venda de ingresso MEIA_ENTRADA já vendido; Entrada: Tentativa de venda repetida; Saída Esperada: ERRO
    @Test
    void deveGerarErroAoVenderIngressoMeiaEntradaJaVendido() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(0, 100, 0); // 100% MEIA_ENTRADA
        Lote lote = new Lote(1, 0.0, ingressos);
        lote.venderIngresso(1);
        assertThrows(IllegalStateException.class, () -> lote.venderIngresso(1));
    }
}
