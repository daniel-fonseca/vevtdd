package service;

import model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProcessadorDeContasTest {

    @Test
    void deveRejeitarBoletosComValoresInvalidos() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.0, "Cliente Teste");

        List<Conta> contas = Arrays.asList(
                new Conta("001", LocalDate.of(2023, 2, 20), 0.0, TipoPagamento.BOLETO),
                new Conta("002", LocalDate.of(2023, 2, 20), 6000.0, TipoPagamento.BOLETO)
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, contas);

        assertTrue(processador.getPagamentos().isEmpty());
        assertFalse(fatura.isPaga());
    }

    @Test
    void deveAplicarAtrasoDe10PorCentoEmBoletosAtrasados() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.0, "Cliente Teste");

        List<Conta> contas = Arrays.asList(
                new Conta("003", LocalDate.of(2023, 2, 21), 500.0, TipoPagamento.BOLETO)
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, contas);

        assertEquals(550.0, processador.getPagamentos().get(0).getValorPago()); // 500 + 10%
        assertFalse(fatura.isPaga());
    }

    @Test
    void deveIgnorarCartaoCreditoForaDoPrazo() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.0, "Cliente Teste");

        List<Conta> contas = Arrays.asList(
                new Conta("004", LocalDate.of(2023, 2, 19), 700.0, TipoPagamento.CARTAO_CREDITO),
                new Conta("005", LocalDate.of(2023, 2, 01), 800.0, TipoPagamento.CARTAO_CREDITO)
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, contas);

        assertEquals(1, processador.getPagamentos().size());
        assertEquals(800.0, processador.getPagamentos().get(0).getValorPago());
        assertFalse(fatura.isPaga());
    }

    @Test
    void deveMarcarFaturaComoPagaComPagamentosValidos() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.0, "Cliente Teste");

        List<Conta> contas = Arrays.asList(
                new Conta("006", LocalDate.of(2023, 2, 20), 800.0, TipoPagamento.TRANSFERENCIA_BANCARIA),
                new Conta("007", LocalDate.of(2023, 2, 5), 700.0, TipoPagamento.CARTAO_CREDITO)
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, contas);

        assertTrue(fatura.isPaga());
        assertEquals(2, processador.getPagamentos().size());
    }

    @Test
    void deveRejeitarTransferenciasAposDataDaFatura() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.0, "Cliente Teste");

        List<Conta> contas = Arrays.asList(
                new Conta("008", LocalDate.of(2023, 2, 21), 800.0, TipoPagamento.TRANSFERENCIA_BANCARIA)
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, contas);

        assertTrue(processador.getPagamentos().isEmpty());
        assertFalse(fatura.isPaga());
    }

    @Test
    void deveAceitarBoletosComValoresLimite() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1000.0, "Cliente Teste");

        List<Conta> contas = Arrays.asList(
                new Conta("001", LocalDate.of(2023, 2, 20), 0.01, TipoPagamento.BOLETO),
                new Conta("002", LocalDate.of(2023, 2, 20), 5000.0, TipoPagamento.BOLETO)
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, contas);

        assertEquals(2, processador.getPagamentos().size());
        assertTrue(fatura.isPaga());
    }

}
