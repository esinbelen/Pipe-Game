package application;
//Nesrine Lagraa 150118921
//Esin Belen 150119025
//This class creates the level object and handles all changes related to the level 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.animation.PathTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.*;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class Level {
	  private GridPane pane = new GridPane(); // contains tiles
	  private Pane container=new Pane(); // contains the grid pain + the ball 
	  private StackPane tilePane[][]= new StackPane[4][4]; // Array that stores tiles as stackPanes
	  private Tiles[][] tiles=new Tiles[4][4]; // Array that stores tiles as Tile Objects 
	  private ImageView[][] imgs=new ImageView[4][4]; // Array that stores tiles as images 
	  private File inputFile; 
	  private final static DataFormat imgFormat =new DataFormat("Image");// Data format used to identify the data stored on the dragboard
	  private ImageView draggingimg;// the image dragged 
	  private Circle c=new Circle(); // the ball 
	  protected int i;// number of the level
	  protected Button next=new Button("Next Level"); // button for next level
	  protected boolean levelPassed; 
	  protected boolean GameOver;
	  protected boolean Lost;
	  protected Label stateText= new Label(); //Text that is displayed according to levelPassed,GameOver,Lost
	  protected int movLast; // number of movements last
	  protected Label movLastText= new Label(); // text of number of movements last
	 
	  //---------- Constructor ---------
	  public Level(String fileName,int i) {
		inputFile=new File(fileName);
		this.i=i;
		this.createGame();
		//set the buttons and the state Text t be invisible 
		this.next.setVisible(false);
		stateText.setVisible(false);
		stateText.setTextFill(Color.WHITE);
		
		//set the number of movements for each level 
		if(i==1)
			movLast=0;
		else if(i==2)
			movLast=4;
		else if(i==3)
			movLast=8;
		else if(i==4)
			movLast=6;
		else 
			movLast=7;
		// set MovLastText and style it 
		movLastText.setText("Mouvements last: "+movLast);
		movLastText.setTextFill(Color.ALICEBLUE);
		movLastText.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR,20));
	  }
	  
	  //--This method creates the game by setting the tiles according to file 
	  public void createGame() {
		try {
			pane.setStyle("-fx-background-color: black;");
			// Reading the file 
			Scanner file=new Scanner(inputFile);
			while(file.hasNextLine()) {
				String l=file.nextLine();
				String line=l.replace(',', ' ');
				if(!line.isEmpty()) {
					Scanner word=new Scanner(line);//Reading The line word by word
					ArrayList<String> tileInfo= new ArrayList<String>();
					while(word.hasNext()) 
						tileInfo.add(word.next()); // store words in tileInfo 
					
					int id=Integer.parseInt(tileInfo.get(0));
					
					// Store the tiles as Object
					Tiles tile=new Tiles(id,tileInfo.get(1),tileInfo.get(2));
					tiles[(id-1)/4][(id-1)%4]=tile;
					
					
					// Store the tiles as images 
					ImageView img=tile.displayTile();
			
					imgs[(id-1)/4][(id-1)%4]=img;
					
					// Store the tiles as StackPane
					StackPane sPane=new StackPane();
					sPane.setPrefHeight(100);
					sPane.setPrefWidth(100);
					sPane.getChildren().add(img);
					tilePane[(id-1)/4][(id-1)%4]=sPane;
					
					//Store the tiles on the GridPane
					pane.add(sPane,(id-1)%4,(id-1)/4);
				}
			}
			c.setRadius(10);
			c.setFill(Color.YELLOW);
			tilePane[0][0].getChildren().add(c);// add the ball to the first tile 
			StackPane.setAlignment(c, Pos.CENTER); // make it at the center
			
			// add Grid pane and ball to the container pane 
			container.getChildren().add(pane);
			container.getChildren().add(tilePane[0][0]);
			
			// Handle movement of tiles 
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(tiles[i][j].isDraggable()) {// check if tile is draggable  
						dragimg(imgs[i][j]);
						addDropHandling(tilePane[i][j],tilePane,imgs,tiles);
					}
				}
			}
			
	
	} catch (FileNotFoundException e) {
		
		System.out.println("There was an error while loading the file");
	}
	 
}
	
