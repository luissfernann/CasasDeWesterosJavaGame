package app;

import domain.Personagem;
import domain.Tabuleiro;


public class Personagem {   //classe personagem
private String nome;
private String casa;
private int coluna;
private int linha;
private int  ataqueBase;
private int defesaBase;
private int vidaAtual;
private int vidaMax;
private double modificadorOfensivo;
}

//Falta realizar o construtor do personagem

public Personagem(String nome, String casa, int linha, int coluna){

    this.nome = nome;
    this.casa = casa;
    this.linha = linha;
    this.coluna = coluna;
    this.ataqueBase = 20;
    this.defesaBase = 10;
    this.vidaMax = 50;
    this.modificadorOfensivo=1.0;

    switch (casa.toUpperCase()){
        case "STARK":
            this.vidaMax = 60;
            break;

        case "LANNISTER":
            this.modificadorOfensivo = 1.15;
            break;


        case "TARGARYEN":
            this.vidaMax = 45;
            break;
    }
this.vidaAtual = this.vidaMax;

}
public String getNome() {
    return nome;
}
public int getICasa() {
return casa.CharAt(0); // não entendi como fazer esse get e o gemini deu essa ideia confirmar ainda

}

public int getLinha() {
    return linha;
}

public int getColuna() {
    return coluna;

}

public void setPosicao(int novalinha, int novaColuna) {

    this.linha = novalinha;
    this.coluna= novaColuna;


}

public class Tabuleiro{

    private Personagem[][] grade;
    private final  int TAMANHO = 10;


public Tabuleiro(){

    this.grade = new Personagem[TAMANHO][TAMANHO];


}
public void adicionarP(Personagem p , int linha, int coluna){}



public void posicicaoPInicial(Personagem[] time1, Personagem[] time2){

}

private boolean moviPersonagem(Personagem p, int novaLinha, int novaColuna){
if(novaLinha<0 || novaLinha>= TAMANHO || novaColuna<0||novaColuna>=TAMANHO){
    //Pode colocar uma impressao falando sobre o movimento ser invalido
    return false;
}
if (grade[novaLinha][novaColuna] != null){
    //Pode colocar uma impressao falando sobre o movimento ser invalido
    return false;
}

//Fazer um nó para atualizar o personagem na grade
grade[personagem.getLinha()][personagem.getColuna()] = null;
personagem.setPosicao(novaColuna, novaLinha);
grade [novaLinha][novaColuna] = p;
return true;
//pode colocar uma impressao avisando que se movimentou para posicao escolhida


}
//Falta fazer uma booleana para mover os personagens


    public void ImprimeTabuleiro(){
    System.out.println(" ----- Tabuleiro Atual -----");
    for(int i = 0; i < TAMANHO; i++){
      for(int j = 0; j < TAMANHO; j++){
         if (grade[i][j] != null){
            System.out.print(" [ " + grade[i][j].getICasa() + " ] ");

         }
         else  {
            System.out.print(" [ " + " ] ");
         }
System.out.println();
      }

      System.out.println("-----------------------\n");


    }

    }



public class Jogo {
    public static void main(String[] args) {

       Tabuleiro tabuleiro = new tabuleiro();


    }
}
}
