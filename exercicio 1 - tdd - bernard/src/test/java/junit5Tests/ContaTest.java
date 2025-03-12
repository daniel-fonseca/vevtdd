package junit5Tests;

import model.Conta;
import model.TipoPagamento;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@DisplayName("Testes de Unidade - Conta")
class ContaTest {

    @Test
    @DisplayName("Deve criar uma conta com valores corretos")
    void deveCriarContaComValoresCorretos() {
        Conta conta = new Conta("C1", LocalDate.of(2023, 2, 20), 1000.00, TipoPagamento.BOLETO);

        assertAll(
                () -> assertEquals("C1", conta.getCodigo()),
                () -> assertEquals(LocalDate.of(2023, 2, 20), conta.getData()),
                () -> assertEquals(1000.00, conta.getValorPago()),
                () -> assertEquals(TipoPagamento.BOLETO, conta.getTipoPagamento())
        );
    }
}