/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.util.Scanner;

/**
 *
 * @author Giovanni
 */
public class Urna {

    boolean listaCarregada;
    boolean votosComputados;
    int i;
    String opcao;

    public Urna() {

    }

    public String getOpcao() {

        if (listaCarregada) {
            System.out.print("\n1 - Votar \n"
                           + "2 - Votar Branco \n"
                           + "3 - Votar Nulo \n");
        } else {
            System.out.print("999 - Carregar lista de candidatos\n");
        }

        if (votosComputados) {
            System.out.print("888 - Enviar votos ao servidor \n");
        }

        System.out.print("Digite a opcao desejada: ");

        Scanner leitura = new Scanner(System.in);
        opcao = leitura.nextLine();

        return opcao;

    }

    public void setListaCarregada(boolean listaCarregada) {
        this.listaCarregada = listaCarregada;
    }

    public void setVotosComputados(boolean votosComputados) {
        this.votosComputados = votosComputados;
    }

}
