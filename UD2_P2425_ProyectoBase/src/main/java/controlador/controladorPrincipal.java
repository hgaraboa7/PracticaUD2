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

    public static void cargarTabla() {
        Connection conn = null;

        try {
            conn = mySQLFactory.getConnection();

            factura.cargartabla(conn);

        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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

        if (!producto.comprobarTabla(modelotabla, (Producto) ventana.getCmbProducto().getSelectedItem(), (int) ventana.getSpCantidad().getValue())) {

            producto.cargartabla(modelotabla, (Producto) ventana.getCmbProducto().getSelectedItem(),
                    (int) ventana.getSpCantidad().getValue());
        }

    }

    public static void listaretirarproducto() {
        if (producto.comprobarTabla(modelotabla, (Producto) ventana.getCmbProducto().getSelectedItem(), (int) ventana.getSpCantidad().getValue())) {
            producto.eliminarProducto(modelotabla,(Producto) ventana.getCmbProducto().getSelectedItem(), (int) ventana.getSpCantidad().getValue());
        }

    }
}
