package utils;

import java.time.Instant;
import java.util.Date;

/**
 * Classe utilitária responsável por facilitar a conversão de datas.
 * Ela padroniza a transformação de objetos Date para String e vice-versa,
 * sendo especialmente útil para a serialização e desserialização de dados (como em arquivos JSON).
 */
public class DateUtils {

    /**
     * Converte um objeto Date para uma representação em String.
     * Utiliza a classe Instant para gerar o formato padrão da data (ISO-8601).
     * * @param date O objeto Date que será convertido.
     * @return Uma String com a representação da data, ou null caso o parâmetro recebido seja null.
     */
    public static String converterParaString(Date date) {
        if(date == null) return null;
        return date.toInstant().toString();
    }

    /**
     * Converte uma String de volta para um objeto Date.
     * Espera que a String recebida esteja no mesmo formato (ISO-8601) gerado pelo método converterParaString.
     * * @param string A String contendo a data que precisa ser convertida.
     * @return O objeto Date correspondente, ou null caso a String recebida seja null.
     */
    public static Date converterDeString(String string) {
        if(string == null) return null;
        return Date.from(Instant.parse(string));
    }
}