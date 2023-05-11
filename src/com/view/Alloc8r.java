package com.view;
import view.component.AudioPlayer;
import view.component.Frame;
import view.component.Panel;

import java.awt.*;

public class Alloc8r {
    private Frame frame;
    private MenuPanel menuPanel;
    private HowPanel howPanel;
    private InputDecisionPanel inputDecisionPanel;
    private InputPanel inputPanel;
    private OutputPanel outputPanel;
    private Panel contentPane;
    private CardLayout cardLayout;
    private AudioPlayer audio;

    public Alloc8r(){
        audio = new AudioPlayer("bgmusic.wav");
        audio.play();
        audio.loop();
        frame = new Frame("Alloc8r");

        // create Panels
        menuPanel = new MenuPanel();
        howPanel = new HowPanel();
        inputDecisionPanel = new InputDecisionPanel();
        inputPanel = new InputPanel();
        outputPanel = new OutputPanel();

        // setup the content pane and card layout
        contentPane = new Panel(true, "bg/menu-panel.png");
        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);

        // add the panels to the content pane
        contentPane.add(menuPanel, "menuPanel");
        contentPane.add(howPanel, "howPanel");

        contentPane.add(inputDecisionPanel, "inputDecisionPanel");
        contentPane.add(inputPanel, "inputPanel");
        contentPane.add(outputPanel, "outputPanel");

        listenToMenu();
        listenToInput();
        listenToInputDecision();
        listenToHow();

        frame.add(contentPane);
        frame.pack();
        frame.setVisible(true);
    }

    public void listenToMenu() {
        menuPanel.getStartButton().addActionListener(e -> cardLayout.show(contentPane, "inputDecisionPanel" ));
        menuPanel.getHowItWorksButton().addActionListener(e -> cardLayout.show(contentPane, "howPanel" ));
        menuPanel.getExitButton().addActionListener(e -> System.exit(0));
        menuPanel.getMusicOnButton().addActionListener(e -> soundClick());
        menuPanel.getMusicOffButton().addActionListener(e -> soundClick());
    }

    public void listenToInputDecision(){
        inputDecisionPanel.getFromATextFileButton();
        inputDecisionPanel.getUserDefinedButton().addActionListener(e -> cardLayout.show(contentPane, "inputPanel" ));
        inputDecisionPanel.getRandomButton();
        inputDecisionPanel.getMusicOnButton().addActionListener(e -> soundClick());
        inputDecisionPanel.getMusicOffButton().addActionListener(e -> soundClick());
        inputDecisionPanel.getHomeButton().addActionListener(e -> cardLayout.show(contentPane, "menuPanel" ));
    }

    public void listenToHow(){
        howPanel.getMusicOnButton().addActionListener(e -> soundClick());
        howPanel.getMusicOffButton().addActionListener(e -> soundClick());
        howPanel.getHomeButton().addActionListener(e -> cardLayout.show(contentPane, "menuPanel" ));
    }

    public void listenToOutput() {
        outputPanel.getMusicOnButton().addActionListener(e -> soundClick());
        outputPanel.getMusicOffButton().addActionListener(e -> soundClick());
        outputPanel.getHomeButton().addActionListener(e -> {
            cardLayout.show(contentPane, "menuPanel");
        });
    }

    public void listenToInput(){
        inputPanel.getMusicOnButton().addActionListener(e -> soundClick());
        inputPanel.getMusicOffButton().addActionListener(e -> soundClick());
        inputPanel.getHomeButton().addActionListener(e -> cardLayout.show(contentPane, "menuPanel" ));
    }

    public void soundClick() {
        menuPanel.musicClick();
        inputDecisionPanel.musicClick();
        inputPanel.musicClick();
        outputPanel.musicClick();
        howPanel.musicClick();
        if (audio.isPlaying()) {
            audio.stop();
        } else {
            audio.play();
        }
    }

}
