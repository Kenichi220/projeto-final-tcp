package Classes.Musica;
import javax.sound.midi.*;


public class Midi {

    //Uso da biblioteca midi
    private Synthesizer synthesizer;
    private MidiChannel channel;

    private static final int CANAL_INICIAL = 0;
    private final int TEMPO_PAUSA = 50;

    public Midi() throws MidiUnavailableException {
        //Pega o sintetizador
        synthesizer = MidiSystem.getSynthesizer();
        //Abre o sintetizador
        synthesizer.open();
        //Define o canal no zero
        channel = synthesizer.getChannels()[CANAL_INICIAL];
    }

    public void tocar(int nota, int duration, int volume) {
        channel.noteOn(nota, volume);
        try { 
            Thread.sleep(duration); 
        } 
        catch (InterruptedException ignored) {}
        channel.noteOff(nota);
        //Delay entre notas
        try {
            Thread.sleep(TEMPO_PAUSA);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void sleep(int duration) {
        try { Thread.sleep(duration); } catch (InterruptedException ignored) {}
    }

    public void trocarInstrumento(int MIDI) {
        channel.programChange(MIDI);
    }


}
