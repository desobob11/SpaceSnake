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
    private static final float MIN_SIZE = 25f;
    private float scale_direction;


    private static final Texture texture = new Texture("fruit.png");

    public Fruit() {
        this.body = new Sprite(texture);
        this.hitbox = new Rectangle();
        Random rand = new Random();
        float x = rand.nextInt(0, (int) Main.WINDOW_WIDTH + 1 - (int) SIZE);
        float y = rand.nextInt(0, (int) Main.WINDOW_HEIGHT + 1 - (int) SIZE);
        x -= x % SIZE;
        y -= y % SIZE;
        this.body.setPosition(x, y);

        this.scale_direction = -1;

        body.setSize(SIZE - 10, SIZE - 10);
        body.setOriginCenter();
        hitbox.setWidth(SIZE);
        hitbox.setHeight(SIZE);          // easy avoid collision by default
        hitbox.setPosition(this.body.getX(), this.body.getY());


    }


    private void rotate_and_scale() {
        body.rotate(5);
        if (body.getWidth() < MIN_SIZE || body.getHeight() > SIZE - 10) {
            scale_direction *= -1;
        }
        body.setSize(body.getWidth() + (0.7f * scale_direction), body.getHeight() + (0.7f * scale_direction));
        body.setOriginCenter();
    }




    public void draw_fruit(SpriteBatch batch, ShapeRenderer shapes) {
        rotate_and_scale();
        body.draw(batch);

    }

    public Rectangle getPosition() {
        return new Rectangle(hitbox);
    }




}
