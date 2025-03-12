package funcional;

import model.Conta;
import model.Fatura;
import model.Pagamento;
import model.TipoPagamento;
import org.junit.jupiter.api.Test;
import service.ProcessadorDeContas;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TabelaDecisaoTest {

    @Test
    public void boletoIgualAoValorFatura() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente A");
        Conta conta = new Conta("C1", LocalDate.of(2023, 2, 20), 1500.00, TipoPagamento.BOLETO);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));
        assertTrue(fatura.isPaga());
    }

    @Test
    public void boletoAbaixoDoValorFatura() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente B");
        Conta conta = new Conta("C2", LocalDate.of(2023, 2, 20), 1400.00, TipoPagamento.BOLETO);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));
        assertFalse(fatura.isPaga());
    }

    @Test
    public void boletoAcimaDoValorFatura() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente C");
        Conta conta = new Conta("C3", LocalDate.of(2023, 2, 20), 1600.00, TipoPagamento.BOLETO);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));
        assertTrue(fatura.isPaga());
    }

    @Test
    public void boletoAtrasado() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 550.00, "Cliente D");
        Conta conta = new Conta("C4", LocalDate.of(2023, 2, 21), 500.00, TipoPagamento.BOLETO);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));
        assertTrue(fatura.isPaga());
        Pagamento pagamento = processador.getPagamentos().get(0);
        assertEquals(550.00, pagamento.getValorPago(), 0.001);
    }

    @Test
    public void cartaoCreditoDentroDoPrazo() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente E");
        Conta conta = new Conta("C5", LocalDate.of(2023, 2, 5), 1500.00, TipoPagamento.CARTAO_CREDITO);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));
        assertTrue(fatura.isPaga());
    }

    @Test
    public void cartaoCreditoForaDoPrazo() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Cliente F");
        Conta conta = new Conta("C6", LocalDate.of(2023, 2, 10), 1500.00, TipoPagamento.CARTAO_CREDITO);
        ProcessadorDeContas processador = new ProcessadorDeContas();
        processador.processar(fatura, Arrays.asList(conta));
        assertFalse(fatura.isPaga());
    }
}