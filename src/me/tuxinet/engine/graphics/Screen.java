/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.tuxinet.engine.graphics;

import java.util.Random;

/**
 *
 * @author Trym
 */
public class Screen {
    
    public int width, height;
    public int[] pixels;
    private Random random = new Random();
    
    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }
    
    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }
    
    public void drawRect(int x, int y, int size) {
        for (int x1 = 0; x1 < size; x1++) {
            for (int y1 = 0; y1 < size; y1++) {
                if (x + x1 > width) break;
                if (y + y1 > height) break;
                if (x + x1 < 0) break;
                if (y + y1 < 0) break;
                pixels[(x + x1) + (y1 * width) + (y * width)] = 0xffffff;
            }
        }
    }
}
