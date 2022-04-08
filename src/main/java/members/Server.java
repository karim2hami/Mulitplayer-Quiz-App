package members;

import com.example.jplquiz.models.QuestionModel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;
    private ClientHandler clientHandler;
    private List<QuestionModel> questionModelList;

    public Server(ServerSocket serverSocket) {

        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new Client has connected");



                clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();

                readQuestions("src/main/resources/Questions/Questions.csv");

                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(questionModelList);

            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readQuestions(String filename) {
        List<QuestionModel> questions = new ArrayList<>();
        Path pathToFile = Paths.get(filename);

        try (BufferedReader bufferedReader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            String line = bufferedReader.readLine();

            while (line != null) {
                String[] attributes = line.split(";");

                QuestionModel questionModel = createQuestionModel(attributes);

                questions.add(questionModel);

                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        questionModelList = questions;
    }


    public static QuestionModel createQuestionModel(String[] data) {
        String question = data[0];
        String answerA = data[1];
        String answerB = data[2];
        String answerC = data[3];
        String answerD = data[4];
        String rightAnswer = data[5];

        return new QuestionModel(question, answerA, answerB, answerC, answerD, rightAnswer);

    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
