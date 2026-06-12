package ui.components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Componente de interface especializado na captura e validação de datas fornecidas via entrada padrão.
 * Oferece suporte a formatos específicos de data e ao registro de pseudônimos (atalhos) para datas predefinidas.
 */
public class ComponenteInputData {

    private final Scanner input;
    private final Map<String, LocalDate> pseudonimos = new HashMap<>();

    /**
     * Construtor para inicialização do componente de entrada de dados.
     *
     * @param input o {@link Scanner} utilizado para capturar as entradas do usuário.
     */
    public ComponenteInputData(Scanner input) {
        this.input = input;
    }

    /**
     * Captura uma data informada pelo usuário, validando o formato "dd/MM/yyyy".
     * Caso a entrada corresponda a um pseudônimo registrado, retorna a data associada.
     * Repete a solicitação até que uma entrada válida seja fornecida.
     *
     * @return a data ({@link LocalDate}) validada ou mapeada.
     */
    public LocalDate receberData() {
        String valorString = input.nextLine();
        LocalDate valor = null;

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (valor == null) {
            if (pseudonimos.containsKey(valorString.toLowerCase())) {
                return pseudonimos.get(valorString.toLowerCase());
            }

            try {
                valor = LocalDate.parse(valorString, formato);
            } catch (Exception _) {
                System.out.println("Entrada inválida, digite uma data no formato: dd/MM/yyyy");
                valorString = input.nextLine();
            }
        }

        return valor;
    }

    /**
     * Registra um pseudônimo (atalho textual) associado a uma data específica.
     *
     * @param pseudonimo o texto utilizado como atalho.
     * @param valor      a data associada ao pseudônimo.
     */
    public void registrarPseudonimo(String pseudonimo, LocalDate valor) {
        pseudonimos.put(pseudonimo.toLowerCase(), valor);
    }
}