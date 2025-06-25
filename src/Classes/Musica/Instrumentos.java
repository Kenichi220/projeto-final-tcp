package Classes.Musica;

public class Instrumentos{

    private int numero_MIDI;

    // Init intrumentos
    static final int PIANO = 0;
    static final int VIOLAO = 24;
    static final int SAXOFONE = 65;
    static final int GAITA_FOLE = 110;
    static final int STEEL_DRAM = 114;

    static final int NUM_INSTRUMENTOS = 5;

    //Operacao que chama a bibiloteca e coloca o instrumento
    private void coloca_intrumento(){}

//----------------------------------------------
//Construtores

    public int getNumero_MIDI() {
        return numero_MIDI;
    }

    public void setNumero_MIDI(int numero_MIDI) {
        this.numero_MIDI = numero_MIDI;
    }
}