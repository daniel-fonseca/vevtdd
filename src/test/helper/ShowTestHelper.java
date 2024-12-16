package test.helper;

import model.Ingresso;
import model.Lote;
import model.TipoIngresso;

import java.util.Arrays;
import java.util.List;

public class ShowTestHelper {

    public static Lote criarLoteValido() {
        List<Ingresso> ingressos = Arrays.asList(
                new Ingresso(1, TipoIngresso.NORMAL, 10.0),
                new Ingresso(6, TipoIngresso.NORMAL, 10.0),
                new Ingresso(7, TipoIngresso.NORMAL, 10.0),
                new Ingresso(8, TipoIngresso.NORMAL, 10.0),
                new Ingresso(9, TipoIngresso.NORMAL, 10.0),
                new Ingresso(10, TipoIngresso.NORMAL, 10.0),
                new Ingresso(3, TipoIngresso.NORMAL, 10.0),
                new Ingresso(4, TipoIngresso.VIP, 20.0),
                new Ingresso(2, TipoIngresso.VIP, 20.0),
                new Ingresso(5, TipoIngresso.MEIA_ENTRADA, 10.0)
        );
        return new Lote(1, 0.10, ingressos);
    }

    public static Lote criarLoteEVenderIngressos() {
        List<Ingresso> ingressos = Arrays.asList(
                new Ingresso(1, TipoIngresso.NORMAL, 10.0),
                new Ingresso(6, TipoIngresso.NORMAL, 10.0),
                new Ingresso(7, TipoIngresso.NORMAL, 10.0),
                new Ingresso(8, TipoIngresso.NORMAL, 10.0),
                new Ingresso(9, TipoIngresso.NORMAL, 10.0),
                new Ingresso(10, TipoIngresso.NORMAL, 10.0),
                new Ingresso(3, TipoIngresso.NORMAL, 10.0),
                new Ingresso(4, TipoIngresso.VIP, 20.0),
                new Ingresso(2, TipoIngresso.VIP, 20.0),
                new Ingresso(5, TipoIngresso.MEIA_ENTRADA, 10.0)
        );
        ingressos.forEach(Ingresso::marcarComoVendido);
        return new Lote(2, 0.15, ingressos);
    }
}
