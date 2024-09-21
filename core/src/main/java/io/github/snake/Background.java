package io.github.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import org.w3c.dom.Text;

import javax.swing.*;
import java.util.Random;

import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;

/**
 * This was hacked together terrible in ~hour, this should be redone so that parallax can be achieved
 *
 *
 */


public class Background {
    private Texture texture;
    private ArrayList<TextureRegion> regions;
    private ArrayList<TextureRegion> buffer1;
    private ArrayList<TextureRegion> buffer2;
    private static final int SIDE_LENGTH = 400;
    private static final int BUFFER_SIZE = 6;
    private static final int BUFFER_ROWS = 2;
    private static final int BUFFER_COLS = 3;
    private int horizontal_offset_1;        // this is bad, these should be scalable
    private int horizontal_offset_2;
    private double last_redraw;
    private double refresh_limit = 0.01f;

    public Background(Texture image) {
        horizontal_offset_1 = 0;
        horizontal_offset_2 = BUFFER_ROWS * SIDE_LENGTH;
        last_redraw = 0;
        this.texture = image;
        this.regions = new ArrayList<>();
        for (int i = 0; i < image.getWidth(); i += SIDE_LENGTH) {
            for (int j = 0; j < image.getHeight(); j += SIDE_LENGTH) {
                this.regions.add(new TextureRegion(this.texture, i, j, SIDE_LENGTH, SIDE_LENGTH));
            }
        }



    }


   // private boolean check_buffer_bounds(ArrayList<TextureRegion> buffer) {
   //     return buffer.get(buffer.size() - 1)
    //}

    private ArrayList<TextureRegion> load_buffer(ArrayList<TextureRegion> buffer) {
        Random rand = new Random();
        ArrayList<TextureRegion> buff = new ArrayList<>();
        for (int i = 0; i < BUFFER_SIZE; ++i) {
            int j = rand.nextInt(0, regions.size());
            buff.add(regions.get(j));        // can use same reference
        }

        return buff;
    }

    // shitty function, should be cleaned up
    private void draw_buffer(SpriteBatch batch, ArrayList<TextureRegion> buffer) {
        int offset = buffer == buffer1 ? horizontal_offset_1 : horizontal_offset_2;     // WOW THIS IS FUCKING TERRIBLE
        int index = 0;
        for (int i = 0; i < BUFFER_ROWS; ++i) {
            for (int j = 0; j < BUFFER_COLS; ++j) {
                batch.draw(buffer.get(index), SIDE_LENGTH * j + offset, SIDE_LENGTH * i, SIDE_LENGTH, SIDE_LENGTH);
                ++index;
            }
        }
    }


    public void draw_background(SpriteBatch batch) {
        if (last_redraw + Gdx.graphics.getDeltaTime() > refresh_limit) {
            last_redraw = 0;
            horizontal_offset_1 -= 2;
            horizontal_offset_2 -= 2;


            if (buffer1 == null) {
                buffer1 = load_buffer(regions);
                buffer2 = load_buffer(regions);
            }
            draw_buffer(batch, buffer1);
            draw_buffer(batch, buffer2);
        }
        last_redraw += Gdx.graphics.getDeltaTime();

        if (horizontal_offset_1 < -BUFFER_COLS * SIDE_LENGTH) {
            horizontal_offset_1 = horizontal_offset_2 + (BUFFER_COLS * SIDE_LENGTH);
            buffer1 = load_buffer(regions);
        }

        if (horizontal_offset_2 < -BUFFER_COLS * SIDE_LENGTH) {
            horizontal_offset_2 = horizontal_offset_1 + (BUFFER_COLS * SIDE_LENGTH);
            buffer2 = load_buffer(regions);
        }
    }


}
