package domain;

public class Personagem {

    private final String nome;
    private final Casa casa;
    private Posicao posicao;
    private int vidaAtual;

    public Personagem(String nome, Casa casa, Posicao posicao) {
        this.nome = nome;
        this.casa = casa;
        this.posicao = posicao;
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

    public void setPosicaoPersonagem(Posicao posicao) {
        this.posicao = posicao;
    }

    public int getLinha() {
        return this.posicao.getLinha();
    }

    public int getColuna() {
        return this.posicao.getColuna();
    }

    public void setPosicao(int linha, int coluna) {
        this.posicao.setPosicao(linha, coluna);
    }

    public char getICasa() {
        return this.casa.getNome().charAt(0);
    }

    public int getAlcanceMaximo() {
        return this.casa.getAlcanceMaximo();
    }

    public boolean isVivo() {
        return this.vidaAtual > 0;
    }

    // Ações
    public void setVidaAtual(int novaVida) {
        this.vidaAtual = novaVida;
    }

    // Ações
    public boolean estaVivo() { //
        return this.vidaAtual > 0;
    }
// ...


}



