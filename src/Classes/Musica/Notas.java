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

    //Recebe a nota a ser tocada e chama a biblioteca de som
    public  void toca_nota() {}

//----------------------------------------------
//Construtores


    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}