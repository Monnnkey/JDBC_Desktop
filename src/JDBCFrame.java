import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCFrame extends JFrame {
    private int ID = 0;
    private static final String URL = "jdbc:mysql://localhost:3306/firma";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public JDBCFrame(){
        JPanel northPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        northPanel.setLayout(gridBagLayout);

        gbc.insets = new Insets(10,10,10,10);
        JLabel nameLabel = new JLabel("Imię");
        JLabel surnameLabel= new JLabel("Naziwsko");
        JLabel positionLabel = new JLabel("Stanowisko");

        gbc.gridx = 0;
        gbc.gridy = 0;
        northPanel.add(nameLabel,gbc);

        gbc.gridx = 1;
        northPanel.add(surnameLabel,gbc);

        gbc.gridx = 2;
        northPanel.add(positionLabel,gbc);

        JTextField nameText = new JTextField(15);
        JTextField surnameText= new JTextField(15);
        JTextField positionText = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 1;
        northPanel.add(nameText,gbc);

        gbc.gridx = 1;
        northPanel.add(surnameText,gbc);

        gbc.gridx = 2;
        northPanel.add(positionText,gbc);

        JButton btn = new JButton("Dodaj pracownika");
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        northPanel.add(btn,gbc);

        String[] columnsName = {"Id","Imię","Nazwisko","Stanowisko"};
        DefaultTableModel model = new DefaultTableModel(columnsName,0);
        JTable table = new JTable();
        table.setModel(model);

        btn.addActionListener(e -> {

            String name = nameText.getText().trim();
            String surname = surnameText.getText().trim();
            String position = positionText.getText().trim();

            String sql = "INSERT INTO pracownicy (imie,nazwisko,stanowisko) VALUES(?,?,?)";

            try (Connection conn = getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, name);
                statement.setString(2, surname);
                statement.setString(3, position);

                statement.executeUpdate();

                model.addRow(new Object[]{ID, name, surname, position});
                ID++;

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        add(northPanel,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);
        setSize(600,450);
        setVisible(true);
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }

    static void main() {
        new JDBCFrame();
    }
}
