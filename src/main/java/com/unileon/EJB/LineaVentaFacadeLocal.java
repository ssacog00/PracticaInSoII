/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.EJB;

import com.unileon.modelo.LineaVenta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sergio
 */
@Local
public interface LineaVentaFacadeLocal {

    void create(LineaVenta lineaVenta);

    void edit(LineaVenta lineaVenta);

    void remove(LineaVenta lineaVenta);

    LineaVenta find(Object id);

    List<LineaVenta> findAll();

    List<LineaVenta> findRange(int[] range);

    int count();
    
}
