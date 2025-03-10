package tddtests;

import static org.junit.jupiter.api.Assertions.*;
import model.*;
import org.junit.jupiter.api.Test;
import helper.ShowTestHelper;

import java.util.List;

class ShowTest {

    @Test
    void deveCriarShowComLotes() {
        List<Ingresso> ingressos = ShowTestHelper.criarIngressosValidos(500);
        Lote lote = new Lote(1, 0.15, ingressos);

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
        List<Ingresso> ingressos = ShowTestHelper.criarIngressosValidos(500);
        Lote lote = new Lote(1, 0.15, ingressos);

        Show show = new Show(
                "03/10/2024",
                "Artista",
                1000.0,
                2000.0,
                true,
                List.of(lote)
        );

        for (Ingresso ingresso : ingressos) {
            show.venderIngresso(1, ingresso.getId());
        }

        assertEquals(StatusFinanceiro.LUCRO, show.getStatusFinanceiro());
        assertTrue(show.calcularReceitaLiquida() > 0);
    }

    @Test
    void deveObterStatusFinanceiroComLucro() {
        List<Ingresso> ingressos = ShowTestHelper.criarIngressosValidos(500);

        Lote lote = new Lote(1, 0.15, ingressos);

        Show show = new Show(
                "03/10/2024",
                "Artista",
                1000.0,
                2000.0,
                true,
                List.of(lote)
        );

        for (Ingresso ingresso : ingressos) {
            show.venderIngresso(1, ingresso.getId());
        }

        assertEquals(StatusFinanceiro.LUCRO, show.getStatusFinanceiro());
    }

    @Test
    void deveGetStatusFinanceiroEstavel() {
        List<Ingresso> ingressos = ShowTestHelper.criarIngressosValidos(500);
        Lote lote = new Lote(1, 0.15, ingressos);

        double cache = 1000.0;
        double despesas = 2000.0;
        double totalCustos = cache + despesas;

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                cache,
                despesas,
                false,
                List.of(lote)
        );

        int index = 0;
        double bilheteriaEsperada = 0.0;

        while (show.getBilheteria() < totalCustos && index < ingressos.size()) {
            bilheteriaEsperada += show.venderIngresso(1, ingressos.get(index).getId());
            index++;
        }

        double receitaLiquidaEsperada = bilheteriaEsperada - show.getCacheArtista() - show.getDespesasInfraestrutura();

        assertEquals(receitaLiquidaEsperada, show.calcularReceitaLiquida());
        assertEquals(StatusFinanceiro.ESTÁVEL, show.getStatusFinanceiro());
    }

    @Test
    void deveGetStatusFinanceiroPrejuizo() {
        List<Ingresso> ingressos = ShowTestHelper.criarIngressosValidos(500);
        Lote lote = new Lote(1, 0.15, ingressos);

        double cache = 1000.0;
        double despesas = 2000.0;
        double totalCustos = cache + despesas;

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                cache,
                despesas,
                false,
                List.of(lote)
        );

        double receitaAlvo = totalCustos - 100;
        double receitaAtual = 0.0;
        int index = 0;

        while (receitaAtual < receitaAlvo && index < ingressos.size()) {
            show.venderIngresso(1, ingressos.get(index).getId());
            receitaAtual += ingressos.get(index).getTipo() == TipoIngresso.MEIA_ENTRADA
                    ? ingressos.get(index).getPreco() * 0.5
                    : ingressos.get(index).getPreco() * 0.85;
            index++;
        }

        assertEquals(StatusFinanceiro.PREJUÍZO, show.getStatusFinanceiro());
    }

    @Test
    void deveLancarExcecaoParaVendaDeIngressoInexistente() {
        List<Ingresso> ingressos = ShowTestHelper.criarIngressosValidos(500);
        Lote lote = new Lote(1, 0.15, ingressos);

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                1000.0,
                2000.0,
                true,
                List.of(lote)
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            show.venderIngresso(1, 9999);
        });

        assertTrue(exception.getMessage().contains("Ingresso com ID 9999 não encontrado no lote 1."));
    }

    @Test
    void deveLancarExcecaoParaVendaDeIngressoJaVendido() {
        List<Ingresso> ingressos = ShowTestHelper.criarIngressosValidos(500);
        Lote lote = new Lote(1, 0.15, ingressos);

        Show show = new Show(
                "03/10/2024",
                "Paul McCartney",
                1000.0,
                2000.0,
                true,
                List.of(lote)
        );

        show.venderIngresso(1, 1);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            show.venderIngresso(1, 1);
        });

        assertTrue(exception.getMessage().contains("Ingresso com ID 1 no lote 1 já foi vendido."));
    }
}
