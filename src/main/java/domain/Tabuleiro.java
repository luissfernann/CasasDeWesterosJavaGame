package domain;



public class Tabuleiro {

    private Personagem[][] grade;
    private final int TAMANHO = 10;


    public Tabuleiro() {

        this.grade = new Personagem[TAMANHO][TAMANHO];


    }

    public void adicionarP(Personagem p, Posicao pos) {

        this.grade[pos.getLinha()][pos.getColuna()] = p;
        p.setPosicaoPersonagem(pos);
    }


    public void posicicaoPInicial(Personagem[] time1, Personagem[] time2) {

        for (Personagem p : time1) {
            if (p == null) continue;

            Posicao posAleatoria;
            do {
                posAleatoria = new Posicao(0, 4, TAMANHO);
            }
            while (grade[posAleatoria.getLinha()][posAleatoria.getColuna()] != null);
            adicionarP(p, posAleatoria);
        }
        for (Personagem p : time2) {

            if (p == null) continue;

            Posicao posAleatoria;
            do {
                posAleatoria = new Posicao(5, 9, TAMANHO);
            }

            while (grade[posAleatoria.getLinha()][posAleatoria.getColuna()] != null);

            adicionarP(p, posAleatoria);

        }


    }

    public boolean moverPersonagem(Personagem p, int novaLinha, int novaColuna) {
        if (novaLinha < 0 || novaLinha >= TAMANHO || novaColuna < 0 || novaColuna >= TAMANHO) {
            //Pode colocar uma impressao falando sobre o movimento ser invalido
            return false;
        }
        if (grade[novaLinha][novaColuna] != null) {
            //Pode colocar uma impressao falando sobre o movimento ser invalido
            return false;
        }

//Fazer um nó para atualizar o personagem na grade
        grade[p.getLinha()][p.getColuna()] = null;
        p.setPosicao(novaLinha, novaColuna);
        grade[novaLinha][novaColuna] = p;
        return true;
//pode colocar uma impressao avisando que se movimentou para posicao escolhida


    }
//Falta fazer uma booleana para mover os personagens


    public void ImprimeTabuleiro() {
        System.out.println(" ----- Tabuleiro Atual -----");
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (grade[i][j] != null) {
                    System.out.print(" [ " + grade[i][j].getICasa() + " ] ");

                } else {
                    System.out.print(" [ " + " ] ");
                }
                System.out.println();
            }

            System.out.println("-----------------------\n");


        }

    }
}





