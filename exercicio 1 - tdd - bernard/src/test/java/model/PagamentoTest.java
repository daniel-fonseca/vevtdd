package test;

import model.Pagamento;
import model.TipoPagamento;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoTest {

    @Test
    void deveCriarPagamentoComValoresCorretos() {
        Pagamento pagamento = new Pagamento(600.0, LocalDate.of(2023, 2, 15), TipoPagamento.CARTAO_CREDITO);

        assertEquals(600.0, pagamento.getValorPago());
        assertEquals(LocalDate.of(2023, 2, 15), pagamento.getData());
        assertEquals(TipoPagamento.CARTAO_CREDITO, pagamento.getTipoPagamento());
    }
}
