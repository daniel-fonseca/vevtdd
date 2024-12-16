package test;

import static org.junit.jupiter.api.Assertions.*;
import model.Ingresso;
import model.TipoIngresso;
import org.junit.jupiter.api.Test;

class IngressoTest {

    @Test
    void deveCriarIngressoComStatusNaoVendido() {
        Ingresso ingresso = new Ingresso(1, TipoIngresso.NORMAL, 10.0);
        assertFalse(ingresso.isVendido());
    }

    @Test
    void deveMarcarIngressoComoVendido() {
        Ingresso ingresso = new Ingresso(1, TipoIngresso.VIP, 20.0);
        ingresso.marcarComoVendido();
        assertTrue(ingresso.isVendido());
    }
    
}
