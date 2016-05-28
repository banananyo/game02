package sut.game02.core.character;

//import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
//import sun.security.krb5.Config;
import sut.game02.core.GameScreen;
//import sut.game02.core.SettingScreen;
import sut.game02.core.Status;
import sut.game02.core.ToolsG;
import sut.game02.core.sprite.Sprite;
import sut.game02.core.sprite.SpriteLoader;
import tripleplay.game.Screen;
//import tripleplay.game.ScreenStack;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;


public class E2  extends Enemy{
    /*public E1(final World world, final float x, final float y, String name,int hp){
        super(world,x,y,name,hp);
    }
    public Body getBody(){
        return super.getBody();
    }*/


    @Override
    public void genIndex(){
        //System.out.println("E2");
        if(e>120) {
            if (this.hp < 0) {
                state = State.DEAD_L;
            }
            cd += 1;
            switch (state) {
                case IDLE_L:
                    if (!(spriteIndex >= 0 && spriteIndex <= 1)) {
                        spriteIndex = 0;
                    }
                    break;
                case IDLE_R:
                    if (!(spriteIndex >= 2 && spriteIndex <= 3)) {
                        spriteIndex = 2;
                    }
                    break;
                case RUN_L:
                    if (cd > 5 && cd < 7) {
                        body.applyLinearImpulse(new Vec2(-80f, 0f), body.getPosition());
                    } else if (cd > 10) {
                        body.applyLinearImpulse(new Vec2(60f, 0f), body.getPosition());
                        cd = 0;
                    } else {
                        state = State.IDLE_L;
                    }
                    if (!(spriteIndex >= 4 && spriteIndex <= 6)) {
                        spriteIndex = 4;
                    }
                    break;
                case RUN_R:
                    if (cd > 5 && cd < 7) {
                        body.applyLinearImpulse(new Vec2(80f, 0f), body.getPosition());
                    } else if (cd > 10) {
                        body.applyLinearImpulse(new Vec2(-60f, 0f), body.getPosition());
                        cd = 0;
                    } else {
                        state = State.IDLE_R;
                    }
                    if (!(spriteIndex >= 7 && spriteIndex <= 9)) {
                        spriteIndex = 7;
                    }
                    break;
                case DEAD_L:
                    if (!(spriteIndex >= 10 && spriteIndex <= 13)) {
                        spriteIndex = 10;
                    }
                    if (spriteIndex == 13) {
                        state = State.DEADED_L;
                        isDead = true;
                    }
                    break;
                case DEAD_R:
                    if (!(spriteIndex >= 14 && spriteIndex <= 17)) {
                        spriteIndex = 14;
                    }
                    if (spriteIndex == 17) {
                        state = State.DEADED_L;
                        isDead = true;
                    }
                    break;

                case DEADED_L:
                    spriteIndex = 13;
                    break;
                case DEADED_R:
                    spriteIndex = 17;
                    break;
                case HIT_L:
                    if (!(spriteIndex >= 10 && spriteIndex <= 11)) {
                        spriteIndex = 10;
                    }
                    if (spriteIndex == 11) {
                        cd = 0;
                        state = State.IDLE_L;
                        isDead = true;
                    }
                    break;
                case HIT_R:
                    if (!(spriteIndex >= 14 && spriteIndex <= 15)) {
                        spriteIndex = 14;
                    }
                    if (spriteIndex == 15) {
                        cd = 0;
                        state = State.IDLE_R;
                        isDead = true;
                    }
                    break;

            }
            e = 0;
            sprite.setSprite(spriteIndex);
            if (!isDead) {
                spriteIndex++;

                try {
                    if (body.getPosition().x > Player.body.getPosition().x && body.getPosition().x - Player.body.getPosition().x < 10) {
                        state = State.RUN_L;
                    } else if (body.getPosition().x < Player.body.getPosition().x && Player.body.getPosition().x - body.getPosition().x < 10) {
                        state = State.RUN_R;
                    }
                } catch (Exception ex) {

                }
            } else {
                Status.enemyDead(this);
            }
        }
    }
    public E2(final World world, final float x, final float y, String name, int hp){
        super(world,x,y,name,hp);
    }

}
