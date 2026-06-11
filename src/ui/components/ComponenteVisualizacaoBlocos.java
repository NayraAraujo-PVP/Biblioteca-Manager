package ui.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ui.UIUtils.quebraLinha;

/**
 * Componente responsável por exibir informações em blocos
 * organizados por identificador e indicadores.
 */
public class ComponenteVisualizacaoBlocos {

    private final List<String> indicadores;
    private final Map<Integer, List<String>> items = new HashMap<>();

    /**
     * Cria o componente de visualização.
     *
     * @param indicadores lista de indicadores que serão exibidos.
     */
    public ComponenteVisualizacaoBlocos(List<String> indicadores) {
        this.indicadores = indicadores;
    }

    /**
     * Adiciona um item para exibição.
     *
     * @param id identificador do item.
     * @param valores valores correspondentes aos indicadores.
     */
    public void adicionarItem(int id, List<String> valores) {
        items.put(id, valores);
    }

    /**
     * Exibe todos os itens cadastrados em formato de blocos.
     */
    public void mostrar() {
        printDivisoria();

        int maiorIndicador = indicadores.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);

        for (Integer id : items.keySet().stream().sorted().toList()) {
            for (int i = 0; i < indicadores.size(); i++) {

                if (i == 0) {
                    System.out.printf(" [ %-3d ] ", id);
                } else {
                    System.out.print("         ");
                }

                System.out.printf(
                        "%-" + (maiorIndicador + 1) + "s %s",
                        indicadores.get(i) + ":",
                        items.get(id).get(i)
                );

                quebraLinha();
            }

            printDivisoria();
        }
    }

    /**
     * Exibe uma linha divisória utilizada na formatação da saída.
     */
    private void printDivisoria() {
        for (int i = 0; i < 50; i++) {
            System.out.print("-");
        }

        quebraLinha();
    }
}
