package com.bouchy.balls;

import javax.swing.*;


public class Ball_Frame extends JFrame {
    public static final int SCREEN_DIM = 750;
    Ball ball1;
    public Ball_Frame() {
        super("Window");
        ball1 = new Ball();
        this.add(ball1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setFocusable(false);
        this.setSize(SCREEN_DIM,SCREEN_DIM);
        this.setVisible(true);
    }
}
