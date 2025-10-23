package domain;

import java.lang.Math;

public class Acoes {

    // ====================================================================
    // 1. MÉTODO PRINCIPAL DE COMBATE: ATACAR
    // ====================================================================

    public static void atacar(Personagem atacante, Personagem alvo, Tabuleiro tabuleiro) {

        if (!verificarCondicoesAtaque(atacante, alvo, tabuleiro)) {
            return;
        }

        double dano = calcularDano(atacante, alvo);
        receberDano(alvo, dano, tabuleiro);

        System.out.println(atacante.getNomePersonagem() + " atacou " + alvo.getNomePersonagem() +
                " e causou " + String.format("%.1f", dano) + " de dano!");
    }

    // ====================================================================
    // 2. MÉTODOS DE APOIO E LÓGICA DE REGRAS
    // ====================================================================

    private static boolean verificarCondicoesAtaque(Personagem atacante, Personagem alvo, Tabuleiro tabuleiro) {

        int distancia = tabuleiro.calcularDistancia(atacante, alvo);
        int alcance = atacante.getCasaPersonagem().getAlcanceMaximo();

        if (distancia > alcance) {
            System.out.println(atacante.getNomePersonagem() + " está fora do alcance para atacar " +
                    alvo.getNomePersonagem() + " (Distância: " + distancia + ", Alcance: " + alcance + ")");
            return false;
        }

        return true;
    }

    private static double calcularDano(Personagem atacante, Personagem alvo) {
        Casa casaAtacante = atacante.getCasaPersonagem();
        Casa casaAlvo = alvo.getCasaPersonagem();

        double ataque = casaAtacante.getAtaqueBase() * (1 + casaAtacante.getModificadorOfensivo());
        double defesa = casaAlvo.getDefesaBase();

        // Regra da Casa Targaryen (IGNORA DEFESA)
        if (casaAtacante.getNome().equalsIgnoreCase("TARGARYEN")) {
            defesa = 0;
            System.out.println("(TARGARYEN ignora a defesa do alvo!)");
        }

        double dano = ataque - defesa;

        // Regra de redução de dano (STARK e LANNISTER)
        if (casaAlvo.getModificadorDefensivo() > 0) {
            dano *= (1 - casaAlvo.getModificadorDefensivo());
            System.out.println("(" + casaAlvo.getNome() + " reduziu o dano recebido em " + (casaAlvo.getModificadorDefensivo() * 100) + "%)");
        }

        return Math.max(dano, 0);
    }

    public static void receberDano(Personagem alvo, double dano, Tabuleiro tabuleiro) {
        int danoArredondado = (int) Math.round(dano);

        int vidaAnterior = alvo.getVidaAtual();
        int novaVida = Math.max(vidaAnterior - danoArredondado, 0);

        alvo.setVidaAtual(novaVida);

        if (novaVida <= 0) {
            System.out.println("\n*** " + alvo.getNomePersonagem() + " FOI DERROTADO! ***");
            tabuleiro.removerPersonagem(alvo);
        } else {
            System.out.println(alvo.getNomePersonagem() + " [Vida: " + vidaAnterior + " -> " + novaVida + "]");
        }
    }
}