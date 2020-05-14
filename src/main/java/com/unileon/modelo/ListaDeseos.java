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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Sergio
 */
@Entity
@Table(name="deseos")
public class ListaDeseos implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idDeseos;
    
    @Column(name="valoracion")
    private Integer valoracion;
    
    @JoinColumn(name="idCliente")
    @ManyToOne
    private Cliente cliente;
    
    @JoinColumn(name="idProducto")
    @OneToOne
    private Producto producto;

    public int getIdDeseos() {
        return idDeseos;
    }

    public void setIdDeseos(int idDeseos) {
        this.idDeseos = idDeseos;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.idDeseos;
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
        final ListaDeseos other = (ListaDeseos) obj;
        if (this.idDeseos != other.idDeseos) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ListaDeseos{" + "idDeseos=" + idDeseos + ", valoracion=" + valoracion + ", cliente=" + cliente + ", producto=" + producto + '}';
    }
    
    
    
}
