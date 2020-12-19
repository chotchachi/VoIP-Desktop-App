package main;

import Model.Account;
import Model.DataSocket;
import UI.app_main_ui;
import UI.item_contact_list;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class Receive extends Thread {
    private Socket socket = null;
    private ObjectInputStream ois = null;
    private DataSocket respon = null;

    public Receive(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ois = new ObjectInputStream(this.socket.getInputStream());
            while (true) {
                respon = (DataSocket) ois.readObject();
                System.out.println(respon.getAction());
                switch (respon.getAction()) {
                    case "loginok":
                        loginOK(respon);
                        break;
                    case "loginwrong":
                        loginWrong();
                        break;
                    case "register":
                        register();
                        break;
                    case "registerok":
                        registerOK();
                        break;
                    case "receiveContact":
                        loadContact(respon);
                        break;
                    case "respon_call":
                        responCall(respon);
                        break;
                    case "request_call":
                        requestCall(respon);
                        break;
                    case "endcall":
                        endCall(respon);
                        break;
                    default:
                        System.out.println("Unknown action");
                }
            }
        } catch (IOException ex) {
            //Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void requestCall(DataSocket data) {
        System.out.println("Đã nhận yêu cầu cuộc gọi");
        ChatClient.nguoiNhan = data.getNguoiGui();
        if (ChatClient.calling) { // client đang thực hiện cuộc gọi 
            ChatClient.mainUI.tuchoi();
        } else {
            ChatClient.mainUI.showcallnotify(data.getNguoiGui());
            ChatClient.mainUI.data_from_server = data.getData();
            ChatClient.calling = true;
        }
    }

    public void responCall(DataSocket data) throws UnknownHostException {
        System.out.println(data.getNguoiGui().getPhoneNumber()+" đã gửi phản hồi yêu cầu");
        
        // Từ chối
        if (!data.isAccept()) {
            if(ChatClient.callUI != null){
                try {
                    ChatClient.callUI.lb_status.setText("Đã từ chối");
                    Thread.sleep(2000);
                    ChatClient.callUI.setVisible(false);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ChatClient.nguoiNhan = null;
            ChatClient.calling = false;
        // Đồng ý    
        } else {
            ChatClient.callUI.lb_status.setText("Đã kết nối");
            ChatClient.callUI.init_recorder(InetAddress.getByName(data.getData()[0]), Integer.valueOf(data.getData()[1]));
        }
    }

    public void loginOK(DataSocket respon) throws IOException {
        ChatClient.isMe = respon.getNguoiGui();

        ChatClient.loginUI.setVisible(false);

        ChatClient.mainUI.lb_display_name.setText(ChatClient.isMe.getName());
        ChatClient.mainUI.lb_display_phone.setText("Số điện thoại: " + ChatClient.isMe.getPhoneNumber());
        ChatClient.mainUI.setVisible(true);
        
        loadContactByPhone(ChatClient.isMe.getPhoneNumber());
    }

    private void loadContactByPhone(String phone) throws IOException {
        OutputStream outputStream = ChatClient.socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        DataSocket dtsk = new DataSocket();
        dtsk.setAction("loadcontact");
        String[] data = new String[1];
        data[0] = phone;
        dtsk.setData(data);

        objectOutputStream.writeObject(dtsk);
    }

    public void loadContact(DataSocket respon) {
        ChatClient.accountList = respon.getAccountList();
        this.showContact(ChatClient.accountList);
    }

    public void showContact(List<Account> data) {
        int size = data.size();
        DefaultListModel<Account> defaultListModel = new DefaultListModel<>();
        for (int i = 0; i < size; i++) {
            defaultListModel.addElement(data.get(i));
        }
        app_main_ui.list_contact.setModel(defaultListModel);
        app_main_ui.list_contact.setCellRenderer(new item_contact_list());
    }

    private void register() {
        ChatClient.regUI.setVisible(true);
    }

    private void loginWrong() {
        JOptionPane.showMessageDialog(null, "Sai tên đăng nhập hoặc mật khẩu");
    }

    private void registerOK() {
        JOptionPane.showMessageDialog(null, "Đăng ký thành công");
        ChatClient.regUI.setVisible(false);
    }

    private void endCall(DataSocket respon) {
        if(respon.getNguoiGui().getPhoneNumber().equals(ChatClient.nguoiNhan.getPhoneNumber())){
            ChatClient.calling = false;
            if(ChatClient.mainUI.clip != null){
                ChatClient.mainUI.clip.stop();
            }
            if(ChatClient.callUI != null){
                ChatClient.callUI.setVisible(false);
            }
            if(ChatClient.notifyUI != null){
                ChatClient.notifyUI.setVisible(false);
            }
        }
    }
}
