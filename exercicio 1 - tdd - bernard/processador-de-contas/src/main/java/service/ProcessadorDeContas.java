package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class ProcessadorDeContas {

    private List<Pagamento> pagamentos;

    public ProcessadorDeContas() {
        this.pagamentos = new ArrayList<>();
    }

    public void processar(Fatura fatura, List<Conta> contas) {
        double totalPagamentos = 0;

        for (Conta conta : contas) {
            if (contaEhValida(conta, fatura.getData())) {
                Pagamento pagamento = new Pagamento(conta.getValorPago(), conta.getData(), conta.getTipoPagamento());
                pagamentos.add(pagamento);
                totalPagamentos += pagamento.getValorPago();
            }
        }

        if (totalPagamentos >= fatura.getValorTotal()) {
            fatura.marcarComoPaga();
        }
    }

    private boolean contaEhValida(Conta conta, LocalDate dataFatura) {
        if (conta.getTipoPagamento() == TipoPagamento.CARTAO_CREDITO) {
            return !conta.getData().isAfter(dataFatura.minusDays(15));
        }
        return !conta.getData().isAfter(dataFatura);
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }
}
