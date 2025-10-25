package domain;

import java.lang.Math;

public class Tabuleiro {

    /*
    * Temos um tabuleiro privado chamado celulas, contendo em cada uma das suas células
    * um Objeto da classe Personagem.
    *
    * VER MELHOR
    * */

    // Definição inicial para o tabuleiro
    private final Personagem[][] celulas;
    public final int TAMANHO = 10;


    // construtor na propria classe, fé
    public Tabuleiro() {
        this.celulas = new Personagem[TAMANHO][TAMANHO];
    }

    /*
     * Adiciona um personagem ao tabuleiro (usado principalmente para setup)
     * OBS: O método original 'adicionarP' foi simplificado no Jogo.java
     */

    public void adicionarPersonagem(Personagem p, Posicao pos) {
        // Validação

                // Seee por acaso a posição é válida, e essa posição não está ocupada, fique aí boy
        if (pos.isPosicaoValida(TAMANHO) && celulas[pos.getLinha()][pos.getColuna()] == null) {
            // aquela célula com àquela linha, e àquela coluna recebe o valor P.

                    // aqui dizemos onde cada personagem está no momento, e salva
            this.celulas[pos.getLinha()][pos.getColuna()] = p;

            // VER MELHOR
            p.setPosicaoPersonagem(pos); // mandando para classe personagem sua posição, que por sua vez, atualiza a classe Posição sobre
        } else {
            System.out.println("ERRO: Tentativa de adicionar personagem em posição inválida/ocupada.");
        }
    }













    /**
     * Tenta mover o personagem para uma nova posição.
     */

    public boolean moverPersonagem(Personagem p, int novaLinha, int novaColuna) {

        // 1. Validação de Limites
        if (novaLinha < 0 || novaLinha >= TAMANHO || novaColuna < 0 || novaColuna >= TAMANHO) {
            System.out.println("Movimento inválido: A posição [" + novaLinha + "," + novaColuna + "] está fora do tabuleiro.");
            return false;
        }
        // 2. Validação de Ocupação
        if (celulas[novaLinha][novaColuna] != null) {
            System.out.println("Movimento inválido: A posição [" + novaLinha + "," + novaColuna + "] já está ocupada por outro personagem.");
            return false;
        }

        // "}else{"
        // 3. Atualiza a grade e o objeto Personagem
        celulas[p.getLinha()][p.getColuna()] = null; // Limpa a posição antiga
        p.setPosicao(novaLinha, novaColuna); // Atualiza a posição no objeto Personagem
        celulas[novaLinha][novaColuna] = p; // Coloca o personagem na nova posição

        return true;
    }







    // Retornando issio aqui para a classe Ações e classe Jogo

    /*
     * Calcular a porqueira da distância (Chebychev) entre dois personagens mais proximos na mesa do jogo
     */

    // isso aqui foi recalculado la na classe Ações: ver melhor se há necessidade
    public int calcularDistancia(Personagem p1, Personagem p2) {
        int dLinha = Math.abs(p1.getLinha() - p2.getLinha());
        int dColuna = Math.abs(p1.getColuna() - p2.getColuna());
        // Distância de Chebychev é o máximo entre os deslocamentos em Linha e Coluna.
        return Math.max(dLinha, dColuna);
    }
    // considerando que o jogador escolhe qual personagem seu usar, e escolhe qual personagem quer atacar









    // essa é uma função chamada somente pela classe ações (após observar a vida nova - que se torna atual - do personagem)
       // depois disso, eu posso atualizar o tabuleiro, e toda a imagem do jogo
    public void removerPersonagem(Personagem p) {
        if (p != null) {

            // devo pegar as coordenadas atuais daquele personagem morto
            int coluna = p.getColuna();
            int linha = p.getLinha();


            // Limpa a célula na posição do personagem
            if (linha >= 0 && linha < TAMANHO         &&          coluna >= 0 && coluna < TAMANHO) {
                this.celulas[linha][coluna] = null;
            }
        }
    }





    /**
     * Imprime o tabuleiro com coordenadas.
     */
    public void ImprimeTabuleiro() {
        System.out.println(" ----- Tabuleiro Atual (" + TAMANHO + "x" + TAMANHO + ") -----");

        // Cabeçalho das Colunas
        System.out.print("   ");
        for (int j = 0; j < TAMANHO; j++) {
            System.out.printf(" %2d ", j);
        }
        System.out.println();

        // Conteúdo da Grade (Linhas e Posições)
        for (int i = 0; i < TAMANHO; i++) {
            System.out.printf("%2d ", i); // Coordenada da Linha
            for (int j = 0; j < TAMANHO; j++) {
                // Usa 'celulas' para verificar se há um personagem
                if (celulas[i][j] != null) {
                    // Imprime a inicial da Casa (ex: S para Stark)
                    System.out.print(" [" + celulas[i][j].getICasa() + "] ");
                } else {
                    System.out.print(" [ ] "); // Posição vazia
                }
            }
            System.out.println();
        }
        System.out.println("-----------------------\n");
    }

    // tinha esquecido disso, mas pela boa prática, vamos remover o jogador morto do tabuleiro



}