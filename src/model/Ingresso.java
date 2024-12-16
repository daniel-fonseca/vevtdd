package model;

public class Ingresso {
    private int id;
    private TipoIngresso tipo;
    private boolean vendido;
    private double preco;

    public Ingresso(int id, TipoIngresso tipo, double preco) {
        this.id = id;
        this.tipo = tipo;
        this.preco = preco;
        this.vendido = false;
    }

    public int getId() {
        return id;
    }

    public TipoIngresso getTipo() { // Retorna o enum
        return tipo;
    }

    public boolean isVendido() {
        return vendido;
    }

    public double getPreco() {
        if (this.tipo.equals(TipoIngresso.MEIA_ENTRADA)) {
            return preco * 0.5;
        } else {
            return preco;
        }
    }

    public void marcarComoVendido() {
        this.vendido = true;
    }
}
