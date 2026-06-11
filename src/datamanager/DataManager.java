package datamanager;

import java.util.Collection;
import java.util.List;

/**
 * Interface responsável pela persistência e recuperação de dados.
 */
public interface DataManager {

    /**
     * Busca os dados armazenados em um arquivo.
     *
     * @param fileName nome do arquivo.
     * @param tClass tipo dos objetos armazenados.
     * @param <T> tipo dos dados.
     * @return lista de objetos recuperados.
     */
    <T> List<T> buscar(String fileName, Class<T> tClass);

    /**
     * Salva uma coleção de dados em um arquivo.
     *
     * @param fileName nome do arquivo.
     * @param data dados a serem armazenados.
     * @param <T> tipo dos dados.
     */
    <T> void salvar(String fileName, Collection<T> data);
}
