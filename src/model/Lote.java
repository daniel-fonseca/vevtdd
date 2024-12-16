package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lote {
    private int id;
    private Map<Integer, Ingresso> ingressos;
    private double desconto;

    public Lote(int id, double desconto, List<Ingresso> ingressos) {
        if (desconto < 0.00 || desconto > 0.25) {
            throw new IllegalArgumentException("Desconto deve estar entre 0% e 25%");
        }

        if (ingressos.isEmpty()) {
            throw new IllegalArgumentException("Não há ingressos no lote.");
        }

        this.id = id;
        this.desconto = desconto;
        this.ingressos = new HashMap<>();

        validarPercentuais(ingressos);
        validarPrecosIngressos(ingressos);
        ingressos.forEach(ingresso -> this.ingressos.put(ingresso.getId(), ingresso));
    }

    public double venderIngresso(int idIngresso) {
        Ingresso ingresso = ingressos.get(idIngresso);

        if (ingresso == null) {
            throw new IllegalArgumentException(String.format("Ingresso com ID %d não encontrado no lote.", idIngresso));
        }

        if (ingresso.isVendido()) {
            throw new IllegalStateException(String.format("Ingresso com ID %d já foi vendido.", idIngresso));
        }

        ingresso.marcarComoVendido();

        double precoFinal = ingresso.getPreco() * (1.00 - desconto);
        return precoFinal;
    }

    public int getId() {
        return id;
    }

    public List<Ingresso> getIngressos() {
        return ingressos.values().stream().toList();
    }

    public double getDesconto() {
        return desconto;
    }

    private void validarPercentuais(List<Ingresso> ingressos) {
        if (ingressos == null || ingressos.isEmpty()) {
            throw new IllegalArgumentException("A lista de ingressos não pode ser nula ou vazia.");
        }

        int totalVIP = (int) ingressos.stream()
                .filter(i -> i.getTipo() == TipoIngresso.VIP)
                .count();
        int totalMEIA = (int) ingressos.stream()
                .filter(i -> i.getTipo() == TipoIngresso.MEIA_ENTRADA)
                .count();
        int total = ingressos.size();

        double percentualVIP = (double) totalVIP / total;
        double percentualMEIA = (double) totalMEIA / total;

        if (percentualVIP < 0.20 || percentualVIP > 0.30) {
            throw new IllegalArgumentException(String.format(
                    "Ingressos VIP devem ser entre 20%% e 30%% do total. Atual: %.2f%%", percentualVIP * 100));
        }

        if (Math.abs(percentualMEIA - 0.1) > 0.0001) {
            throw new IllegalArgumentException(String.format(
                    "Ingressos MEIA_ENTRADA devem ser exatamente 10%% do total. Atual: %.2f%%", percentualMEIA * 100));
        }
    }

    private void validarPrecosIngressos(List<Ingresso> ingressos) {
        Ingresso primeiroIngresso = ingressos.get(0);
        double precoNormal = primeiroIngresso.getTipo() == TipoIngresso.MEIA_ENTRADA
                ? primeiroIngresso.getPreco() * 2
                : primeiroIngresso.getPreco();

        for (Ingresso ingresso : ingressos) {
            double precoEsperado = switch (ingresso.getTipo()) {
                case NORMAL -> precoNormal;
                case MEIA_ENTRADA -> precoNormal / 2;
                case VIP -> precoNormal * 2;
            };

            if (Math.abs(ingresso.getPreco() - precoEsperado) > 0.0001) {
                throw new IllegalArgumentException(String.format(
                        "Preço inválido para ingresso do tipo %s com ID %d. Esperado: %.2f, Encontrado: %.2f",
                        ingresso.getTipo(), ingresso.getId(), precoEsperado, ingresso.getPreco()));
            }
        }
    }

    public Map<Integer, Ingresso> getIngressosMap() {
        return this.ingressos;
    }
}
