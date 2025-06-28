package Classes.Interface;

import Classes.Musica.Instrumentos;
import Classes.Musica.Musica;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class OptionsTab extends Tab {

    private static int VOLUME_MAX = 100;
    private static int VOLUME_MIN = 0;


    public OptionsTab(Musica musica) {

        super("Options");
        setClosable(false);

        Slider volumeSlider = new Slider(VOLUME_MIN, VOLUME_MAX, Musica.getVolume());
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        //volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> Musica.setVolume(newVal.intValue()));
        volumeSlider.valueProperty().bindBidirectional(Musica.volumeProperty());

        ComboBox<String> sequenciaInstrumentos = new ComboBox<>();
        String[] nomesInstrumentos = Instrumentos.obterNomesInstrumentos();
        int[] midiInstrumentos = Instrumentos.obterInstrumentos();
        sequenciaInstrumentos.getItems().addAll(nomesInstrumentos);
        sequenciaInstrumentos.getSelectionModel().select(Instrumentos.obterInstrumentoInicial());

        sequenciaInstrumentos.setOnAction(e -> {
            int numberSelected = sequenciaInstrumentos.getSelectionModel().getSelectedIndex();
            int instrumentoSelecionado;
            if (numberSelected < (Instrumentos.NUM_INSTRUMENTOS)) {
                instrumentoSelecionado = midiInstrumentos[numberSelected];
                Musica.trocarInstrumento(instrumentoSelecionado);
            } else {
                instrumentoSelecionado = Musica.trocarInstrumentoRandom();
                sequenciaInstrumentos.getSelectionModel().select(instrumentoSelecionado);

            }
        });

        VBox optionsLayout = new VBox(UIBuilder.ESPACAMENTO_LAYOUT, new Label("Volume:"), volumeSlider, new Label("Instrumento:"), sequenciaInstrumentos);
        optionsLayout.setAlignment(Pos.CENTER);
        optionsLayout.setStyle("-fx-padding: 20;");

        setContent(optionsLayout);
    }
}
