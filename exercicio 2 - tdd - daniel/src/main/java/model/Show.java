package model;

import jdk.jshell.Snippet;

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
        this.bilheteria = 0.00;

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

    public double venderIngresso(int idLote, int idIngresso) {
        Lote lote = lotes.get(idLote);

        if (lote == null) {
            throw new IllegalArgumentException(String.format("Lote com ID %d não encontrado no show.", idLote));
        }

        try {
            double valorVenda = lote.venderIngresso(idIngresso);
            bilheteria += valorVenda;
            return valorVenda;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("Ingresso com ID %d não encontrado no lote %d.", idIngresso, idLote));
        } catch (IllegalStateException e) {
            throw new IllegalStateException(String.format("Ingresso com ID %d no lote %d já foi vendido.", idIngresso, idLote));
        }
    }

    public double calcularReceitaLiquida() {
        return getBilheteria() - getCacheArtista() - getDespesasInfraestrutura();
     }

    public boolean isDataEspecial() {
        return dataEspecial;
    }

    public List<Lote> getLotes() {
        return lotes.values().stream().toList();
    }

    public StatusFinanceiro getStatusFinanceiro() {
        double receitaLiquida = calcularReceitaLiquida();

        if (receitaLiquida >= 1) {
            return StatusFinanceiro.LUCRO;
        } else if (receitaLiquida < 0) {
            return StatusFinanceiro.PREJUÍZO;
        }

        return StatusFinanceiro.ESTÁVEL;
    }

    public double getBilheteria() {
        return this.bilheteria;
    }

    public Lote getLote(int id) {
        return lotes.get(id);
    }
}
