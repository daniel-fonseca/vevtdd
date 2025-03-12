package junit5Tests;

import model.*;
import org.junit.jupiter.api.*;
import service.ProcessadorDeContas;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@DisplayName("Testes de Unidade - Partições de Equivalência")
class ParticoesEquivalenciaTest {

    private ProcessadorDeContas processador;
    private Fatura fatura;

    @BeforeEach
    void setup() {
        processador = new ProcessadorDeContas();
    }

    @Test
    @DisplayName("Deve manter fatura pendente quando pagamento total for menor que a fatura")
    void testPagamentoTotalMenorQueFatura() {
        fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente A");
        List<Conta> contas = List.of(
                new Conta("C1", LocalDate.of(2023, 2, 20), 1000.00, TipoPagamento.BOLETO),
                new Conta("C2", LocalDate.of(2023, 2, 20), 400.00, TipoPagamento.TRANSFERENCIA_BANCARIA)
        );

        processador.processar(fatura, contas);

        assertFalse(fatura.isPaga(), "A fatura não deveria estar paga.");
    }

    @Test
    @DisplayName("Deve marcar fatura como paga quando pagamento total for igual à fatura")
    void testPagamentoTotalIgualAFatura() {
        fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente B");
        List<Conta> contas = List.of(
                new Conta("C3", LocalDate.of(2023, 2, 20), 1000.00, TipoPagamento.BOLETO),
                new Conta("C4", LocalDate.of(2023, 2, 20), 500.00, TipoPagamento.TRANSFERENCIA_BANCARIA)
        );

        processador.processar(fatura, contas);

        assertTrue(fatura.isPaga(), "A fatura deveria estar paga.");
    }

    @Test
    @DisplayName("Deve marcar fatura como paga quando pagamento total for maior que a fatura")
    void testPagamentoTotalMaiorQueFatura() {
        fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente C");
        List<Conta> contas = List.of(
                new Conta("C5", LocalDate.of(2023, 2, 20), 1000.00, TipoPagamento.BOLETO),
                new Conta("C6", LocalDate.of(2023, 2, 20), 600.00, TipoPagamento.TRANSFERENCIA_BANCARIA)
        );

        processador.processar(fatura, contas);

        assertTrue(fatura.isPaga(), "A fatura deveria estar paga.");
    }

    @Test
    @DisplayName("Deve aplicar multa de 10% em boleto atrasado")
    void testPagamentoBoletoAtrasado() {
        fatura = new Fatura(LocalDate.of(2023, 2, 20), 550.00, "Cliente D");
        Conta conta = new Conta("C7", LocalDate.of(2023, 2, 21), 500.00, TipoPagamento.BOLETO);

        processador.processar(fatura, List.of(conta));

        assertTrue(fatura.isPaga(), "A fatura deveria estar paga com a multa aplicada.");
        assertEquals(550.00, processador.getPagamentos().get(0).getValorPago(), 0.001, "O valor pago deveria incluir a multa de 10%.");
    }

    @Test
    @DisplayName("Deve rejeitar pagamento por cartão de crédito fora do prazo")
    void testPagamentoCartaoForaDoPrazo() {
        fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente E");
        Conta conta = new Conta("C8", LocalDate.of(2023, 2, 10), 1500.00, TipoPagamento.CARTAO_CREDITO);

        processador.processar(fatura, List.of(conta));

        assertFalse(fatura.isPaga(), "A fatura não deveria estar paga com cartão fora do prazo.");
    }

    @Test
    @DisplayName("Deve aceitar pagamento por cartão de crédito dentro do prazo")
    void testPagamentoCartaoDentroDoPrazo() {
        fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente F");
        Conta conta = new Conta("C9", LocalDate.of(2023, 2, 5), 1500.00, TipoPagamento.CARTAO_CREDITO);

        processador.processar(fatura, List.of(conta));

        assertTrue(fatura.isPaga(), "A fatura deveria estar paga com o pagamento no prazo.");
    }
}