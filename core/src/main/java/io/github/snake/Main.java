package io.github.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public static boolean SNAKE_IS_COLLIDING = false;
    public static float WINDOW_WIDTH = 1200;
    public static float WINDOW_HEIGHT = 900;

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private Snake snake;
    private Fruit fruit;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        shapes.setColor(Color.RED);
        shapes.setAutoShapeType(true);
        snake = new Snake(0, 0);
        fruit = new Fruit();
    }

    @Override
    public void render() {
        if (!SNAKE_IS_COLLIDING) {


            ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1f);
            batch.begin();
           // shapes.begin();

            fruit.draw_fruit(batch, shapes);
            snake.draw_snake(batch, shapes);
            snake.move();
            if (snake.ate_fruit(fruit)) {
                fruit = new Fruit();
                snake.grow_snake();
            }
            SNAKE_IS_COLLIDING = snake.is_colliding_self() || snake.is_colliding_edges();


            //shapes.end();
            batch.end();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.PERIOD)) {
            SNAKE_IS_COLLIDING = false;
            snake = new Snake(0, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.COMMA)) {
            fruit = new Fruit();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapes.dispose();
    }
}
