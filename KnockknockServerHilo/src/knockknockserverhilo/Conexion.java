/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knockknockserverhilo;

/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import Base.ConectarMostrar;
import java.net.*;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Conexion extends Thread {

    private Socket socket = null;
    private Integer cliente;
    private File file;
    private ArrayList <String> datosEntrada=new ArrayList<String>();
    public Conexion(Socket socket, Integer cliente, File file) {
        super("Conexion");
        this.socket = socket;
        this.cliente = cliente;
        this.file = file;
    }

    private static String printUsage(String mensaje) {
        String resultados = "";
        for (int i = 0; i < mensaje.length(); i++) {
            int dato = mensaje.codePointAt(i);
            dato += 4;
            resultados += String.valueOf((char) dato);
        }
        return resultados;
    }

    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));) {
            FileWriter bufferedWriter;
//            File fileUsuarios = new File("Usuarios.txt");
//            FileReader fileReader = new FileReader(fileUsuarios);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String ip = "";
//            System.out.println("archivo:"+bufferedReader.readLine());
//            System.out.println("Socket:"+this.socket.getInetAddress().toString());
//            if ((ip = bufferedReader.readLine()) != null && ip.equals(this.socket.getInetAddress().toString())) {
                
                bufferedWriter = new FileWriter(file);
                PrintWriter printWriter = new PrintWriter(bufferedWriter);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date iniciaConexion = Calendar.getInstance().getTime();
                System.out.println("Hola " + this.cliente + " Ip: " + this.socket.getInetAddress() + " Tiempo " + dateFormat.format(iniciaConexion));
                String datos = ("Cliente " + this.cliente + " Ip: " + this.socket.getInetAddress() + " Tiempo " + dateFormat.format(iniciaConexion) + "\t\n");

                String inputLine, outputLine;
                KnockKnockProtocol kkp = new KnockKnockProtocol();
                outputLine = kkp.processInput(null);
                out.println(outputLine);
                int cont=0;
                String pass = null;
                String user = null;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                    if(cont == 0) {
                        user = inputLine;
                    }
                    if (cont==1) {
                        pass=getHash(inputLine, "MD5");
                        System.out.println("pas: " + pass);
                    }
                    
                    cont++;
                    datosEntrada.add(datos);
                    datos += inputLine;
                    datos += this.printUsage(inputLine);
                    outputLine = kkp.processInput(inputLine);
                    out.println(outputLine);
                    if (outputLine.equals("Bye")) {
                        break;
                    }
                }
                
                ConectarMostrar con = new ConectarMostrar();
                if(con.consultarUsuario(user) && con.consultarContrasena(pass)) {
                    System.out.println("acceso concedido");
                } else {
                    con.crearPersona(user, pass);
                }
                
                
                for (int i = 0; i < 3; i++) {
                 System.out.println(datosEntrada.get(i));
                  
                }
                printWriter.println(datos);
                bufferedWriter.close();
                printWriter.close();
                Date finConexion = Calendar.getInstance().getTime();
                Long tiempo = finConexion.getTime() - iniciaConexion.getTime();
                bufferedWriter = new FileWriter(file, true);
                PrintWriter printWriter2 = new PrintWriter(bufferedWriter);
                printWriter.println("Conexion Cliente " + this.cliente + " duro: " + tiempo / 60);
                bufferedWriter.close();
                printWriter.close();
                printWriter2.close();
//            }else
//            System.out.println("no entro");
            
           // fileReader.close();
            //bufferedReader.close();
            socket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
