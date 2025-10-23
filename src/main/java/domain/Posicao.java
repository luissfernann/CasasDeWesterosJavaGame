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
	return linha >= 0 && linha < tamanhoTabuleiro && coluna >= 0 && coluna < tamanhoTabuleiro;
	}

public bolean igual (Posicao diferente) {
		int diffLinha = Math.abs(this.linha - outra.linha);
		int diffColuna = Math.abs(this.coluna - outra.coluna);
		return Math.max(diffLinha, diffColuna);
	}

	@Override
	public String toString() {
		return "(" + linha + ", " + coluna + ")";
	}
}

//tentativa de exemplo
int TAMANHO_TABULEIRO = 10;
// Time 1
Personagem luis = new Personagem("luis Stark", casaStark, new Posicao(0, 3, TAMANHO_TABULEIRO));

// Time 2 
Personagem gabriel = new Personagem("gabriel Lannister", casaLannister, new Posicao(6, 9, TAMANHO_TABULEIRO));

System.out.println(arya.getPosicaoPersonagem());  // ex: (2, 5)
System.out.println(cersei.getPosicaoPersonagem()); // ex: (8, 1)



}
