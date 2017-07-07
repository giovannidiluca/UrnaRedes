/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Giovanni
 */
public class ServidorTcp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    try {
      // Instancia o ServerSocket ouvindo a porta 12345
      ServerSocket servidor = new ServerSocket(40006);
      System.out.println("Servidor ouvindo a porta 40006");
      
      while(true) {
        // o método accept() bloqueia a execução até que
        // o servidor receba um pedido de conexão
        Socket cliente = servidor.accept();
             //Inicia thread do cliente
             new ThreadCliente(cliente).start();
      }  
    }   
    catch(Exception e) {
       System.out.println("Erro: " + e.getMessage());
    }
  }     
}