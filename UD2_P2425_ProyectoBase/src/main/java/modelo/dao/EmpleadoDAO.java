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
import modelo.vo.Empleado;

/**
 *
 * @author hector.garaboacasas
 */
public class EmpleadoDAO {

    public void cargarcombo(Connection conn, DefaultComboBoxModel modelocomboEmpleado) throws SQLException {
        
        String consulta="select * from empleado";
        
        Statement sentencia=conn.createStatement();
        
        ResultSet rs=sentencia.executeQuery(consulta);
        
        while(rs.next()){
            
            modelocomboEmpleado.addElement(new Empleado(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getInt(5)));
            
        }
        
            
      }
    
}
