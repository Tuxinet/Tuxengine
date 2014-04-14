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
            pixels[i] = i + random.nextInt();
        }
    }
}
