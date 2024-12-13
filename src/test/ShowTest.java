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
    void deveCalcularReceitaLiquida() {
        List<Ingresso> ingressos = List.of(
                new Ingresso(1, TipoIngresso.NORMAL, 10.0),
                new Ingresso(2, TipoIngresso.VIP, 20.0)
        );
        ingressos.get(1).marcarComoVendido();

        Lote lote = new Lote(1, ingressos, 0.10);
        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                1000.0,
                2000.0,
                true,
                List.of(lote)
        );

        double receitaEsperada = 20.0 * 0.90;
        assertEquals(receitaEsperada, show.calcularReceitaLiquida());
    }

}
