package sut.game02.core.character;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.contacts.Position;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game02.core.GameScreen;
import sut.game02.core.SettingScreen;
import sut.game02.core.Status;
import sut.game02.core.sprite.Sprite;
import sut.game02.core.sprite.SpriteLoader;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.util.Colors;

import java.util.HashMap;


public class Player extends  Screen{
    private static String key="";
    public static SwordBeam swordBeam;
    public static Body con;
    public static Sprite sprite;
    private static boolean hasLoaded = false;
    public static Body body;
    private Body initPhysicsBody(World world, float x, float y){
        //public static Body initPhysicsBody(World world, float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        //bodyDef.type = BodyType.STATIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1.5f);
        //shape.setRadius(1.4f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 7;
        fixtureDef.friction = 1;
        //fixtureDef.restitution = 0.2f;

        body.createFixture(fixtureDef);
        //body.setLinearDamping(0f);
        body.setTransform(new Vec2(x,y),0f);
        body.setFixedRotation(true);
        return body;
    }
    public Body getBody(){
        return this.body;
    }
    public enum State{
        IDLE_R, IDLE_L,
        RtoL, LtoR,
        Run_R, Run_L,
        BR_L, BR_R,
        BRK_L, BRK_R,
        DEAD_R, DEAD_L, DEADED_L, DEADED_R,
        JUMP_L, JUMP_R,
        JM_L, JM_R,
        ATK1_L, ATK1_R
    }
    public static State state = State.IDLE_R;
    private static int spriteIndex = 0;
    private static int e = 0;
    public void action(int key){
        //this.key = key;
        /*
        1. attack
        2. right
        3. left
        4. idle_r
        5. idle_l
        6. jump
        7. broke
        8. dead
        */
        switch (key) {
            case 1:
                if (state == State.IDLE_L || state == State.Run_L || state == State.BR_L) {
                    state = State.ATK1_L;
                } else if (state == State.IDLE_R || state == State.Run_R || state == State.BR_R) {
                    state = State.ATK1_R;
                }
                break;
            case 2:
                if ((state == State.IDLE_L || state == State.Run_L || state == State.BR_L) && Status.isLand) {
                    state = State.Run_R;
                } else if ((state == State.IDLE_R || state == State.Run_R || state == State.BR_L) && Status.isLand) {
                    state = State.Run_R;
                }/*else if(state == State.JUMP_R && !Status.isLand){
                 state = State.JM_R;
                 }else if(state == State.JUMP_L && !Status.isLand){
                     state = State.JM_R;
                 }*/
                break;
            case 3:
                if ((state == State.IDLE_L || state == State.Run_L || state == State.BR_L) && Status.isLand) {
                    state = State.Run_L;
                } else if ((state == State.IDLE_R || state == State.Run_R || state == State.BR_R) && Status.isLand) {
                    state = State.Run_L;
                }/*else if(state == State.JUMP_R && !Status.isLand){
                 state = State.JM_L;
                 }else if(state == State.JUMP_L && !Status.isLand){
                     state = State.JM_L;
                 }*/
                break;
            case 4:
                switch (state) {
                    case Run_L:
                        state = State.BR_L;
                        break;
                    case Run_R:
                        state = State.BR_R;
                        break;
                }
            case 5:
                switch (state) {
                    case Run_L:
                        state = State.BR_L;
                        break;
                    case Run_R:
                        state = State.BR_R;
                        break;
                }
                break;
            case 6:
                if (state == State.IDLE_R) {
                    state = State.JUMP_R;
                } else if (state == State.IDLE_L) {
                    state = State.JUMP_L;
                } else if (state == State.BR_L) {
                    state = State.JM_L;
                } else if (state == State.BR_R) {
                    state = State.JM_R;
                }
                break;
            case 7:
                if (state == State.IDLE_R || state == State.Run_R || state == State.BR_R) {
                    state = State.BRK_R;
                    body.applyForceToCenter(new Vec2(-1700f, -500f));
                } else if (state == State.IDLE_L || state == State.Run_L || state == State.BR_L) {
                    state = State.BRK_L;
                    body.applyForceToCenter(new Vec2(1700f, -500f));
                }
                break;
            case 8:
                if (state == State.IDLE_R || state == State.Run_R) {
                    state = State.DEAD_R;
                } else if (state == State.IDLE_L || state == State.Run_L) {
                    state = State.DEAD_L;
                }
                break;
        }
    }
    public Player(final World world, final float x, final float y){

        sprite = SpriteLoader.getSprite("images/sho/sho.json");
        sprite.addCallback(new Callback<Sprite>(){
            @Override
            public void onSuccess(Sprite result){
                sprite.setSprite(spriteIndex);
                layer().setOrigin(sprite.width()/2f, sprite.height()/2f+50);
                layer().setTranslation(x, y);
                body = initPhysicsBody(world,
                        GameScreen.M_PER_PIXEL*x,
                        GameScreen.M_PER_PIXEL*y);
                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable cause){
                PlayN.log().error("Error Loading image!", cause);
            }
        });
    }
    public Layer layer(){
        return sprite.layer();
    }
    @Override
    public void update(int delta) {
        if(hasLoaded == false) return;
        e += delta;
        //body.applyForce(new Vec2(0,2f),body.getPosition());
        if(e > 50) {
            switch (state){
                case IDLE_L:
                    Status.isLand =true;
                    if (!(spriteIndex >= 0 && spriteIndex <= 15)) {
                        spriteIndex = 0;
                    }
                    break;

                case IDLE_R:
                    Status.isLand =true;
                    if (!(spriteIndex >= 16 && spriteIndex <= 31)) {
                        spriteIndex = 16;
                    }
                    break;

                case LtoR:
                    if (!(spriteIndex >= 35 && spriteIndex <= 37)) {
                        spriteIndex = 35;
                    }
                    if(spriteIndex==37)
                        state = State.IDLE_R;
                    break;

                case RtoL:
                    if (!(spriteIndex >= 32 && spriteIndex <= 34)) {
                        spriteIndex = 32;
                    }
                    if(spriteIndex==34)
                        state = State.IDLE_L;
                    break;

                case Run_L:
                    if (!(spriteIndex >= 39 && spriteIndex <= 45)) {
                        spriteIndex = 39;
                    }else if(spriteIndex%2==0){
                        body.applyLinearImpulse(new Vec2(-250,0),body.getPosition());
                    }
                    break;

                case Run_R:
                    if (!(spriteIndex >= 50 && spriteIndex <= 56)) {
                        spriteIndex = 50;
                    }else if(spriteIndex%2==1){
                        body.applyLinearImpulse(new Vec2(250,0),body.getPosition());
                    }
                    break;

                case BR_L:
                    body.applyLinearImpulse(new Vec2(80,0),body.getPosition());
                    if (!(spriteIndex >= 46 && spriteIndex <= 48)) {
                        spriteIndex = 46;
                    }
                    if(spriteIndex==48)
                        state = State.IDLE_L;
                    break;

                case BR_R:
                    body.applyLinearImpulse(new Vec2(-80,0),body.getPosition());
                    if (!(spriteIndex >= 57 && spriteIndex <= 59)) {
                        spriteIndex = 57;
                    }
                    if(spriteIndex==59)
                        state = State.IDLE_R;
                    break;

                case BRK_L:
                    if (!(spriteIndex >= 60 && spriteIndex <= 64)) {
                        spriteIndex = 60;
                    }
                    if(spriteIndex==64){

                        state = State.IDLE_L;
                    }
                    break;

                case BRK_R:
                    if (!(spriteIndex >= 65 && spriteIndex <= 69)) {
                        spriteIndex = 65;
                    }
                    if(spriteIndex==69) {

                        state = State.IDLE_R;
                    }
                    break;

                case DEAD_L:
                    if (!(spriteIndex >= 70 && spriteIndex <= 77)) {
                        spriteIndex = 70;
                    }
                    if(spriteIndex==77) {
                        state = State.DEADED_L;
                        Status.isDead = true;
                    }
                    break;

                case DEAD_R:
                    if (!(spriteIndex >= 78 && spriteIndex <= 85)) {
                        spriteIndex = 78;
                    }
                    if(spriteIndex==85){
                        state = State.DEADED_R;
                        Status.isDead = true;
                    }
                    break;

                case DEADED_L:
                    spriteIndex = 77;
                    break;

                case DEADED_R:
                    spriteIndex = 85;
                    break;

                case JUMP_L:
                    if (!(spriteIndex >= 86 && spriteIndex <= 95)) {
                        spriteIndex = 86;
                    }
                    if(spriteIndex==89){
                        Status.isLand = false;
                        body.applyLinearImpulse(new Vec2(0f,-500f),body.getPosition());
                    }else if(spriteIndex > 91 && !Status.isLand){
                        spriteIndex=90;
                    }else if(spriteIndex==95){
                        state = State.IDLE_L;
                    }
                    break;

                case JUMP_R:
                    if (!(spriteIndex >= 96 && spriteIndex <= 105)) {
                        spriteIndex = 96;
                    }
                    if(spriteIndex==99){
                        Status.isLand = false;
                        body.applyLinearImpulse(new Vec2(0f,-500f),body.getPosition());
                    }else if(spriteIndex > 101 && !Status.isLand){
                        spriteIndex=100;
                    }else if(spriteIndex==105){
                        state = State.IDLE_R;
                    }
                    break;

                case ATK1_L:
                    if (!(spriteIndex >= 106 && spriteIndex <= 115)) {
                        spriteIndex = 106;
                    }
                    if(spriteIndex==111){
                        Status.playerAttack(body,"L");
                        //con.applyLinearImpulse(new Vec2(-100f,-10f),body.getPosition());
                        //con.applyLinearImpulse(new Vec2(-100f,-10f),con.getPosition());
                        Status.isContact = false;
                    }
                    if(spriteIndex == 115){
                        state = State.IDLE_L;
                    }
                    break;

                case ATK1_R:
                    if (!(spriteIndex >= 116 && spriteIndex <= 125)) {
                        spriteIndex = 116;
                    }

                    if(spriteIndex==121){
                        Status.playerAttack(body,"R");
                        //con.applyLinearImpulse(new Vec2(100f,-10f),body.getPosition());
                        //con.applyLinearImpulse(new Vec2(100f,-10f),con.getPosition());
                        Status.isContact = false;
                    }

                    if(spriteIndex == 125){
                        state = State.IDLE_R;
                    }
                    break;

                case JM_L:
                    if(!(spriteIndex>=86 &&spriteIndex<=95)){
                        spriteIndex=86;
                    }
                    if(spriteIndex==89){
                        Status.isLand = false;
                        body.applyLinearImpulse(new Vec2(-120f,-550f),body.getPosition());
                    }
                    else if(spriteIndex > 91 && ! Status.isLand){
                        spriteIndex=90;
                    }else if(spriteIndex==95){
                        state = State.IDLE_L;
                    }
                    break;

                case JM_R:
                    if (!(spriteIndex >= 96 && spriteIndex <= 105)) {
                        spriteIndex = 96;
                    }
                    if(spriteIndex==99){
                        Status.isLand = false;
                        body.applyLinearImpulse(new Vec2(120f,-550f),body.getPosition());
                    }
                    else if(spriteIndex > 101 && ! Status.isLand){
                        spriteIndex=100;
                    }else if(spriteIndex==105){
                        state = State.IDLE_R;
                    }
                    break;
            }

            sprite.setSprite(spriteIndex);
            if(! Status.isDead){
                spriteIndex++;
            }
            e = 0;

        }
    }
    @Override
    public void paint(Clock clock){
        super.paint(clock);
        if(!hasLoaded)return;
        layer().setTranslation(
                (body.getPosition().x / GameScreen.M_PER_PIXEL),
                (body.getPosition().y / GameScreen.M_PER_PIXEL));
        //layer().setRotation(body.getAngle());
    }
}
