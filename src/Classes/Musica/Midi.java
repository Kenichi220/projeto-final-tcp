package Classes.Musica;
import javax.sound.midi.*;


public class Midi {

    //Uso da biblioteca midi
    private Synthesizer synthesizer;
    private MidiChannel channel;

    //Essa parte de uso da midi o chat me ajudou ent n sei como que funciona direito
    public Midi() throws MidiUnavailableException {
        //Pega o sintetizador
        synthesizer = MidiSystem.getSynthesizer();
        //Abre o sintetizador
        synthesizer.open();
        //Define o canal no zero
        channel = synthesizer.getChannels()[0];
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
            Thread.sleep(50);
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
