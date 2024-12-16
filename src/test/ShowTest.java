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

        double receitaEsperada = (100.0 * 0.85) + (200.0 * 0.85); // Desconto de 15%
        assertEquals(receitaEsperada - 300.0, show.calcularReceitaLiquida());
    }

    @Test
    void deveAvaliarStatusFinanceiroComLucro() {
        Lote lote = ShowTestHelper.criarLoteEVenderIngressos();

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                100.0,
                200.0,
                false,
                List.of(lote)
        );

        assertEquals(StatusFinanceiro.LUCRO, show.avaliarStatusFinanceiro());
    }

    @Test
    void deveAvaliarStatusFinanceiroEstavel() {
        Lote lote = ShowTestHelper.criarLoteEVenderIngressos();

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                300.0,
                0.0,
                false,
                List.of(lote)
        );

        assertEquals(StatusFinanceiro.ESTÁVEL, show.avaliarStatusFinanceiro());
    }

    @Test
    void deveAvaliarStatusFinanceiroPrejuizo() {
        Lote lote = ShowTestHelper.criarLoteEVenderIngressos();

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                300.0,
                100.0,
                false,
                List.of(lote)
        );

        assertEquals(StatusFinanceiro.PREJUÍZO, show.avaliarStatusFinanceiro());
    }
}
