package domain;

public class Casa {

    /*

    Podemos criar uma classe Casa, herdando variáveis fixas da casa, e subclasses, que seriam
    Extents, ou melhor, heranças, da classe Casa, onde cada subclasse seria uma casa. Aí nesse caso
    teríamos, por exemplo, a CasaStark extends Casa, CasaLannister extends Casa...

    Nesse caso, teríamos algo para cada personagem herdar fixamente.

    */

    // atributos fixos seguindo a tabela 1 do documento.

    // DEFINIÇÃO FIXA ( constantes ) DE VALORES : CADA CASA

    // 1)

    private static final Casa STARK;
    private static final Casa LANNISTER;
    private static final Casa TARGARYEN;
    // inicializando
    static {
        STARK = new Casa("STARK", 60, 20, 10, 1, 0.20, 0);
        LANNISTER = new Casa("LANNISTER", 50, 20, 10, 2, 0, 0.15);
        TARGARYEN = new Casa("TARGARYEN", 45, 20, 10, 3, 0, 0);
    }


    public String nome;
    public int vidaMaxima;
    public int ataqueBase;
    public int defesaBase;
    public int alcanceMaximo;

    // ainda não entendi para que serve a variável abaixo
    public double modificadorOfensivo;
    public double modificadorDefensivo;

    // como cada personagem pertence a uma casa, e cada casa
    // tem suas variáveis fixas, seguimos o construtor

    // 2 )

    public Casa (String nome, int vidaMaxima, int ataqueBase, int defesaBase, int alcanceMaximo, double modificadorOfensivo, double modificadorDefensivo) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.ataqueBase = ataqueBase;
        this.defesaBase = defesaBase;
        this.alcanceMaximo = alcanceMaximo;
        this.modificadorOfensivo = modificadorOfensivo;
        this.modificadorDefensivo = modificadorDefensivo;

    }

    // retornando os atributos que os personagens devem ter acesso

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
