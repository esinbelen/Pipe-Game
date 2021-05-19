package application;
//Nesrine Lagraa 150118921
//Esin Belen 150119025
//This class creates the tiles as objects with the appropriate image,id,type,property 
// it has draggable and free data fields that are useful for Level class
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Tiles {
	private int id;
	private String type;
	private String property;
	private boolean draggable; // determines whether the tile can be dragged 
	private boolean free; // determines whether the tile is free
	
	// Constructors and methods
	public Tiles(int id,String type,String property) {
		this.id=id;
		this.type=type;
		this.property=property;
	}
	
	// Assign the appropriate picture to the appropriate tile and return imageView of the tile 
	public ImageView displayTile() {
		switch(type){
			case"Starter":
				if(property.equals("Vertical")) {
					return new ImageView(new Image("StartVer.png"));
				}else if(property.equals("Horizontal")) {
					return new ImageView(new Image("StartHor.png"));
				}
				break;
			case"Empty":
				draggable=true;
				if(property.equals("none")) {
					return new ImageView(new Image("Empty.png"));
				}else if(property.equals("Free")) {
					free=true;
					return new ImageView(new Image("EmptyFree.png"));
				}
				break;
			case"Pipe":
				draggable=true;
				if(property.equals("Vertical")) {
					return new ImageView(new Image("PipeVer.png"));
				}else if(property.equals("Horizontal")) {
					return new ImageView(new Image("PipeHor.png"));
				}else if(property.equals("00")) {
					return new ImageView(new Image("CurvedPipe00.png"));
				}else if(property.equals("01")) {
					return new ImageView(new Image("CurvedPipe01.png"));
				}else if(property.equals("10")) {
					return new ImageView(new Image("CurvedPipe10.png"));
				}else if(property.equals("11")) {
					return new ImageView(new Image("CurvedPipe11.png"));
				}
				break;
			case"PipeStatic":
				if(property.equals("Vertical")) {
					return new ImageView(new Image("PipeStaticVer.png"));
				}else if(property.equals("Horizontal")) {
					return new ImageView(new Image("PipeStaticHor.png"));
				}else if(property.equals("01")) {
					return new ImageView(new Image("PipeStatic01.png"));
				}
				break;
			case"End":
				if(property.equals("Vertical")) {
					return new ImageView(new Image("EndVer.png"));
				}else if(property.equals("Horizontal")) {
					return new ImageView(new Image("EndHor.png"));
				}
				break;
			
		}
		return null;
	}
	
	//---Get and set methods-------
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public boolean isDraggable() {
		return draggable;
	}
	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}
	public boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}
	
	
}
