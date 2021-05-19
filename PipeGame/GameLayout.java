package application;
//Nesrine Lagraa 150118921
//Esin Belen 150119025
// This class creates the game layout by adding the level, the buttons and 
//colors all together in the UI

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameLayout {
	
	//--- Message to be displayed at the beginning of the game----
	  private Text label=new Text("Welcome to");
	  private Label label2=new Label ("Pipe Game");
	  private DisplayButton start=new DisplayButton("Start Game"); // button to start the game
	  protected DisplayButton RestartGame=new DisplayButton("Restart Game"); // button to reastart the game
	  private VBox pane=new VBox(); // Pane that contains the 1st msg to be displayed to user 
	  private VBox bigPane=new VBox();// Pane that contains the 1st msg + the button "start game"
	
	  //--- buttons to go to next level ---- 
	  private DisplayButton next1=new DisplayButton("Next Level");
	  private DisplayButton next2=new DisplayButton("Next Level");
	  private DisplayButton next3=new DisplayButton("Next Level");
	  private DisplayButton next4=new DisplayButton("Next Level");
	  
	  //--- Create level objects----
	  private Level l1=new Level("level1.txt",1);
	  private Level l2=new Level("level2.txt",2);
	  private Level l3=new Level("level3.txt",3);
	  private Level l4=new Level("level4.txt",4);
	  private Level l5=new Level("level5.txt",5);
	  
	  //--- Create the game layout pane-----
	  private BorderPane borderPane=new BorderPane();
	  private Pane headerPane = new Pane();// top pane 
	  private HBox hbox = new HBox();// hbox inside top pane to control nodes 
	  private Label levelText= new Label();// Text indicating the current level
	  private AnchorPane anchorpane = new AnchorPane(); // botton box 
	  private HBox buttonBox = new HBox();// hbox of the bottom box that contains "next" button
	  //--- create left and right spacing---
	  private VBox vBoxLeft = new VBox(); 
	  private VBox vBoxRight = new VBox();
	
	  
	  //--- Constructor of Game layout takes window as parameter 
	public GameLayout(Stage window) {
		
		//-------------First Scene---------------------
		 //Styling pane 
		  pane.setAlignment(Pos.CENTER);
		  pane.setPadding(new Insets(50, 50, 50, 50));
		  pane.setSpacing(10);
		  //Styling label "Welcome"
		  label.setFill(Color.ALICEBLUE);
		  label.setStroke(Color.BROWN);
		  label.setStrokeType(StrokeType.OUTSIDE);
		  label.setStyle("-fx-font-size:40px;");
		  
		  //Styling label 2 " To Pipe Game"
		  label2.setTextFill(Color.ALICEBLUE);
		  label2.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC,30));
		  
		//Styling button " Start Game"
		  start.b.setPadding(new Insets(10, 10, 10, 10));
		  start.b.setAlignment(Pos.CENTER);
		  
		  // Add labels to pane
		  pane.getChildren().addAll(label,label2);
		  
		 // Styling bigPane and adding pane and start button to it
		  bigPane.setAlignment(Pos.CENTER);
		  bigPane.setStyle("-fx-background-color: linear-gradient(to left bottom, #c33122, #942229, #631a25, #34131a, #000000);"
		  			
				  +"-fx-text-alignment:center;");
		  bigPane.getChildren().addAll(pane,start.b);
		  
		  //create the scene 
		  Scene scene = new Scene(bigPane,400,400);
	      
	      
		  //-------------Second Scene---------------------
  
		  //The header Pane contains the current level and the movements last
		  	levelText.setText("Level 1: ");
	        levelText.setTextFill(Color.ALICEBLUE);
	        levelText.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR,20));
	        hbox.setPadding(new Insets(15, 12, 15, 12));
	        hbox.setSpacing(180);
	        hbox.getChildren().addAll(levelText,l1.movLastText); // add level Text and movement last in the level to pane
	        l1.movLastText.setAlignment(Pos.TOP_RIGHT);// Align mouvements last text
	        headerPane.getChildren().add(hbox);
	        
	     // Left Box
	       
	        vBoxLeft.setPadding(new Insets(15, 20, 15, 20));
	        vBoxLeft.setSpacing(10);
	        
	     // Right Box
	        
	        vBoxRight.setPadding(new Insets(15, 20, 15, 20));
	        vBoxRight.setSpacing(10);
	        
	        
	     // Bottom pane 

	        buttonBox.setPadding(new Insets(10, 10, 10, 10));
	        buttonBox.setSpacing(10);
	        buttonBox.getChildren().addAll(l1.stateText,next1.b);
	        AnchorPane.setBottomAnchor(buttonBox, 8.0);
	        AnchorPane.setRightAnchor(buttonBox, 5.0);
	  
	        
		  // Content of border Pane
			  anchorpane.getChildren().add(buttonBox);
			  borderPane.setTop(headerPane);
		      borderPane.setLeft(vBoxLeft);
		      borderPane.setCenter(l1.getContainer()); // the grid of the the given level
		      borderPane.setRight(vBoxRight);
		      borderPane.setBottom(anchorpane);
		      // set background color of borderPane 
		      borderPane.setStyle("-fx-background-color: linear-gradient(to left bottom, #c33122, #942229, #631a25, #34131a, #000000);");
	      
	      
	      //set the 2nd scene 
	      Scene scene2 =new Scene(borderPane);
	      
	      //--- Handle clicking next button events and going to next level 
		  next1.b.setOnAction(e -> {
			  goToNextLevel(l1,l2,next1,next2);
		  });
		  

		  next2.b.setOnMouseClicked(e -> {
			  goToNextLevel(l2,l3,next2,next3);
		  });
		

		  next3.b.setOnMouseClicked(e -> {
			  goToNextLevel(l3,l4,next3,next4);
		  });
		

		  next4.b.setOnMouseClicked(e -> {
			  goToNextLevel(l4,l5,next4,RestartGame); // in the last level retart game in displayed when level completed 
		  });
		
		  //--Handle clicking start button and change to scene2----
		  start .b.setOnMouseClicked(e -> {
			  window.setScene(scene2);
		  });
		
		// set the stage with the 1st scene 
		window.setScene(scene);
		window.setTitle("Pipe Game");
		window.getIcons().add(new Image("GameIcon.png")); // put the picture of the game as icon of it
		window.setResizable(false); // set stage to be unresizable 
		
		  
		
	}
	
