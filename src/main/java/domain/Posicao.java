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
        Random random = new Random();
        this.linha = random.nextInt(maxLinha - minLinha + 1) + minLinha;
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

public boolean isPosicaoValida(int tamanhoTabuleiro) {
	return linha >= 0 && linha < tamanhoTabuleiro && coluna >= 0 && coluna < tamanhoTabuleiro;
	}

public int CalcularDistancia(Posicao outra) {
    int diffLinha = Math.abs(this.linha - outra.getLinha());
    int diffColuna = Math.abs(this.coluna - outra.getColuna());
    return Math.max(diffLinha, diffColuna);
	}

	@Override
	public String toString() {
		return "(" + linha + ", " + coluna + ")";
	}
}



