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


}



