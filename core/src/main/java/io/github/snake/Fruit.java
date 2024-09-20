package io.github.snake;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;

public class Fruit {
    private Sprite body;
    private Rectangle hitbox;

    private static final float SIZE = 50f;


    private static final Texture texture = new Texture("fruit.png");

    public Fruit() {
        this.body = new Sprite();
        this.hitbox = new Rectangle();
        Random rand = new Random();
        float x = rand.nextInt(0, (int) Main.WINDOW_WIDTH + 1);
        float y = rand.nextInt(0, (int) Main.WINDOW_HEIGHT + 1);
        x -= x % SIZE;
        y -= y % SIZE;
        this.body.setPosition(x, y);
        this.body.setTexture(texture);

        hitbox.setWidth(SIZE);
        hitbox.setHeight(SIZE);          // easy avoid collision by default
        hitbox.setPosition(this.body.getX(), this.body.getY());


    }






    public void draw_fruit(SpriteBatch batch, ShapeRenderer shapes) {
        batch.draw(body.getTexture(), body.getX(), body.getY(), SIZE, SIZE);
        //shapes.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        //System.out.println(hitbox.getX() + " " +  hitbox.getWidth());
    }

    public Rectangle getPosition() {
        return new Rectangle(hitbox);
    }




}
