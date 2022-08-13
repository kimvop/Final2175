import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoanP {
    private JTextField txtn0;
    private JTextField txtname;
    private JTextField txtloan;
    private JTextField txtyears;
    private JComboBox cbtype;
    private JTable table1;
    private JTable table2;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField txtpayment;
    private JPanel Main;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Loan Projection");
        frame.setContentPane(new LoanP().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect() {
        final String DB_URL = "jdbc:mysql://localhost/loan";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Success");

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    //Load Table
    void generateTable() {
        try {
            pst = con.prepareStatement("select * from loantable");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public LoanP() {
        connect();
        //generateTable();

        //add client

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientno, clientname, loanamount, years, loantype;

                clientno = txtn0.getText();
                clientname = txtname.getText();
                loanamount = txtloan.getText();
                years = txtyears.getText();
                loantype = (String) cbtype.getSelectedItem();

                try {
                    pst = con.prepareStatement("INSERT INTO loan (clientno, clientname, loanamount, years, loantype ) value(?,?,?,?,?)");
                    pst.setString(1, clientno);
                    pst.setString(2, clientname);
                    pst.setDouble(3, Double.parseDouble(loanamount));
                    pst.setInt(4, Integer.parseInt(years));
                    pst.setString(5, loantype);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record added !");

                    txtn0.setText("");
                    txtname.setText("");
                    txtloan.setText("");
                    txtyears.setText("");
                    cbtype.setSelectedItem("");

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }


            }
        });

        //Edit client
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientno, clientname, loanamount, years, loantype;

                clientno = txtn0.getText();
                clientname = txtname.getText();
                loanamount = txtloan.getText();
                years = txtyears.getText();
                loantype = (String) cbtype.getSelectedItem();

                try {
                    pst = con.prepareStatement(("update client set clientno = ? , clientname = ? , loanamount = ?, years = ?," +
                            " loantype = ? where clientno = ? "));
                    pst.setString(1, clientno);
                    pst.setString(2, clientname);
                    pst.setDouble(3, Double.parseDouble(loanamount));
                    pst.setInt(4, Integer.parseInt(years));
                    pst.setString(5, loantype);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record edited !");

                    txtn0.setText("");
                    txtname.setText("");
                    txtloan.setText("");
                    txtyears.setText("");
                    cbtype.setSelectedItem("");

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientno;
                clientno = txtn0.getText();
                try {
                    pst = con.prepareStatement("delete from loan where clientno = ? ");
                    pst.setString(1, clientno);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record deleted !");

                    generateTable();
                    txtn0.setText("");
                    txtname.setText("");
                    txtloan.setText("");
                    txtyears.setText("");
                    cbtype.setSelectedItem("");

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }


            }
        });

    }
}



