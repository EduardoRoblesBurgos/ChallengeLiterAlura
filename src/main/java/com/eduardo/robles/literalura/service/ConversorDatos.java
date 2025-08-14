package com.eduardo.robles.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversorDatos implements IConversorDatos {
    private ObjectMapper mapeadorObjetos = new ObjectMapper();

    //Método implementado de la interfaz para deserializar un json
    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return mapeadorObjetos.readValue(json,clase);
        } catch (JsonProcessingException e) {
            System.out.println("Error en ConversorDatos: " + e + ", " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}