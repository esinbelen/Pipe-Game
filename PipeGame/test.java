package application;
import javafx.application.Application;
import javafx.stage.Stage;

public class test extends Application{
		  @Override // Override the start method in the Application class
		  public void start(Stage primaryStage) {
		
				startGame(primaryStage);
					   
		  }
		  public static void main(String[] args) {
			    launch(args);
			  }
		  // This method starts the game
		  void startGame(Stage stage) {  
				GameLayout game=new GameLayout(stage);
			    game.RestartGame.b.setOnAction(e -> {
				       restart(stage);
				    });
				
				stage.show();
			}
		  
		  //This method restarts the game 
			void restart(Stage stage) {
			 
			    startGame(stage);
			}
		
}



