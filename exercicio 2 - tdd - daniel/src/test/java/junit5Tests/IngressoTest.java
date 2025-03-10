package junit5Tests;

import static org.junit.jupiter.api.Assertions.*;
import model.Ingresso;
import model.TipoIngresso;
import org.junit.jupiter.api.*;

@Tag("unit")
@DisplayName("Testes de Unidade - Ingresso")
class IngressoTest {

    @Test
    @DisplayName("Deve criar um ingresso com status NÃO vendido")
    void deveCriarIngressoComStatusNaoVendido() {
        Ingresso ingresso = new Ingresso(1, TipoIngresso.NORMAL, 10.0);
        assertFalse(ingresso.isVendido(), "O ingresso recém-criado deve estar disponível.");
    }

    @Test
    @DisplayName("Deve marcar um ingresso como vendido")
    void deveMarcarIngressoComoVendido() {
        Ingresso ingresso = new Ingresso(1, TipoIngresso.VIP, 20.0);
        ingresso.marcarComoVendido();
        assertTrue(ingresso.isVendido(), "O ingresso deve ser marcado como vendido.");
    }
}