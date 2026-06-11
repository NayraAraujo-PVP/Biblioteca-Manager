package datamanager;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Implementação de DataManager que utiliza arquivos JSON.
 */
public class JsonFileDataManager implements DataManager {

    /** Responsável pela leitura e escrita dos arquivos JSON. */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Busca os dados armazenados em um arquivo JSON.
     *
     * @param fileName nome do arquivo.
     * @param tClass tipo dos objetos armazenados.
     * @param <T> tipo dos dados.
     * @return lista de objetos recuperados.
     */
    @Override
    public <T> List<T> buscar(String fileName, Class<T> tClass) {
        File arquivoJson = new File("data/" + fileName + ".json");

        try {
            JavaType javaType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, tClass);

            return objectMapper.readValue(arquivoJson, javaType);
        } catch (Exception e) {
            return List.of();
        }
    }

    /**
     * Salva uma coleção de dados em um arquivo JSON.
     *
     * @param fileName nome do arquivo.
     * @param data dados a serem armazenados.
     * @param <T> tipo dos dados.
     */
    @Override
    public <T> void salvar(String fileName, Collection<T> data) {
        File arquivoJson = new File("data/" + fileName + ".json");

        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(arquivoJson, data);
        } catch (Exception e) {
        }
    }
}
