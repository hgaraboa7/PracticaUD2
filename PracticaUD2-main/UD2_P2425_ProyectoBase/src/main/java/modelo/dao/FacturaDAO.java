/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import modelo.vo.Empleado;

/**
 *
 * @author hector.garaboacasas
 */
public class FacturaDAO {

    public void generar(Connection conn, String numFactura, String idCliente, Empleado empleado, Date date, boolean cobrada, double iva) throws SQLException {
        String consulta="Insert into factura(numfactura, idcliente, idempleado, fecha, cobrada, iva)values(?,?,?,?,?,?)";
        PreparedStatement sentencia=conn.prepareStatement(consulta);
        sentencia.setString(1, numFactura);
        sentencia.setString(2, idCliente);
        sentencia.setString(3, empleado.getIdempleado());
        sentencia.setDate(4, date);
        sentencia.setBoolean(5, cobrada);
        sentencia.setDouble(6, iva);
        sentencia.executeUpdate();
        
    }

   
    
    
}