// ---This method goes to next level without changing scene ------
public void goToNextLevel(Level l1,Level l2,DisplayButton next1,DisplayButton next2) {
	  if(l1.levelPassed) { // check if player passed 
		  	 // Remove button and panes related to previous level
			  anchorpane.getChildren().remove(buttonBox);
			  buttonBox.getChildren().removeAll(l1.stateText,next1.b);
			  borderPane.getChildren().removeAll(anchorpane,l1.getContainer(),headerPane);
			  headerPane.getChildren().remove(hbox);
			  hbox.getChildren().removeAll(levelText,l1.movLastText);
			  
			  // Set the button and panes related to next level 
			  levelText.setText("Level "+l2.i+":");
			  hbox.getChildren().addAll(levelText,l2.movLastText);
			  headerPane.getChildren().add(hbox);
			  buttonBox.getChildren().addAll(l2.stateText,next2.b);
			  anchorpane.getChildren().add(buttonBox);
			  borderPane.setTop(headerPane);
			  borderPane.setCenter(l2.getContainer());
			  borderPane.setBottom(anchorpane);
	  }else if(l1.Lost) {// if player lost next2 is replaced by restart button 
			  anchorpane.getChildren().remove(buttonBox);
			  buttonBox.getChildren().removeAll(l1.stateText,next1.b);
			  buttonBox.getChildren().addAll(l2.stateText,RestartGame.b);
			  anchorpane.getChildren().add(buttonBox);
			  borderPane.setBottom(anchorpane);
}
}
}
