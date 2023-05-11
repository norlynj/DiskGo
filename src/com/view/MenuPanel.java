package com.view;

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
        howItWorksButton = new ImageButton("buttons/info.png");
        exitButton = new ImageButton("buttons/exit.png");
        musicOnButton = new ImageButton("buttons/volume-on.png");
        musicOffButton = new ImageButton("buttons/volume-off.png");
        aboutButton = new ImageButton("buttons/about.png");
        aboutPanel = new Panel("bg/about-hover-label.png");

        startButton.setBounds(380, 572, 152, 61);
        howItWorksButton.setBounds(1023, 25, 47, 47);
        exitButton.setBounds(572, 572, 152, 61);
        musicOnButton.setBounds(920, 25, 47, 47);
        musicOffButton.setBounds(920, 25, 47, 47);
        aboutButton.setBounds(972, 25, 47, 47);
        aboutPanel.setBounds(684, 49, 320, 141);
        aboutPanel.setVisible(false);

        musicOffButton.setVisible(false);

        setListeners();

        ImageIcon background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/bg/menu-panel.png")));

        JLabel bgImage = new JLabel();

        bgImage.setBounds(0, 0, 1100, 800);
        bgImage.setIcon(background);
        bgImage.add(startButton);
        bgImage.add(howItWorksButton);
        bgImage.add(exitButton);
        bgImage.add(musicOnButton);
        bgImage.add(musicOffButton);
        bgImage.add(aboutButton);
        bgImage.add(aboutPanel);

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
        musicOnButton.hover("buttons/volume-off-hover.png", "buttons/volume-on.png");
        musicOffButton.hover("buttons/volume-on-hover.png", "buttons/volume-off.png");    }

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
