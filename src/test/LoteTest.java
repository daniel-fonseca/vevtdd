package test;

import static org.junit.jupiter.api.Assertions.*;
import model.Lote;
import model.Ingresso;
import model.TipoIngresso;
import org.junit.jupiter.api.Test;
import java.util.List;

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

}
