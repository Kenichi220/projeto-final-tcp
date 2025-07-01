package Classes.Interface;

import Classes.Musica.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class PlayTab extends Tab {
    //Variaveis
    private static TextArea inputArea = null;
    private final Button playButton;
    private final Button pauseButton;
    public static String[] texto;

    public PlayTab(TextoMusicalParser parser, Musica musica) {
        super("Play");
        setClosable(false);

        inputArea = new TextArea();
        inputArea.clear();
        inputArea.setPromptText("Digite a música...");

        playButton = new Button("Play");
        pauseButton = new Button("Pause");
        // Impede que pressione o pause
        pauseButton.setDisable(true);

        playButton.setOnAction(e -> {
            if (musica.isTocando()) {
                musica.stop();
                pauseButton.setDisable(true);
                pauseButton.setText("Pause");
            } else {
                musica.play(inputArea.getText(), playButton, pauseButton);
                pauseButton.setDisable(false);
            }
        });

        pauseButton.setOnAction(e -> {
            musica.togglePause(pauseButton);
        });

        VBox buttonBox = new VBox(UIBuilder.ESPACAMENTO_LAYOUT, playButton, pauseButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox playLayout = new VBox(UIBuilder.ESPACAMENTO_LAYOUT, inputArea, buttonBox);
        playLayout.setAlignment(Pos.CENTER);
        playLayout.setStyle("-fx-padding: 20;");

        setContent(playLayout);
    }

//----------------------------------------------
//Getter Setter
    public TextArea getInputArea() {
        return inputArea;
    }

    public static String getTexto() {
        return inputArea.getText();
    }
}