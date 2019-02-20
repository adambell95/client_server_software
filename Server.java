import java.io.*;
import java.net.*;

public class Server {
	private static Socket socket;

	public static void main(String[] args) {
		try {
			// Connecting with the client
			int port = 6000;
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server Started and listening to the port 6000");
			
			int length;
			int vowelCount;

			// Server is running always. This is done using this while(true) loop
			while (true) {
				// Connecting to the client
				System.out.println("\nWaiting for client connection...");
				socket = serverSocket.accept();
				System.out.println("Client connected...\n");
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				
				while(true) {
					System.out.println("Waiting for client message...");
					// Reading the message from the client
					length = in.readInt();
					byte[] rmessage = new byte[256];
					in.readFully(rmessage, 0, length);
					String recvMessage = new String(rmessage);
					recvMessage = recvMessage.trim();
					System.out.println("Client message: " + recvMessage);
					if(recvMessage.equals("Bye"))
						break;
					System.out.println("Counting vowels...");
					vowelCount = countVowels(recvMessage);
					System.out.println("Vowels in message: " + vowelCount);
					
					// Sending the response back to the client
					String sendMessage = ""	 + vowelCount;
					byte[] smessage;
					smessage = sendMessage.getBytes();
					out.writeInt(smessage.length);
					out.write(smessage);
					out.flush();
					System.out.println("Vowel Count Sent to Client: " + sendMessage);
				}
				
				// Closing the connection
				socket.close();
				System.out.println("Client disconnected...");
			}
			//close afterwards
//			serverSocket.close();
		} catch (Exception e) {
			System.out.println("Server socket error!");
		}
	}
	
	private static int countVowels(String string) {
		int count = 0;
		for (int i = 0; i < string.length(); i++) {
			if(isVowel(string.charAt(i)))
				count++;
		}
		return count;
	}

	private static boolean isVowel(char ch) {
		ch = Character.toLowerCase(ch);
		return ch == 'a' ||ch == 'e' ||ch == 'i' ||ch == 'o' ||ch == 'u';
	}
}
