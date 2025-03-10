package functionalTests;

import helper.ShowTestHelper;
import model.Lote;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TabelasDecisaoTest {

    // Regra 1 - Ingresso disponível: Sim; Tipo: NORMAL; Lote tem desconto? Não; Preço final: Preço padrão
    @Test
    void deveCobrarPrecoPadraoParaIngressoNormalSemDesconto() {
        Lote lote = new Lote(1, 0.0, ShowTestHelper.criarIngressosValidos(100)); // Criando lote sem desconto
        double preco = lote.venderIngresso(61); // Pegando um ingresso NORMAL (ID após os ingressos VIP e MEIA)
        assertEquals(10.0, preco); // Preço padrão sem desconto
    }

    // Regra 2 - Ingresso disponível: Sim; Tipo: VIP; Lote tem desconto? Sim (10% - já presente no lote válido); Preço final: Preço = 2 * NORMAL com desconto
    @Test
    void deveAplicarDescontoEmIngressoVIP() {
        Lote lote = ShowTestHelper.criarLoteValido(); // Garantindo um lote válido com ingressos distribuídos corretamente
        double preco = lote.venderIngresso(2); // Pegando um ingresso VIP (ID 2, conforme definido no ShowTestHelper)
        assertEquals(18.0, preco, 0.001); // VIP = (2 * 10.00) - 10% desconto (já que lote válido tem 10% de desconto)
    }

    // Regra 3 - Ingresso disponível: Sim; Tipo: MEIA; Lote tem desconto? Sim (10% - já presente no lote válido); Preço final: Preço = 50% * NORMAL com desconto
    @Test
    void deveAplicarDescontoEmIngressoMeiaEntrada() {
        Lote lote = ShowTestHelper.criarLoteValido(); // Garantindo um lote válido
        double preco = lote.venderIngresso(5); // Pegando um ingresso MEIA_ENTRADA (definido no lote válido)
        assertEquals(4.5, preco, 0.001); // (10.00 * 50%) - 10% desconto (já que lote válido tem 10% de desconto)
    }

    // Regra 4 - Ingresso disponível: Não; Tipo: -; Lote tem desconto? -; Preço final: ERRO
    @Test
    void deveGerarErroParaIngressoIndisponivel() {
        Lote lote = ShowTestHelper.criarLoteEVenderIngressos(); // Criando lote onde todos os ingressos foram vendidos
        assertThrows(IllegalStateException.class, () -> lote.venderIngresso(1)); // Tentando vender ingresso já vendido
    }
}
