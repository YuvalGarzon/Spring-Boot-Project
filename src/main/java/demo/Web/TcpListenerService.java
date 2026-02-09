package demo.Web;

import demo.BusinessLogic.ObjectLogic;
import demo.Controller.ObjectBoundary;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

@Service
public class TcpListenerService {

    private boolean serverStarted = false;
    private ServerSocket serverSocket;

    public TcpListenerService() {

    }

    public void startSimulation(ObjectBoundary activity, ObjectLogic objectLogic) {
        if (serverStarted) {
            System.out.println("TCP Server is already running.");
            return;
        }


        Thread tcpServerThread = new Thread(() -> {
            try {
                Map<String, Object> details;
                details = activity.getDetails();
                serverSocket = new ServerSocket(5050);
                System.out.println("Java TCP Server listening on port 5050");

                while (!serverSocket.isClosed()) {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        int currentReps = (int) details.get("reps");
                        details.put("reps", currentReps + 1);
                        activity.setDetails(details);
                        String objId = activity.getId().getObjectId();
                        String userSystemId = activity.getCreatedBy().getSystemID();
                        String userEmail = activity.getCreatedBy().getEmail();
                        objectLogic.update(objId, activity, userSystemId, userEmail);
                        System.out.println("Received from Unity: " + inputLine);
                        WebSocketHandler.broadcast(inputLine);
                    }

                    clientSocket.close();
                }

            } catch (SocketException e) {
                System.err.println("TCP status: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("TCP IO Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected Error in TCP Server: " + e.getMessage());
            }
        });

        tcpServerThread.setDaemon(true);
        tcpServerThread.start();
        serverStarted = true;
    }

    public void stopSimulation() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("TCP Server has been stopped.");
            }
            serverStarted = false;
        } catch (Exception e) {
            System.err.println("Failed to stop TCP server: " + e.getMessage());
        }
    }
}