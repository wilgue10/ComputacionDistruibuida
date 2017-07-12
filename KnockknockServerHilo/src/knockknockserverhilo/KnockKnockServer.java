package knockknockserverhilo;

/*
 * Copyright (c) 1995, 2014, Oracle and/or its affiliates. All rights reserved.
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
import java.util.concurrent.ExecutorService;

public class KnockKnockServer {

    public static Integer contadorClientes = 0;

    public static void main(String[] args) throws IOException, InterruptedException {

//        if (args.length != 1) {
 //       System.err.println("Usage: java KnockKnockServer <port number>");
//            System.exit(1);
//        }
/**
 * conexion a la base
 */
       
        
       
        int portNumber = 4445;// Integer.parseInt(args[0]);
        boolean listening = true;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            contadorClientes++;
            while (listening)   {
                System.out.println("Contador clientes en el servidor:" + contadorClientes);
                if (contadorClientes <= 10) {
                    new Conexion(serverSocket.accept(), contadorClientes, new File("log" + contadorClientes + ".txt")).start();
                    incrementCount();
                } else {
                    while (contadorClientes > 10) {
//                        serverSocket.
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }

    public static synchronized void incrementCount() {
        contadorClientes++;
    }
}