//---- This method makes a small tile-like img when dragging tile---- 
	 public void dragimg(ImageView b) {
	     b.setOnDragDetected(e -> {
		 Dragboard db = b.startDragAndDrop(TransferMode.MOVE);
		 db.setDragView(b.snapshot(null, null)); 
		 ClipboardContent cc = new ClipboardContent();
		 cc.put(imgFormat, " "); 
		 db.setContent(cc); 
		 draggingimg = b;	
  });
	 }
	
	 
//----This method handles drop of an image-----
	 public void addDropHandling(StackPane pane,StackPane[][] sPane,ImageView[][] imgs,Tiles[][]tiles) {
		 // set the icon displayed when drag 
		 pane.setOnDragOver(e -> {
		          Dragboard db = e.getDragboard();
		          if (db.hasContent(imgFormat) && draggingimg != null) {
		              e.acceptTransferModes(TransferMode.MOVE);
          		}
		 	});
		 // handle the drop of tile 
      		pane.setOnDragDropped(e -> {
	          Dragboard db = e.getDragboard();
	          if (db.hasContent(imgFormat) && !Lost) { // move tiles as long as not lost and tile is moved 
	        	  
	        	  /* check if tile is dropped in the appropriate place Thus.if it is: 
	        	   *  (sameRow and oneColumnAway) XOR (sameColumn and oneRowAway)*/
	        	  int rowPane=getRowtilePane (pane,sPane);
	        	  int columnPane=getColumntilePane (pane,sPane);
	        	  int rowImg=getRowImgs(draggingimg,imgs); 
	        	  int columnImg=getColumnImgs(draggingimg,imgs);
	        	  boolean sameRow=rowImg==rowPane;
	        	  boolean sameColumn=columnPane==columnImg;
	        	  boolean oneRowAway=(rowPane==(rowImg+1) || rowPane==(rowImg-1));
	        	  boolean oneColumnAway=(columnPane==(columnImg+1) ||columnPane==(columnImg-1));
	        	  if((sameRow && oneColumnAway) ^ (sameColumn && oneRowAway)) {
	        		  //check if the destination tile is free 
	        		  if(tiles[rowPane][columnPane].isFree() ||tiles[rowImg][columnImg].isFree()) {
	        			  //---- swap tiles as objects, images and stackPanes-------
			              sPane[rowImg][columnImg].getChildren().addAll(pane.getChildren());
			              pane.getChildren().add(draggingimg);
			              pane.getChildren().remove(pane.getChildren());
			              swapImgs(rowImg,columnImg, rowPane,columnPane,imgs);
			              swapTiles (rowImg, columnImg,rowPane,columnPane, tiles);
			        	  
			       //--------check if solution path is correct and display stateText 
			              if(isSolutionPath()) {
			            	  Animate();
			            	  if(i<5) {
			            		  levelPassed=true;
			            		  this.next.setVisible(true);
			            		  stateText.setText("Y o u  P a s s e d  T h i s  L e v e l !        ");
			            		  stateText.setVisible(true);
			            		  movLastText.setVisible(false);
			            		  }
			            	  else { 
			            		  GameOver=true;
			            		  stateText.setText("Y O U  W O N !                ");
			            		  stateText.setVisible(true);
			            		  
			            		  }  
			        //----Check if solutionPath is incorrect and display stateText and update movLast 
			              }else if( movLast > 0){
			            	  movLast--;
			            	  movLastText.setText("Mouvements last: "+movLast);
			              }else { 
			            	  stateText.setText("Y o u  Lost :(                    ");
			            	  stateText.setVisible(true);
			            	  Lost=true;
			            	  }
			            	  
			              e.setDropCompleted(true);   
				          draggingimg = null; 
	              }
	        	  }
          }           
      });  
  }
  //-------The animation pattern of the ball ------
	 public  void Animate() {
		 Path path=new Path();
		// set the start point of the path at the center of the ball 
		 path.getElements().add(new MoveTo(c.getCenterX(),c.getCenterY()));
		 // set the same path for the first three levels 
		 if(i>0 && i<4) {
			 CubicCurveTo cct=new CubicCurveTo();
			 cct.setX(300);
			 cct.setY(305);
			 cct.setControlX1(0);
			 cct.setControlY1(350);
			 cct.setControlX2(0);			
			 cct.setControlY2(310);
			 path.getElements().add(cct);
			 
		// set the same path for the last two levels
		 }else if (i > 3 && i < 6) {
			CubicCurveTo cct=new CubicCurveTo();
			CubicCurveTo cct2=new CubicCurveTo();
			cct.setX(200);
			cct.setY(200);
			cct.setControlX1(0);
			cct.setControlY1(220);
			cct.setControlX2(0);
			cct.setControlY2(200);
			cct2.setX(310);
			cct2.setY(90);
			cct2.setControlX1(300);
			cct2.setControlY1(220);
			cct2.setControlX2(300);
			cct2.setControlY2(200);
			path.getElements().add(cct);
			path.getElements().add(cct2);
		 }
		 PathTransition p=new PathTransition();
		 p.setNode(c);
		 p.setDuration(Duration.millis(2000));
		 p.setPath(path);
		 p.play(); // play the animation
	 }
	 
	 //To Test if the solution path is correct 
	 public boolean isSolutionPath() {
		 if(i>0 && i<4) {// solution  path for the first three levels is the same 
			 for(int j=0;j<3;j++) {
				 if(!(tiles[j][0].getProperty().equals("Vertical"))) {
					 return false;
				 }	 
			 }
			 if(tiles[3][0].getProperty().equals("01") && tiles[3][1].getProperty().equals("Horizontal")) {
				 return true;}

		 }else if (i > 3 && i < 6) {// solution  path for the last two levels is the same 
			 if(tiles[1][0].getProperty().equals("Vertical") && tiles[2][0].getProperty().equals("01")) {
				 if(tiles[2][1].getProperty().equals("Horizontal") && tiles[2][2].getProperty().equals("Horizontal") && tiles[2][3].getProperty().equals("00")) {
					 return true;
				 }
			 } 
		 }
		 return false;
	 }
	 
