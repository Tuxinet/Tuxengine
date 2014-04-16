/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.tuxinet.engine;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;
import me.tuxinet.engine.graphics.Screen;
/**
 * 
 * @author Trym
 */
public class Tuxengine extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    
    public static int width = 300;
    public static int height = width /16 * 9;
    public static int scale = 3;
    public static int x = 0;
    public static int y = 0;
    public static String title = "Rain";
    private Thread thread;
    private JFrame frame;
    private boolean running = false;
    
    private Screen screen;
    
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    
    public Tuxengine() {
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        
        screen = new Screen(width, height);
        frame = new JFrame();
        
    } 

    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }
    
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
         }
    }
    
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int ticks = 0;
        requestFocus();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                ticks++;
                delta = 0;
            }
            render();
            frames++;
            
            if (System.currentTimeMillis() - timer > 1000) {
                timer = System.currentTimeMillis();
                System.out.println(ticks + " ups, " + frames + " fps");
                frame.setTitle(title + "  |  " + ticks + " ups, " + frames + " fps");
                ticks = 0;
                frames = 0;
            }
        }
        stop();
    }
    
    public void tick() {
        
    }
    
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }        
        
        screen.clear();
        screen.drawRect(x, y, 16);
        
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }
        
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        bs.show();
    }
    
    public static void main(String[] args) {
        Tuxengine tuxengine = new Tuxengine();
        tuxengine.frame.setResizable(true);
        tuxengine.frame.add(tuxengine);
        tuxengine.frame.pack();
        tuxengine.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tuxengine.frame.setLocationRelativeTo(null);
        tuxengine.frame.setVisible(true);
        
        tuxengine.start();
    }
}
