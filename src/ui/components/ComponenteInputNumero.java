package ui.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static ui.UIUtils.quebraLinha;

/**
 * Componente de interface especializado na captura e validação de valores numéricos
 * fornecidos via entrada padrão. Oferece suporte ao registro de pseudônimos
 * (atalhos textuais) que representam valores numéricos específicos.
 */
public class ComponenteInputNumero {

    private final Scanner input;
    private final Map<String, Integer> pseudonimos = new HashMap<>();

    /**
     * Construtor para inicialização do componente de entrada numérica.
     *
     * @param input o {@link Scanner} utilizado para capturar as entradas do usuário.
     */
    public ComponenteInputNumero(Scanner input) {
        this.input = input;
    }

    /**
     * Captura um valor numérico informado pelo usuário. Caso a entrada corresponda a
     * um pseudônimo registrado, retorna o valor inteiro associado. Se a entrada não
     * for numérica, solicita a inserção novamente até que um dado válido seja fornecido.
     *
     * @return o valor numérico (int) validado ou mapeado a partir de um pseudônimo.
     */
    public int receberNumero() {
        String valorString = input.nextLine();
        Integer valor = null;

        while (valor == null) {
            // Verifica se a entrada corresponde a um atalho registrado
            if (pseudonimos.containsKey(valorString.toLowerCase())) {
                return pseudonimos.get(valorString.toLowerCase());
            }

            try {
                valor = Integer.parseInt(valorString);
            } catch (Exception _) {
                quebraLinha();
                System.out.println("O valor deve ser numérico.");

                valorString = input.nextLine();
            }
        }

        return valor;
    }

    /**
     * Registra um pseudônimo (atalho textual) associado a um valor numérico.
     *
     * @param pseudonimo o texto utilizado como atalho.
     * @param valor      o valor numérico associado ao pseudônimo.
     */
    public void registrarPseudonimo(String pseudonimo, int valor) {
        pseudonimos.put(pseudonimo.toLowerCase(), valor);
    }
}