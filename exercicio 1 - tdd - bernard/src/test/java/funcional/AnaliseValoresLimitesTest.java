package funcional;

import model.*;
import org.junit.jupiter.api.Test;
import service.ProcessadorDeContas;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class AnaliseValoresLimitesTest {

    @Test
    public void testPagamentoBoletoAbaixoDoValorFatura() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente A");
        Conta conta = new Conta("C1", LocalDate.of(2023, 2, 20), 1499.99, TipoPagamento.BOLETO);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));
        assertFalse(fatura.isPaga());
    }

    @Test
    public void testPagamentoBoletoIgualAoValorFatura() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente B");
        Conta conta = new Conta("C2", LocalDate.of(2023, 2, 20), 1500.00, TipoPagamento.BOLETO);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));
        assertTrue(fatura.isPaga());
    }

    @Test
    public void testPagamentoBoletoAcimaDoValorFatura() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente C");
        Conta conta = new Conta("C3", LocalDate.of(2023, 2, 20), 1500.01, TipoPagamento.BOLETO);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));
        assertTrue(fatura.isPaga());
    }

    @Test
    public void testPagamentoBoletoValorMaximo() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente D");
        Conta conta = new Conta("C4", LocalDate.of(2023, 2, 20), 5000.00, TipoPagamento.BOLETO);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));
        assertTrue(fatura.isPaga());
    }

    @Test
    public void testPagamentoBoletoSemPagamento() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente E");
        Conta conta = new Conta("C5", LocalDate.of(2023, 2, 20), 0.00, TipoPagamento.BOLETO);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));
        assertFalse(fatura.isPaga());
    }

    @Test
    public void testPagamentoCartaoCreditoDentroDoPrazo() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente F");
        Conta conta = new Conta("C6", LocalDate.of(2023, 2, 5), 1500.00, TipoPagamento.CARTAO_CREDITO);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));
        assertTrue(fatura.isPaga());
    }

    @Test
    public void testPagamentoCartaoCreditoForaDoPrazo() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente G");
        Conta conta = new Conta("C7", LocalDate.of(2023, 2, 10), 1500.00, TipoPagamento.CARTAO_CREDITO);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));
        assertFalse(fatura.isPaga());
    }
}