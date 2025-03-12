package junit5Tests;

import model.*;
import org.junit.jupiter.api.*;
import service.ProcessadorDeContas;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@DisplayName("Testes de Unidade - Análise de Valores Limite")
class AnaliseValoresLimitesTest {

    private ProcessadorDeContas processador;
    private Fatura fatura;

    @BeforeEach
    void setup() {
        processador = new ProcessadorDeContas();
    }

    @Test
    @DisplayName("Deve manter fatura pendente quando pagamento por boleto for inferior ao valor total")
    void deveManterFaturaPendenteQuandoPagamentoBoletoForMenor() {
        fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente A");
        Conta conta = new Conta("C1", LocalDate.of(2023, 2, 20), 1499.99, TipoPagamento.BOLETO);

        processador.processar(fatura, List.of(conta));

        assertFalse(fatura.isPaga(), "Fatura não deve ser paga com pagamento abaixo do total.");
    }

    @Test
    @DisplayName("Deve marcar fatura como paga quando pagamento por boleto for igual ao valor total")
    void devePagarFaturaQuandoPagamentoBoletoForIgual() {
        fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente B");
        Conta conta = new Conta("C2", LocalDate.of(2023, 2, 20), 1500.00, TipoPagamento.BOLETO);

        processador.processar(fatura, List.of(conta));

        assertTrue(fatura.isPaga(), "Fatura deve ser marcada como paga.");
    }

    @Test
    @DisplayName("Deve marcar fatura como paga quando pagamento por boleto for maior que o valor total")
    void devePagarFaturaQuandoPagamentoBoletoForMaior() {
        fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente C");
        Conta conta = new Conta("C3", LocalDate.of(2023, 2, 20), 1500.01, TipoPagamento.BOLETO);

        processador.processar(fatura, List.of(conta));

        assertTrue(fatura.isPaga(), "Fatura deve ser paga mesmo com pagamento superior.");
    }

    @Test
    @DisplayName("Deve permitir pagamento por boleto até o valor máximo permitido")
    void deveAceitarPagamentoBoletoAteValorMaximo() {
        fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente D");
        Conta conta = new Conta("C4", LocalDate.of(2023, 2, 20), 5000.00, TipoPagamento.BOLETO);

        processador.processar(fatura, List.of(conta));

        assertTrue(fatura.isPaga(), "Fatura deve ser paga mesmo com pagamento muito acima do valor.");
    }

    @Test
    @DisplayName("Deve manter fatura pendente quando nenhum pagamento for realizado")
    void deveManterFaturaPendenteQuandoNaoHouverPagamento() {
        fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente E");
        Conta conta = new Conta("C5", LocalDate.of(2023, 2, 20), 0.00, TipoPagamento.BOLETO);

        processador.processar(fatura, List.of(conta));

        assertFalse(fatura.isPaga(), "Fatura não pode ser paga sem pagamento efetivo.");
    }

    @Test
    @DisplayName("Deve pagar fatura quando pagamento via cartão de crédito for realizado dentro do prazo")
    void devePagarFaturaQuandoCartaoCreditoDentroPrazo() {
        fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente F");
        Conta conta = new Conta("C6", LocalDate.of(2023, 2, 5), 1500.00, TipoPagamento.CARTAO_CREDITO);

        processador.processar(fatura, List.of(conta));

        assertTrue(fatura.isPaga(), "Fatura deve ser paga quando o pagamento via cartão ocorrer dentro do prazo.");
    }

    @Test
    @DisplayName("Deve rejeitar pagamento por cartão de crédito fora do prazo")
    void deveRejeitarPagamentoCartaoCreditoForaPrazo() {
        fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente G");
        Conta conta = new Conta("C7", LocalDate.of(2023, 2, 10), 1500.00, TipoPagamento.CARTAO_CREDITO);

        processador.processar(fatura, List.of(conta));

        assertFalse(fatura.isPaga(), "Fatura não pode ser paga quando o pagamento via cartão ocorrer fora do prazo.");
    }
}