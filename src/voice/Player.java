package voice;

import main.ChatClient;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.SourceDataLine;

public class Player extends Thread{
    public DatagramSocket dtSocket;
    public SourceDataLine audioOut;
    byte[] buffer = new byte[512];
        @Override
        public void run(){
        Long pack = 0l;
        try {
            
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

            System.out.println("Server socket created. Waiting for incoming data...");
            while(ChatClient.calling){
                dtSocket.receive(incoming);
                buffer = incoming.getData();
                audioOut.write(buffer, 0, buffer.length);
                System.out.println("receive: #"+ pack++);
            }
            System.out.println("call in player: player is stop");
            audioOut.drain();
            audioOut.close();
            System.out.println("call in player: audio is drain and close");
        } catch (SocketException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
}
