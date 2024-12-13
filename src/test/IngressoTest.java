package test;

import static org.junit.jupiter.api.Assertions.*;
import model.Ingresso;
import org.junit.jupiter.api.Test;

public class IngressoTest {

    @Test
    void deveCriarIngressoCorretamente() {
        Ingresso ingresso = new Ingresso(1, "VIP", false, 20.0);
        assertEquals(1, ingresso.getId());
        assertEquals("VIP", ingresso.getTipo());
        assertFalse(ingresso.isVendido());
        assertEquals(20.0, ingresso.getPreco());
    }
}
