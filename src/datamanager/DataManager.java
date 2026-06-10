package datamanager;

import java.util.Collection;
import java.util.List;

public interface DataManager {
    <T> List<T> buscar(String fileName, Class<T> tClass);
    <T> void salvar(String fileName, Collection<T> data);
}
