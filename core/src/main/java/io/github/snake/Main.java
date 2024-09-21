package io.github.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public static boolean SNAKE_IS_COLLIDING = false;
    public static float WINDOW_WIDTH = 800;
    public static float WINDOW_HEIGHT = 600;
    public static final float[] BACKGROUND_COLOR = {18f, 3f, 38f, 255f};

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private Snake snake;
    private Fruit fruit;
    private Background back_close;
    Texture text;
    TextureRegion textR;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        shapes.setColor(Color.RED);
        shapes.setAutoShapeType(true);
        snake = new Snake(0, 0);
        fruit = new Fruit();
        text = new Texture("back/space2.png");
        textR = new TextureRegion(text, 1600, 100);
        back_close = new Background(text);
    }


    @Override
    public void render() {
        ScreenUtils.clear(Color.valueOf("#120326"));
            batch.begin();
            back_close.draw_background(batch);
            play_game(batch);




           // shapes.begin();

            batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapes.dispose();
    }

    public void play_game(SpriteBatch batch) {


            // shapes.begin();

            fruit.draw_fruit(batch, shapes);
            snake.draw_snake(batch, shapes);
        if (!SNAKE_IS_COLLIDING) {
            snake.move();
            if (snake.ate_fruit(fruit)) {
                fruit = new Fruit();
                snake.grow_snake();
            }
            SNAKE_IS_COLLIDING = snake.is_colliding_self() || snake.is_colliding_edges();


            //shapes.end();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.PERIOD)) {
            SNAKE_IS_COLLIDING = false;
            snake = new Snake(0, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.COMMA)) {
            fruit = new Fruit();
        }
    }

}
