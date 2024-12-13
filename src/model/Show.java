package model;

import java.util.List;

public class Show {
    private String data;
    private String artista;
    private boolean dataEspecial;
    private List<Lote> lotes;

    public Show(String data, String artista, boolean dataEspecial, List<Lote> lotes) {
        this.data = data;
        this.artista = artista;
        this.dataEspecial = dataEspecial;
        this.lotes = lotes;
    }

    public String getData() {
        return data;
    }

    public String getArtista() {
        return artista;
    }


    public boolean isDataEspecial() {
        return dataEspecial;
    }

    public List<Lote> getLotes() {
        return lotes;
    }
}
