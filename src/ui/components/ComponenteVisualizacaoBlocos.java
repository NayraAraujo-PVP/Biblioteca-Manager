package ui.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponenteVisualizacaoBlocos {
    private final List<String> indicadores;
    private final Map<Integer, List<String>> items = new HashMap<>();

    public ComponenteVisualizacaoBlocos(List<String> indicadores) {
        this.indicadores = indicadores;
    }

    public void adicionarItem(int id, List<String> valores) {
        items.put(id, valores);
    }

    public void mostrar() {
        printDivisoria();

        int maiorIndicador = indicadores.stream().mapToInt(String::length).max().orElse(0);

        for (Integer id : items.keySet().stream().sorted().toList()) {
            for (int i = 0; i < indicadores.size(); i++) {
                if (i == 0) System.out.printf(" [ %-3d ] ", id);
                else System.out.print("         ");

                System.out.printf("%-" + (maiorIndicador + 1) + "s %s", indicadores.get(i) + ":", items.get(id).get(i));
                System.out.println();
            }

            printDivisoria();
        }
    }

    private void printDivisoria() {
        for (int i = 0; i < 50; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
