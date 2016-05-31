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
    @Override
    public Body initPhysicsBody(World world, float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        //bodyDef.type = BodyType.KINEMATIC;
        bodyDef.position = new Vec2(0,0);
        body = world.createBody(bodyDef);
        Vec2[] vertices = {
                new Vec2(  0, - 2),
                new Vec2(+ 1, + 1),
                new Vec2(- 1, + 1)
        };
        shape = new PolygonShape();

        shape.set(vertices,vertices.length);
        //shape.setAsBox(1,1.8f);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2;
        fixtureDef.friction = 0.5f;
        //fixtureDef.restitution = 0.5f;

        f = body.createFixture(fixtureDef);
        body.setTransform(new Vec2(x,y),0f);
        body.setFixedRotation(true);
        layer().setOrigin(sprite.width()/2, sprite.height()/2+14);
        return body;
    }

    @Override
    public void genIndex(){
        //System.out.println("E2");

        if(e>120) {
            if (this.hp < 0) {
                state = State.DEAD_L;
            }
            cd += 1;
            switch (state) {
                case ATK_L:
                    if (!(spriteIndex >= 4 && spriteIndex <= 6)) {
                        spriteIndex = 4;
                    }else if(spriteIndex==6){
                        Status.playerHit(22f);
                        state = State.IDLE_L;
                    }
                    break;
                case ATK_R:
                    if (!(spriteIndex >= 7 && spriteIndex <= 9)) {
                        spriteIndex = 7;
                    }else if(spriteIndex==9){
                        Status.playerHit(22f);
                        state = State.IDLE_R;
                    }
                    break;
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
                    body.applyLinearImpulse(new Vec2(-10, 0), body.getPosition());
                    /*if (cd > 5 && cd < 7) {
                        body.applyLinearImpulse(new Vec2(-30, -10), body.getPosition());
                    } else if (cd > 10) {
                        body.applyLinearImpulse(new Vec2(20, 0), body.getPosition());
                        cd = 0;
                    } else {
                        state = State.IDLE_L;
                    }*/
                    if (!(spriteIndex >= 4 && spriteIndex <= 6)) {
                        spriteIndex = 4;
                    }
                    break;
                case RUN_R:
                    body.applyLinearImpulse(new Vec2(10, 0), body.getPosition());
                    /*if (cd > 5 && cd < 7) {
                        body.applyLinearImpulse(new Vec2(30, -10), body.getPosition());
                    } else if (cd > 10) {
                        body.applyLinearImpulse(new Vec2(-20, 0), body.getPosition());
                        cd = 0;
                    } else {
                        state = State.IDLE_R;
                    }*/
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
                    if (body.getPosition().x - Player.body.getPosition().x>=0 &&
                            body.getPosition().x - Player.body.getPosition().x <= 2 &&
                            cd > 20) {
                        state = State.ATK_L;
                        cd=0;
                    } else if (Player.body.getPosition().x - body.getPosition().x>=0 &&
                            Player.body.getPosition().x - body.getPosition().x <= 2 &&
                            cd > 20) {
                        state = State.ATK_R;
                        cd=0;
                    }

                    if (body.getPosition().x - Player.body.getPosition().x>2 &&
                            body.getPosition().x - Player.body.getPosition().x <= 8) {
                        state = State.RUN_L;
                    } else if (Player.body.getPosition().x - body.getPosition().x>2 &&
                            Player.body.getPosition().x - body.getPosition().x <= 8) {
                        state = State.RUN_R;
                    } else if(body.getPosition().x - Player.body.getPosition().x >8){
                        state = State.IDLE_L;
                    } else if(Player.body.getPosition().x - body.getPosition().x >8){
                        state = State.IDLE_R;
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
