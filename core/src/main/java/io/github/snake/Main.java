package io.github.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public static boolean SNAKE_IS_COLLIDING = false;
    private SpriteBatch batch;
    private ShapeRenderer shapes;
    Snake snake;
    Texture text;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        shapes.setColor(Color.RED);
        shapes.setAutoShapeType(true);
        snake = new Snake(0, 0);
    }

    @Override
    public void render() {
        if (!SNAKE_IS_COLLIDING) {


            ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1f);
            batch.begin();
            shapes.begin();

            snake.draw_snake(batch, shapes);
            snake.move();
            SNAKE_IS_COLLIDING = snake.is_colliding_self();


            shapes.end();
            batch.end();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.PERIOD)) {
            SNAKE_IS_COLLIDING = false;
            snake = new Snake(0, 0);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapes.dispose();
    }
}
