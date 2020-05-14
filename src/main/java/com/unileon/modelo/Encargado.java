/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Sergio
 */
@Entity
@Table(name="encargados")
public class Encargado implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idEncargado;
    
    @Column(name="nombre")
    private String nombre;
    
    @Column(name="apellido1")
    private String apellido1;
    
    @Column(name="apellido2")
    private String apellido2;
    
    @Column(name="productosAniadidos")
    private int productosAniadidos;

    public int getIdEncargado() {
        return idEncargado;
    }

    public void setIdEncargado(int idEncargado) {
        this.idEncargado = idEncargado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public int getProductosAniadidos() {
        return productosAniadidos;
    }

    public void setProductosAniadidos(int productosAniadidos) {
        this.productosAniadidos = productosAniadidos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.idEncargado;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Encargado other = (Encargado) obj;
        if (this.idEncargado != other.idEncargado) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Encargado{" + "idEncargado=" + idEncargado + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", productosAniadidos=" + productosAniadidos + '}';
    }
    
}
