package com.bouchy.balls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;


public class Ball extends JLabel {
    final int DIAMETER =75;
    final int ROOF= 0;
    final int FLOOR=Ball_Frame.SCREEN_DIM;
    final int LEFT_WALL=0;
    final int RIGHT_WALL =Ball_Frame.SCREEN_DIM;
    Timer moveBall;
    Random coordinates;
    double movingX;
    double movingY;
    double originX;
    double originY;
    double pressedX =0;
    double pressedY =0;
    Color ballColor;
    Listener interact;
    double slope;
    double constant;
    int count =0;
    final int DELAY =1;
    final int INCREMENTS=2;


    public Ball() {
        coordinates = new Random();
        interact = new Listener();
        moveBall = new Timer(DELAY,interact);
        movingX = coordinates.nextInt(Ball_Frame.SCREEN_DIM)-DIAMETER;
        movingY = ROOF;
        originX = movingX;
        ballColor = new Color(coordinates.nextInt(256),coordinates.nextInt(256),coordinates.nextInt(256));
        originY = movingY;
        this.setBounds(0,0,Ball_Frame.SCREEN_DIM,Ball_Frame.SCREEN_DIM);
        this.addMouseListener(interact);
        this.addMouseMotionListener(interact);
        System.out.println("Ball1");
    }

    @Override
    public void paint (Graphics g){
        super.paint(g);
        g.setColor(Color.black);
        g.drawRect(0,0,Ball_Frame.SCREEN_DIM,Ball_Frame.SCREEN_DIM);
        g.setColor(ballColor);
        g.fillOval((int) movingX,(int) movingY,DIAMETER,DIAMETER);
    }

    public class Listener extends MouseAdapter implements ActionListener {
        char c = 'f';
        @Override
        public void mousePressed(MouseEvent e) {
            if (
                    e.getX() < (movingX +DIAMETER) && e.getX() > (movingX) &&
                    e.getY() < (movingY + DIAMETER) && e.getY() >  (movingY))
            {
                moveBall.stop();
                pressedX =e.getX()- movingX;
                pressedY =e.getY()- movingY;
            }

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (
                    e.getX() < (movingX +DIAMETER) && e.getX() > (movingX) &&
                    e.getY() < (movingY + DIAMETER) && e.getY() >  (movingY))
            {
                moveBall.stop();
                movingX = e.getX() - pressedX;
                movingY = e.getY() - pressedY;
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if
            (e.getX() < (movingX +DIAMETER) && e.getX() > (movingX) &&
             e.getY() < (movingY + DIAMETER) && e.getY() >  (movingY))
            {
                moveBall.start();
                slope = ((originY - movingY) / (originX - movingX));
                constant = movingY - (slope * movingX);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (movingY > ROOF && count == 0) {
                movingY-=INCREMENTS;
                movingX = (movingY - constant) / slope;
                repaint();
            }
            if (movingX <=  LEFT_WALL || movingX >= (RIGHT_WALL-DIAMETER )|| movingY <= ROOF || movingY >= (FLOOR-DIAMETER ))
            {
                if (movingX <=  LEFT_WALL) c = 'l';
                if (movingX >= (RIGHT_WALL-DIAMETER)) c = 'r';
                if (movingY <= ROOF) c = 't';
                if (movingY >= (FLOOR-DIAMETER)) c = 'b';
                slope = (-slope);
                constant = movingY - (slope * movingX);
                count++;
                ballColor = new Color(coordinates.nextInt(256),coordinates.nextInt(256),coordinates.nextInt(256));

            }

            if (c == 't') {
                movingY+=INCREMENTS;
                movingX = (movingY - constant) / (slope);
                repaint();
            }
            if (c == 'b') {
                movingY-=INCREMENTS;
                movingX = (movingY - constant) / (slope);
                repaint();
            }
            if (c == 'l') {
                movingX+=INCREMENTS;
                movingY = (movingX * slope) + constant;
                repaint();
            }
            if (c == 'r'){
                movingX-=INCREMENTS;
                 movingY = (movingX * slope) + constant;
                 repaint();
            }

        }
    }
}


