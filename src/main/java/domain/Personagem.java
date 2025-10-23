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
        this.vidaAtual = casa.getVidaMaxima();
    }

    // Getters
    public String getNomePersonagem() { return nome; }
    public Casa getCasaPersonagem() { return casa; }
    public Posicao getPosicaoPersonagem() { return posicao; }
    public int getVidaAtual() { return vidaAtual; }

    // Getters de Posição simplificados
    public int getLinha() { return this.posicao.getLinha(); }
    public int getColuna() { return this.posicao.getColuna(); }

    // Setters
    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }

    public Posicao setPosicaoPersonagem(Posicao posicao) {
        this.posicao = posicao;
        return posicao;
    }

    // Estado de vida
    public boolean estaVivo() {
        return this.vidaAtual > 0;
    }
}