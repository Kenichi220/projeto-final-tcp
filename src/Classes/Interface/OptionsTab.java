package Classes.Interface;

import Classes.Musica.Musica;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class OptionsTab extends Tab {
    private final int NUM_INSTRUMENTOS = 5;

    public OptionsTab(Musica musica) {
        super("Options");
        setClosable(false);

        Slider volumeSlider = new Slider(0, 100, Musica.getVolume());
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        //volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> Musica.setVolume(newVal.intValue()));
        volumeSlider.valueProperty().bindBidirectional(Musica.volumeProperty());

        ComboBox<String> sequenciaInstrumentos = new ComboBox<>();
        String[] nomes = {"Piano", "Violao", "Saxofone", "Gaita_Fole", "Steel_Drum", "Aleatório"};
        int[] midiInstrumentos = {0, 24, 65, 110, 114};
        sequenciaInstrumentos.getItems().addAll(nomes);
        sequenciaInstrumentos.getSelectionModel().select(0);

        sequenciaInstrumentos.setOnAction(e -> {
            int numberSelected = sequenciaInstrumentos.getSelectionModel().getSelectedIndex();
            int instrumentoSelecionado;
            if (numberSelected < NUM_INSTRUMENTOS) {
                instrumentoSelecionado = midiInstrumentos[numberSelected];
                Musica.trocar_instrumento(instrumentoSelecionado);
            } else {
                int random = (int)(Math.random() * NUM_INSTRUMENTOS);
                instrumentoSelecionado = midiInstrumentos[random];
                Musica.trocar_instrumento(instrumentoSelecionado);
                sequenciaInstrumentos.getSelectionModel().select(random);
            }
        });

        VBox optionsLayout = new VBox(10, new Label("Volume:"), volumeSlider, new Label("Instrumento:"), sequenciaInstrumentos);
        optionsLayout.setAlignment(Pos.CENTER);
        optionsLayout.setStyle("-fx-padding: 20;");

        setContent(optionsLayout);
    }
}
