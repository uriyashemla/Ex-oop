package gameClient;

import Server.Game_Server_Ex2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login extends JFrame implements ActionListener {
        JTextField idText;
        JTextField levelText;
        JLabel idLabel;
        JLabel levelLabel;
        JLabel error;
        JButton button;

        public login (){
            setSize(500,500);

            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            idText = new JTextField();
            idText.setBounds(50, 100, 150, 20);

            idLabel = new JLabel();
            idLabel.setBounds(50, 75, 250, 20);
            idLabel.setText("Enter your ID:");
            levelText = new JTextField();
            levelText.setBounds(50, 150, 150, 20);
            levelLabel = new JLabel();
            levelLabel.setBounds(50, 125, 250, 20);
            levelLabel.setText("Enter a level:");

            button = new JButton("Start Game");
            button.setBounds(50, 250, 150, 30);
            button.addActionListener(this);

            this.add(idText);
            this.add(levelText);
            this.add(idLabel);
            this.add(levelLabel);
            this.add(button);

            error = new JLabel();
            error.setBounds(50, 200, 300, 60);
            error.setText("Please try again, incorrect id or level");

            setLayout(null);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                    if (isCorrect()) {
                        setIdText(idText);
                        setLevelText(levelText);
                        Thread client = new Thread(new Ex2());
                        client.start();
                        close();

                    }
                    this.add(error);
                    this.update(this.getGraphics());
                    this.remove(error);
                }

        private boolean isCorrect (){
            this.update(this.getGraphics());
            if (idText.getText().length()!=9){
                return false;
            }
            if(Integer.parseInt(levelText.getText())>=24||Integer.parseInt(levelText.getText())<0){
                return false;
            }
            return true;
        }

    public int getID(){
            return Integer.parseInt(this.idText.getText());
    }
    public int getLevel(){
            return Integer.parseInt(this.levelText.getText());
    }

    public void setIdText(JTextField a){
            this.idText = a;
    }
    public void setLevelText(JTextField a){
        this.levelText = a;
    }
    public void close(){
        setVisible(false);
    }

}
