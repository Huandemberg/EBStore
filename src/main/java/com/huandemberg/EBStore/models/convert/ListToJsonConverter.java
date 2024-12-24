package com.huandemberg.EBStore.models.convert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import java.util.List;

public class ListToJsonConverter implements AttributeConverter<List<Integer>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao converter lista para JSON", e);
        }
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<Integer>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Falha ao converter JSON para lista", e);
        }
    }
}
