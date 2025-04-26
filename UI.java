package BTL;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UI {
    public static void main(String[] args) {
        new UI().createUI();
    }

    public void createUI() {
        JFrame frame = new JFrame("Hệ thống đặt vé xe khách");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new CardLayout());


        // Panel đăng ký
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new BorderLayout(10, 10));
        registerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Thêm padding


        JLabel registerLabel = new JLabel("Đăng ký", SwingConstants.CENTER);
        JPanel registerForm = new JPanel(new GridLayout(5, 2, 10, 10));
        JTextField registerName = new JTextField();
        JPasswordField registerPassword = new JPasswordField();
        JTextField registerEmail = new JTextField();
        JTextField registerPhone = new JTextField();
        JButton registerButton = new JButton("Đăng ký");
        JButton switchToLogin = new JButton("Chuyển sang Đăng nhập");


        registerForm.add(new JLabel("Họ và tên:"));
        registerForm.add(registerName);
        registerForm.add(new JLabel("Mật khẩu:"));
        registerForm.add(registerPassword);
        registerForm.add(new JLabel("Email:"));
        registerForm.add(registerEmail);
        registerForm.add(new JLabel("Số điện thoại:"));
        registerForm.add(registerPhone);
        registerForm.add(registerButton);
        registerForm.add(switchToLogin);


        registerPanel.add(registerLabel, BorderLayout.NORTH);
        registerPanel.add(registerForm, BorderLayout.CENTER);


        // Panel đăng nhập
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BorderLayout(10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Thêm padding


        JLabel loginLabel = new JLabel("Đăng nhập", SwingConstants.CENTER);
        JPanel loginForm = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField loginUsername = new JTextField();
        JPasswordField loginPassword = new JPasswordField();
        JButton loginButton = new JButton("Đăng nhập");
        JButton switchToRegister = new JButton("Chuyển sang Đăng ký");


        loginForm.add(new JLabel("Email/SĐT:"));
        loginForm.add(loginUsername);
        loginForm.add(new JLabel("Mật khẩu:"));
        loginForm.add(loginPassword);
        loginForm.add(loginButton);
        loginForm.add(switchToRegister);


        loginPanel.add(loginLabel, BorderLayout.NORTH);
        loginPanel.add(loginForm, BorderLayout.CENTER);


        // Panel chương trình chính
        JPanel mainProgramPanel = new JPanel();
        mainProgramPanel.setLayout(new BorderLayout());
        JLabel mainLabel = new JLabel("Chào mừng đến với chương trình chính!", SwingConstants.CENTER);
        mainProgramPanel.add(mainLabel, BorderLayout.CENTER);


        // CardLayout để chuyển đổi giữa các panel
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        frame.add(registerPanel, "register");
        frame.add(loginPanel, "login");
        frame.add(mainProgramPanel, "mainProgram");


        // Sự kiện chuyển đổi giữa các panel
        switchToLogin.addActionListener(e -> cardLayout.show(frame.getContentPane(), "login"));
        switchToRegister.addActionListener(e -> cardLayout.show(frame.getContentPane(), "register"));


        // Sự kiện nút đăng ký
        registerButton.addActionListener(e -> {
            String name = registerName.getText();
            String password = new String(registerPassword.getPassword());
            String email = registerEmail.getText();
            String phone = registerPhone.getText();


            if (!name.isEmpty() && !password.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Đăng ký thành công!");
                cardLayout.show(frame.getContentPane(), "login");
            } else {
                JOptionPane.showMessageDialog(frame, "Vui lòng nhập đầy đủ thông tin!");
            }
        });


        // Sự kiện nút đăng nhập
        loginButton.addActionListener(e -> {
            String username = loginUsername.getText();
            String password = new String(loginPassword.getPassword());


            if (checkLogin(username, password)) {
                JOptionPane.showMessageDialog(frame, "Đăng nhập thành công!");
                cardLayout.show(frame.getContentPane(), "mainProgram"); // Chuyển sang giao diện chương trình chính
            } else {
                JOptionPane.showMessageDialog(frame, "Tên đăng nhập hoặc mật khẩu không đúng!");
            }
        });


        frame.setVisible(true);
    }


    private boolean checkLogin(String username, String password) {
        boolean isValid = false;
        String connectionUrl =
                "jdbc:sqlserver://1205-DELLPC\\SQLEXPRESS;"
                + "database=quanlyxekhach;"
                + "integratedSecurity=true;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";


        try (Connection conn = DriverManager.getConnection(connectionUrl)) {
            String query = "SELECT * FROM Khachhang WHERE (Email = ? OR SDT = ?) AND Matkhau = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, username);
            stmt.setString(3, password);


            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                isValid = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!");
        }


        return isValid;
    }
}




