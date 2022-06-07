package com.example.jplquiz.controller;

import com.example.jplquiz.models.QuestionModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import members.Client;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author devinhasler ClientQuestionView: Controller Class of client-questionView.fxml Displays the
 * game and questions to the player Handles Validataion of the players answers Sends the players
 * score back to the server.
 */
@SuppressWarnings("DuplicatedCode")
public class ClientQuestionView implements Initializable {

    @FXML
    private Button btnA;
    @FXML
    private Button btnB;
    @FXML
    private Button btnC;
    @FXML
    private Button btnD;
    @FXML
    private ImageView imgQuestion;
    @FXML
    private Label lbCountDown;
    @FXML
    private Label lbPlayerPoints;
    @FXML
    private Label lbQuestion;
    @FXML
    private Label lbQuestionCounter;

    private Client client;
    private List<QuestionModel> questionModels;
    private Socket socket;
    private int playerScore = 0;
    private int questionsNumber = 1;
    private String rightAnswer;
    private Timer timer;

    /**
     * @param url            - The location used to resolve relative paths for the root object, or null if the
     *                       location is not known.
     * @param resourceBundle - The resources are used to localize the root object, or null if the root
     *                       object was not localized.
     * @author karimtouhami Method initialize overrides the method from the Initializable Interface of
     * the javafx.fxml package. The method is called to initialize the ClientQuestionView
     * Controller after its root element has been completely processed.
     * <p>Initialize a Timer object Add all event listeneners to the GUI buttons
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // initialize Timer
        timer = new Timer();
        countDownTimer();

        // initialize all buttons and their corresponding event listeners
        btnA.setOnMouseClicked(
                actionEvent -> {
                    System.out.println("Button A was pressed...");
                    // Validate value
                    if (btnA.getText().equals(rightAnswer)) {
                        playerScore += Integer.parseInt(lbCountDown.getText()) * 100;
                        lbPlayerPoints.setText(String.valueOf(playerScore));
                    }
                    countdownSolutionTimer();

                    // Stop Timer and initialize a new one
                    timer.cancel();
                    timer = new Timer();
                    // loadNewQuestion
                });

        btnB.setOnMouseClicked(
                actionEvent -> {
                    System.out.println("Button B was pressed...");
                    // Validate value
                    if (btnB.getText().equals(rightAnswer)) {
                        playerScore += Integer.parseInt(lbCountDown.getText()) * 100;
                        lbPlayerPoints.setText(String.valueOf(playerScore));
                    }
                   countdownSolutionTimer();

                    // Stop Timer and initialize a new one
                    timer.cancel();
                    timer = new Timer();
                    // loadNewQuestion
                });

        btnC.setOnMouseClicked(
                actionEvent -> {

                    // Validate value
                    if (btnC.getText().equals(rightAnswer)) {
                        playerScore += Integer.parseInt(lbCountDown.getText()) * 100;
                        lbPlayerPoints.setText(String.valueOf(playerScore));
                    }
                    countdownSolutionTimer();
                    // Stop Timer and initialize a new one
                    timer.cancel();
                    timer = new Timer();
                    // loadNewQuestion
                });

        btnD.setOnMouseClicked(
                actionEvent -> {
                    // Validate value
                    if (btnD.getText().equals(rightAnswer)) {
                        playerScore += Integer.parseInt(lbCountDown.getText()) * 100;
                        lbPlayerPoints.setText(String.valueOf(playerScore));
                    }
                    countdownSolutionTimer();
                    // Stop Timer and initialize a new one

                    timer.cancel();
                    timer = new Timer();
                    // loadNewQuestion
                });
    }


    /**
     * @author devinhasler
     * gives the GUI Buttons the right colors so that the user recieves a recall for his answer
     */
    public void showColorSolution() {
        String greenColor = "-fx-background-color: green";
        String redColor = "-fx-background-color: red";

        if (btnA.getText().equals(rightAnswer)) {
            btnA.setStyle(greenColor);
            btnB.setStyle(redColor);
            btnC.setStyle(redColor);
            btnD.setStyle(redColor);
        } else if (btnB.getText().equals(rightAnswer)) {
            btnA.setStyle(redColor);
            btnB.setStyle(greenColor);
            btnC.setStyle(redColor);
            btnD.setStyle(redColor);
        } else if (btnC.getText().equals(rightAnswer)) {
            btnA.setStyle(redColor);
            btnB.setStyle(redColor);
            btnC.setStyle(greenColor);
            btnD.setStyle(redColor);
        } else {
            btnA.setStyle(redColor);
            btnB.setStyle(redColor);
            btnC.setStyle(redColor);
            btnD.setStyle(greenColor);
        }
    }

