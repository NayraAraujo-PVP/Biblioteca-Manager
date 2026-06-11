package ui.components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ComponenteInputData {
    private final Scanner input;
    private final Map<String, LocalDate> pseudonimos = new HashMap<>();

    public ComponenteInputData(Scanner input) {
        this.input = input;
    }

    public LocalDate receberData() {
        String valorString = input.nextLine();
        LocalDate valor = null;

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (valor == null) {
            if(pseudonimos.containsKey(valorString.toLowerCase())) {
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

    public void registrarPseudonimo(String pseudonimo, LocalDate valor) {
        pseudonimos.put(pseudonimo.toLowerCase(), valor);
    }
}
