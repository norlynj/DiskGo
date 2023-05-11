package com.view;

import view.component.Frame;
import view.component.ImageButton;
import view.component.Panel;

import javax.swing.*;
import java.util.Objects;

public class InputDecisionPanel extends Panel{
    private ImageButton fromATextFileButton, userDefinedButton, randomButton;
    private ImageButton musicOnButton, musicOffButton, homeButton;
    public InputDecisionPanel() {
        super("bg/input-choice-panel.png");



        fromATextFileButton = new ImageButton("buttons/fromtext.png");
        userDefinedButton = new ImageButton("buttons/user.png");
        randomButton = new ImageButton("buttons/random.png");
        musicOnButton = new ImageButton("buttons/volume-on.png");
        musicOffButton = new ImageButton("buttons/volume-off.png");
        homeButton = new ImageButton("buttons/home.png");


        fromATextFileButton.setBounds(526, 552, 385, 61);
        userDefinedButton.setBounds(462, 472, 449, 61);
        randomButton.setBounds(683, 391, 229, 61);
        musicOnButton.setBounds(945, 25, 47, 47);
        musicOffButton.setBounds(945, 25, 47, 47);
        homeButton.setBounds(1010, 25, 47, 47);

        musicOffButton.setVisible(false);


        setListeners();

        ImageIcon background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/bg/input-choice-panel.png")));

        JLabel bgImage = new JLabel();

        bgImage.setBounds(0, 0, 1100, 800);
        bgImage.setIcon(background);
        bgImage.add(fromATextFileButton);
        bgImage.add(userDefinedButton);
        bgImage.add(randomButton);
        bgImage.add(musicOnButton);
        bgImage.add(musicOffButton);
        bgImage.add(homeButton);

        this.add(bgImage);
    }

    private void setListeners() {
        fromATextFileButton.hover("buttons/fromtext-hover.png", "buttons/fromtext.png");
        userDefinedButton.hover("buttons/user-hover.png", "buttons/user.png");
        randomButton.hover("buttons/random-hover.png", "buttons/random.png");
        musicOnButton.hover("buttons/volume-off-hover.png", "buttons/volume-on.png");
        musicOffButton.hover("buttons/volume-on-hover.png", "buttons/volume-off.png");
        homeButton.hover("buttons/home-hover.png", "buttons/home.png");
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


    public static void main(String[] args) {
        InputDecisionPanel m = new InputDecisionPanel();
        Frame frame = new Frame("Input Decision Panel");
        frame.add(m);
        frame.setVisible(true);
    }

    public ImageButton getFromATextFileButton() {
        return fromATextFileButton;
    }

    public ImageButton getUserDefinedButton() {
        return userDefinedButton;
    }

    public ImageButton getRandomButton() {
        return randomButton;
    }

    public ImageButton getMusicOnButton() {
        return musicOnButton;
    }
    public ImageButton getMusicOffButton() {
        return musicOffButton;
    }

    public ImageButton getHomeButton() {
        return homeButton;
    }
}
