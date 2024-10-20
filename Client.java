package Upd;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("localhost");

        System.out.println("Connected to the server. You can start chatting (type 'exit' to quit):");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Send message to the server
            System.out.print("Enter your message: ");
            String msg = scanner.nextLine();
            DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(), msg.length(), ip, 9999);
            socket.send(sendPacket);

            // Exit if the user types 'exit'
            if (msg.equalsIgnoreCase("exit")) {
                break;
            }

            // Receive reply from the server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received from server: " + message);
        }

        socket.close();
    }
}
