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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Sergio
 */
@Entity
@Table(name="lineaventa")
public class LineaVenta implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idLineaVenta;
    
    @JoinColumn(name="idVenta")
    @OneToMany
    private Venta venta;
    
    @JoinColumn(name="idProducto")
    @OneToMany
    private Producto producto;
    
    @Column(name="cantidad")
    private int cantidad;

    public int getIdLineaVenta() {
        return idLineaVenta;
    }

    public void setIdLineaVenta(int idLineaVenta) {
        this.idLineaVenta = idLineaVenta;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.idLineaVenta;
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
        final LineaVenta other = (LineaVenta) obj;
        if (this.idLineaVenta != other.idLineaVenta) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LineaVenta{" + "idLineaVenta=" + idLineaVenta + ", venta=" + venta + ", producto=" + producto + ", cantidad=" + cantidad + '}';
    }
 
}
