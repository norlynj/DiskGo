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
    private ImageButton startButton, startHover;
    private ImageButton howItWorksButton, helpHover;
    private ImageButton exitButton, exitHover;
    private ImageButton aboutButton;
    private Panel aboutPanel;

    public MenuPanel(){
        super("bg/menu-panel.png");

        startButton = new ImageButton("buttons/start.png");
        startHover = new ImageButton("buttons/start-hover-label.png");
        howItWorksButton = new ImageButton("buttons/info.png");
        helpHover = new ImageButton("buttons/help-hover-label.png");
        exitButton = new ImageButton("buttons/exit.png");
        exitHover = new ImageButton("buttons/exit-hover-label.png");

        musicOnButton = new ImageButton("buttons/volume-on.png");
        musicOffButton = new ImageButton("buttons/volume-off.png");
        aboutButton = new ImageButton("buttons/about.png");
        aboutPanel = new Panel("bg/about-hover-label.png");

        startButton.setBounds(630, 240, 83, 83);
        startHover.setBounds(734, 240, 242, 83);
        howItWorksButton.setBounds(651, 365, 83, 83);
        helpHover.setBounds(754, 365, 242, 83);
        exitButton.setBounds(630, 491, 83, 83);
        exitHover.setBounds(734, 491, 242, 83);
        musicOnButton.setBounds(958, 37, 44, 44);
        musicOffButton.setBounds(958, 37, 44, 44);
        aboutButton.setBounds(1017, 35, 47, 47);
        aboutPanel.setBounds(721, 59, 320, 141);

        musicOffButton.setVisible(false);
        aboutPanel.setVisible(false);
        startHover.setVisible(false);
        helpHover.setVisible(false);
        exitHover.setVisible(false);



        setListeners();

        ImageIcon background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/bg/menu-panel.gif")));

        JLabel bgImage = new JLabel();

        bgImage.setBounds(0, 0, 1100, 800);
        bgImage.setIcon(background);
        bgImage.add(startButton);
        bgImage.add(startHover);
        bgImage.add(howItWorksButton);
        bgImage.add(helpHover);
        bgImage.add(exitButton);
        bgImage.add(exitHover);
        bgImage.add(aboutPanel);
        bgImage.add(musicOnButton);
        bgImage.add(musicOffButton);
        bgImage.add(aboutButton);

        this.add(bgImage);
    }

    private void setListeners(){
        startButton.hover("buttons/start-hover.png", "buttons/start.png");
        howItWorksButton.hover("buttons/info-hover.png", "buttons/info.png");
        exitButton.hover("buttons/exit-hover.png", "buttons/exit.png");
        aboutButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { aboutPanel.setVisible(true); }
            public void mouseExited(MouseEvent e) { aboutPanel.setVisible(false); }
        });

        startButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { startHover.setVisible(true); }
            public void mouseExited(MouseEvent e) { startHover.setVisible(false); }
        });

        howItWorksButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { helpHover.setVisible(true); }
            public void mouseExited(MouseEvent e) { helpHover.setVisible(false); }
        });

        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { exitHover.setVisible(true); }
            public void mouseExited(MouseEvent e) { exitHover.setVisible(false); }
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