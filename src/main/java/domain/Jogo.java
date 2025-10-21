package domain;

public class Jogo {


        public static void main(String[] args) {
            Tabuleiro tabuleiro = new Tabuleiro();


            tabuleiro.adicionarPersonagem(personagemAtivo, 5, 5);

            Scanner scanner = new Scanner(System.in);


            System.out.println("Início do Jogo - A Batalha de Westeros");
            tabuleiro.imprimirTabuleiro();

            boolean movimentoRealizado = false;
            while (!movimentoRealizado) {
                System.out.println("--- Turno de: " + personagemAtivo.getNome() + " ---");
                System.out.print("Escolha a direção para mover (W=Cima, A=Esquerda, S=Baixo, D=Direita): ");
                String direcao = scanner.nextLine().toUpperCase();

                int linhaAtual = personagemAtivo.getLinha();
                int colunaAtual = personagemAtivo.getColuna();
                int novaLinha = linhaAtual;
                int novaColuna = colunaAtual;

                switch (direcao) {
                    case "W":
                        novaLinha--;
                        break;
                    case "S":
                        novaLinha++;
                        break;
                    case "A":
                        novaColuna--;
                        break;
                    case "D":
                        novaColuna++;
                        break;
                    default:
                        System.out.println("Opção de movimento inválida!");
                        continue;
                }


                movimentoRealizado = tabuleiro.moverPersonagem(personagemAtivo, novaLinha, novaColuna);
            }


            tabuleiro.imprimirTabuleiro();

            System.out.println("Fim do turno de movimento. Próxima fase seria o ataque.");
            scanner.close();
        }
    }




