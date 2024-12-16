package test;

import static org.junit.jupiter.api.Assertions.*;
import model.Ingresso;
import model.Lote;
import model.TipoIngresso;
import test.helper.ShowTestHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class LoteTest {

    @Test
    void deveCriarLoteEValidarPercentuaisCorretos() {
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

        Lote lote = new Lote(1, 0.10, ingressos);

        assertEquals(10, lote.getIngressos().size());
    }

    @Test
    void deveFalharQuandoIngressosVIPForemExcessivos() {
        List<Ingresso> ingressos = Arrays.asList(
                new Ingresso(1, TipoIngresso.NORMAL, 10.0),
                new Ingresso(7, TipoIngresso.NORMAL, 10.0),
                new Ingresso(8, TipoIngresso.NORMAL, 10.0),
                new Ingresso(9, TipoIngresso.NORMAL, 10.0),
                new Ingresso(10, TipoIngresso.NORMAL, 10.0),
                new Ingresso(2, TipoIngresso.VIP, 20.0),
                new Ingresso(3, TipoIngresso.VIP, 20.0),
                new Ingresso(4, TipoIngresso.VIP, 20.0),
                new Ingresso(5, TipoIngresso.VIP, 20.0),
                new Ingresso(6, TipoIngresso.MEIA_ENTRADA, 20.0)
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
                new Ingresso(3, TipoIngresso.NORMAL, 10.0),
                new Ingresso(4, TipoIngresso.NORMAL, 10.0),
                new Ingresso(5, TipoIngresso.NORMAL, 10.0),
                new Ingresso(6, TipoIngresso.NORMAL, 10.0),
                new Ingresso(7, TipoIngresso.NORMAL, 10.0),
                new Ingresso(8, TipoIngresso.NORMAL, 10.0),
                new Ingresso(9, TipoIngresso.NORMAL, 10.0),
                new Ingresso(10, TipoIngresso.VIP, 10.0),
                new Ingresso(2, TipoIngresso.VIP, 20.0)
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Lote(1, 0.10, ingressos);
        });

        assertTrue(exception.getMessage().contains("Ingressos MEIA_ENTRADA devem ser exatamente 10% do total."));
    }

    @Test
    void deveVenderIngressoDisponivel() {
        Lote lote = ShowTestHelper.criarLoteValido();

        double precoVendido = lote.venderIngresso(1);

        assertEquals(90.0, precoVendido);
        assertTrue(lote.getIngressos().get(1).isVendido());
    }

    @Test
    void deveLancarExcecaoParaIngressoInexistente() {
        Lote lote = ShowTestHelper.criarLoteValido();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            lote.venderIngresso(999);
        });

        assertTrue(exception.getMessage().contains("Ingresso com ID 999 não encontrado no lote."));
    }

    @Test
    void deveLancarExcecaoParaIngressoJaVendido() {
        Lote lote = ShowTestHelper.criarLoteEVenderIngressos();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            lote.venderIngresso(1);
        });

        assertTrue(exception.getMessage().contains("Ingresso com ID 1 já foi vendido."));
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
