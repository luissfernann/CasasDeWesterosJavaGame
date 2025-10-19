package domain;

public class Personagem {

    /*

    Os personagens herdam algumas características com valores fixos,
    sendo que esses valores fixos serão pegos la da classe Casa.
    Por isso, eu também devo retornar os atributos da classe Casa: get; → vai la na classe casa, e retorna tudo

     */

    // características fixas desse personagem: que independem da sua casa
    private String nome;
    private Casa casa;

    // Idealização: criação de uma classe posição para não ter conflitos
        //  entre o controle de posição gerado pelo tabuleiro, e uma classe personagem

    private Posicao posicao;
    private int vidaAtual;



    // construindo um personagem com essas características

    public Personagem(String nome, Casa casa,Posicao posicao) {
        this.nome = nome;
        this.casa = casa;
        this.posicao = posicao;
        // a posição é passada, apesar de que ela poderá ser inicialmente definida pelo tabuleiro
        this.vidaAtual = casa.getVidaMaxima();
    }


    // Métodos observados para a classe personagem:
        // métodos para retornar dados privados
    public String getNomePersonagem(){
        return nome;
    }
    // lá da classe casa, eu retorno getCasa CASA personagem DESSA CLASSE AQUI
    public Casa getCasaPersonagem(){
        return casa;
    }
    public Posicao getPosicaoPersonagem(){
        return posicao;
    }
    public int getVidaAtual(){
        return vidaAtual;
    }
        // Métodos que alterarão dados privados: isso será feito por outra classe
        // Tabuleiro: no seu cálculo, ela irá alterar a posição do personagem.

    public Posicao setPosicaoPersonagem(Posicao posicao){
        this.posicao = posicao;
        return posicao;
    }










}
