package src.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientView {
    public JFrame frame;
    public Map<String, JTextArea> textAreas;
    public Map<String, JButton> buttons;

    public ClientView(List<String> toSubscribeTopics, String clientName, Client client)
    {
        frame = new JFrame(clientName);
        frame.setBounds(10,10,500,150);
        textAreas = new HashMap<String, JTextArea>();
        buttons = new HashMap<String, JButton>();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        JButton jButtonT = new JButton();
        jButtonT.setText("PassToken");
        jButtonT.addActionListener(new PassTokenAction(client));
        frame.getContentPane().add(jButtonT);
        for ( String s : toSubscribeTopics)
        {
            JPanel jPanel = new JPanel();
            jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));
            JTextArea jTextArea = new JTextArea();
            JScrollPane jScrollPane = new JScrollPane(jTextArea);


            JTextField jTextField = new JTextField();
            JButton jButton = new JButton();
            jButton.setText("Publish to "+s);
            jButton.addActionListener(new PublishAction(jTextField, s, client));
            jPanel.add(jTextField);
            jPanel.add(jButton);
            jPanel.add(jScrollPane);

            textAreas.put(s,jTextArea);
            buttons.put(s,jButton);
            frame.getContentPane().add(jPanel);


        }

    }

    public void initialize()
    {

        // Adds Button to content pane of frame
        frame.setVisible(true);
    }

    public void addText(String queue, String text)
    {
        JTextArea jTextArea = this.textAreas.get(queue);
        if( jTextArea != null)
        {
            jTextArea.setText(jTextArea.getText()+text);
        }
    }
    public void setEnableButtons(boolean state)
    {
        for(Map.Entry<String,JButton> entry : this.buttons.entrySet())
        {
            entry.getValue().setEnabled(state);
        }

    }
    public class PublishAction implements ActionListener
    {
        private JTextField jTextField;
        private String queueName;
        private Client client;

        public PublishAction(JTextField jTextField, String queueName, Client client) {
            this.jTextField = jTextField;
            this.queueName = queueName;
            this.client = client;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                String s = jTextField.getText();
                client.publish(queueName, s);
        }
    }
    public class PassTokenAction implements ActionListener
    {

        private Client client;

        public PassTokenAction( Client client) {

            this.client = client;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            client.passToken();
        }
    }
}
