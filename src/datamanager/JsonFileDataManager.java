package datamanager;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Implementação da interface {@link DataManager} para persistência de dados em arquivos JSON.
 * Utiliza a biblioteca Jackson para realizar a serialização e desserialização de coleções.
 */
public class JsonFileDataManager implements DataManager {

    /**
     * Instância do {@link ObjectMapper} configurada para processamento de JSON.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Realiza a leitura de objetos a partir de um arquivo JSON localizado no diretório de dados.
     * Caso ocorra falha na leitura, retorna uma lista vazia.
     *
     * @param fileName o nome do arquivo (sem extensão) a ser lido.
     * @param tClass   a classe dos objetos contidos no arquivo.
     * @param <T>      o tipo dos objetos retornados.
     * @return uma lista de objetos desserializados ou uma lista vazia em caso de erro.
     */
    @Override
    public <T> List<T> buscar(String fileName, Class<T> tClass) {
        File arquivoJson = new File("data/" + fileName + ".json");

        try {
            JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, tClass);
            return objectMapper.readValue(arquivoJson, javaType);
        } catch (Exception e) {
            return List.of();
        }
    }

    /**
     * Persiste uma coleção de objetos em um arquivo JSON com formatação legível (pretty printer).
     * Falhas na escrita do arquivo são suprimidas.
     *
     * @param fileName o nome do arquivo (sem extensão) onde os dados serão salvos.
     * @param data     a coleção de objetos a ser persistida.
     * @param <T>      o tipo dos objetos contidos na coleção.
     */
    @Override
    public <T> void salvar(String fileName, Collection<T> data) {
        File arquivoJson = new File("data/" + fileName + ".json");

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(arquivoJson, data);
        } catch (Exception _) {
            // Exceção suprimida conforme implementação original
        }
    }
}