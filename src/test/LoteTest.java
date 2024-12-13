package test;

import static org.junit.jupiter.api.Assertions.*;
import model.Lote;
import model.Ingresso;
import org.junit.jupiter.api.Test;
import java.util.List;

class LoteTest {

    @Test
    void deveCriarLoteComIngressos() {
        List<Ingresso> ingressos = List.of(
                new Ingresso(1, TipoIngresso.NORMAL, false, 10.0),
                new Ingresso(2, TipoIngresso.VIP, false, 20.0)
        );
        Lote lote = new Lote(1, ingressos, 0.10); // 10% de desconto
        assertEquals(1, lote.getId());
        assertEquals(2, lote.getIngressos().size());
        assertEquals(0.10, lote.getDesconto());
    }

}
