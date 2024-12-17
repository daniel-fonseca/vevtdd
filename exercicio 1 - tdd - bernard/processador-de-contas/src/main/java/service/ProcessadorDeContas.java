package service;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProcessadorDeContas {

    private List<Pagamento> pagamentos;

    public ProcessadorDeContas() {
        this.pagamentos = new ArrayList<>();
    }

    public void processar(Fatura fatura, List<Conta> contas) {
        double totalPagamentos = 0.0;

        for (Conta conta : contas) {
            Pagamento pagamento = processarConta(conta, fatura.getData());
            if (pagamento != null) {
                pagamentos.add(pagamento);
                totalPagamentos += pagamento.getValorPago();
            }
        }

        if (totalPagamentos >= fatura.getValorTotal()) {
            fatura.marcarComoPaga();
        }
    }

    private Pagamento processarConta(Conta conta, LocalDate dataFatura) {
        LocalDate dataConta = conta.getData();
        double valorPago = conta.getValorPago();
        TipoPagamento tipo = conta.getTipoPagamento();

        if (tipo == TipoPagamento.BOLETO) {
            if (valorPago < 0.01 || valorPago > 5000.0) {
                return null;
            }
            if (dataConta.isAfter(dataFatura)) {
                valorPago += valorPago * 0.1;
            }
        } else if (tipo == TipoPagamento.CARTAO_CREDITO) {
            if (dataConta.isAfter(dataFatura.minusDays(15))) {
                return null;
            }
        } else if (tipo == TipoPagamento.TRANSFERENCIA_BANCARIA) {
            if (dataConta.isAfter(dataFatura)) {
                return null;
            }
        }

        return new Pagamento(valorPago, dataConta, tipo);
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }
}