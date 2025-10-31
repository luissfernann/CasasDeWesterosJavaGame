package domain;

public class Casa {

    private static final Casa STARK;
    private static final Casa LANNISTER;
    private static final Casa TARGARYEN;

    // inicializando atributos fixos de cada casa.
    static {
        STARK = new Casa("STARK", 60, 20, 10, 1, 0.20, 0);
        LANNISTER = new Casa("LANNISTER", 50, 20, 10, 2, 0, 0.15);
        TARGARYEN = new Casa("TARGARYEN", 45, 20, 10, 3, 0, 0);
    }

    private final String nome;
    private final int vidaMaxima;
    private final int ataqueBase;
    private final int defesaBase;
    private final int alcanceMaximo;

    private final double modificadorOfensivo;
    private final double modificadorDefensivo;

    public Casa (String nome, int vidaMaxima, int ataqueBase, int defesaBase, int alcanceMaximo, double modificadorOfensivo, double modificadorDefensivo) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.ataqueBase = ataqueBase;
        this.defesaBase = defesaBase;
        this.alcanceMaximo = alcanceMaximo;
        this.modificadorOfensivo = modificadorOfensivo;
        this.modificadorDefensivo = modificadorDefensivo;

    }

    public String getNome() {
        return nome;
    }
    public int getVidaMaxima() {
        return vidaMaxima;
    }
    public int getAtaqueBase() {
        return ataqueBase;
    }
    public int getDefesaBase(){
        return defesaBase;
    }
    public int getAlcanceMaximo() {
        return alcanceMaximo;
    }
    public double getModificadorOfensivo() {
        return modificadorOfensivo;
    }
    public double getModificadorDefensivo() {
        return modificadorDefensivo;
    }

    // retornando o identificador de cada casa
    public static Casa getSTARK() {
        return STARK;
    }
    public static Casa getLANNISTER() {
        return LANNISTER;
    }
    public static Casa getTARGARYEN() {
        return TARGARYEN;
    }

}
