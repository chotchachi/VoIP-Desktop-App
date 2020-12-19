package voice;

import main.ChatClient;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.TargetDataLine;

public class Recorder extends Thread{
        public TargetDataLine audioIn = null;
        public DatagramSocket dtSocket;
        byte[] buffer = new byte[512];
        public InetAddress receiveIP;
        public int reveivePort;
        @Override
        public void run(){
                Long pack = 0l;
                while(ChatClient.calling){
                    try {
                        int read = audioIn.read(buffer, 0, buffer.length);
                        DatagramPacket data = new DatagramPacket(buffer, buffer.length, receiveIP, reveivePort);
                        System.out.println("send: #"+ pack++);
                        dtSocket.send(data);
                    } catch (IOException ex) {
                        Logger.getLogger(Recorder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
               
                System.out.println("call in recorder: recorder is stop");
                audioIn.drain();
                audioIn.close();
                
                System.out.println("call in recorder: audio is drain and close");
            
        }
}
