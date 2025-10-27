package domain;
import java.util.List;

public class Tabuleiro {

    private Personagem[][] grade;
    private final int TAMANHO = 10;


    public int calcularDistancia(Personagem p1, Personagem p2) {
        return p1.getPosicaoPersonagem().CalcularDistancia(p2.getPosicaoPersonagem());
    }


    public int getTamanho() {
        return TAMANHO;
    }


    public Personagem getPersonagemNaPosicao(int linha, int coluna) {
        if (linha < 0 || linha >= TAMANHO || coluna < 0 || coluna >= TAMANHO) {
            return null;
        }
        return grade[linha][coluna];
    }


    public void removerPersonagem(Personagem p) {
        if (p != null) {
            grade[p.getLinha()][p.getColuna()] = null;
        }
    }


    public Tabuleiro() {
        this.grade = new Personagem[TAMANHO][TAMANHO];
    }

    public void adicionarPersonagem(Personagem p, Posicao pos) {
        this.grade[pos.getLinha()][pos.getColuna()] = p;
        p.setPosicaoPersonagem(pos);
    }


    public void posicicaoPInicial(List<Personagem> time1, List<Personagem> time2) {

        for (Personagem p : time1) {
            if (p == null) continue;

            Posicao posAleatoria;
            do {

                posAleatoria = new Posicao(0, 4, TAMANHO);
            }
            while (grade[posAleatoria.getLinha()][posAleatoria.getColuna()] != null);
            adicionarPersonagem(p, posAleatoria);
        }

        for (Personagem p : time2) {
            if (p == null) continue;

            Posicao posAleatoria;
            do {

                posAleatoria = new Posicao(5, 9, TAMANHO);
            }

            while (grade[posAleatoria.getLinha()][posAleatoria.getColuna()] != null);
            adicionarPersonagem(p, posAleatoria);
        }
    }

    public boolean moverPersonagem(Personagem p, int novaLinha, int novaColuna) {

        if (novaLinha < 0 || novaLinha >= TAMANHO || novaColuna < 0 || novaColuna >= TAMANHO) {
            System.out.println("Movimento inválido: Fora do tabuleiro.");
            return false;
        }

        if (grade[novaLinha][novaColuna] != null) {
            System.out.println("Movimento inválido: Posição ocupada.");
            return false;
        }


        grade[p.getLinha()][p.getColuna()] = null;
        p.setPosicao(novaLinha, novaColuna);
        grade[novaLinha][novaColuna] = p;

        System.out.println(p.getNomePersonagem() + " moveu-se para (" + novaLinha + ", " + novaColuna + ")");
        return true;
    }


    public void ImprimeTabuleiro() {
        System.out.println("\n===== TABULEIRO ATUAL =====");
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (grade[i][j] != null) {
                    System.out.print("[ " + grade[i][j].getICasa() + " ]"); // Usa 3 caracteres
                } else {
                    System.out.print("[   ]"); // Usa 3 caracteres
                }
            }
            // CORREÇÃO: O println() foi movido para fora do loop 'j'
            System.out.println();
        }
        System.out.println("===========================\n");
    }
}