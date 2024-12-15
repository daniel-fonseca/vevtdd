package test;

import static org.junit.jupiter.api.Assertions.*;
import model.Lote;
import model.TipoIngresso;
import org.junit.jupiter.api.Test;

class LoteTest {

    @Test
    void todosIngressosDoLoteDevemSerNaoVendidosAoCriar() {
        Lote lote = new Lote(1, 0.10);

        lote.criarIngresso(TipoIngresso.NORMAL, 10.0);
        lote.criarIngresso(TipoIngresso.VIP, 20.0);

        assertTrue(lote.getIngressos().values().stream().allMatch(ingresso -> !ingresso.isVendido()));
    }

    @Test
    void deveCriarLoteEAdicionarIngressosComPercentuaisCorretos() {
        Lote lote = new Lote(1, 0.10);

        lote.criarIngresso(TipoIngresso.VIP, 20.0);
        lote.criarIngresso(TipoIngresso.VIP, 20.0);
        lote.criarIngresso(TipoIngresso.MEIA_ENTRADA, 10.0);
        lote.criarIngresso(TipoIngresso.NORMAL, 10.0);
        lote.criarIngresso(TipoIngresso.NORMAL, 10.0);
        lote.criarIngresso(TipoIngresso.NORMAL, 10.0);

        assertEquals(6, lote.getIngressos().size());
    }

    @Test
    void deveFalharQuandoIngressosVIPForemExcessivos() {
        Lote lote = new Lote(1, 0.10);

        lote.criarIngresso(TipoIngresso.VIP, 20.0);
        lote.criarIngresso(TipoIngresso.VIP, 20.0);
        lote.criarIngresso(TipoIngresso.VIP, 20.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            lote.criarIngresso(TipoIngresso.NORMAL, 10.0);
        });

        assertTrue(exception.getMessage().contains("Ingressos VIP devem ser entre 20% e 30%"));
    }

    @Test
    void deveFalharQuandoIngressosMeiaEntradaForemInsuficientes() {
        Lote lote = new Lote(1, 0.10);

        lote.criarIngresso(TipoIngresso.VIP, 20.0);
        lote.criarIngresso(TipoIngresso.VIP, 20.0);
        lote.criarIngresso(TipoIngresso.NORMAL, 10.0);
        lote.criarIngresso(TipoIngresso.NORMAL, 10.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            lote.criarIngresso(TipoIngresso.NORMAL, 10.0);
        });

        assertTrue(exception.getMessage().contains("Ingressos MEIA_ENTRADA devem ser 10%"));
    }

    @Test
    void deveVenderIngressoDisponivel() {
        Lote lote = new Lote(1, 0.10);
        lote.criarIngresso(TipoIngresso.NORMAL, 100.0);
        lote.criarIngresso(TipoIngresso.VIP, 200.0);

        double precoVendido = lote.venderIngresso();

        assertEquals(90.0, precoVendido);
        assertTrue(lote.getIngressos().get(1).isVendido());
    }

    @Test
    void deveRetornarValorAoVenderIngresso() {
        Lote lote = new Lote(1, 0.10);
        lote.criarIngresso(TipoIngresso.NORMAL, 100.0);
        lote.criarIngresso(TipoIngresso.VIP, 200.0);

        double precoVendido = show.venderIngresso();

        assertEquals(90.0, precoVendido);
    }

    @Test
    void deveLancarExcecaoQuandoNaoHouverIngressosDisponiveis() {
        Lote lote = new Lote(1, 0.10);

        Exception exception = assertThrows(IllegalStateException.class, lote::venderIngresso);
        assertTrue(exception.getMessage().contains("Não há ingressos disponíveis"));
    }

}
