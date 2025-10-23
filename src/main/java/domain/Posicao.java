package domain;
import java.util.Random;

public class Posicao {
    private int linha;
    private int coluna;

    // Construtor principal
    public Posicao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    // Construtor para posição aleatória (corrigido: new Random())
    public Posicao(int minLinha, int maxLinha, int tamanhoTabuleiro) {
        Random random = new Random();
        this.linha = random.nextInt(maxLinha - minLinha + 1) + minLinha;
        this.coluna = random.nextInt(tamanhoTabuleiro);
    }

    // Getters
    public int getLinha() { return linha; }
    public int getColuna() { return coluna; }

    // Setter (corrigido)
    public void setPosicao(int novaLinha, int novaColuna) {
        this.linha = novaLinha;
        this.coluna= novaColuna;
    }

    // Método de validação
    public boolean isValida(int tamanhoTabuleiro) {
        return linha >= 0 && linha < tamanhoTabuleiro &&
                coluna >= 0 && coluna < tamanhoTabuleiro;
    }
}