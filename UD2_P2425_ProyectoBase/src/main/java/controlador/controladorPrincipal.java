/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.DAOFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.EmpleadoDAO;
import modelo.dao.FacturaDAO;
import modelo.dao.ProductoDAO;
import modelo.vo.Producto;
import vista.Principal;

/**
 *
 * @author Acceso a datos
 */
public class controladorPrincipal {

    public static DAOFactory mySQLFactory;
    //declara los objetos DAO

    public static Principal ventana = new Principal();

    static DefaultComboBoxModel modelocomboEmpleado = new DefaultComboBoxModel();

    static DefaultComboBoxModel modelocomboProducto = new DefaultComboBoxModel();

    static DefaultTableModel modelotabla = new DefaultTableModel();

    static ProductoDAO producto;

    static EmpleadoDAO empleado;

    static FacturaDAO factura;

   
   
            
             

    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);

        ventana.getCmbEmpleado().setModel(modelocomboEmpleado);

        ventana.getCmbProducto().setModel(modelocomboProducto);

        modelotabla = (DefaultTableModel) ventana.getTblProductos().getModel();

    }

    public static void iniciaFactory() {
        mySQLFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        //inicializa los objetos DAO

        producto = mySQLFactory.getProductoDAO();

        empleado = mySQLFactory.getEmpleadoDAO();

    }

    public static void cerrarFactory() {
        try {
            mySQLFactory.shutdown();
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargarcomboProducto() {
        Connection conn = null;

        try {
            conn = mySQLFactory.getConnection();

            producto.cargarcombo(conn, modelocomboProducto);

        } catch (SQLException ex1) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mySQLFactory.releaseConnection(conn);
        }

    }


    public static void cargarcomboEmpleado() {

        Connection conn = null;

        try {
            conn = mySQLFactory.getConnection();

            empleado.cargarcombo(conn, modelocomboEmpleado);

        } catch (SQLException ex1) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mySQLFactory.releaseConnection(conn);
        }

    }

    public static void listaañadirproducto() {
        
        if((int) ventana.getSpCantidad().getValue()==0){
            JOptionPane.showMessageDialog(null, "Faltan datos");
            return;
        }

        if (!producto.comprobarTabla(modelotabla, (Producto) ventana.getCmbProducto().getSelectedItem(), (int) ventana.getSpCantidad().getValue())) {

            producto.cargartabla(modelotabla, (Producto) ventana.getCmbProducto().getSelectedItem(),
                    (int) ventana.getSpCantidad().getValue());
        }

    }

    public static void listaretirarproducto() {
         if((int) ventana.getSpCantidad().getValue()==0){
            JOptionPane.showMessageDialog(null, "Faltan datos");
            return;
        }
         
        
        
            producto.eliminarProducto(modelotabla,(Producto) ventana.getCmbProducto().getSelectedItem(), (int) ventana.getSpCantidad().getValue());
        
    }

    public static void sumartotal() {
        
         if(((int) ventana.getSpCantidad().getValue())==0){
           
            return;
        }
        
        producto.calcularTotal(modelotabla, ventana.getTxtTotal());
        
        
    }

    public static void añadirProductoStock() {
         Connection conn = null;
         
          if((int) ventana.getSpCantidad().getValue()==0){
            JOptionPane.showMessageDialog(null, "Faltan datos");
            return;
        }

        try {
            conn = mySQLFactory.getConnection();

            producto.añadirproducto(conn, modelotabla, String.valueOf(ventana.getCmbProducto().getSelectedItem()), (int) ventana.getSpCantidad().getValue() );

        } catch (SQLException ex1) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mySQLFactory.releaseConnection(conn);
        }
        
        
 
    }
    
     public static void comprobarStock() {
  
          Connection conn = null;
          
          boolean comp;
         
          if(modelotabla.getRowCount()==0){
            JOptionPane.showMessageDialog(null, "No hay productos que facturar");
            return;
        }

        try {
            conn = mySQLFactory.getConnection();

           comp=producto.comprobarStock(conn, modelotabla);
           
           
            //
          //realizar  factura y updates
          //
           if(comp){
               System.out.println("funciona suficiente");
           }else{  
               System.out.println("funciona insuficiente");
           }

        } catch (SQLException ex1) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mySQLFactory.releaseConnection(conn);
        }
        
         
         
         
     }
    
}
