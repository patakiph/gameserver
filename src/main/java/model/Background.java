package model;

import Main.GamePanel;

import java.awt.*;

/**
 * Created by Ольга on 06.10.2016.
 */
public class Background extends Entity {
    public Background(String s){
        super(s, 0, 0,Color.WHITE,new Rectangle(0,0,320,280));
    }
    public void draw(Graphics2D g) {
        g.drawImage(this.texture, (int) this.position.x, (int) this.position.y, null);
        if (position.x<0){
            g.drawImage(this.texture, (int) this.position.x + GamePanel.WIDTH, (int) this.position.y, null);
        }
        if (position.x>0){
            g.drawImage(this.texture, (int) this.position.x - GamePanel.WIDTH, (int) this.position.y, null);
        }
        if (position.y>0){
            g.drawImage(this.texture, (int) this.position.x + GamePanel.WIDTH, (int) this.position.y, null);
        }
        if (position.y<0){
            g.drawImage(this.texture, (int) this.position.x - GamePanel.WIDTH, (int) this.position.y, null);
        }
    }
}
