package io.github.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.util.List;
import java.util.ArrayList;



public class Snake {
        private List<Sprite> body;
        private List<Rectangle> hitboxes;
        private double last_refresh = 0f;
        private double threshold = 0.075f;

        private int dir;

        private static int[][] moves =  {{1,0}, {0,-1}, {-1, 0}, {0, 1}};

        private static final float SIZE = 50f;

        private static final Texture texture = new Texture("body.png");

        public Snake(int x, int y) {
            this.body = new ArrayList<>();
            this.hitboxes = new ArrayList<>();

            Sprite head = new Sprite(texture);

            head.setPosition(x, y);
            head.setSize(SIZE, SIZE);

            Rectangle hitbox = new Rectangle();
            hitbox.setWidth(SIZE -1);
            hitbox.setHeight(SIZE - 1);          // easy avoid collision by default
            hitbox.setPosition(head.getX(), head.getY());

            this.body.add(head);
            this.hitboxes.add(hitbox);
            this.dir = 3;
        }

        public void grow_snake() {

            Sprite head = new Sprite(texture);
            Texture t = new Texture("body.png");

            head.setPosition(this.body.get(0).getX(), this.body.get(0).getY());
            head.setColor(124f, 124f, 124f, 1f);
            head.setSize(SIZE, SIZE);

            Rectangle hitbox = new Rectangle();
            hitbox.setWidth(SIZE - 1);
            hitbox.setHeight(SIZE - 1);
            hitbox.setPosition(head.getX(), head.getY());

            this.body.add(1, head);
            this.hitboxes.add(1, hitbox);

        }


        public void move() {
            if (Gdx.input.isKeyPressed(Input.Keys.W) && this.dir !=1) {
                this.dir = 3;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.D) && this.dir != 2) {
                this.dir = 0;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.S) && this.dir != 3) {
                this.dir = 1;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.A) && this.dir != 0) {
                this.dir = 2;
            }

            if (last_refresh + Gdx.graphics.getDeltaTime() > threshold) {
                last_refresh = 0f;

                for (int i = this.body.size() - 1; i >= 1; --i) {
                    this.body.get(i).setPosition(this.body.get(i - 1).getX(), this.body.get(i - 1).getY());
                    this.hitboxes.get(i).setPosition(this.body.get(i).getX(), this.body.get(i).getY());
                }

                this.body.get(0).translate(moves[this.dir][0] * SIZE, moves[this.dir][1] * SIZE);
                this.hitboxes.get(0).setPosition(this.body.get(0).getX(), this.body.get(0).getY());
            }
            last_refresh += Gdx.graphics.getDeltaTime();

        }

        public void draw_snake(SpriteBatch batch, ShapeRenderer shapes) {
            for (Sprite i : this.body) {
                i.draw(batch);
            }
        }

        // O(n)
        public boolean is_colliding_self() {
            for (int i = 2; i < body.size(); ++i) {
                if (hitboxes.get(i).overlaps(hitboxes.get(0))) {
                    return true;
                }
            }
            return false;
        }

        public boolean is_colliding_edges() {
            Sprite head = body.get(0);
            return (head.getX() < 0 || head.getX() + SIZE > Main.WINDOW_WIDTH || head.getY() < 0 || head.getY() + SIZE > Main.WINDOW_HEIGHT);
        }

        public boolean ate_fruit(Fruit fruit) {
            return hitboxes.get(0).overlaps(fruit.getPosition());
        }
}
