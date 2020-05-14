/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.EJB;

import com.unileon.modelo.ListaDeseos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sergio
 */
@Local
public interface ListaDeseosFacadeLocal {

    void create(ListaDeseos listaDeseos);

    void edit(ListaDeseos listaDeseos);

    void remove(ListaDeseos listaDeseos);

    ListaDeseos find(Object id);

    List<ListaDeseos> findAll();

    List<ListaDeseos> findRange(int[] range);

    int count();
    
}