//-----Search methods-------------
	 
	 
	 public int getRowtilePane (StackPane pane,StackPane[][] sPane) {
	 for(int i=0;i<4;i++) {
		 for(int j=0;j<4;j++) {
			 if(pane==sPane[i][j])
				 return i;
		 }
	 }
	 return -1;
 }
	 public int getColumntilePane (StackPane pane,StackPane[][] sPane) {
	 for(int i=0;i<4;i++) {
		 for(int j=0;j<4;j++) {
			 if(pane==sPane[i][j])
				 return j;
		 }
	 }
	 return -1;
 }
	 public int getRowImgs (ImageView img,ImageView[][] imgs) {
	 for(int i=0;i<4;i++) {
		 for(int j=0;j<4;j++) {
			 if(img==imgs[i][j])
				 return i;
		 }
	 }
	 return -1;
 }
	 public int getColumnImgs (ImageView img,ImageView[][] imgs) {
	 for(int i=0;i<4;i++) {
		 for(int j=0;j<4;j++) {
			 if(img==imgs[i][j])
				 return j;
		 }
	 }
	 return -1;
 }
 public void swapImgs (int rowImg, int columnImg,int rowPane, int columnPane, ImageView[][] imgs) {
	 ImageView temp=imgs[rowImg][columnImg];
      imgs[rowImg][columnImg]=imgs[rowPane][columnPane];
      imgs[rowPane][columnPane]=temp;
 }
 
 public void swapTiles (int rowImg, int columnImg,int rowPane, int columnPane, Tiles[][] tiles) {
	 Tiles temp=tiles[rowPane][columnPane];
      tiles[rowPane][columnPane]=tiles[rowImg][columnImg];
      tiles[rowImg][columnImg]=temp;
 }
	
//----- getters and setters---------- 
public GridPane getPane() {
	return pane;
}
public void setPane(GridPane pane) {
	this.pane = pane;
}
public Pane getContainer() {
	return container;
}
public void setContainer(Pane container) {
	this.container = container;
}
public StackPane[][] getTilePane() {
	return tilePane;
}
public void setTilePane(StackPane[][] tilePane) {
	this.tilePane = tilePane;
}
public Tiles[][] getTiles() {
	return tiles;
}
public void setTiles(Tiles[][] tiles) {
	this.tiles = tiles;
}
public ImageView[][] getImgs() {
	return imgs;
}
public void setImgs(ImageView[][] imgs) {
	this.imgs = imgs;
}



}
