package Classes.Musica;

public class Instrumentos{

    private int numero_MIDI;

    // Init intrumentos
    private static final int PIANO = 0;
    private static final int VIOLAO = 24;
    private static final int SAXOFONE = 65;
    private static final int GAITA_FOLE = 110;
    private static final int STEEL_DRAM = 114;

    public static final int NUM_INSTRUMENTOS = 5;

    public static int[] obterInstrumentos(){
        int[] vetor = {PIANO, VIOLAO, SAXOFONE, GAITA_FOLE, STEEL_DRAM};
        return vetor;
    }

    public static String[] obterNomesInstrumentos(){
        String[] nomes = {"Piano", "Violao", "Saxofone", "Gaita_Fole", "Steel_Drum", "Aleatório"};
        return nomes;

    }
    public static int instrumentoAleatorio(){
        int[] vetorInstrumentos = obterInstrumentos();
        int random = (int) (Math.random() * NUM_INSTRUMENTOS);
        return vetorInstrumentos[random];
    }

    public static int obterInstrumentoInicial(){
        return PIANO;
    }

//----------------------------------------------
//Getter Setter

    public int getNumero_MIDI() {
        return numero_MIDI;
    }

    public void setNumero_MIDI(int numero_MIDI) {
        this.numero_MIDI = numero_MIDI;
    }
}