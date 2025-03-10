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
        // Vender todos os ingressos
        for (Ingresso ingresso : show.getLotes().get(0).getIngressos()) {
            show.venderIngresso(1, ingresso.getId());
        }

        double receitaLiquida = show.calcularReceitaLiquida();
        System.out.println("Receita líquida final: " + receitaLiquida);

        assertTrue(receitaLiquida > 0, "A receita líquida deveria ser positiva.");
        assertEquals(StatusFinanceiro.LUCRO, show.getStatusFinanceiro());
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
