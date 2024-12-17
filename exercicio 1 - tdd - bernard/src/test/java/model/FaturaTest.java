package model;

import model.Fatura;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FaturaTest {

    @Test
    void deveCriarFaturaComValoresCorretos() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.0, "Cliente Teste");

        assertEquals(LocalDate.of(2023, 2, 20), fatura.getData());
        assertEquals(1500.0, fatura.getValorTotal());
        assertEquals("Cliente Teste", fatura.getNomeCliente());
        assertFalse(fatura.isPaga());
    }

    @Test
    void deveMarcarFaturaComoPaga() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.0, "Cliente Teste");

        fatura.marcarComoPaga();
        assertTrue(fatura.isPaga());
    }
}
