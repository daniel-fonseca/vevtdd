package model;

import java.time.LocalDate;

public class Pagamento {
    private double valorPago;
    private LocalDate data;
    private TipoPagamento tipoPagamento;

    public Pagamento(double valorPago, LocalDate data, TipoPagamento tipoPagamento) {
        this.valorPago = valorPago;
        this.data = data;
        this.tipoPagamento = tipoPagamento;
    }

    public double getValorPago() {
        return valorPago;
    }

    public LocalDate getData() {
        return data;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }
}
