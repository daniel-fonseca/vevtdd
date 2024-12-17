package model;

import java.time.LocalDate;

public class Fatura {
    private LocalDate data;
    private double valorTotal;
    private String nomeCliente;
    private boolean paga;

    public Fatura(LocalDate data, double valorTotal, String nomeCliente) {
        this.data = data;
        this.valorTotal = valorTotal;
        this.nomeCliente = nomeCliente;
        this.paga = false;
    }

    public LocalDate getData() {
        return data;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public boolean isPaga() {
        return paga;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }

    public void marcarComoPaga() {
        this.paga = true;
    }
}
