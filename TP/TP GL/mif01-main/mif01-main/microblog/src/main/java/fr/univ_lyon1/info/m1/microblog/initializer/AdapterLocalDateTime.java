package fr.univ_lyon1.info.m1.microblog.initializer;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Adaptateur Gson pour sérialiser/désérialiser les LocalDateTime.
 */
public class AdapterLocalDateTime implements
     JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Sérialise une date en JSON.
     * @param src La date à sérialiser
     * @param typeOfSrc Le type de la source
     * @param context Le contexte de sérialisation
     * @return L'élément JSON
     */
    @Override
    public JsonElement serialize(final LocalDateTime src, final Type typeOfSrc,
     final JsonSerializationContext context) {
        return new JsonPrimitive(FORMATTER.format(src));
    }

    /**
     * Désérialise une date à partir d'un JSON.
     * @param json L'élément JSON
     * @param typeOfT Le type de la destination
     * @param context Le contexte de désérialisation
     * @return La date désérialisée
     * @throws JsonParseException Si la désérialisation échoue
     */
    @Override
    public LocalDateTime deserialize(final JsonElement json, final Type typeOfT,
     final JsonDeserializationContext context) throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(), FORMATTER);
    }
} 
