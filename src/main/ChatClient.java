package main;

import Model.Account;
import UI.quayso_ui;
import UI.app_main_ui;
import UI.call_notify_ui;
import UI.call_ui;
import UI.login_ui;
import UI.register_ui;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatClient {
    public static int serverPort = 8888;
    public static String ipServer = "192.168.43.86";

    public static String myIP = "192.168.43.86";
    public static int myPort = 50000;

    public static Socket socket = null;
    public static boolean login = false;
        
    public static Account isMe;
    public static Account nguoiNhan;
    
    public static boolean connected = false;
    
    public static app_main_ui mainUI = null;
    public static login_ui loginUI = null;
    public static quayso_ui quaysoUI = null;
    public static register_ui regUI = null;
    public static call_ui callUI = null;
    public static call_notify_ui notifyUI = null;
        
    public static List<Account> accountList = new ArrayList<>();
    public static boolean calling = false;
    
    public static void main(String[] args) throws IOException {
        loginUI = new login_ui();
        mainUI = new app_main_ui();
        quaysoUI = new quayso_ui();
        regUI = new register_ui();
        notifyUI = new call_notify_ui();
        notifyUI.setTitle("Cuộc gọi đến");
        callUI = new call_ui();

        mainUI.setTitle("VOIP APPLICATION");
        loginUI.setVisible(true);
        
        ChatClient.socket = new Socket(ChatClient.ipServer, ChatClient.serverPort);
        Thread receive = new Receive(ChatClient.socket);
        receive.start();

        connected = true;
        System.out.println("Kết nối đến Server thành công");
    }
}
