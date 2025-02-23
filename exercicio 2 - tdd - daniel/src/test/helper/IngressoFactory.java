package test.helper;

import model.Ingresso;
import model.TipoIngresso;

import java.util.ArrayList;
import java.util.List;

public class IngressoFactory {

    public static List<Ingresso> criarIngressos(int qtdNormal, int qtdMeia, int qtdVip) {
        List<Ingresso> ingressos = new ArrayList<>();
        int id = 1;

        for (int i = 0; i < qtdNormal; i++) {
            ingressos.add(new Ingresso(id++, TipoIngresso.NORMAL, 10.00));
        }

        for (int i = 0; i < qtdMeia; i++) {
            ingressos.add(new Ingresso(id++, TipoIngresso.MEIA_ENTRADA, 5.00));
        }

        for (int i = 0; i < qtdVip; i++) {
            ingressos.add(new Ingresso(id++, TipoIngresso.VIP, 20.00));
        }

        return ingressos;
    }
}
