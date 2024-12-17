package service;

import model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProcessadorDeContasTest {

    @Test
    void deveMarcarFaturaComoPagaComPagamentosValidos() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.0, "Cliente Teste");

        List<Conta> contas = Arrays.asList(
                new Conta("123", LocalDate.of(2023, 2, 20), 500.0, TipoPagamento.BOLETO),
                new Conta("456", LocalDate.of(2023, 2, 20), 400.0, TipoPagamento.BOLETO),
                new Conta("789", LocalDate.of(2023, 2, 20), 600.0, TipoPagamento.BOLETO)
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, contas);

        assertTrue(fatura.isPaga());
    }

    @Test
    void deveMarcarFaturaComoPendenteQuandoPagamentosInsuficientes() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.0, "Cliente Teste");

        List<Conta> contas = Arrays.asList(
                new Conta("123", LocalDate.of(2023, 2, 20), 700.0, TipoPagamento.BOLETO),
                new Conta("456", LocalDate.of(2023, 2, 20), 600.0, TipoPagamento.TRANSFERENCIA_BANCARIA)
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, contas);

        assertFalse(fatura.isPaga());
    }

    @Test
    void deveIgnorarPagamentosDeCartaoCreditoForaDoPrazo() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.0, "Cliente Teste");

        List<Conta> contas = Arrays.asList(
                new Conta("123", LocalDate.of(2023, 2, 15), 700.0, TipoPagamento.CARTAO_CREDITO), // fora do prazo
                new Conta("456", LocalDate.of(2023, 2, 05), 800.0, TipoPagamento.CARTAO_CREDITO)  // dentro do prazo
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, contas);

        assertEquals(1, processador.getPagamentos().size()); // so 1 pagamento valido
        assertEquals(800.0, processador.getPagamentos().get(0).getValorPago());
        assertFalse(fatura.isPaga());
    }

}