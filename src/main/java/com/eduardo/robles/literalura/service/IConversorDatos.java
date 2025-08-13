package com.eduardo.robles.literalura.service;

//Interfaz para deserializar cualquier tipo de dato
public interface IConversorDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}