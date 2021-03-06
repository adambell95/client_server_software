import java.io.*;
import java.net.*;

public class Client {
	private static Socket socket;
	
	public static void main(String args[]) {
		try {
			// Connecting with the server
			String host = "localhost";// Both in the same machine [IP address 127.0.0.1]
			// String host = "129.120.151.94"; //IP address of server
			int port = 6000;
			InetAddress address = InetAddress.getByName(host);
			socket = new Socket(address, port);
			
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			int length;

			while(true) {
				// Sending the message to the server
				System.out.print("Enter message: ");
				String sendMessage = br.readLine();
				byte[] smessage = new byte[256];
				smessage = sendMessage.getBytes();
				out.writeInt(smessage.length);
				out.write(smessage);
				out.flush();
				System.out.println("Message sent to the server...");
				if(sendMessage.equals("Bye")) {
					System.out.println("Disconnecting and exiting...");
					break;
				}

				// Receiving the message from the server
				length = in.readInt();
				byte[] rmessage = new byte[256];
				in.readFully(rmessage, 0, length);
				String recvMessage = new String(rmessage);
				recvMessage = recvMessage.trim();
				System.out.println("Vowel count from server: " + recvMessage);
				System.out.println();
			}

			// Closing the connection
			socket.close();
		} catch (Exception e) {
			System.out.println("Client socket error!");
		}
	}
}
