package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Show {
    private String data;
    private String artista;
    private double cacheArtista;
    private double despesasInfraestrutura;
    private boolean dataEspecial;
    private Map<Integer, Lote> lotes;
    private double bilheteria;

    public Show(String data, String artista, double cacheArtista, double despesasInfraestrutura, boolean dataEspecial, List<Lote> lotes) {
        this.data = data;
        this.artista = artista;
        this.cacheArtista = cacheArtista;
        this.despesasInfraestrutura = despesasInfraestrutura;
        this.dataEspecial = dataEspecial;
        this.lotes = new HashMap<Integer, Lote>();
        this.bilheteria = 0.0;

        for (Lote lote : lotes) {
            this.lotes.put(lote.getId(), lote);
        }
    }

    public String getData() {
        return data;
    }

    public String getArtista() {
        return artista;
    }

    public double getCacheArtista() {
        return cacheArtista;
    }

    public double getDespesasInfraestrutura() {
        return dataEspecial ? despesasInfraestrutura * 1.15 : despesasInfraestrutura;
    }

    public void venderIngresso(int idLote, int idIngresso) {
        if (!lotes.containsKey(idLote)) {
            throw new IllegalArgumentException(String.format("Lote com ID %d não encontrado no show.", idLote));
        }

        Lote lote = lotes.get(idLote);

        try {
            double valorVenda = lote.venderIngresso(idIngresso);
            this.bilheteria += valorVenda;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("Ingresso com ID %d não encontrado no lote %d.", idIngresso, idLote));
        } catch (IllegalStateException e) {
            throw new IllegalStateException(String.format("Ingresso com ID %d no lote %d já foi vendido.", idIngresso, idLote));
        }
    }

    public double calcularReceitaLiquida() {
        return this.bilheteria - getCacheArtista() - getDespesasInfraestrutura();
    }

    public boolean isDataEspecial() {
        return dataEspecial;
    }

    public List<Lote> getLotes() {
        return lotes.values().stream().toList();
    }

    public StatusFinanceiro getStatusFinanceiro() {
        double receitaLiquida = calcularReceitaLiquida();
        if (receitaLiquida > 0) return StatusFinanceiro.LUCRO;
        if (receitaLiquida == 0) return StatusFinanceiro.ESTÁVEL;
        return StatusFinanceiro.PREJUÍZO;
    }

    public double getBilheteria() {
        return bilheteria;
    }

    public Lote getLote(int id) {
        return lotes.get(id);
    }
}
