package test;

import static org.junit.jupiter.api.Assertions.*;
import model.Ingresso;
import model.TipoIngresso;
import org.junit.jupiter.api.Test;

class IngressoTest {

    @Test
    void deveCriarIngressoCorretamente() {
        Ingresso ingresso = new Ingresso(1, TipoIngresso.VIP, false, 20.0);
        assertEquals(1, ingresso.getId());
        assertEquals(TipoIngresso.VIP, ingresso.getTipo()); // Verifica o tipo
        assertFalse(ingresso.isVendido());
        assertEquals(20.0, ingresso.getPreco());
    }

    @Test
    void deveMarcarIngressoComoVendido() {
        Ingresso ingresso = new Ingresso(1, TipoIngresso.NORMAL, false, 10.0);
        ingresso.marcarComoVendido();
        assertTrue(ingresso.isVendido());
    }
}
