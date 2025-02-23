package test.functionalTests;

import test.helper.IngressoFactory;
import model.Ingresso;
import model.Lote;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class TabelasDecisaoTest {

    // Regra 1 - Ingresso disponível: Sim; Tipo: NORMAL; Lote tem desconto? Não; Preço final: Preço padrão
    @Test
    void deveCobrarPrecoPadraoParaIngressoNormalSemDesconto() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(100, 0, 0); // 100% NORMAL
        Lote lote = new Lote(1, 0.0, ingressos);
        double preco = lote.venderIngresso(1);
        assertEquals(10.0, preco); // Preço padrão
    }

    // Regra 2 - Ingresso disponível: Sim; Tipo: VIP; Lote tem desconto? Sim (20%); Preço final: Preço = 2 * NORMAL com desconto
    @Test
    void deveAplicarDescontoEmIngressoVIP() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(0, 0, 100); // 100% VIP
        Lote lote = new Lote(1, 0.20, ingressos);
        double preco = lote.venderIngresso(1);
        assertEquals(16.0, preco); // (2 * 10.00) - 20% desconto
    }

    // Regra 3 - Ingresso disponível: Sim; Tipo: MEIA; Lote tem desconto? Sim (25%); Preço final: Preço = 50% * NORMAL com desconto
    @Test
    void deveAplicarDescontoEmIngressoMeiaEntrada() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(0, 100, 0); // 100% MEIA_ENTRADA
        Lote lote = new Lote(1, 0.25, ingressos);
        double preco = lote.venderIngresso(1);
        assertEquals(3.75, preco); // (10.00 * 50%) - 25% desconto
    }

    // Regra 4 - Ingresso disponível: Não; Tipo: -; Lote tem desconto? -; Preço final: ERRO
    @Test
    void deveGerarErroParaIngressoIndisponivel() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(0, 0, 0); // Nenhum ingresso disponível
        Lote lote = new Lote(1, 0.0, ingressos);
        assertThrows(IllegalArgumentException.class, () -> lote.venderIngresso(1)); // ERRO esperado
    }
}
