/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.controller;


import com.unileon.EJB.LineaVentaFacadeLocal;
import com.unileon.EJB.ListaDeseosFacadeLocal;
import com.unileon.EJB.ProductoFacadeLocal;
import com.unileon.modelo.LineaVenta;
import com.unileon.modelo.ListaDeseos;
import com.unileon.modelo.Producto;
import com.unileon.modelo.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Sergio
 */
@Named
@ViewScoped

public class ProductoController implements Serializable{
    
    @EJB
    private ProductoFacadeLocal productoEJB;
    
    @EJB
    private LineaVentaFacadeLocal lineaVentaEJB;
    
    @EJB
    private ListaDeseosFacadeLocal deseosEJB;
    
    private List<Producto> listaProductos;
    private Producto producto;
    private Usuario usuario;
    private String auxPrecio;

    
    @PostConstruct
    public void init() {
        listaProductos = productoEJB.findAll(); 
        producto = new Producto();
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getAuxPrecio() {
        return auxPrecio;
    }

    public void setAuxPrecio(String auxPrecio) {
        this.auxPrecio = auxPrecio;
    }
    
    

    public String create() {
        try {
            
            if(this.comprobarCampos()) {
                
                producto.setEncargado(usuario.getEncargado());
                
                productoEJB.create(producto);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Producto creado correctamente"));
                
                return "/privado/encargado/pantallaInicio.xhtml";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos introducidos incorrectos"));
                return "";
            }
            
        } catch(Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
        
        return "/privado/encargado/aniadirProducto.xhtml";
    }
    
    private boolean comprobarCampos(){
        if(producto.getNombre().length() > 50) {
            producto.setNombre(producto.getNombre().substring(0, 49));
        }
        
        if(producto.getDescripcion().length() > 500) {
            producto.setDescripcion(producto.getDescripcion().substring(0, 499));
        }
        
        try{
            producto.setPrecio(Float.parseFloat(auxPrecio)); // convirtiendo la cadena
            
            if(producto.getPrecio() <= 0){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "El precio no puede ser negativo"));
                return false;
            }
        }catch(NumberFormatException e){
            // captura la exepcion, en caso de que no sea un formato de Float
            //valido y envia un mensaje.
            return false;
        }
        
        return true;
    }
    
    public String eliminarProducto(Producto producto){
        
        if(comprobarAparicionesProducto(producto)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "El producto no ha podido ser borrado"));
            System.out.println("\n\n\nNO SE HA PODIDO BORRAR\n\n");
            return "";
        } else {
            try {
                for(Producto p:listaProductos){
                    if(p.getIdProducto() == producto.getIdProducto()){
                        producto = p;
                        break;
                    }
                }
                productoEJB.remove(producto);
                this.actualizarTabla();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Producto eliminado"));
                return "/privado/encargado/pantallaInicio.xthml";

            } catch(Exception e) {
                System.out.println("Error al eliminar categoria: "+e.getMessage());
            }
        }
        
        return "";
    } 
    
    private void actualizarTabla() {
        listaProductos = productoEJB.findAll();
    }
    
    public String modificarProducto() {
        
        try {
            
            String nuevoNombre = producto.getNombre();
            String nuevaDescripcion = producto.getDescripcion();
            Float nuevoPrecio = producto.getPrecio();

            for(Producto p:listaProductos){
                if(p.getIdProducto() == producto.getIdProducto()){
                    producto = p;
                    producto.setNombre(nuevoNombre);
                    producto.setDescripcion(nuevaDescripcion);
                    producto.setPrecio(nuevoPrecio);
                    break;
                }
            }
            productoEJB.edit(producto);
            this.actualizarTabla();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Producto modificado con exito"));

            return "/privado/encargado/pantallaInicio.xhtml";
               
        } catch(Exception e) {
            System.out.println("Error al modificar producto: "+e.getMessage());
            return "";
        }
    }
    
    public String eliminarProducto2(){
        
        if(comprobarAparicionesProducto(producto)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "El producto no ha podido ser borrado"));
            System.out.println("\n\n\nNO SE HA PODIDO BORRAR\n\n");
            return "";
        } else {
            try {
                for(Producto p:listaProductos){
                    if(p.getIdProducto() == producto.getIdProducto()){
                        producto = p;
                        break;
                    }
                }
                productoEJB.remove(producto);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Producto eliminado con exito"));

                return "/privado/encargado/pantallaInicio.xhtml";
            } catch(Exception e) {
                System.out.println("Error al eliminar producto: "+e.getMessage());
                return "";
            }
        }
    }
    
    public boolean comprobarAparicionesProducto(Producto producto) {
        
        List<LineaVenta> lineasDeVenta = lineaVentaEJB.findAll();
        List<ListaDeseos> deseos = deseosEJB.findAll();
        
        boolean encontrado = false;
        
        for(LineaVenta lv:lineasDeVenta) {
            if(lv.getProducto().getIdProducto() == producto.getIdProducto()) {
                encontrado = true;
                System.out.println("\n\n\nPRODUCTO ENCONTRADO EN VENTA\n\n");
            }
        }
        
        for(ListaDeseos ld:deseos) {
            if(ld.getProducto().getIdProducto() == producto.getIdProducto()){
                encontrado = true;
                System.out.println("\n\n\nPRODUCTO ENCONTRADO EN DESEOS\n\n");
            }
        }
        
        return encontrado;
    }
}
