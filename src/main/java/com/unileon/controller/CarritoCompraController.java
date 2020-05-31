/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.controller;

import com.unileon.EJB.LineaVentaFacadeLocal;
import com.unileon.EJB.VentaFacadeLocal;
import com.unileon.modelo.LineaVenta;
import com.unileon.modelo.Producto;
import com.unileon.modelo.Usuario;
import com.unileon.modelo.Venta;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Sergio
 */
@Named
@SessionScoped

public class CarritoCompraController implements Serializable{
    
    @EJB
    private VentaFacadeLocal ventaEJB;
    
    @EJB
    private LineaVentaFacadeLocal lineaVentaEJB;
    
    private List<Producto> listaProductos = new ArrayList<>();
    private List<Integer> cantidades = new ArrayList<>();
    private Venta venta;
    private LineaVenta lineadeVenta;
    float precioTotal = 0;

    @PostConstruct
    public void inicio(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listaCarrito", listaProductos);
    }
    
    
    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }
    
    public void quitarProducto(Producto producto){
        List<Producto> listaAux =  (List<Producto>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaCarrito");
        
        for(Producto p:listaAux) {
            if(p.getIdProducto()==producto.getIdProducto()){
                producto = p;
                break;
            }
        }
        listaAux.remove(producto);
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listaCarrito", listaAux);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Producto eliminado del carrito"));
    }
    
    public String realizarCompra() {
        
        /*
        *   COMPRA (Venta y linea de Venta)
        *       Se crea una venta y tantas lineas de venta como productos
        */
        this.calcularCantidades();
        this.calcularPrecioTotal();
        List<Producto> listaProductosAux = (List<Producto>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaCarrito");
        
        if(!listaProductosAux.isEmpty()){
            
            //Creamos la venta
            venta = new Venta();
            Usuario usAux = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            venta.setCliente(usAux.getCliente());
            venta.setFecha(new Date());
            venta.setTotal(precioTotal);
            ventaEJB.create(venta);
            
            //Creamos las lineas de venta
            for(int i=0; i<listaProductosAux.size(); i++) {
                lineadeVenta = new LineaVenta();
                lineadeVenta.setVenta(venta);
                lineadeVenta.setProducto(listaProductosAux.get(i));
                lineadeVenta.setCantidad(cantidades.get(i));
                
                lineaVentaEJB.create(lineadeVenta);
            }


            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Compra realizada correctamente"));
            listaProductos = new ArrayList<>();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listaCarrito", listaProductos);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Compra abortada: sin productos en el carrito"));
        }
        
        return "privado/cliente/pantallaInicio.xhtml";
    }
    
    private void calcularPrecioTotal(){
        List<Producto> listaProductosAux = (List<Producto>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaCarrito");
        precioTotal = 0;
        
        for (int i=0; i<listaProductosAux.size(); i++) {
            precioTotal += listaProductosAux.get(i).getPrecio()*cantidades.get(i);
        }
    }
    
    
    private void calcularCantidades() {
        
        List<Producto> listaProductosAux = (List<Producto>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaCarrito");
        List<Producto> nuevaLista = new ArrayList<>();
        
        boolean encontrado = false;
        int posEncontrado = 0;

        for(int i = 0; i < listaProductosAux.size(); i++) {
            
            if(nuevaLista.isEmpty()){
                nuevaLista.add(listaProductosAux.get(i));
                cantidades.add(i, 1);
            } else if(nuevaLista.size() == 1) {
            	
            	if(listaProductosAux.get(i).getIdProducto() == nuevaLista.get(0).getIdProducto()) {
                    // Ya esta en la nueva lista
                    cantidades.set(0, cantidades.get(0)+1);
                } else {
                    nuevaLista.add(listaProductosAux.get(i));
                    cantidades.add(1);
                }
            	
            } else {
                for(int j = 0; j < nuevaLista.size(); j++) {
                    if(listaProductosAux.get(i).getIdProducto() == nuevaLista.get(j).getIdProducto()) {
                        // Ya esta en la nueva lista
                        encontrado = true;
                        posEncontrado = j;
                    }
                }

                if(encontrado == true) {
                        encontrado = false;
                        cantidades.set(posEncontrado, cantidades.get(posEncontrado)+1);
                } else {
                        nuevaLista.add(listaProductosAux.get(i));
                        cantidades.add(1);
                }
            } 
        }
        
        System.out.println("\n\n\nlista           cantidad");
        for (int i=0;i<nuevaLista.size();i++) {
                System.out.println(nuevaLista.get(i)+"                  "+cantidades.get(i));
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listaCarrito", nuevaLista);
    }
}
