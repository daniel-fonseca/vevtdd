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

    @Test
    void deveValidarPrecoIngressoVIP() {
        Ingresso ingressoVIP = new Ingresso(1, TipoIngresso.VIP, 10.0);
        assertEquals(20.0, ingressoVIP.getPreco(), "Ingresso VIP deve custar o dobro do ingresso NORMAL.");
    }

    @Test
    void deveValidarPrecoIngressoMeiaEntrada() {
        Ingresso ingressoMeiaEntrada = new Ingresso(2, TipoIngresso.MEIA_ENTRADA, 10.0);
        assertEquals(5.0, ingressoMeiaEntrada.getPreco(), "Ingresso MEIA_ENTRADA deve custar a metade do ingresso NORMAL.");
    }

    @Test
    void deveValidarPrecoIngressoNormal() {
        Ingresso ingressoNormal = new Ingresso(3, TipoIngresso.NORMAL, 10.0);
        assertEquals(10.0, ingressoNormal.getPreco(), "Ingresso NORMAL deve manter o preço original.");
    }
}
