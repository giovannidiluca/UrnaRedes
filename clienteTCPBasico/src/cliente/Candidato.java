/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

/**
 *
 * @author Bruno
 */
public class Candidato implements java.io.Serializable {

    int cod; // código da votação
    String nome; //nome candidato
    String partido; // partido
    int num_votos;

    public Candidato(int cod, String nome, String partido, int num_votos) {
        super();
        this.cod = cod;
        this.nome = nome;
        this.partido = partido;
        this.num_votos = num_votos;
    }

    public int getCod() {
        return cod;
    }

    public void setNum_votos() {
        this.num_votos++;
    }

    public int getNum_votos() {
        return num_votos;
    }

    public String getPartido() {
        return partido;
    }

    public String getNome() {
        return nome;
    }

}
