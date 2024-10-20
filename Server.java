package Upd;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 9999;
    private static Queue<String> messageQueue = new LinkedList<>();
    private static Map<InetAddress, Integer> users = new HashMap<>();

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(PORT);
        System.out.println("Server is running on port " + PORT);
        
        while (true) {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            // Store user address in HashMap
            users.put(clientAddress, clientPort);
            messageQueue.add(message);  // Add message to the queue
            System.out.println("Received: " + message);

            if (message.equalsIgnoreCase("exit")) {
                System.out.println("Exiting server...");
                break;
            }

            System.out.println("Enter your reply:");
            Scanner scanner = new Scanner(System.in);
            String reply = scanner.nextLine();

            DatagramPacket sendPacket = new DatagramPacket(reply.getBytes(), reply.length(), clientAddress, clientPort);
            socket.send(sendPacket);
        }

        socket.close();
    }
}
