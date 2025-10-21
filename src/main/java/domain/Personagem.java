package domain;

public class Personagem {

    private String nome;
    private Casa casa;
    private Posicao posicao;
    private int vidaAtual;

    public Personagem(String nome, Casa casa, Posicao posicao) {
        this.nome = nome;
        this.casa = casa;
        this.posicao = posicao;
        // a posição é passada, apesar de que ela poderá ser inicialmente definida pelo tabuleiro
        this.vidaAtual = casa.getVidaMaxima();
    }

    public String getNomePersonagem() {
        return nome;
    }

    public Casa getCasaPersonagem() {
        return casa;
    }

    public Posicao getPosicaoPersonagem() {
        return posicao;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public Posicao setPosicaoPersonagem(Posicao posicao) {
        this.posicao = posicao;
        return posicao;
    }


}