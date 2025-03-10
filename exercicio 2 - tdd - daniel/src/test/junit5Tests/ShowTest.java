package test.junit5Tests;

import static org.junit.jupiter.api.Assertions.*;
import model.*;
import org.junit.jupiter.api.*;
import test.helper.IngressoFactory;

import java.util.List;

@Tag("unit")
@DisplayName("Testes de Unidade - Show")
class ShowTest {

    private Show show;

    @BeforeEach
    void setup() {
        List<Ingresso> ingressos = IngressoFactory.criarIngressos(70, 10, 20); // 70% NORMAL, 10% MEIA, 20% VIP
        Lote lote = new Lote(1, 0.0, ingressos);
        show = new Show("03/10/2024", "Paul McCartney", 1000.0, 2000.0, true, List.of(lote));
    }

    @Test
    @DisplayName("Deve criar um show corretamente")
    void deveCriarShowComLotes() {
        assertAll(
                () -> assertEquals("03/10/2024", show.getData()),
                () -> assertEquals("Paul McCartney", show.getArtista()),
                () -> assertEquals(1, show.getLotes().size()),
                () -> assertTrue(show.isDataEspecial())
        );
    }

    @Test
    @DisplayName("Deve calcular receita líquida corretamente com lucro")
    void deveCalcularReceitaLiquidaComLucro() {
        double receitaTotal = 0.0;

        for (Ingresso ingresso : show.getLotes().get(0).getIngressos()) {
            receitaTotal += show.venderIngresso(1, ingresso.getId());
        }

        double cache = show.getCacheArtista();
        double despesas = show.getDespesasInfraestrutura();
        double receitaLiquidaCalculada = show.calcularReceitaLiquida();
        double receitaLiquidaEsperada = receitaTotal - cache - despesas;

        assertEquals(receitaLiquidaEsperada, receitaLiquidaCalculada, 0.01,
                "A receita líquida calculada deve corresponder à receita esperada.");

        if (receitaLiquidaCalculada >= 1) {
            assertEquals(StatusFinanceiro.LUCRO, show.getStatusFinanceiro(), "O status financeiro deveria ser LUCRO.");
        } else if (receitaLiquidaCalculada < 0) {
            assertEquals(StatusFinanceiro.PREJUÍZO, show.getStatusFinanceiro(), "O status financeiro deveria ser PREJUÍZO.");
        } else {
            assertEquals(StatusFinanceiro.ESTÁVEL, show.getStatusFinanceiro(), "O status financeiro deveria ser ESTÁVEL.");
        }
    }

    @Test
    @DisplayName("Deve lançar exceção para venda de ingresso inexistente")
    void deveLancarExcecaoParaVendaDeIngressoInexistente() {
        assertThrows(IllegalArgumentException.class, () -> show.venderIngresso(1, 9999));
    }

    @Test
    @DisplayName("Deve lançar exceção para venda de ingresso já vendido")
    void deveLancarExcecaoParaVendaDeIngressoJaVendido() {
        show.venderIngresso(1, 1);
        assertThrows(IllegalStateException.class, () -> show.venderIngresso(1, 1));
    }
}
