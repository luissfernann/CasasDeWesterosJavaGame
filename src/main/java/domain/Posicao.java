package domain;
import java.util.Random;
import java.util.Scanner;

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
    }public int getColuna() {
        return coluna;
    }
    public void setPosicao(int novaLinha, int novaColuna) {
        this.linha = novaLinha;
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

 class Movimentacao {

    private final int tamanho;
    private final Personagem[][] grade;

    public Movimentacao(int tamanho, Personagem[][] grade) {
        this.tamanho = tamanho;
        this.grade = grade;
    }

    public boolean mover(Personagem personagem) {
        Scanner scanner = new Scanner(System.in);
        int linhaAtual = personagem.getPosicaoPersonagem().getLinha();
        int colunaAtual = personagem.getPosicaoPersonagem().getColuna();

        System.out.println("\n--- Movimento de " + personagem.getNomePersonagem() + " ---");
        System.out.println("Digite a direção (W = cima, S = baixo, A = esquerda, D = direita, Q = diagonal superior esquerda, E = diagonal superior direita, Z = diagonal inferior esquerda, C = diagonal inferior direita):");
        String direcao = scanner.next().toUpperCase();

        int novaLinha = linhaAtual;
        int novaColuna = colunaAtual;

        switch (direcao) {
            case "W": novaLinha--; break; // cima
            case "S": novaLinha++; break; // baixo
            case "A": novaColuna--; break; // esquerda
            case "D": novaColuna++; break; // direita
            case "Q": novaLinha--; novaColuna--; break; // diagonal sup. esquerda
            case "E": novaLinha--; novaColuna++; break; // diagonal sup. direita
            case "Z": novaLinha++; novaColuna--; break; // diagonal inf. esquerda
            case "C": novaLinha++; novaColuna++; break; // diagonal inf. direita
            default:
                System.out.println("❌ Direção inválida!");
                return false;
        }

        // Verificando aqui se está dentro dos limites
        if (novaLinha < 0 || novaLinha >= tamanho || novaColuna < 0 || novaColuna >= tamanho) {
            System.out.println("❌ Movimento inválido: fora dos limites do tabuleiro!");
            return false;
        }

        // Verifica se posição já está ocupada
        if (grade[novaLinha][novaColuna] != null) {
            System.out.println("❌ Movimento inválido: posição ocupada!");
            return false;
        }

        grade[linhaAtual][colunaAtual] = null;
        grade[novaLinha][novaColuna] = personagem;
        personagem.setPosicaoPersonagem(new Posicao(novaLinha, novaColuna));

        System.out.println("✅ " + personagem.getNomePersonagem() + " moveu-se para (" + novaLinha + ", " + novaColuna + ").");
        return true;
    }
}



