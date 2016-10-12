package Main;

import javax.swing.*;

/**
 * Created by Ольга on 06.10.2016.
 */
public class Game {
    public static void main(String[] args) {
        JFrame window = new JFrame("agar.io");
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack(); //устанавливаем минимальный размер окна для отображения всех компонентов
        window.setVisible(true);

    }
}
