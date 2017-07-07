/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giovanni
 */
public class ClienteTCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int sair = 0;
        HashMap<Integer, Integer> votos = new HashMap<>();
        String opcao = "null";
        
        while (sair == 0) {
            // TODO code application logic here
            //Cria o Socket para buscar o arquivo no servidor
            try {
                Socket rec = new Socket("cosmos.lasdpc.icmc.usp.br", 40006);

                ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
                DataInputStream entrada = new DataInputStream(rec.getInputStream());// DataInputStream para processar os bytes recebidos

                //888
                if (opcao.equals("888")) {
                    saida.writeObject(opcao);
                    saida.writeObject(votos);
                    System.out.println("Dados enviados ao servidor");
                    sair = 1;
                    if (sair==1) break;

                }
                try {
                    Urna urna = new Urna();
                    FileOutputStream sarq = new FileOutputStream("candidatos.txt");//FileOuputStream para salvar o arquivo recebido  

                    do {
                        opcao = urna.getOpcao();

                        Scanner leituraTeclado = new Scanner(System.in);
                        int candidato;

                        if (opcao.equals("1")) {
                            System.out.print("Digite o número do candidato: ");
                            candidato = leituraTeclado.nextInt();

                            if (votos.containsKey(candidato)) {
                                int votoAtual = votos.get(candidato);
                                votoAtual++;
                                votos.replace(candidato, votoAtual);
                            } else {
                                votos.put(candidato, 1);
                            }

                            System.out.println(" \n Candidato: " + candidato + ", Votos: " + votos.get(candidato));
                            urna.setVotosComputados(true);
                        }
                        if (opcao.equals("2")) {
                            System.out.println("Voto Branco");
                        }
                        if (opcao.equals("3")) {
                            System.out.println("Voto Nulo");
                        }

                        if (opcao.equals("999")) {
                            saida.writeObject(opcao);
                            urna.setListaCarregada(true);
                            //recebe os objetos do servidor
                            byte[] br = new byte[512];
                            int leitura = entrada.read(br);
                            String msgDecode = new String(br, "UTF-8");
                            System.out.println();
                            System.out.println("---------------------------\n" + msgDecode
                                    + "\n---------------------------");

                            while (leitura != -1) {
                                if (leitura != -2) {
                                    sarq.write(br, 0, leitura);
                                }
                                leitura = entrada.read(br);
                            }

                            sarq.close();
                        }

                    } while (opcao.equals("888") != true);

                    saida.close();
                    entrada.close();
                    rec.close();
                } catch (IOException ex) {
                    Logger.getLogger(ClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException e) {
                System.out.println("Exceção:" + e.getMessage());
            }
        }
        System.out.println("Urna Finalizada");
    }

}
