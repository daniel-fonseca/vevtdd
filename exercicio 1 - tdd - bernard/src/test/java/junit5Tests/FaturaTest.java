package junit5Tests;

import model.Fatura;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@DisplayName("Testes de Unidade - Fatura")
class FaturaTest {

    @Test
    @DisplayName("Deve criar uma fatura com valores corretos")
    void deveCriarFaturaComValoresCorretos() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.0, "Cliente Teste");

        assertAll(
                () -> assertEquals(LocalDate.of(2023, 2, 20), fatura.getData()),
                () -> assertEquals(1500.0, fatura.getValorTotal()),
                () -> assertEquals("Cliente Teste", fatura.getNomeCliente()),
                () -> assertFalse(fatura.isPaga())
        );
    }

    @Test
    @DisplayName("Deve marcar a fatura como paga corretamente")
    void deveMarcarFaturaComoPaga() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.0, "Cliente Teste");

        fatura.marcarComoPaga();

        assertTrue(fatura.isPaga(), "A fatura deveria estar marcada como paga.");
    }
}