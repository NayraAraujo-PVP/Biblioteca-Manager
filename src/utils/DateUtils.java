package utils;

import java.time.Instant;
import java.util.Date;

/**
 * Classe utilitária para conversão de datas no sistema.
 * Facilita a serialização e desserialização de objetos {@link Date} 
 * para o formato String (ISO-8601).
 */
public class DateUtils {

    /**
     * Converte um objeto {@link Date} para sua representação em String.
     *
     * @param date o objeto de data a ser convertido.
     * @return a representação em String (ISO-8601) da data, ou null se a entrada for nula.
     */
    public static String converterParaString(Date date) {
        if (date == null) return null;
        return date.toInstant().toString();
    }

    /**
     * Converte uma String (formato ISO-8601) para um objeto {@link Date}.
     *
     * @param string a string contendo a data no formato ISO-8601.
     * @return um objeto {@link Date} correspondente, ou null se a entrada for nula.
     */
    public static Date converterDeString(String string) {
        if (string == null) return null;
        return Date.from(Instant.parse(string));
    }
}