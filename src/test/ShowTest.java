package test;

import static org.junit.jupiter.api.Assertions.*;
import model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

class ShowTest {

    @Test
    void deveCriarShowComLotes() {
        List<Ingresso> ingressos = List.of(
                new Ingresso(1, TipoIngresso.NORMAL, 10.0),
                new Ingresso(2, TipoIngresso.VIP, 20.0)
        );
        Lote lote = new Lote(1, ingressos, 0.10);
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
        List<Ingresso> ingressos = List.of(
                new Ingresso(1, TipoIngresso.NORMAL, 100.0),
                new Ingresso(2, TipoIngresso.VIP, 200.0)
        );
        ingressos.forEach(Ingresso::marcarComoVendido);

        Lote lote = new Lote(1, ingressos, 0.10);
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
        List<Ingresso> ingressos = List.of(
                new Ingresso(1, TipoIngresso.NORMAL, 100.0),
                new Ingresso(2, TipoIngresso.VIP, 200.0)
        );
        ingressos.forEach(Ingresso::marcarComoVendido);

        Lote lote = new Lote(1, ingressos, 0.10);
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
        List<Ingresso> ingressos = List.of(
                new Ingresso(1, TipoIngresso.NORMAL, 100.0),
                new Ingresso(2, TipoIngresso.VIP, 200.0)
        );
        ingressos.forEach(Ingresso::marcarComoVendido);

        Lote lote = new Lote(1, ingressos, 0.0);
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
        List<Ingresso> ingressos = List.of(
                new Ingresso(1, TipoIngresso.NORMAL, 50.0),
                new Ingresso(2, TipoIngresso.VIP, 100.0)
        );
        ingressos.forEach(Ingresso::marcarComoVendido);

        Lote lote = new Lote(1, ingressos, 0.0); // Sem desconto
        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                300.0,  // Cachê
                100.0,  // Infraestrutura
                false,  // Não é data especial
                List.of(lote)
        );

        assertEquals(StatusFinanceiro.PREJUÍZO, show.avaliarStatusFinanceiro());
    }

}
