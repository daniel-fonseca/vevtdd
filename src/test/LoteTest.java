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
        List<Ingresso> ingressos = List.of(
                new Ingresso(1, TipoIngresso.NORMAL, 10.0),
                new Ingresso(2, TipoIngresso.VIP, 20.0)
        );
        Lote lote = new Lote(1, ingressos, 0.10);
        assertTrue(lote.getIngressos().stream().allMatch(ingresso -> !ingresso.isVendido()));
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
        lote.criarIngresso(TipoIngresso.VIP, 20.0); // Excesso de VIPs

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lote.criarIngresso(TipoIngresso.NORMAL, 10.0);
        });

        assertEquals("Ingressos VIP devem ser entre 20% e 30% do total.", exception.getMessage());
    }

    @Test
    void deveFalharQuandoIngressosMeiaEntradaForemInsuficientes() {
        Lote lote = new Lote(1, 0.10);

        lote.criarIngresso(TipoIngresso.VIP, 20.0);
        lote.criarIngresso(TipoIngresso.VIP, 20.0);
        lote.criarIngresso(TipoIngresso.NORMAL, 10.0);
        lote.criarIngresso(TipoIngresso.NORMAL, 10.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lote.criarIngresso(TipoIngresso.NORMAL, 10.0);
        });

        assertEquals("Ingressos MEIA_ENTRADA devem ser 10% do total.", exception.getMessage());
    }

}
