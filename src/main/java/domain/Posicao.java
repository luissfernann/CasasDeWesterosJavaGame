package domain;
import java.util.Random;
    
public class Posicao {
 private int linha;
 private int coluna;

  
public Posicao(int linha,int  coluna) {
        this.linha = linha;
        this.coluna = coluna;
}
    public Posicao(int minLinha, int maxLinha, int tamanhoTabuleiro) {
        Random random = new random();
        this.linha = random nextInt(maxLinha - minLinha + 1) + minLinha;
        this.coluna = random.nextInt(tamanhoTabuleiro);
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

public boolean posicao (int tamanhoTabuleiro) {
    return linha








}
