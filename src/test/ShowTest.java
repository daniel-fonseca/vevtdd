package test;

import static org.junit.jupiter.api.Assertions.*;
import model.*;
import test.helper.ShowTestHelper;
import org.junit.jupiter.api.Test;

import java.util.List;

class ShowTest {

    @Test
    void deveCriarShowComLotes() {
        Lote lote = ShowTestHelper.criarLoteValido();

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                1000.0,
                2000.0,
                true,
                List.of(lote)
        );

        assertEquals("03/10/2024", show.getData());
        assertEquals("Paul McCartney", show.getArtista());
        assertEquals(1, show.getLotes().size());
        assertTrue(show.isDataEspecial());
    }

    @Test
    void deveCalcularReceitaLiquidaComLucro() {
        Lote lote = ShowTestHelper.criarLoteEVenderIngressos();

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                100.0,
                200.0,
                false,
                List.of(lote)
        );

        double receitaEsperada = ((10.0 * 0.85 * 7) + (20.0 * 0.85 * 2) + (10.0 * 0.5));
        assertEquals(receitaEsperada - 300.0, show.calcularReceitaLiquida());
    }

    @Test
    void deveGetStatusFinanceiroComLucro() {
        Lote lote = ShowTestHelper.criarLoteEVenderIngressos();

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                100.0,
                200.0,
                false,
                List.of(lote)
        );

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
        show.venderIngresso(1);
        double precoEsperado = 9.0;
        double bilheteriaDepois = show.getBilheteria();

        assertEquals(bilheteriaAntes + precoEsperado, bilheteriaDepois);
        assertTrue(show.getLotes().get(0).getIngressos().get(1).isVendido());
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
            show.venderIngresso(999);
        });

        assertTrue(exception.getMessage().contains("Ingresso com ID 999 não encontrado em nenhum lote."));
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
            show.venderIngresso(1);
        });

        assertTrue(exception.getMessage().contains("Ingresso com ID 1 já foi vendido."));
    }
}
