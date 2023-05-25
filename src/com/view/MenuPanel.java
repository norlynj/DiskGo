package view;

import view.component.Frame;
import view.component.ImageButton;
import view.component.Panel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class MenuPanel extends Panel{

    private ImageButton musicOnButton, musicOffButton;
    private ImageButton startButton;
    private ImageButton howItWorksButton;
    private ImageButton exitButton;
    private ImageButton aboutButton;
    private Panel aboutPanel;

    public MenuPanel(){
        super("bg/menu-panel.png");

        startButton = new ImageButton("buttons/start.png");
        howItWorksButton = new ImageButton("buttons/how-it-works.png");
        exitButton = new ImageButton("buttons/exit.png");

        musicOnButton = new ImageButton("buttons/volume-on.png");
        musicOffButton = new ImageButton("buttons/volume-off.png");
        aboutButton = new ImageButton("buttons/about.png");
        aboutPanel = new Panel("bg/info-hover-label.png");

        startButton.setBounds(168, 603, 186, 81);
        howItWorksButton.setBounds(384, 603, 332, 81);
        exitButton.setBounds(745, 603, 186, 81);
        musicOnButton.setBounds(958, 37, 44, 44);
        musicOffButton.setBounds(958, 37, 44, 44);
        aboutButton.setBounds(1020, 37, 44, 44);
        aboutPanel.setBounds(721, 59, 320, 141);

        musicOffButton.setVisible(false);
        aboutPanel.setVisible(false);



        setListeners();

        ImageIcon background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/bg/menu-panel.png")));

        JLabel bgImage = new JLabel();

        bgImage.setBounds(0, 0, 1100, 800);
        bgImage.setIcon(background);
        bgImage.add(startButton);
        bgImage.add(howItWorksButton);
        bgImage.add(exitButton);
        bgImage.add(aboutPanel);
        bgImage.add(musicOnButton);
        bgImage.add(musicOffButton);
        bgImage.add(aboutButton);

        this.add(bgImage);
    }

    private void setListeners(){
        startButton.hover("buttons/start-hover.png", "buttons/start.png");
        howItWorksButton.hover("buttons/how-it-works-hover.png", "buttons/how-it-works.png");
        exitButton.hover("buttons/exit-hover.png", "buttons/exit.png");
        aboutButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { aboutPanel.setVisible(true); }
            public void mouseExited(MouseEvent e) { aboutPanel.setVisible(false); }
        });
        musicOnButton.hover("buttons/volume-off-hover.png", "buttons/volume-on.png");
        musicOffButton.hover("buttons/volume-on-hover.png", "buttons/volume-off.png");
        aboutButton.hover("buttons/about-hover.png", "buttons/about.png");
    }

    public static void main(String[] args) {
        MenuPanel m = new MenuPanel();
        Frame frame = new Frame("Menu Panel");
        frame.add(m);
        frame.setVisible(true);
    }

    public ImageButton getStartButton() {
        return startButton;
    }

    public ImageButton getHowItWorksButton() {
        return howItWorksButton;
    }

    public ImageButton getExitButton() {
        return exitButton;
    }

    public void musicClick() {
        if (musicOffButton.isVisible()){
            musicOnButton.setVisible(true);
            musicOffButton.setVisible(false);
        } else {
            musicOnButton.setVisible(false);
            musicOffButton.setVisible(true);
        }
    }

    public ImageButton getMusicOnButton() {
        return musicOnButton;
    }
    public ImageButton getMusicOffButton() {
        return musicOffButton;
    }
}