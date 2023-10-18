import java.awt.Color;
import java.awt.Graphics;
public class Obstacle {

    private int x;
    private int y;
    private int width;
    private int height;

    public Obstacle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.gray); // ここで適切な色を設定
        g.fillRect(x, y, width, height);
    }


    // 他のメソッドや getter/setter を必要に応じて追加
}


