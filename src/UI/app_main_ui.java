package UI;

import Model.Account;
import Model.DataSocket;
import main.ChatClient;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import main.Receive;

public class app_main_ui extends javax.swing.JFrame {
    public Clip clip;
    private Dimension dim;
    public String[] data_from_server;

    public app_main_ui() {
        this.dim = Toolkit.getDefaultToolkit().getScreenSize();
        initComponents();
        this.setLocation(dim.width - this.getSize().width - 50, 50);
        list_contact.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        btn_add_friend = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_contact = new javax.swing.JList<>();
        lb_display_name = new javax.swing.JLabel();
        lb_display_phone = new javax.swing.JLabel();
        btn_logout = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("jMenuItem2");
        jPopupMenu1.add(jMenuItem2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        setLocation(new java.awt.Point(0, 0));
        setResizable(false);
        setSize(new java.awt.Dimension(500, 780));

        btn_add_friend.setText("Quay số");
        btn_add_friend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_friendActionPerformed(evt);
            }
        });

        list_contact.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                list_contactMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(list_contact);

        lb_display_name.setBackground(new java.awt.Color(204, 204, 204));
        lb_display_name.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        lb_display_name.setText("Họ tên");
        lb_display_name.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lb_display_phone.setText("Số điện thoại");

        btn_logout.setText("Đăng xuất");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                    .addComponent(lb_display_name, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lb_display_phone)
                        .addGap(0, 405, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_add_friend)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_logout)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_display_name, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_display_phone)
                .addGap(43, 43, 43)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_add_friend)
                    .addComponent(btn_logout))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_add_friendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_friendActionPerformed
        ChatClient.quaysoUI.setVisible(true);
    }//GEN-LAST:event_btn_add_friendActionPerformed

    private void list_contactMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_list_contactMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.call(list_contact.getSelectedIndex());
        }
    }//GEN-LAST:event_list_contactMouseClicked

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        ObjectOutputStream objectOutputStream = null;
        try {
            OutputStream outputStream = ChatClient.socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            DataSocket dtsk = new DataSocket();
            dtsk.setAction("logout");

            objectOutputStream.writeObject(dtsk);

            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(app_main_ui.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                objectOutputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(app_main_ui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_logoutActionPerformed

    public void showcallnotify(Account guest) {
        System.out.println("Showcallnotify");
        
        ChatClient.notifyUI.txt_tennguoigoi.setText(guest.getName());
        ChatClient.notifyUI.txt_songuoigoi.setText(guest.getPhoneNumber());
        ChatClient.notifyUI.setLocation(dim.width / 2 - ChatClient.notifyUI.getWidth() / 2, dim.height / 2 - ChatClient.notifyUI.getHeight() / 2);
        ChatClient.notifyUI.setVisible(true);
        this.clip = this.playsound();
        this.clip.start();
    }

    public Clip playsound() {
        String file_path = "sound/ringer.wav";
        try {
            URL yourFile = getClass().getClassLoader().getResource(file_path);
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;

            stream = AudioSystem.getAudioInputStream(yourFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            Logger.getLogger(app_main_ui.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public void call(int index) {
        if(ChatClient.calling){
            JOptionPane.showMessageDialog(null, "Bạn đang thực hiện cuộc gọi khác!");
        } else {
            Account nguoinhan = ChatClient.accountList.get(index);
            ChatClient.nguoiNhan = nguoinhan;

            //request_call
            ChatClient.calling = true;

            init_call("request_call");
        }
    }

    public void tuchoi(){
        DataSocket dtsk = new DataSocket();
        dtsk.setAction("respon_call");
        dtsk.setNguoiGui(ChatClient.isMe);
        dtsk.setNguoiNhan(ChatClient.nguoiNhan);
        dtsk.setAccept(false);
        try {
            ObjectOutputStream dout = new ObjectOutputStream(ChatClient.socket.getOutputStream());
            dout.writeObject(dtsk);
            System.out.println("Đã gửi phản hồi: từ chối");
        } catch (IOException ex) {
            Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void init_call(String mode) {
        ChatClient.callUI.init_audio();
        ChatClient.callUI.init_player();

        int x, y;
        x = this.getLocation().x + this.getWidth();
        y = this.getLocation().y;
        if (x + ChatClient.callUI.getWidth() > dim.width) {
            x = this.getLocation().x - ChatClient.callUI.getWidth();
        }
        ChatClient.callUI.setLocation(x, y);
        ChatClient.callUI.setVisible(true);

        DataSocket dtsk = new DataSocket();
        dtsk.setAction(mode);
        dtsk.setAccept(true);
        dtsk.setNguoiGui(ChatClient.isMe);
        dtsk.setNguoiNhan(ChatClient.nguoiNhan);
        String data[] = new String[2];
        data[0] = ChatClient.myIP;
        data[1] = String.valueOf(ChatClient.myPort);
        dtsk.setData(data);
        ObjectOutputStream dout;
        try {
            dout = new ObjectOutputStream(ChatClient.socket.getOutputStream());
            dout.writeObject(dtsk);
        } catch (IOException ex) {
            Logger.getLogger(app_main_ui.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (mode.equals("respon_call")) {
            try {
                // nếu chấp chận thì khởi tạo phần thu âm
                ChatClient.callUI.init_recorder(InetAddress.getByName(this.data_from_server[0]), Integer.valueOf(this.data_from_server[1]));
                ChatClient.callUI.lb_status.setText("Đã kết nối");
            } catch (UnknownHostException ex) {
                Logger.getLogger(app_main_ui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(app_main_ui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new app_main_ui().setVisible(true);
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_friend;
    private javax.swing.JButton btn_logout;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lb_display_name;
    public javax.swing.JLabel lb_display_phone;
    public static javax.swing.JList<Account> list_contact;
    // End of variables declaration//GEN-END:variables

}
