package test;

import static org.junit.jupiter.api.Assertions.*;
import model.Ingresso;
import model.Lote;
import model.TipoIngresso;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class LoteTest {

    @Test
    void deveCriarLoteEValidarPercentuaisCorretos() {
        List<Ingresso> ingressos = Arrays.asList(
                new Ingresso(1, TipoIngresso.NORMAL, 10.0),
                new Ingresso(2, TipoIngresso.VIP, 20.0),
                new Ingresso(3, TipoIngresso.VIP, 20.0),
                new Ingresso(4, TipoIngresso.MEIA_ENTRADA, 10.0)
        );

        Lote lote = new Lote(1, 0.10, ingressos);

        assertEquals(4, lote.getIngressos().size());
    }

    @Test
    void deveFalharQuandoIngressosVIPForemExcessivos() {
        List<Ingresso> ingressos = Arrays.asList(
                new Ingresso(1, TipoIngresso.NORMAL, 10.0),
                new Ingresso(2, TipoIngresso.VIP, 20.0),
                new Ingresso(3, TipoIngresso.VIP, 20.0),
                new Ingresso(4, TipoIngresso.VIP, 20.0)
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Lote(1, 0.10, ingressos);
        });

        assertTrue(exception.getMessage().contains("Ingressos VIP devem ser entre 20% e 30% do total."));
    }

    @Test
    void deveFalharQuandoIngressosMeiaEntradaForemInsuficientes() {
        List<Ingresso> ingressos = Arrays.asList(
                new Ingresso(1, TipoIngresso.NORMAL, 10.0),
                new Ingresso(2, TipoIngresso.VIP, 20.0),
                new Ingresso(3, TipoIngresso.NORMAL, 10.0)
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Lote(1, 0.10, ingressos);
        });

        assertTrue(exception.getMessage().contains("Ingressos MEIA_ENTRADA devem ser exatamente 10% do total."));
    }

    @Test
    void deveVenderIngressoDisponivel() {
        List<Ingresso> ingressos = Arrays.asList(
                new Ingresso(1, TipoIngresso.NORMAL, 100.0),
                new Ingresso(2, TipoIngresso.VIP, 200.0),
                new Ingresso(3, TipoIngresso.VIP, 200.0),
                new Ingresso(4, TipoIngresso.MEIA_ENTRADA, 50.0)
        );

        Lote lote = new Lote(1, 0.10, ingressos);

        double precoVendido = lote.venderIngresso();

        assertEquals(90.0, precoVendido);
        assertTrue(lote.getIngressos().get(1).isVendido());
    }

    @Test
    void deveLancarExcecaoQuandoNaoHouverIngressosDisponiveis() {
        List<Ingresso> ingressos = Arrays.asList();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Lote(1, 0.10, ingressos);
        });

        assertTrue(exception.getMessage().contains("Não há ingressos no lote."));
    }
}
