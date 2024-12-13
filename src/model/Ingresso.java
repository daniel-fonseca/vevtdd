package model;

public class Ingresso {
    private int id;
    private String tipo;
    private boolean vendido;
    private double preco;

    public Ingresso(int id, String tipo, boolean vendido, double preco) {
        this.id = id;
        this.tipo = tipo;
        this.vendido = vendido;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isVendido() {
        return vendido;
    }

    public double getPreco() {
        return preco;
    }

    public void marcarComoVendido() {
        this.vendido = true;
    }
}
