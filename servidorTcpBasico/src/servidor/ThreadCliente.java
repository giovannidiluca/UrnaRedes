/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author Giovanni
 */
class ThreadCliente extends Thread {

    private final Socket cliente;
    private HashMap<Integer, Integer> votos;

    public ThreadCliente(Socket cliente) {
        this.cliente = cliente;
        System.out.println("\nCliente: " + cliente.getRemoteSocketAddress().toString());
    }

    @Override
    public void run() {
        try {
            //InputStream para receber e enviar informações
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            DataOutputStream saida = new DataOutputStream(cliente.getOutputStream());

            //Recebe o opcode que descreve a operação
            String operacao = (String) entrada.readObject();
            System.out.println("Operação: " + operacao);

            if (operacao.equals("888")) {
                votos = (HashMap) entrada.readObject();
                System.out.println("Votos: " + votos.entrySet());
                
                BufferedWriter buffWrite = new BufferedWriter(new FileWriter("votos.txt"));
                
                buffWrite.append(" 13 " + ":"    +votos.get(13)+ ";");
                buffWrite.append(" 45 " + ":"    +votos.get(45)+ ";");
                buffWrite.append(" 50 " + ":"    +votos.get(50)+ ";");
                buffWrite.append(" 25 " + ":"    +votos.get(25)+ ";");
                buffWrite.append(" 10 " + ":"    +votos.get(10)); 
                buffWrite.newLine();
                buffWrite.close();
            
                
            }

            if (operacao.equals("999")) {

                //envia o arquivo para o cliente
                //Buffer de leitura dos bytes do arquivo
                byte buffer[] = new byte[512]; //Leitura do arquivo solicitado
                FileInputStream file = new FileInputStream("candidatos.txt"); //DataInputStream para processar o arquivo solicitado
                DataInputStream arq = new DataInputStream(file);
                saida.flush();
                int leitura = arq.read(buffer); //Lendo os bytes do arquivo e enviando para o socket     

                while (leitura != - 1) {
                    if (leitura != - 2) {
                        saida.write(buffer, 0, leitura);
                    }
                    leitura = arq.read(buffer);
                }
            }

            System.out.println("Cliente atendido com sucesso");

            entrada.close();
            saida.close();
            cliente.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Excecao ocorrida na thread: " + e.getMessage());
            try {
                cliente.close();
            } catch (Exception ec) {
            }
        }
    }
}
