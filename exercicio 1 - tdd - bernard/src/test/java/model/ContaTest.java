package model;

import model.Conta;
import model.TipoPagamento;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    @Test
    void deveCriarContaComValoresCorretos() {
        Conta conta = new Conta("123", LocalDate.of(2023, 2, 15), 700.0, TipoPagamento.BOLETO);

        assertEquals("123", conta.getCodigo());
        assertEquals(LocalDate.of(2023, 2, 15), conta.getData());
        assertEquals(700.0, conta.getValorPago());
        assertEquals(TipoPagamento.BOLETO, conta.getTipoPagamento());
    }
}
