package junit5Tests;

import model.Conta;
import model.Fatura;
import model.Pagamento;
import model.TipoPagamento;
import org.junit.jupiter.api.*;
import service.ProcessadorDeContas;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@DisplayName("Testes de Unidade - Tabela de Decisão")
class TabelaDecisaoTest {

    private ProcessadorDeContas processador;
    private Fatura fatura;

    @BeforeEach
    void setup() {
        processador = new ProcessadorDeContas();
    }

    @Nested
    @DisplayName("Testes de Pagamento com Boleto")
    class TestesBoleto {

        @Test
        @DisplayName("Deve marcar fatura como paga se boleto for igual ao valor da fatura")
        void boletoIgualAoValorFatura() {
            fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente A");
            Conta conta = new Conta("C1", LocalDate.of(2023, 2, 20), 1500.00, TipoPagamento.BOLETO);
            processador.processar(fatura, List.of(conta));
            assertTrue(fatura.isPaga());
        }

        @Test
        @DisplayName("Deve manter fatura como não paga se boleto for abaixo do valor da fatura")
        void boletoAbaixoDoValorFatura() {
            fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente B");
            Conta conta = new Conta("C2", LocalDate.of(2023, 2, 20), 1400.00, TipoPagamento.BOLETO);
            processador.processar(fatura, List.of(conta));
            assertFalse(fatura.isPaga());
        }

        @Test
        @DisplayName("Deve marcar fatura como paga se boleto for acima do valor da fatura")
        void boletoAcimaDoValorFatura() {
            fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente C");
            Conta conta = new Conta("C3", LocalDate.of(2023, 2, 20), 1600.00, TipoPagamento.BOLETO);
            processador.processar(fatura, List.of(conta));
            assertTrue(fatura.isPaga());
        }

        @Test
        @DisplayName("Deve aplicar acréscimo de 10% para boletos pagos após o vencimento")
        void boletoAtrasado() {
            fatura = new Fatura(LocalDate.of(2023, 2, 20), 550.00, "Cliente D");
            Conta conta = new Conta("C4", LocalDate.of(2023, 2, 21), 500.00, TipoPagamento.BOLETO);
            processador.processar(fatura, List.of(conta));

            assertAll(
                    () -> assertTrue(fatura.isPaga()),
                    () -> assertEquals(550.00, processador.getPagamentos().get(0).getValorPago(), 0.001)
            );
        }
    }

    @Nested
    @DisplayName("Testes de Pagamento com Cartão de Crédito")
    class TestesCartaoCredito {

        @Test
        @DisplayName("Deve aceitar pagamento com cartão dentro do prazo")
        void cartaoCreditoDentroDoPrazo() {
            fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente E");
            Conta conta = new Conta("C5", LocalDate.of(2023, 2, 5), 1500.00, TipoPagamento.CARTAO_CREDITO);
            processador.processar(fatura, List.of(conta));
            assertTrue(fatura.isPaga());
        }

        @Test
        @DisplayName("Deve recusar pagamento com cartão fora do prazo")
        void cartaoCreditoForaDoPrazo() {
            fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente F");
            Conta conta = new Conta("C6", LocalDate.of(2023, 2, 10), 1500.00, TipoPagamento.CARTAO_CREDITO);
            processador.processar(fatura, List.of(conta));
            assertFalse(fatura.isPaga());
        }
    }
}