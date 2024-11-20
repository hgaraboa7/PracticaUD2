/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.DAOFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.ClienteDAO;
import modelo.dao.EmpleadoDAO;
import modelo.dao.FacturaDAO;
import modelo.dao.ProductoDAO;
import modelo.vo.Empleado;
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
    
    static ClienteDAO cliente;

   
   
            
             

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
        cliente=mySQLFactory.getClienteDAO();

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
            JOptionPane.showMessageDialog(null, "la cantidad debe ser superior a 0");
            return;
        }

        if (!producto.comprobarTabla(modelotabla, (Producto) ventana.getCmbProducto().getSelectedItem(), (int) ventana.getSpCantidad().getValue())) {

            producto.cargartabla(modelotabla, (Producto) ventana.getCmbProducto().getSelectedItem(),
                    (int) ventana.getSpCantidad().getValue());
        }

    }

    public static void listaretirarproducto() {
         if((int) ventana.getSpCantidad().getValue()==0){
          //  JOptionPane.showMessageDialog(null, "Faltan datos");
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

    
    
    public static void comprobarStock() {  
          Connection conn = null;          
          boolean comp;          
          Savepoint sp=null;         
          if(modelotabla.getRowCount()==0){
            JOptionPane.showMessageDialog(null, "No hay productos que facturar");
            return;
        }
        try {
            conn = mySQLFactory.getConnection();
           comp=producto.comprobarStock(conn, modelotabla); 
           empleado.aumentaroperativa(conn,(Empleado)ventana.getCmbEmpleado().getSelectedItem());
           sp=conn.setSavepoint();
           
            //
          //realizar  factura y updates
          //
           if(comp){
               System.out.println("stock suficiente");
               producto.actualizarStock(conn, modelotabla);
               empleado.incentivar(conn, Double.valueOf(ventana.getTxtTotal().getText()), (Empleado)ventana.getCmbEmpleado().getSelectedItem());
           }else{  
               System.out.println("stock insuficiente");
           }
        } catch (SQLException ex1) {
              try {
                  conn.rollback(sp);
              } catch (SQLException ex) {
                  Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
              }
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (Exception ex) {
              try {
                  conn.rollback(sp);
              } catch (SQLException ex1) {
                  Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
              }
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
              try {
                  conn.commit();
              } catch (SQLException ex) {
                  Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
              }
            mySQLFactory.releaseConnection(conn);
            
        }
        
         
         
         
     }

    public static void mostrarCliente() {
        Connection conn=null;
        
        
        
        
        
    }
    
}
