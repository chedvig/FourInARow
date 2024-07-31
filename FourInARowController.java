package q2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class FourInARowController {

    @FXML
    private GridPane grid;

    @FXML
    private HBox hBox;

    private Button[] buttons;
    private final int NUMBER_OF_BUTTONS = 7;
    private FourInARowGame game;

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    public void initialize() {
        game = new FourInARowGame();
        buttons = new Button[NUMBER_OF_BUTTONS];
        
        // Initialize the buttons and add them to the HBox
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            buttons[i] = new Button(" " + (i + 1));
            buttons[i].prefWidthProperty().bind(hBox.widthProperty().divide(NUMBER_OF_BUTTONS));
            buttons[i].prefHeightProperty().bind(hBox.heightProperty());
            hBox.getChildren().add(buttons[i]);
            final int index = i;
            buttons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    handleButtonAction(index);
                }
            });
        }
    }

    /**
     * Handles the action when a column button is pressed.
     * 
     * @param index The index of the pressed button (column).
     */
    private void handleButtonAction(int index) {
        int player = game.getCurrentPlayer();
        Color color = (player == 1) ? Color.RED : Color.BLUE;

        int j = game.addItem(index, player);
        if (j < 0) {
            showColumnFullError();
            game.getCurrentPlayer(); // Reset player count due to invalid move
        } else {
            addCircle(grid, index, j, color);
        }
        if (game.isColumn(j, index) || game.isRow(j, index) || game.isDiagonal(j, index)) {
            showEndGameDialog("win");
        } else if (game.isBoardFull()) {
            showEndGameDialog("tie");
        }
    }

    /**
     * Adds a circle to the specified position in the GridPane.
     * 
     * @param grid  The GridPane to which the circle will be added.
     * @param index The column index.
     * @param j     The row index.
     * @param color The color of the circle.
     */
    private void addCircle(GridPane grid, int index, int j, Color color) {
        double radius = Math.min(grid.getHeight(), grid.getWidth()) / 15;
        Circle circle = new Circle(radius);
        circle.setStroke(color);
        circle.setFill(color);
        StackPane stackPane = new StackPane(circle);
        grid.add(stackPane, index, j);
    }

    /**
     * Clears the game board and resets the game state.
     */
    @FXML
    void buttonClearPressed(ActionEvent event) {
        clearGame();
    }

    /**
     * Clears the game board and resets the game state.
     */
    private void clearGame() {
        grid.getChildren().clear();
        grid.setGridLinesVisible(false);
        grid.setGridLinesVisible(true);
        game.resetGame();
    }

    /**
     * Displays an end game dialog with the outcome.
     * 
     * @param outcome The outcome of the game ("win" or "tie").
     */
    private void showEndGameDialog(String outcome) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        if (outcome.equals("win")) {
            alert.setHeaderText("Player wins!");
        } else {
            alert.setHeaderText("It's a tie!");
        }
        alert.setContentText("Do you want to start a new game or exit?");

        ButtonType newGameButton = new ButtonType("New Game");
        ButtonType exitButton = new ButtonType("Exit");

        alert.getButtonTypes().setAll(newGameButton, exitButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == newGameButton) {
                clearGame();
            } else {
                System.exit(0);
            }
        });
    }

    /**
     * Displays an error dialog when a column is full.
     */
    private void showColumnFullError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("This column is full, please try another one");

        alert.showAndWait();
    }
}
