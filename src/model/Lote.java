package model;

import java.util.List;

public class Lote {
    private int id;
    private List<Ingresso> ingressos;
    private double desconto;

    public Lote(int id, List<Ingresso> ingressos, double desconto) {
        if (desconto < 0 || desconto > 0.25) {
            throw new IllegalArgumentException("Desconto deve estar entre 0% e 25%");
        }
        this.id = id;
        this.ingressos = ingressos;
        this.desconto = desconto;
    }

    public int getId() {
        return id;
    }

    public List<Ingresso> getIngressos() {
        return ingressos;
    }

    public double getDesconto() {
        return desconto;
    }
}
