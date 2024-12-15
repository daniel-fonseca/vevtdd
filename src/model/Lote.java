package model;

import java.util.HashMap;
import java.util.Map;

public class Lote {
    private int id;
    private Map<Integer, Ingresso> ingressos;
    private double desconto;

    public Lote(int id, double desconto) {
        if (desconto < 0 || desconto > 0.25) {
            throw new IllegalArgumentException("Desconto deve estar entre 0% e 25%");
        }

        this.id = id;
        this.ingressos = new HashMap<>();
        this.desconto = desconto;
    }

    public Ingresso criarIngresso(TipoIngresso tipo, double preco) {
        validarPercentuais(tipo);

        int novoId = ingressos.size() + 1;
        Ingresso ingresso = new Ingresso(novoId, tipo, preco);
        ingressos.put(novoId, ingresso);

        return ingresso;
    }

    public double venderIngresso() {
        Ingresso ingressoDisponivel = ingressos.values().stream()
                .filter(ingresso -> !ingresso.isVendido())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Não há ingressos disponíveis para venda."));

        ingressoDisponivel.marcarComoVendido();

        double precoFinal = ingressoDisponivel.getPreco();
        if (ingressoDisponivel.getTipo() != TipoIngresso.MEIA_ENTRADA) {
            precoFinal *= (1 - desconto);
        }

        return precoFinal;
    }

    public int getId() {
        return id;
    }

    public Map<Integer, Ingresso> getIngressos() {
        return ingressos;
    }

    public double getDesconto() {
        return desconto;
    }

    private void validarPercentuais(TipoIngresso novoTipo) {
        long totalIngressos = ingressos.size() + 1;
        long totalVIP = ingressos.values().stream().filter(i -> i.getTipo() == TipoIngresso.VIP).count();
        long totalMeiaEntrada = ingressos.values().stream().filter(i -> i.getTipo() == TipoIngresso.MEIA_ENTRADA).count();

        if (novoTipo == TipoIngresso.VIP) {
            totalVIP++;
        } else if (novoTipo == TipoIngresso.MEIA_ENTRADA) {
            totalMeiaEntrada++;
        }

        double percentualVIP = (double) totalVIP / totalIngressos;
        double percentualMeiaEntrada = (double) totalMeiaEntrada / totalIngressos;

        if (percentualVIP < 0.2 || percentualVIP > 0.3) {
            throw new IllegalArgumentException("Ingressos VIP devem ser entre 20% e 30% do total.");
        }

        if (Math.abs(percentualMeiaEntrada - 0.1) > 0.0001) {
            throw new IllegalArgumentException("Ingressos MEIA_ENTRADA devem ser exatamente 10% do total.");
        }
    }

}
