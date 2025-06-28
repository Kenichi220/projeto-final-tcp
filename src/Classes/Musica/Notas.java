package Classes.Musica;

public  class Notas{

    private int nota;

    static final int DO  = 60;
    static final int RE  = 62;
    static final int MI  = 64;
    static final int FA  =  65;
    static final int SOL =  67;
    static final int LA =  69;
    static final int SI  = 71;
    static final int TELEFONE  = 125;

    static final int NUM_NOTAS = 7;

    public static int[] obterNotas(){
       int[] vetor = {Notas.DO,Notas.RE,Notas.MI,Notas.FA,Notas.SOL,Notas.LA,Notas.SI};
        return vetor;
    }

    public static int notaAleatoria(){
        int[] vetorNotas = obterNotas();
        int random = (int) (Math.random() * NUM_NOTAS);
        return vetorNotas[random];
    }

//----------------------------------------------
//Getter e Setter


    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}