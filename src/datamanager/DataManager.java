package datamanager;

import java.util.Collection;
import java.util.List;

/**
 * Interface responsável pela definição de operações de persistência de dados.
 * Define os métodos necessários para a leitura e escrita de coleções de objetos
 * em arquivos de armazenamento.
 */
public interface DataManager {

    /**
     * Realiza a leitura dos dados armazenados em um arquivo específico.
     *
     * @param fileName o caminho ou nome do arquivo de onde os dados serão lidos.
     * @param tClass   a classe dos objetos contidos no arquivo, utilizada para desserialização.
     * @param <T>      o tipo dos objetos retornados pela busca.
     * @return uma lista contendo os objetos desserializados a partir do arquivo.
     */
    <T> List<T> buscar(String fileName, Class<T> tClass);

    /**
     * Persiste uma coleção de dados em um arquivo específico.
     *
     * @param fileName o caminho ou nome do arquivo onde os dados serão salvos.
     * @param data     a coleção de objetos a ser persistida.
     * @param <T>      o tipo dos objetos contidos na coleção.
     */
    <T> void salvar(String fileName, Collection<T> data);
}