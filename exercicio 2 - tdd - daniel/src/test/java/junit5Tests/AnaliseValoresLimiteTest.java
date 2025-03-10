package junit5Tests;

import static org.junit.jupiter.api.Assertions.*;
import model.Lote;
import model.Ingresso;
import org.junit.jupiter.api.*;
import helper.IngressoFactory;

import java.util.List;

@Tag("unit")
@DisplayName("Testes de Unidade - Análise de Valores Limite")
class AnaliseValoresLimiteTest {

    @Test
    @DisplayName("Deve gerar erro para percentual VIP abaixo do limite (19%)")
    void deveGerarErroParaPercentualVIPAbaixoDoLimite() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(71, 10, 19);
        assertThrows(IllegalArgumentException.class, () -> new Lote(1, 0.0, ingressos));
    }

    @Test
    @DisplayName("Deve aceitar percentual VIP mínimo permitido (20%)")
    void deveAceitarPercentualVIPMinimoPermitido() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(70, 10, 20);
        assertDoesNotThrow(() -> new Lote(1, 0.0, ingressos));
    }

    @Test
    @DisplayName("Deve aceitar percentual VIP máximo permitido (30%)")
    void deveAceitarPercentualVIPMaximoPermitido() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(60, 10, 30);
        assertDoesNotThrow(() -> new Lote(1, 0.0, ingressos));
    }

    @Test
    @DisplayName("Deve gerar erro para percentual VIP acima do limite (31%)")
    void deveGerarErroParaPercentualVIPAcimaDoLimite() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(59, 10, 31);
        assertThrows(IllegalArgumentException.class, () -> new Lote(1, 0.0, ingressos));
    }

    @Test
    @DisplayName("Deve gerar erro para percentual MEIA abaixo do mínimo (9%)")
    void deveGerarErroParaPercentualMeiaEntradaAbaixoDoLimite() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(71, 9, 20);
        assertThrows(IllegalArgumentException.class, () -> new Lote(1, 0.0, ingressos));
    }

    @Test
    @DisplayName("Deve aceitar desconto máximo permitido (25%)")
    void deveAceitarDescontoMaximoPermitido() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(70, 10, 20);
        assertDoesNotThrow(() -> new Lote(1, 0.25, ingressos));
    }

    @Test
    @DisplayName("Deve gerar erro para desconto acima do permitido (26%)")
    void deveGerarErroParaDescontoAcimaDoPermitido() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(70, 10, 20);
        assertThrows(IllegalArgumentException.class, () -> new Lote(1, 0.26, ingressos));
    }
}
