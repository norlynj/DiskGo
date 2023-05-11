package com.view;

import view.component.Frame;
import view.component.ImageButton;
import view.component.Panel;

public class HowPanel extends Panel{

    private ImageButton musicOnButton, musicOffButton, homeButton;
    public HowPanel() {

        super("bg/how.png");

        musicOnButton = new ImageButton("buttons/volume-on.png");
        musicOffButton = new ImageButton("buttons/volume-off.png");
        homeButton = new ImageButton("buttons/home.png");

        musicOnButton.setBounds(945, 25, 47, 47);
        musicOffButton.setBounds(945, 25, 47, 47);
        homeButton.setBounds(1010, 25, 47, 47);

        musicOffButton.setVisible(false);


        setListeners();

        this.add(musicOnButton);
        this.add(musicOffButton);
        this.add(homeButton);
    }

    private void setListeners() {
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
        HowPanel m = new HowPanel();
        Frame frame = new Frame("How Panel");
        frame.add(m);
        frame.setVisible(true);
    }


    public ImageButton getHomeButton() {
        return homeButton;
    }
    public ImageButton getMusicOnButton() {
        return musicOnButton;
    }
    public ImageButton getMusicOffButton() {
        return musicOffButton;
    }

}
