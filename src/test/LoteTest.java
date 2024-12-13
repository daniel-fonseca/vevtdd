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

}
