package datamanager;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class JsonFileDataManager implements DataManager {
    private final ObjectMapper objectMapper = new ObjectMapper();

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

    @Override
    public <T> void salvar(String fileName, Collection<T> data) {
        File arquivoJson = new File("data/" + fileName + ".json");

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(arquivoJson, data);
        } catch (Exception _) {}
    }
}
