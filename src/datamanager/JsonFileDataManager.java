package datamanager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class JsonFileDataManager implements DataManager {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> List<T> buscar(String fileName) {
        File arquivoJson = new File("data/" + fileName + ".json");

        try {
            return objectMapper.readValue(arquivoJson, new TypeReference<>() {});
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public <T> void salvar(String fileName, Collection<T> data) {
        File arquivoJson = new File("data/" + fileName + ".json");

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(arquivoJson, data);
        } catch (Exception _) {}
    }
}
