/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import entity.Player;
import tile.TileManager;

/**
 *
 * @author guard
 */
public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16;
    final int scale = 3;
    
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    
    // FPS
    
    int FPS = 60;
    
    TileManager tileM = new TileManager(this);
    
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);
    
    // Set Players default position
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
//    @Override
//    public void run() {
//        while (gameThread != null){
//            
//            double drawInterval = 1000000000 / FPS;
//            double nextDrawTime = System.nanoTime() + drawInterval;
//            
//            while (gameThread != null) {
//            
//                update();
//                repaint();
//                try {
//                    double remainingTime = nextDrawTime - System.nanoTime();
//                    remainingTime = remainingTime /1000000;
//                    
//                    if (remainingTime < 0) {
//                        remainingTime = 0;
//                    }
//                    Thread.sleep((long) remainingTime);
//                    
//                    nextDrawTime += drawInterval;
//                } catch (InterruptedException e){
//                    e.printStackTrace();
//                }
//        }
//     }
//    }
    @Override
    public void run(){
        
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
      
        while(gameThread != null){
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime)/ drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            
            if (delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
                
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
           
        }
    }
    public void update() {
        player.update();
   
    }
    @Override
    public void paintComponent(Graphics g){
       
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        tileM.draw(g2);
        
        player.draw(g2);
        
        g2.dispose();
        
        
    }
}
