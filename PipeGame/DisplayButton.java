package application;
//Nesrine Lagraa 150118921
//Esin Belen 150119025
// This method aims to create a button with a certain CSS 
//We had to add a class because adding a Style.css file did not work well
import javafx.scene.control.Button;
public class DisplayButton {
	protected Button b;
	public DisplayButton(String s) {
		b=new Button(s);
		b.setStyle("-fx-background-color: #653f3f;"+"-fx-text-fill:white; "
		);
		b.setOnMouseEntered(e-> {
			b.setStyle("-fx-background-color: #b36666;"
					+"-fx-text-fill:white; "
					);
			}
			);
		b.setOnMouseExited(e-> {
			b.setStyle("-fx-background-color: #653f3f;"
					+"-fx-text-fill:white; "
					
					);
			}
			);

}}
