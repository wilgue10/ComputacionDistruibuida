/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base;

/**
 *
 * @author Wilmer
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class ConectarMostrar {
    private static Connection conexion = null;
    private static String bd = "distribuidas"; // Nombre de BD.
    private static String user = "root"; // Usuario de BD.
    private static String password = "root"; // Password de BD.
    // Driver para MySQL en este caso.
    private static String driver = "com.mysql.jdbc.Driver";
    // Ruta del servidor.
    private static String server = "jdbc:mysql://localhost:3306/" + bd;
 
    public  String consultarLista(String cadena)  {
 
        System.out.println("INICIO DE EJECUCIÓN.");
        conectar();
        Statement st = conexion();
     String  usuarioContrasena="";
        ResultSet rs = consultaQuery(st, cadena);
        if (rs != null) {
            System.out.println("El listado de persona es el siguiente:");
            try {
                while (rs.next()) {
                    String nombre=rs.getObject("NOMBREUSUARIO").toString();
                    String contrasena=rs.getObject("CONTRASENA").toString();
                     usuarioContrasena=nombre+" "+contrasena;
                     break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConectarMostrar.class.getName()).log(Level.SEVERE, null, ex);
            }
            cerrar(rs);
        }
        cerrar(st);
        System.out.println("FIN DE EJECUCIÓN.");
        return usuarioContrasena;
    }
 
    /**
     * Método neecesario para conectarse al Driver y poder usar MySQL.
     */
    public static void conectar() {
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(server, user, password);
        } catch (Exception e) {
            System.out.println("Error: Imposible realizar la conexion a BD.");
            e.printStackTrace();
        }
    }
 
    /**
     * Método para establecer la conexión con la base de datos.
     *
     * @return
     */
    private static Statement conexion() {
        Statement st = null;
        try {
            st = conexion.createStatement();
        } catch (SQLException e) {
            System.out.println("Error: Conexión incorrecta.");
            e.printStackTrace();
        }
        return st;
    }
 
    /**
     * Método para realizar consultas del tipo: SELECT * FROM tabla WHERE..."
     *
     * @param st
     * @param cadena La consulta en concreto
     * @return
     */
    private static ResultSet consultaQuery(Statement st, String cadena) {
        ResultSet rs = null;
        try {
            rs = st.executeQuery(cadena);
        } catch (SQLException e) {
            System.out.println("Error con: " + cadena);
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }
 
    /**
     * Método para realizar consultas de actualización, creación o eliminación.
     *
     * @param st
     * @param cadena La consulta en concreto
     * @return
     */
    private static int consultaActualiza(Statement st, String cadena) {
        int rs = -1;
        try {
            rs = st.executeUpdate(cadena);
        } catch (SQLException e) {
            System.out.println("Error con: " + cadena);
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }
 
    /**
     * Método para cerrar la consula
     *
     * @param rs
     */
    private static void cerrar(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                System.out.print("Error: No es posible cerrar la consulta.");
            }
        }
    }
 
    /**
     * Método para cerrar la conexión.
     *
     * @param st
     */
    private static void cerrar(java.sql.Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (Exception e) {
                System.out.print("Error: No es posible cerrar la conexión.");
            }
        }
    }

   
}