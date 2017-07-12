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
    public boolean insertarPassword(String cadena){
        boolean respuesta=false;
         conectar();
        Statement st = conexion();
        try {
              consultaActualiza(st, cadena);
              respuesta=true;
        } catch (Exception e) {
            e.printStackTrace();
        }      
        return respuesta;
    }
    public  Boolean consultarUsuario(String user)  {
        boolean res=false;
        String query="select nombreusuario from persona where nombreusuario='"+user+"'";
       
        conectar();
        Statement st = conexion();
        ResultSet rs = consultaQuery(st, query);
        if (rs != null) {
            res=true;               
            cerrar(rs);
        }
        cerrar(st);
        return res;
    }
    
  public  Boolean consultarContrasena(String contrasena)  {
        boolean res=false;
        String query="select contrasena from persona where contrasena='"+contrasena+"'";
        conectar();
        Statement st = conexion();
        ResultSet rs = consultaQuery(st, query);
        if (rs != null) {
            res=true;               
            cerrar(rs);
        }
        cerrar(st);
        return res;
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