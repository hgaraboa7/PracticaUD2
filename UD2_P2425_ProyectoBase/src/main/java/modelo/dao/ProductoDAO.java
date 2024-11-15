/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Producto;

/**
 *
 * @author hector.garaboacasas
 */
public class ProductoDAO {

    public void cargarcombo(Connection conn, DefaultComboBoxModel modelocomboProducto) throws SQLException {

        String consulta = "select * from productos";

        Statement sentencia = conn.createStatement();

        ResultSet rs = sentencia.executeQuery(consulta);
        while (rs.next()) {

            modelocomboProducto.addElement(new Producto(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDouble(4)));

        }

    }

    public void cargartabla(DefaultTableModel modelotabla, Producto producto, int cantidad) {

        modelotabla.setRowCount(modelotabla.getRowCount() + 1);

        modelotabla.setValueAt(producto.getNomproducto(), modelotabla.getRowCount() - 1, 0);
        modelotabla.setValueAt(cantidad, modelotabla.getRowCount() - 1, 1);
        modelotabla.setValueAt(producto.getPrecio() * cantidad, modelotabla.getRowCount() - 1, 2);

    }

    public boolean comprobarTabla(DefaultTableModel modelotabla, Producto producto, int cantidad) {

        
        //revisar este metodo no me gusta
        boolean comp = false;

        for (int i = 0; i < modelotabla.getRowCount(); i++) {

            String nombretabla = (String) modelotabla.getValueAt(i, 0);

            if (nombretabla == producto.getNomproducto()) {

                modelotabla.setValueAt(((int) modelotabla.getValueAt(i, 1) + cantidad), i, 1);

                modelotabla.setValueAt(((int) modelotabla.getValueAt(i, 1) * producto.getPrecio()), i, 2);

                comp = true;

            } else if (nombretabla != producto.getNomproducto()) {

                comp = false;

            }

        }

        return comp;

    }

    public void eliminarProducto(DefaultTableModel modelotabla, Producto producto, int cantidad) {
    
        
        //revisar este metodo no me gusta
        
        
         for (int i = 0; i < modelotabla.getRowCount(); i++) {

            String nombretabla = (String) modelotabla.getValueAt(i, 0);

            if (nombretabla == producto.getNomproducto()) {
                
                 modelotabla.setValueAt(((int) modelotabla.getValueAt(i, 1) - cantidad), i, 1);

                modelotabla.setValueAt(((int) modelotabla.getValueAt(i, 1) * producto.getPrecio()), i, 2);
                
                
            }
        
        
        
    }
    }

}
