package votingbooth.gui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import votingbooth.Booth;
import votingbooth.Clock;
import votingbooth.VoterProducer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by kylef_000 on 10/12/2016.
 */
public class BasicSimulatorController extends AnimationTimer implements Initializable {


    @FXML private TextField secondsToNext;
    @FXML private TextField avgCheckInTime;
    @FXML private TextField totalTime;
    @FXML private TextField avgVotingTime;
    @FXML private TextField secondsBeforeLeave;
    @FXML private TextField boothCount;

    private Button startSim;
    private Button stopSim;

    private Clock clk;
    private Booth booth;

    private int numOfTicksNextPerson = 20;
    private int averageBoothTime = 20;
    
    private int nextPerson = 0;
    private int avgSecondsCheckIn = 0;
    private int totalTimeSec = 0;
    private int avgSecondsVoting = 0;
    private int secondsBeforeLeaves = 0;
    private int booths = 0;
    private int throughput = 0;
    
    private VoterProducer produce;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        clk = new Clock();
        booth = new Booth();
        produce = new VoterProducer(booth, 20, 18);

        clk.add(produce);
        clk.add(booth);

        clk.run(10000);

        start();
    }
    /***********************************************
     * This will link the Start Button to the Logic in the back end
     * For this we are re-assigning the variables to what is
     * stored in the text fields -JP
     * @return void
     ***********************************************/
    
    public void startSimulation(){
    	//Enter Number of People
    	
    	nextPerson = Integer.parseInt(secondsToNext.getText());
    	avgSecondsCheckIn = Integer.parseInt(avgCheckInTime.getText());
    	totalTimeSec = Integer.parseInt(totalTime.getText());
    	avgSecondsVoting = Integer.parseInt(avgVotingTime.getText());
    	secondsBeforeLeaves = Integer.parseInt(secondsBeforeLeave.getText());
    	booths = Integer.parseInt(boothCount.getText());
    	outputInformation();
    }
    /**********************************************
     * Prints the output information
     * @return void
     *********************************************/
    private void outputInformation() {
		//Print throughput
    	/**
    	 * Total number of people passing through the voting booths, I feel like this
    	 * is calculated from the other variables that the user enters
    	 */
    	//Test for git integration with IJ -- Ignore this comment
    	
		
    	
	}
	@Override
    public void handle(long now) {
    	
    }
    
    /**************************************************
     * Methods to do the Input Information
     * 
     **************************************************/
    
}
