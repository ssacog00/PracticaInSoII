/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.controller;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Sergio
 */
@Named
@ViewScoped

public class PlantillaControler implements Serializable{
    
    public void verificarYMostrar() throws IOException{
        
        if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario") == null){
            FacesContext.getCurrentInstance().getExternalContext().redirect("../../permisosInsuficientes.softwareII");
        }
        
    }
}
