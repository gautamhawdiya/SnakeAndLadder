package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class SnakeLadder extends Application {
	public static final int tilesize = 40, width = 10, height = 10;
	public static final int buttonLine = height*tilesize + 50, infoLine = buttonLine - 30;
	
	private static Dice dice = new Dice();
	private Player playerOne, playerTwo;
	private boolean gameStarted = false, playerOneTurn = false, playerTwoTurn = false;
	
	private Pane createContent(){
		Pane root = new Pane();
		root.setPrefSize(width*tilesize, height*tilesize + 100);
		
		for(int i =0;i< height ;i++) {
			for(int j =0;j<width;j++) {
				Tile tile = new Tile(tilesize);
				tile.setTranslateX(j*tilesize);
				tile.setTranslateY(i*tilesize);
				root.getChildren().add(tile);
			}
		}
		
		Image img = new Image("file:///C:/Users/gauta/eclipse-workspace/SnakeAndLadder/src/application/Screenshot%202024-03-15%20112645.png");
		ImageView board = new ImageView();
		board.setImage(img);
		board.setFitHeight(height*tilesize);
		board.setFitWidth(width*tilesize);
		
		//Buttons
		Button playerOneButton = new Button("Player One");
		Button playerTwoButton = new Button("Player Two");
		Button startButton = new Button("Start");
		playerOneButton.setTranslateY(buttonLine);
		playerOneButton.setTranslateX(20);
		playerOneButton.setDisable(true);
		playerTwoButton.setTranslateY(buttonLine);
		playerTwoButton.setTranslateX(300);
		playerTwoButton.setDisable(true);
		startButton.setTranslateY(buttonLine);
		startButton.setTranslateX(170);
		
		Label playerOneLabel = new Label("");
		Label playerTwoLabel = new Label("");
		Label diceLabel = new Label("Start The Game");
		
		playerOneLabel.setTranslateY(infoLine);
		playerOneLabel.setTranslateX(20);
		playerTwoLabel.setTranslateY(infoLine);
		playerTwoLabel.setTranslateX(300);
		diceLabel.setTranslateY(infoLine);
		diceLabel.setTranslateX(180);
		
		playerOne = new Player(tilesize-5, Color.BLACK, "P1");
		playerTwo = new Player(tilesize-13, Color.WHITE, "P2");
		
		//Player Action
		playerOneButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(gameStarted) {
					if(playerOneTurn) {				
						int diceValue = dice.getRolledDiceValue();
						diceLabel.setText("Dice Value"+ diceValue);
						playerOne.movePlayer(diceValue);
						
						if(playerOne.isWinner()) {
							diceLabel.setText("Winner is "+ playerOne.getName());
							
							playerOneTurn = false;
							playerOneButton.setDisable(true);
							playerOneLabel.setText("");
							
							playerTwoTurn = false;
							playerTwoButton.setDisable(true);
							playerTwoLabel.setText("");
							
							startButton.setDisable(false);
							startButton.setText("Restart");
							gameStarted = false;
						}
						else {
							playerOneTurn = false;
							playerOneButton.setDisable(true);
							playerOneLabel.setText("");
							
							playerTwoTurn = true;
							playerTwoButton.setDisable(false);
							playerTwoLabel.setText("Your Turn "+ playerTwo.getName());
						}
						
					}
				}
				
			}
		});
		playerTwoButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(gameStarted) {
					if(playerTwoTurn) {				
						int diceValue = dice.getRolledDiceValue();
						diceLabel.setText("Dice Value"+ diceValue);
						playerTwo.movePlayer(diceValue);
						if(playerTwo.isWinner()) {
							diceLabel.setText("Winner is "+ playerTwo.getName());
							
							playerOneTurn = false;
							playerOneButton.setDisable(true);
							playerOneLabel.setText("");
							
							playerTwoTurn = false;
							playerTwoButton.setDisable(true);
							playerTwoLabel.setText("");
							
							startButton.setDisable(false);
							startButton.setText("Restart");
							
						}
						else {
						playerOneTurn = true;
						playerOneButton.setDisable(false);
						playerOneLabel.setText("Your Turn "+ playerOne.getName());
						
						playerTwoTurn = false;
						playerTwoButton.setDisable(true);
						playerTwoLabel.setText("");
						}
					}
				}
				
			}
		});
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				gameStarted = true;
				diceLabel.setText("Game Started");
				startButton.setDisable(true);
				playerOneTurn = true;
				playerOneLabel.setText("Your Turn "+ playerOne.getName());
				playerOneButton.setDisable(false);
				playerOne.startingPosition();
				
				playerTwoTurn = false;
				playerTwoLabel.setText("");
				playerTwoButton.setDisable(true);
				playerTwo.startingPosition();
			}
			
		});
		
		root.getChildren().addAll(board, playerOneButton, playerTwoButton, startButton,
				playerOneLabel, playerTwoLabel, diceLabel,
				playerOne.getCoin(), playerTwo.getCoin()
				);
		return root;
	}
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			Scene scene = new Scene(createContent());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Snake And Ladder");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
