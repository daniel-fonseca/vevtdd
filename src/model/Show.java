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

    public void venderIngresso(int id) {
        double valor = 0.0;

        for (Lote lote : lotes.values()) {
            if (lote.getIngressos().containsKey(id)) {
                valor = lote.venderIngresso(id);
                break;
            }
        }

        if (valor == 0.0) {
            throw new IllegalArgumentException(String.format("Ingresso com ID %d não encontrado em nenhum lote.", id));
        }

        bilheteria += valor;
    }

    public double calcularReceitaLiquida() {
        double receitaTotal = lotes.values().stream()
                .mapToDouble(lote -> lote.getIngressos().values().stream()
                        .filter(Ingresso::isVendido)
                        .mapToDouble(ingresso -> {
                            double precoFinal = ingresso.getPreco();
                            if (ingresso.getTipo() != TipoIngresso.MEIA_ENTRADA) {
                                precoFinal *= (1 - lote.getDesconto());
                            }
                            return precoFinal;
                        })
                        .sum())
                .sum();

        double custosTotais = getCacheArtista() + getDespesasInfraestrutura();

        return receitaTotal - custosTotais;
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
