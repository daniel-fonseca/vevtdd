package funcional;

import model.*;
import org.junit.jupiter.api.Test;
import service.ProcessadorDeContas;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ParticoesEquivalenciaTest {

    @Test
    public void testPagamentoTotalMenorQueFatura() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente A");
        Conta conta1 = new Conta("C1", LocalDate.of(2023, 2, 20), 1000.00, TipoPagamento.BOLETO);
        Conta conta2 = new Conta("C2", LocalDate.of(2023, 2, 20), 400.00, TipoPagamento.TRANSFERENCIA_BANCARIA);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta1, conta2));
        assertFalse(fatura.isPaga());
    }

    @Test
    public void testPagamentoTotalIgualAFatura() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente B");
        Conta conta1 = new Conta("C3", LocalDate.of(2023, 2, 20), 1000.00, TipoPagamento.BOLETO);
        Conta conta2 = new Conta("C4", LocalDate.of(2023, 2, 20), 500.00, TipoPagamento.TRANSFERENCIA_BANCARIA);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta1, conta2));
        assertTrue(fatura.isPaga());
    }

    @Test
    public void testPagamentoTotalMaiorQueFatura() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente C");
        Conta conta1 = new Conta("C5", LocalDate.of(2023, 2, 20), 1000.00, TipoPagamento.BOLETO);
        Conta conta2 = new Conta("C6", LocalDate.of(2023, 2, 20), 600.00, TipoPagamento.TRANSFERENCIA_BANCARIA);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta1, conta2));
        assertTrue(fatura.isPaga());
    }

    @Test
    public void testPagamentoBoletoAtrasado() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 550.00, "Cliente D");

        Conta conta = new Conta("C7", LocalDate.of(2023, 2, 21), 500.00, TipoPagamento.BOLETO);

        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));

        assertTrue(fatura.isPaga());

        Pagamento pagamento = processador.getPagamentos().get(0);
        assertEquals(550.00, pagamento.getValorPago(), 0.001);
    }

    @Test
    public void testPagamentoCartaoForaDoPrazo() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente E");
        Conta conta = new Conta("C8", LocalDate.of(2023, 2, 10), 1500.00, TipoPagamento.CARTAO_CREDITO);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));
        assertFalse(fatura.isPaga());
    }

    @Test
    public void testPagamentoCartaoDentroDoPrazo() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente F");
        Conta conta = new Conta("C9", LocalDate.of(2023, 2, 5), 1500.00, TipoPagamento.CARTAO_CREDITO);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));
        assertTrue(fatura.isPaga());
    }
}