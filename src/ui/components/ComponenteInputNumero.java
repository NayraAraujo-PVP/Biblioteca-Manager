package ui.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static ui.UIUtils.quebraLinha;

/**
 * Componente responsável por receber valores numéricos
 * digitados pelo usuário.
 * Também permite cadastrar pseudônimos que representam
 * valores inteiros específicos.
 */
public class ComponenteInputNumero {

    private final Scanner input;
    private final Map<String, Integer> pseudonimos = new HashMap<>();

    /**
     * Cria o componente de entrada numérica.
     *
     * @param input scanner utilizado para leitura dos dados.
     */
    public ComponenteInputNumero(Scanner input) {
        this.input = input;
    }

    /**
     * Solicita e valida um número informado pelo usuário.
     *
     * @return número informado ou valor associado a um pseudônimo.
     */
    public int receberNumero() {
        String valorString = input.nextLine();
        Integer valor = null;

        while (valor == null) {
            if (pseudonimos.containsKey(valorString.toLowerCase())) {
                return pseudonimos.get(valorString.toLowerCase());
            }

            try {
                valor = Integer.parseInt(valorString);
            } catch (Exception e) {
                quebraLinha();
                System.out.println("O valor deve ser numérico.");

                valorString = input.nextLine();
            }
        }

        return valor;
    }

    /**
     * Associa um texto a um valor numérico.
     *
     * @param pseudonimo texto que representa o valor.
     * @param valor valor associado ao pseudônimo.
     */
    public void registrarPseudonimo(String pseudonimo, int valor) {
        pseudonimos.put(pseudonimo.toLowerCase(), valor);
    }
}
