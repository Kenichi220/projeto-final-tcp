package Classes.Interface;

import Classes.Musica.TextoMusicalParser;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class PlayTab extends Tab {
    private final TextArea inputArea;
    private final Button playButton;
    private final Button pauseButton;

    public PlayTab(TextoMusicalParser parser) {
        super("Play");
        setClosable(false);

        inputArea = new TextArea();
        inputArea.setPromptText("Digite a música...");

        playButton = new Button("Play");
        pauseButton = new Button("Pause");
        // Impede que pressione o pause
        pauseButton.setDisable(true);

        playButton.setOnAction(e -> {
            if (parser.isTocando()) {
                parser.stop();
                pauseButton.setDisable(true);
                pauseButton.setText("Pause");
            } else {
                parser.play(inputArea.getText(), playButton, pauseButton);
                pauseButton.setDisable(false);
            }
        });

        pauseButton.setOnAction(e -> {
            parser.togglePause(pauseButton);
        });

        VBox buttonBox = new VBox(10, playButton, pauseButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox playLayout = new VBox(10, inputArea, buttonBox);
        playLayout.setAlignment(Pos.CENTER);
        playLayout.setStyle("-fx-padding: 20;");

        setContent(playLayout);
    }

    public TextArea getInputArea() {
        return inputArea;
    }
}