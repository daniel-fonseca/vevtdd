package test.functionalTests;

import test.helper.IngressoFactory;
import model.Ingresso;
import model.Lote;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AnaliseValoresLimitesTest {

    // Caso de Teste 1 - %VIP: 19%; %MEIA: 10%; Preço NORMAL: 10.00; Desconto: 0%; Infraestrutura Especial: Não; Saída Esperada: ERRO
    @Test
    void deveGerarErroParaPercentualVIPAbaixoDoLimite() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(71, 10, 19); // 19% VIP, 10% MEIA
        assertThrows(IllegalArgumentException.class, () -> new Lote(1, 0.0, ingressos));
    }

    // Caso de Teste 2 - %VIP: 20%; %MEIA: 10%; Preço NORMAL: 10.00; Desconto: 0%; Infraestrutura Especial: Não; Saída Esperada: Configuração válida
    @Test
    void deveAceitarPercentualVIPMinimoPermitido() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(70, 10, 20); // 20% VIP, 10% MEIA
        assertDoesNotThrow(() -> new Lote(1, 0.0, ingressos));
    }

    // Caso de Teste 3 - %VIP: 30%; %MEIA: 10%; Preço NORMAL: 10.00; Desconto: 0%; Infraestrutura Especial: Não; Saída Esperada: Configuração válida
    @Test
    void deveAceitarPercentualVIPMaximoPermitido() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(60, 10, 30); // 30% VIP, 10% MEIA
        assertDoesNotThrow(() -> new Lote(1, 0.0, ingressos));
    }

    // Caso de Teste 4 - %VIP: 31%; %MEIA: 10%; Preço NORMAL: 10.00; Desconto: 0%; Infraestrutura Especial: Não; Saída Esperada: ERRO
    @Test
    void deveGerarErroParaPercentualVIPAcimaDoLimite() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(59, 10, 31); // 31% VIP, 10% MEIA
        assertThrows(IllegalArgumentException.class, () -> new Lote(1, 0.0, ingressos));
    }

    // Caso de Teste 5 - %VIP: 20%; %MEIA: 9%; Preço NORMAL: 10.00; Desconto: 0%; Infraestrutura Especial: Não; Saída Esperada: ERRO
    @Test
    void deveGerarErroParaPercentualMeiaEntradaAbaixoDoLimite() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(71, 9, 20); // 20% VIP, 9% MEIA
        assertThrows(IllegalArgumentException.class, () -> new Lote(1, 0.0, ingressos));
    }

    // Caso de Teste 6 - %VIP: 20%; %MEIA: 10%; Preço NORMAL: 10.00; Desconto: 25%; Infraestrutura Especial: Sim; Saída Esperada: Configuração válida
    @Test
    void deveAceitarDescontoMaximoPermitido() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(70, 10, 20); // 20% VIP, 10% MEIA
        assertDoesNotThrow(() -> new Lote(1, 0.25, ingressos));
    }

    // Caso de Teste 7 - %VIP: 20%; %MEIA: 10%; Preço NORMAL: 10.00; Desconto: 26%; Infraestrutura Especial: Não; Saída Esperada: ERRO
    @Test
    void deveGerarErroParaDescontoAcimaDoPermitido() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(70, 10, 20); // 20% VIP, 10% MEIA
        assertThrows(IllegalArgumentException.class, () -> new Lote(1, 0.26, ingressos));
    }
}