    /**
     * @author devinhasler sendAnswersToServer: Sets up an Outputstream of type object and writes the
     * answers of the user to the stream.
     */
    @FXML
    public void sendNamePointsString() {

        try {
            client.sendNamesAndPoints();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Sending of Answers failed");
        }
    }

    /**
     * @author karimtouhami loadQuestionFromList: Loads one question element out of the List
     * questionModels, updates the GUI, starts a new countdown timer, increments the
     * questioncounter.
     */
    @FXML
    public void loadQuestionFromList() {
        btnA.setStyle("-fx-background-color: #FF006E");
        btnB.setStyle("-fx-background-color: #3A86FF");
        btnC.setStyle("-fx-background-color: #FFBE0B");
        btnD.setStyle("-fx-background-color: #FB5607");

        if (questionsNumber < questionModels.size()) {
            lbQuestionCounter.setText((questionsNumber) + " von " + (questionModels.size() - 1));
            QuestionModel questionModel = questionModels.get(questionsNumber);
            lbQuestion.setText(questionModel.getQuestion());
            btnA.setText(questionModel.getAnswerA());
            btnB.setText(questionModel.getAnswerB());
            btnC.setText(questionModel.getAnswerC());
            btnD.setText(questionModel.getAnswerD());
            rightAnswer = questionModel.getRightAnswer();
            imgQuestion.setImage(new Image(
                    String.valueOf(getClass().getResource(questionModel.getImagePath()))));
            countDownTimer();
            questionsNumber++;

        } else {
            // send answers back to server...
            sendNamePointsString();
            Platform.runLater(this::changeToServerRanking);
        }
    }

    /**
     * @author karimtouhami countDownTimer: Sets up a new TimerTask of a fixed rate of 1 second to
     * count down from 31 and updates the counter in the GUI.
     */
    @FXML
    public void countDownTimer() {
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    int count = 31;

                    @Override
                    public void run() {

                        if (count > 0) {
                            Platform.runLater(() -> lbCountDown.setText(String.valueOf(count)));
                            count--;
                        } else {
                            timer.cancel();


                        }
                    }
                },
                0,
                1000);
    }


    /**
     * @author devinhasler
     * Creates a Timer that shows the right and wrong answers via GUI for 2 seconds
     */
    @FXML
    public void countdownSolutionTimer() {

        btnA.setDisable(true);
        btnB.setDisable(true);
        btnC.setDisable(true);
        btnD.setDisable(true);

        // color button in red when answer was wrong and in green when answer was right.
        showColorSolution();

        Timer solutionTimer = new Timer();
        solutionTimer.schedule(new TimerTask() {

            @Override
            public void run() {

                Platform.runLater(() -> {
                    loadQuestionFromList();

                    btnA.setDisable(false);
                    btnB.setDisable(false);
                    btnC.setDisable(false);
                    btnD.setDisable(false);
                });

            }
        }, 2000);


    }


    /**
     * @author karimtouhami
     * Changes UI from ClientQuestionView to ServerRanking and initialises listenForRankings
     */
    @FXML
    void changeToServerRanking() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/jplquiz/server-ranking.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ServerRanking serverRanking = fxmlLoader.getController();
            serverRanking.setClient(client);
            serverRanking.setSocket(socket);
            serverRanking.listenForPoints();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Multiplayer Quiz App");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage clientQuestionView = (Stage) btnA.getScene().getWindow();
        clientQuestionView.close();
    }

    public void setQuestionModels(List<QuestionModel> questionModels) {
        this.questionModels = questionModels;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public int getPlayerScore() {
        return playerScore;
    }
}
