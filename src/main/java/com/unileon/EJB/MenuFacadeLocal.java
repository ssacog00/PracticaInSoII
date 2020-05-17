/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.EJB;

import com.unileon.modelo.Menu;
import com.unileon.modelo.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sergio
 */
@Local
public interface MenuFacadeLocal {

    void create(Menu menu);

    void edit(Menu menu);

    void remove(Menu menu);

    Menu find(Object id);

    List<Menu> findAll();

    List<Menu> findRange(int[] range);

    int count();
    
    public List<Menu> obtenerMenusUsuario(Usuario us);
    
}
