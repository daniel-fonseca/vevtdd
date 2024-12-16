package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lote {
    private int id;
    private Map<Integer, Ingresso> ingressos;
    private double desconto;

    public Lote(int id, double desconto, List<Ingresso> ingressos) {
        if (desconto < 0 || desconto > 0.25) {
            throw new IllegalArgumentException("Desconto deve estar entre 0% e 25%");
        }

        if (ingressos.isEmpty()) {
            throw new IllegalArgumentException("Não há ingressos no lote.");
        }

        this.id = id;
        this.desconto = desconto;
        this.ingressos = new HashMap<>();

        ingressos.forEach(ingresso -> this.ingressos.put(Integer.valueOf(ingresso.getId()), ingresso));
        validarPercentuais();
    }

    public double venderIngresso(int id) {
        Ingresso ingresso = ingressos.get(id);

        if (ingresso == null) {
            throw new IllegalArgumentException(String.format("Ingresso com ID %d não encontrado no lote.", id));
        }

        if (ingresso.isVendido()) {
            throw new IllegalStateException(String.format("Ingresso com ID %d já foi vendido.", id));
        }

        ingresso.marcarComoVendido();

        double precoFinal = ingresso.getPreco();
        if (ingresso.getTipo() != TipoIngresso.MEIA_ENTRADA) {
            precoFinal *= (1 - desconto);
        }

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

    private void validarPercentuais() {
        int totalVIP = (int) ingressos.values().stream()
                .filter(i -> i.getTipo() == TipoIngresso.VIP).count();
        int totalMEIA = (int) ingressos.values().stream()
                .filter(i -> i.getTipo() == TipoIngresso.MEIA_ENTRADA).count();
        int total = ingressos.size();
        double percentualVIP = (double) totalVIP / total;
        double percentualMEIA = (double) totalMEIA / total;

        if (percentualVIP < 0.2 || percentualVIP > 0.3) {
            throw new IllegalArgumentException(String.format(
                    "Ingressos VIP devem ser entre 20%% e 30%% do total. Atual: %.2f%%", percentualVIP * 100));
        }

        if (Math.abs(percentualMEIA - 0.1) > 0.0001) {
            throw new IllegalArgumentException(String.format(
                    "Ingressos MEIA_ENTRADA devem ser exatamente 10%% do total. Atual: %.2f%%", percentualMEIA * 100));
        }
    }

    public Map<Integer, Ingresso> getIngressosMap() {
        return this.ingressos;
    }
}
