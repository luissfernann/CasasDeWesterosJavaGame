package domain;

// Importa a classe Math para operações como Math.abs, Math.max, Math.round
import java.lang.Math;


public class Acoes {

    public static int atacar(Personagem atacante, Personagem alvo, Tabuleiro tabuleiro) {

        // 1. VERIFICANDO ALCANCE
        int dLinha = Math.abs(atacante.getLinha() - alvo.getLinha());
        int dColuna = Math.abs(atacante.getColuna() - alvo.getColuna());



        int distancia = Math.max(dLinha, dColuna);
        int alcance = atacante.getCasaPersonagem().getAlcanceMaximo();

        if (distancia > alcance) {
            System.out.println(atacante.getNomePersonagem() + " está fora do alcance para atacar " +
                    alvo.getNomePersonagem() + " (Distância: " + distancia + ", Alcance: " + alcance + ")");
            return 0;
        }

        // 2. CÁLCULO DO DANO E DEFESA

        Casa casaAtacante = atacante.getCasaPersonagem();
        Casa casaAlvo = alvo.getCasaPersonagem(); // para o defensor

        // ATACANDO
        double ataque = casaAtacante.getAtaqueBase() * (1 + casaAtacante.getModificadorOfensivo());

        // DEFESA
        // CORREÇÃO: DECLARAÇÃO DA VARIÁVEL 'defesa' MOVIDA PARA AQUI!
        // Assim, ela existe antes de ser usada no IF do TARGARYEN.
        double defesa = casaAlvo.getDefesaBase();

        // Caso especial TARGARYEN (agora a variável 'defesa' existe!)
        if (casaAtacante.getNome().equalsIgnoreCase("TARGARYEN")) {
            defesa = 0; // Atribuição correta
            System.out.println("  (TARGARYEN ignora a defesa!)");
        }

        // Dano Bruto
        double danoBruto = ataque - defesa;

        // ÚNICO CASO ESPECIAL PARA DEFESA: Modificador Defensivo (STARK)
        if (casaAlvo.getNome().equalsIgnoreCase("STARK")) {
            danoBruto *= 0.80;
            System.out.println("  (Defensor da casa STARK reduziu o dano em 20%.)");
        }

        // Garante que o dano não seja negativo
        double danoFinal = Math.max(danoBruto, 0);
        int danoArredondado = (int) Math.round(danoFinal);

        // 3. APLICAÇÃO DO DANO E ATUALIZAÇÃO DE VIDA

        int vidaAnterior = alvo.getVidaAtual();
        int novaVida = vidaAnterior - danoArredondado;

        // Garante que a vida não seja negativa
        if (novaVida < 0) {
            novaVida = 0;
        }

        alvo.setVidaAtual(novaVida);

        System.out.println(atacante.getNomePersonagem() + " atacou " + alvo.getNomePersonagem() +
                " e causou " + danoArredondado + " de dano!");

        System.out.println("  " + alvo.getNomePersonagem() + " [Vida: " + vidaAnterior + " -> " + novaVida + "]");

        // 4. VERIFICAÇÃO DE MORTE E REMOÇÃO
        if (novaVida <= 0) {
            System.out.println("\n*** " + alvo.getNomePersonagem() + " FOI DERROTADO! ***");
            tabuleiro.removerPersonagem(alvo);
        }

        // Retorna o dano causado (essencial para o RegistroJogada)
        return danoArredondado;
    }
}