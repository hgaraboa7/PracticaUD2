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
import modelo.vo.Producto;

/**
 *
 * @author hector.garaboacasas
 */
public class ProductoDAO {

    public void cargarcombo(Connection conn, DefaultComboBoxModel modelocomboProducto) throws SQLException {
        
        String consulta="select * from productos";
        
        Statement sentencia= conn.createStatement();
        
        ResultSet rs=sentencia.executeQuery(consulta);
        while(rs.next()){
            
            modelocomboProducto.addElement(new Producto(rs.getString(1), rs.getString(2),rs.getInt(3), rs.getDouble(4)));
            
        }
        
        
        
   }
    
    
    
}
