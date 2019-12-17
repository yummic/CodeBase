package fun.clclcl.yummic.codebase.sample.swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Foo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu");
        frame.setVisible(true);
        frame.setSize(400,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);

        JMenu file = new JMenu("File");
        menubar.add(file);

        JMenuItem exit = new JMenuItem("Exit");
        file.add(exit);

        JMenu help = new JMenu("Help");
        menubar.add(help);
        JMenuItem about = new JMenuItem("About");
        help.add(about);

        class exitaction implements ActionListener {

            public void actionPerformed (ActionEvent e) {
                System.exit(0);
            }
        }
        exit.addActionListener(new exitaction());

    }
}
