package model;

import java.time.LocalDate;

public class Conta {
    private String codigo;
    private LocalDate data;
    private double valorPago;
    private TipoPagamento tipoPagamento;

    public Conta(String codigo, LocalDate data, double valorPago, TipoPagamento tipoPagamento) {
        this.codigo = codigo;
        this.data = data;
        this.valorPago = valorPago;
        this.tipoPagamento = tipoPagamento;
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDate getData() {
        return data;
    }

    public double getValorPago() {
        return valorPago;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }
}
