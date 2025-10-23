package domain;

import java.lang.Math;

public class Tabuleiro {

    private Personagem[][] grade;
    public final int TAMANHO = 10;

    public Tabuleiro() {
        this.grade = new Personagem[TAMANHO][TAMANHO];
    }

    public void adicionarPersonagem(Personagem p, int linha, int coluna) {
        if (p.getPosicaoPersonagem() == null) {
            p.setPosicaoPersonagem(new Posicao(linha, coluna));
        } else {
            p.getPosicaoPersonagem().setPosicao(linha, coluna);
        }
        this.grade[linha][coluna] = p;
    }

    public boolean moverPersonagem(Personagem p, int novaLinha, int novaColuna) {
        if (novaLinha < 0 || novaLinha >= TAMANHO || novaColuna < 0 || novaColuna >= TAMANHO) {
            System.out.println("Movimento inválido: fora dos limites do tabuleiro.");
            return false;
        }
        if (grade[novaLinha][novaColuna] != null) {
            System.out.println("Movimento inválido: posição já ocupada.");
            return false;
        }

        // 1. Remove da posição antiga
        grade[p.getLinha()][p.getColuna()] = null;

        // 2. Atualiza a posição no objeto Personagem
        p.getPosicaoPersonagem().setPosicao(novaLinha, novaColuna);

        // 3. Adiciona na nova posição
        grade[novaLinha][novaColuna] = p;

        System.out.println(p.getNomePersonagem() + " moveu para (" + novaLinha + ", " + novaColuna + ").");
        return true;
    }

    public int calcularDistancia(Personagem p1, Personagem p2) {
        // Distância de Chebychev
        int dLinha = Math.abs(p1.getLinha() - p2.getLinha());
        int dColuna = Math.abs(p1.getColuna() - p2.getColuna());
        return Math.max(dLinha, dColuna);
    }

    public void removerPersonagem(Personagem p) {
        if (p != null) {
            grade[p.getLinha()][p.getColuna()] = null;
            System.out.println(p.getNomePersonagem() + " removido do tabuleiro.");
        }
    }

    public void ImprimeTabuleiro() {
        System.out.println(" ----- Tabuleiro Atual (" + TAMANHO + "x" + TAMANHO + ") -----");
        System.out.print("   ");
        for (int j = 0; j < TAMANHO; j++) {
            System.out.printf(" %2d ", j);
        }
        System.out.println();

        for (int i = 0; i < TAMANHO; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < TAMANHO; j++) {
                if (grade[i][j] != null) {
                    System.out.print(" [" + grade[i][j].getCasaPersonagem().getNome().charAt(0) + "] ");
                } else {
                    System.out.print(" [ ] ");
                }
            }
            System.out.println();
        }
        System.out.println("----------------------------------------");
    }

    // Método para posicionar inicialmente os times (implementação base)
    public void posicicaoPInicial(Personagem[] time1, Personagem[] time2) {
        // A lógica de posicionamento é feita na classe Jogo para este projeto.
    }
}