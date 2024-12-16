package test;

import static org.junit.jupiter.api.Assertions.*;
import model.*;
import test.helper.ShowTestHelper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ShowTest {

    @Test
    void deveCriarShowComLotes() {
        List<Lote> lotes = List.of(ShowTestHelper.criarLoteValido());

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                1000.0,
                2000.0,
                true,
                lotes
        );

        assertEquals("03/10/2024", show.getData());
        assertEquals("Paul McCartney", show.getArtista());
        assertEquals(1, show.getLotes().size());
        assertTrue(show.isDataEspecial());
    }

    @Test
    void deveCalcularReceitaLiquidaComLucro() {
        List<Ingresso> ingressos = new ArrayList<>();
        for (int i = 1; i <= 500; i++) {
            if (i <= 100) {
                ingressos.add(new Ingresso(i, TipoIngresso.VIP, 10.0));
            } else if (i <= 150) {
                ingressos.add(new Ingresso(i, TipoIngresso.MEIA_ENTRADA, 10.0));
            } else {
                ingressos.add(new Ingresso(i, TipoIngresso.NORMAL, 10.0));
            }
        }

        Lote lote = new Lote(1, 0.15, ingressos);

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                1000.0,
                2000.0,
                true,
                List.of(lote)
        );

        for (Ingresso ingresso : lote.getIngressos()) {
            show.venderIngresso(1, ingresso.getId());
        }

        double bilheteriaEsperada = (10.0 * 0.85 * 350) + (20.0 * 0.85 * 100) + (10.0 * 0.5 * 50);
        double custosTotais = 1000.0 + (2000.0 * 1.15);
        double receitaLiquidaEsperada = bilheteriaEsperada - custosTotais;

        assertEquals(receitaLiquidaEsperada, show.calcularReceitaLiquida());
    }

    @Test
    void deveObterStatusFinanceiroComLucro() {
        List<Ingresso> ingressos = new ArrayList<>();
        for (int i = 1; i <= 500; i++) {
            if (i <= 100) {
                ingressos.add(new Ingresso(i, TipoIngresso.VIP, 10.0));
            } else if (i <= 150) {
                ingressos.add(new Ingresso(i, TipoIngresso.MEIA_ENTRADA, 10.0));
            } else {
                ingressos.add(new Ingresso(i, TipoIngresso.NORMAL, 10.0));
            }
        }

        Lote lote = new Lote(1, 0.15, ingressos);

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                1000.0,
                2000.0,
                true,
                List.of(lote)
        );

        for (Ingresso ingresso : lote.getIngressos()) {
            show.venderIngresso(1, ingresso.getId());
        }

        assertEquals(StatusFinanceiro.LUCRO, show.getStatusFinanceiro());
    }

    @Test
    void deveGetStatusFinanceiroEstavel() {
        Lote lote = ShowTestHelper.criarLoteEVenderIngressos();

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                300.0,
                0.0,
                false,
                List.of(lote)
        );

        assertEquals(StatusFinanceiro.ESTÁVEL, show.getStatusFinanceiro());
    }

    @Test
    void deveGetStatusFinanceiroPrejuizo() {
        Lote lote = ShowTestHelper.criarLoteEVenderIngressos();

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                300.0,
                100.0,
                false,
                List.of(lote)
        );

        assertEquals(StatusFinanceiro.PREJUÍZO, show.getStatusFinanceiro());
    }

    @Test
    void deveVenderIngressoPorId() {
        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                1000.0,
                2000.0,
                true,
                List.of(ShowTestHelper.criarLoteValido())
        );

        double bilheteriaAntes = show.getBilheteria();
        show.venderIngresso(1, 1);
        double precoEsperado = 9.0;
        double bilheteriaDepois = show.getBilheteria();

        assertEquals(bilheteriaAntes + precoEsperado, bilheteriaDepois);
        assertTrue(show.getLote(1).getIngressosMap().get(1).isVendido());
    }

    @Test
    void deveLancarExcecaoParaVendaDeIngressoInexistente() {
        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                1000.0,
                2000.0,
                true,
                List.of(ShowTestHelper.criarLoteValido())
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            show.venderIngresso(1, 999);
        });

        assertTrue(exception.getMessage().contains("Ingresso com ID 999 não encontrado no lote 1."));
    }

    @Test
    void deveLancarExcecaoParaVendaDeIngressoJaVendido() {
        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                1000.0,
                2000.0,
                true,
                List.of(ShowTestHelper.criarLoteEVenderIngressos())
        );

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            show.venderIngresso(2, 1);
        });

        assertTrue(exception.getMessage().contains("Ingresso com ID 1 no lote 2 já foi vendido."));
    }
}
