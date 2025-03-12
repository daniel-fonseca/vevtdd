package junit5Tests;

import model.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import service.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@DisplayName("Testes de Unidade - Processador de Contas")
class ProcessadorDeContasTest {

    private static ProcessadorDeContas processador;
    private Fatura fatura;

    @BeforeAll
    static void setupClass() {
        processador = new ProcessadorDeContas();  // Criamos apenas uma instância para todos os testes
    }

    @BeforeEach
    void setup() {
        fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.0, "Cliente Teste");
    }

    @Nested
    @DisplayName("Cenários de Rejeição de Pagamentos")
    class TestesRejeicao {

        @Test
        @DisplayName("Deve rejeitar boletos com valores inválidos (0 ou acima do limite)")
        void deveRejeitarBoletosComValoresInvalidos() {
            List<Conta> contas = List.of(
                    new Conta("001", LocalDate.of(2023, 2, 20), 0.0, TipoPagamento.BOLETO),
                    new Conta("002", LocalDate.of(2023, 2, 20), 6000.0, TipoPagamento.BOLETO)
            );

            processador.processar(fatura, contas);

            assertAll(
                    () -> assertTrue(processador.getPagamentos().isEmpty(), "Nenhum pagamento deveria ser registrado"),
                    () -> assertFalse(fatura.isPaga(), "Fatura deveria continuar pendente")
            );
        }

        @Test
        @DisplayName("Deve rejeitar transferências após a data da fatura")
        void deveRejeitarTransferenciasAposDataDaFatura() {
            List<Conta> contas = List.of(
                    new Conta("008", LocalDate.of(2023, 2, 21), 800.0, TipoPagamento.TRANSFERENCIA_BANCARIA)
            );

            processador.processar(fatura, contas);

            assertAll(
                    () -> assertTrue(processador.getPagamentos().isEmpty(), "Nenhum pagamento deveria ser registrado"),
                    () -> assertFalse(fatura.isPaga(), "Fatura deveria continuar pendente")
            );
        }

        @Test
        @DisplayName("Deve ignorar pagamento por cartão de crédito fora do prazo")
        void deveIgnorarCartaoCreditoForaDoPrazo() {
            List<Conta> contas = List.of(
                    new Conta("004", LocalDate.of(2023, 2, 19), 700.0, TipoPagamento.CARTAO_CREDITO),
                    new Conta("005", LocalDate.of(2023, 2, 1), 800.0, TipoPagamento.CARTAO_CREDITO)
            );

            processador.processar(fatura, contas);

            assertAll(
                    () -> assertEquals(1, processador.getPagamentos().size(), "Apenas um pagamento deve ser aceito"),
                    () -> assertEquals(800.0, processador.getPagamentos().get(0).getValorPago(), "Valor do pagamento aceito deve ser 800.0"),
                    () -> assertFalse(fatura.isPaga(), "Fatura não deveria ser marcada como paga")
            );
        }
    }

    @Nested
    @DisplayName("Cenários de Pagamento Bem Sucedidos")
    class TestesAprovacao {

        @Test
        @DisplayName("Deve aplicar atraso de 10% em boletos atrasados")
        void deveAplicarAtrasoDe10PorCentoEmBoletosAtrasados() {
            List<Conta> contas = List.of(
                    new Conta("003", LocalDate.of(2023, 2, 21), 500.0, TipoPagamento.BOLETO)
            );

            processador.processar(fatura, contas);

            assertAll(
                    () -> assertEquals(550.0, processador.getPagamentos().get(0).getValorPago(), "Valor pago deveria ser 550.0"),
                    () -> assertFalse(fatura.isPaga(), "Fatura deveria continuar pendente")
            );
        }

        @Test
        @DisplayName("Deve marcar fatura como paga com pagamentos válidos")
        void deveMarcarFaturaComoPagaComPagamentosValidos() {
            List<Conta> contas = List.of(
                    new Conta("006", LocalDate.of(2023, 2, 20), 800.0, TipoPagamento.TRANSFERENCIA_BANCARIA),
                    new Conta("007", LocalDate.of(2023, 2, 5), 700.0, TipoPagamento.CARTAO_CREDITO)
            );

            processador.processar(fatura, contas);

            assertAll(
                    () -> assertTrue(fatura.isPaga(), "Fatura deveria estar paga"),
                    () -> assertEquals(2, processador.getPagamentos().size(), "Dois pagamentos deveriam ser registrados")
            );
        }

        @ParameterizedTest
        @DisplayName("Deve aceitar boletos com valores limite")
        @CsvSource({
                "0.01, true",
                "5000.00, true"
        })
        void deveAceitarBoletosComValoresLimite(double valor, boolean esperado) {
            List<Conta> contas = List.of(
                    new Conta("009", LocalDate.of(2023, 2, 20), valor, TipoPagamento.BOLETO)
            );

            processador.processar(fatura, contas);

            assertEquals(esperado, fatura.isPaga());
        }
    }
}