import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
public class SessionLogGui implements ActionListener {
    JPanel bottomMenu;
    Font logFont = new Font("Monospaced",Font.ITALIC,11);
    Font exportStatus = new Font("Monospaced", Font.PLAIN,9);
    JButton exportLog;
    JLabel exportLabel;
    public static JTextArea logField;
    public SessionLogGui() {
        JFrame frame = new JFrame("Calculator Session Log");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel rootPanel = new JPanel();
            rootPanel.setLayout(new GridBagLayout());

        JPanel displayLog = new JPanel();
            displayLog.setLayout(new BorderLayout());

        logField = new JTextArea();
            logField.setEditable(false);
            logField.setFont(logFont);
            logField.setMargin(new Insets(7,7,7,7));

        JScrollPane logScrollPane = new JScrollPane(logField);
        GridBagConstraints logScrollPaneConstraints = new GridBagConstraints();
            logScrollPaneConstraints.gridx = 0;
            logScrollPaneConstraints.gridy = 0;
            logScrollPaneConstraints.weightx = 1.0;
            logScrollPaneConstraints.weighty = 1.0;
            logScrollPaneConstraints.fill = GridBagConstraints.BOTH;

        bottomMenu = new JPanel();
        bottomMenu.setLayout(new GridBagLayout());
        GridBagConstraints bottomMenuConstraints = new GridBagConstraints();
            bottomMenuConstraints.gridx = 0;
            bottomMenuConstraints.gridy = 1;
            bottomMenuConstraints.weightx = 1.0;
            bottomMenuConstraints.weighty = 0.1;
            bottomMenuConstraints.fill = GridBagConstraints.HORIZONTAL;

        exportLog = new JButton("Export log");
            exportLog.setFocusable(false);
            exportLog.addActionListener(this);
        GridBagConstraints exportConstraints = new GridBagConstraints();
            exportConstraints.fill = GridBagConstraints.BOTH;
            exportConstraints.gridx = 0;
            exportConstraints.gridy = 0;
            exportConstraints.anchor = GridBagConstraints.PAGE_START;

        bottomMenu.add(exportLog, exportConstraints);

        exportLabel = new JLabel("Export status: Not exported.");
        exportLabel.setFont(exportStatus);
        GridBagConstraints labelConstraints = new GridBagConstraints();
            labelConstraints.fill = GridBagConstraints.BOTH;
            labelConstraints.gridx = 0;
            labelConstraints.gridy = 1;
            labelConstraints.anchor = GridBagConstraints.PAGE_START;


        bottomMenu.add(exportLabel, labelConstraints);

        rootPanel.add(logScrollPane, logScrollPaneConstraints);
        rootPanel.add(bottomMenu, bottomMenuConstraints);

        logField.setText(SessionLog.logGui());

        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(500,350));
        frame.setMaximumSize(new Dimension(500,350));
        frame.add(rootPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public static void setLogField(){
        logField.setText(SessionLog.logGui());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==exportLog){
            if (SessionLog.exportLog() && exportLabel != null){
                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
                String time = currentTime.format(formatterTime);

                String userHome = System.getProperty("user.home");
                exportLabel.setText("Log exported " + time + " to: " + userHome);
            }
        }
    }
}
