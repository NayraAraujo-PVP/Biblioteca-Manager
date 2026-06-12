package ui.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ui.UIUtils.quebraLinha;

/**
 * Componente de interface destinado à exibição formatada de conjuntos de dados.
 * Organiza informações em blocos visuais, onde cada bloco corresponde a um item
 * identificado, apresentando seus atributos conforme uma lista de indicadores definida.
 */
public class ComponenteVisualizacaoBlocos {

    private final List<String> indicadores;
    private final Map<Integer, List<String>> items = new HashMap<>();

    /**
     * Construtor para inicialização do componente de visualização.
     *
     * @param indicadores a lista de títulos/rótulos dos atributos que serão exibidos para cada item.
     */
    public ComponenteVisualizacaoBlocos(List<String> indicadores) {
        this.indicadores = indicadores;
    }

    /**
     * Adiciona um novo item ao componente para exibição.
     *
     * @param id      o identificador único do item.
     * @param valores a lista de valores correspondentes aos indicadores definidos no construtor.
     */
    public void adicionarItem(int id, List<String> valores) {
        items.put(id, valores);
    }

    /**
     * Renderiza no console a visualização em blocos de todos os itens adicionados,
     * ordenados pelo identificador. Cada bloco exibe o ID do item e os indicadores
     * associados aos seus respectivos valores.
     */
    public void mostrar() {
        printDivisoria();

        // Calcula o espaçamento necessário para alinhar os valores com base no maior indicador
        int maiorIndicador = indicadores.stream().mapToInt(String::length).max().orElse(0);

        // Exibe itens ordenados pelo ID
        for (Integer id : items.keySet().stream().sorted().toList()) {
            for (int i = 0; i < indicadores.size(); i++) {
                // Exibe o ID apenas na primeira linha do bloco
                if (i == 0) System.out.printf(" [ %-3d ] ", id);
                else System.out.print("         ");

                System.out.printf("%-" + (maiorIndicador + 1) + "s %s", indicadores.get(i) + ":", items.get(id).get(i));
                quebraLinha();
            }

            printDivisoria();
        }
    }

    /**
     * Imprime uma linha divisória no console para delimitar os blocos de dados.
     */
    private void printDivisoria() {
        for (int i = 0; i < 50; i++) {
            System.out.print("-");
        }
        quebraLinha();
    }
}