package utilitaire;

//import com.iniesta.chronometer.components.ChronometerComponent;

import com.sun.javafx.css.Style;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;


public class Chrono extends GridPane{
	private ChronometerComponent chronoComponent;
        private Button start;
        private Button stop;
	public void init(Duration duration) throws Exception {
		String style = "-fx-font: 12pt \"Arial\"; -fx-alignment:center; -fx-font-weight:bold;";
		
		chronoComponent = new ChronometerComponent(duration);
		chronoComponent.setChronoStyle(style);		
	
		this.setOnKeyPressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				
			}
		});
                this.getChildren().clear();
                this.setAlignment(Pos.CENTER);  
                this.getChronoComponent().setPrefHeight(30);
                chronoComponent.setAlignment(Pos.CENTER);
                this.add(chronoComponent, 0, 0);
                GridPane gp=new GridPane();
                
                gp.setHgap(5);
                gp.setVgap(20);
                gp.setAlignment(Pos.CENTER);
                start.setPrefWidth(75);
                start.setTextFill(Paint.valueOf("white"));
                start.setStyle("-fx-color:white; -fx-background-color:#3498db");
                start.setCursor(Cursor.HAND);
                stop.setPrefWidth(75);
                stop.setStyle("-fx-color:white; -fx-background-color:#e74c3c");
                stop.setTextFill(Paint.valueOf("white"));
                stop.setCursor(Cursor.HAND);
                gp.add(new Label(""), 0,0);
                gp.add(start, 1,0);
                gp.add(stop, 2,0);
                this.add(gp, 0, 1);
//                this.add(start, 0, 1);
//                this.add(stop, 0, 2);
                
	}

    public Button getStart() {
        return start;
    }

    public void setStart(Button start) {
        this.start = start;
    }

    public Button getStop() {
        return stop;
    }

    public void setStop(Button stop) {
        this.stop = stop;
    }

    public ChronometerComponent getChronoComponent() {
        return chronoComponent;
    }

    public void setChronoComponent(ChronometerComponent chrono) {
        this.chronoComponent = chrono;
    }
}