package junit5Tests;

import model.Pagamento;
import model.TipoPagamento;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@DisplayName("Testes de Unidade - Pagamento")
class PagamentoTest {

    @Test
    @DisplayName("Deve criar um pagamento com valores corretos")
    void deveCriarPagamentoComValoresCorretos() {
        Pagamento pagamento = new Pagamento(600.0, LocalDate.of(2023, 2, 15), TipoPagamento.CARTAO_CREDITO);

        assertAll(
                () -> assertEquals(600.0, pagamento.getValorPago()),
                () -> assertEquals(LocalDate.of(2023, 2, 15), pagamento.getData()),
                () -> assertEquals(TipoPagamento.CARTAO_CREDITO, pagamento.getTipoPagamento())
        );
    }
}