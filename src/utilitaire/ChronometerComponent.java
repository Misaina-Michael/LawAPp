
package utilitaire;



import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * @author antonio
 *
 */
public class ChronometerComponent extends HBox{

	private Label labelSeconds;
	private Label labelMinutes;
	private Label labelHours;        
	private long timeCounter;
	private Timeline timeline;

	private Label semicolon2;
	private Label semicolon3;

	/**
	 * Chronometer with time 0
	 */
	public ChronometerComponent(){
		this(Duration.seconds(0));
                
	}
	
	/**
	 * Chronometer with the given time
	 * @param duration
	 */
	public ChronometerComponent(Duration duration){		
		timeCounter = (long)duration.toSeconds();
	
		
		semicolon2 = new Label(":");		
		semicolon3 = new Label(":");		
		
		labelSeconds = new Label();
		labelMinutes = new Label();
		labelHours = new Label();
		updateLabels(timeCounter);
		getChildren().addAll(labelHours, semicolon3, labelMinutes, semicolon2, labelSeconds);		
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				updateLabels(timeCounter++);				
			}}));
		timeline.setCycleCount(Timeline.INDEFINITE);
	}
	
	/**
	 * Get the time represented in this chronometer
	 * @return
	 */
	public long getTime(){
		return timeCounter;
	}
	
	/**
	 * Get the time of the chronometer in Duration
	 * @return
	 */
	public Duration getDuration(){
		return Duration.seconds(timeCounter);
	}
	
	/**
	 * Set the time of this chronometer
	 * @param time
	 */
	public void setTime(long time){
		this.timeCounter = time;
		updateLabels(timeCounter);
	}
	
	/**
	 * set the duration 
	 * @param time
	 */
	public void setDuration(Duration time){
		this.timeCounter = (long)time.toSeconds();
		updateLabels(timeCounter);
	}
	
	/**
	 * Play the chronometer
	 */
	public void play(){
		timeline.play();
	}
	
	/**
	 * Stop the chronometer 
	 */
	public void stop(){
		timeline.stop();
	}
	
	/**
	 * Reset the chronometer
	 */
	public void reset(){
		stop();
		timeCounter = 0L;
	}
	/**
	 * Update the labels with the timeCounter, time in the chronometer in milliseconds
	 * @param timeCounter
	 */
	protected void updateLabels(long timeCounter) {
	
		long seconds = (timeCounter)%60;
		long minutes = (timeCounter/60)%60;
		long hours = (timeCounter/3600)%60;
		
		String sSec = seconds<10?("0"+seconds):(""+seconds);
		String sMin = minutes<10?("0"+minutes):(""+minutes);
		String sHour = hours<10?("0"+hours):(""+hours);
		
		labelSeconds.setText(sSec);
		labelMinutes.setText(sMin);
                labelHours.setText(sHour);
	}

	public void setChronoStyle(String style) {
		
		labelMinutes.setStyle(style);
		labelSeconds.setStyle(style);
		labelHours.setStyle(style);
		
		semicolon3.setStyle(style);
		semicolon2.setStyle(style);
	}

}