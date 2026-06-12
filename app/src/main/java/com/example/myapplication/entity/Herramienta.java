package com.example.myapplication.entity;

public class Herramienta {

    private int idherramienta;
    private String nombre;
    private String marca;
    private String descripcion;

    //Constructor vacío
    public Herramienta() {
    }

    //Constructor con todos los atributos
    public Herramienta(int idherramienta, String nombre, String marca, String descripcion) {
        this.idherramienta = idherramienta;
        this.nombre = nombre;
        this.marca = marca;
        this.descripcion = descripcion;
    }

    public int getIdherramienta() {
        return idherramienta;
    }

    public void setIdherramienta(int idherramienta) {
        this.idherramienta = idherramienta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}