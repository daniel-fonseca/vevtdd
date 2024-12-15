package test;

import static org.junit.jupiter.api.Assertions.*;
import model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

class ShowTest {

    @Test
    void deveCriarShowComLotes() {
        Lote lote = new Lote(1, 0.10);
        lote.criarIngresso(TipoIngresso.NORMAL, 10.0);
        lote.criarIngresso(TipoIngresso.VIP, 20.0);

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
        Lote lote = new Lote(1, 0.10);
        lote.criarIngresso(TipoIngresso.NORMAL, 100.0).marcarComoVendido();
        lote.criarIngresso(TipoIngresso.VIP, 200.0).marcarComoVendido();

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                100.0,
                200.0,
                false,
                List.of(lote)
        );

        double receitaEsperada = (100.0 * 0.90) + (200.0 * 0.90);
        assertEquals(receitaEsperada - 300.0, show.calcularReceitaLiquida());
    }


    @Test
    void deveAvaliarStatusFinanceiroComLucro() {
        Lote lote = new Lote(1, 0.10);
        lote.criarIngresso(TipoIngresso.NORMAL, 150.0).marcarComoVendido();
        lote.criarIngresso(TipoIngresso.VIP, 300.0).marcarComoVendido();

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
        Lote lote = new Lote(1, 0.0);
        lote.criarIngresso(TipoIngresso.NORMAL, 100.0).marcarComoVendido();
        lote.criarIngresso(TipoIngresso.VIP, 200.0).marcarComoVendido();

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
        Lote lote = new Lote(1, 0.0); // Sem desconto
        lote.criarIngresso(TipoIngresso.NORMAL, 50.0).marcarComoVendido();
        lote.criarIngresso(TipoIngresso.VIP, 100.0).marcarComoVendido();

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
