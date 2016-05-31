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
import tripleplay.game.UIScreen;
import tripleplay.util.Colors;

import java.util.HashMap;


public class Player extends UIScreen {
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

        Vec2[] vertices = {
                new Vec2(  0, +  2),
                new Vec2(+ 1, +  0),
                new Vec2(  0, +  -2),
                new Vec2(- 1, +  0)
        };
        PolygonShape shape = new PolygonShape();
        shape.set(vertices,vertices.length);
        //shape.setAsBox(1,1.5f);
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
        ATK1_L, ATK1_R,
        ATK2_L, ATK2_R,
        ATK3_L, ATK3_R,
        ATK4_L, ATK4_R,
        DEF_L, DEF_R,
        AB_L, AB_R,
        C_L, C_R

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
        9. def
        10.atk2
        11.atk3
        12.atk4
        13.LR
        14.
        */
        switch (key) {
            case 1:
                if (state == State.IDLE_L  || state == State.BR_L) {
                    state = State.ATK1_L;
                } else if (state == State.IDLE_R  || state == State.BR_R) {
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
                if (state == State.IDLE_R ||
                        state == State.Run_R ||
                        state == State.BR_R ||
                        state == State.ATK1_R) {
                    state = State.BRK_R;
                    //body.applyForceToCenter(new Vec2(-500f, -400f));
                } else if (state == State.IDLE_L ||
                        state == State.Run_L ||
                        state == State.BR_L ||
                        state == State.ATK1_L) {
                        state = State.BRK_L;
                    //body.applyForceToCenter(new Vec2(500f, -400f));
                } 
                break;
            case 8:
                if (state == State.IDLE_R || state == State.Run_R) {
                    state = State.DEAD_R;
                } else if (state == State.IDLE_L || state == State.Run_L) {
                    state = State.DEAD_L;
                }
                break;
            case 9:
                if (state == State.IDLE_R || state == State.Run_R) {
                    state = State.DEF_R;
                } else if (state == State.IDLE_L || state == State.Run_L) {
                    state = State.DEF_L;
                }
                break;
            case 10:
                if (state == State.IDLE_L  || state == State.BR_L) {
                    state = State.ATK2_L;
                } else if (state == State.IDLE_R  || state == State.BR_R) {
                    state = State.ATK2_R;
                }
                break;
            case 11:
                if (state == State.IDLE_L  || state == State.BR_L) {
                    state = State.ATK3_L;
                } else if (state == State.IDLE_R  || state == State.BR_R) {
                    state = State.ATK3_R;
                }
                break;
            case 12:
                if (state == State.IDLE_L  || state == State.BR_L) {
                    state = State.ATK4_L;
                } else if (state == State.IDLE_R  || state == State.BR_R) {
                    state = State.ATK4_R;
                } else if (state == State.ATK4_L) {
                    state = State.C_L;
                } else if (state == State.ATK4_R) {
                    state = State.C_R;
                }
                break;
            case 13:
                if(state == State.DEF_L){
                    state=State.IDLE_L;
                }else if(state == State.DEF_R){
                    state=State.IDLE_R;
                }
                break;
            case 14:
                if (state == State.JM_L ||
                        state == State.JUMP_L) {
                    state = State.AB_L;
                    body.applyForceToCenter(new Vec2(600f, -600f));
                } else if (state == State.JM_R ||
                        state == State.JUMP_R) {
                    state = State.AB_R;
                    body.applyForceToCenter(new Vec2(-600f, -600f));
                } else if(state == State.IDLE_R ||
                        state == State.Run_R ||
                        state == State.BR_R ||
                        state == State.ATK1_R ||
                        state == State.ATK2_R ||
                        state == State.ATK3_R ||
                        state == State.ATK4_R){
                        state = State.AB_R;
                } else if(state == State.IDLE_L ||
                        state == State.Run_L ||
                        state == State.BR_L ||
                        state == State.ATK1_L ||
                        state == State.ATK2_L ||
                        state == State.ATK3_L ||
                        state == State.ATK4_L){
                        state = State.AB_L;
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
                layer().setOrigin(sprite.width()/2f, sprite.height()/2f+38);
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
                        body.applyLinearImpulse(new Vec2(-150,0),body.getPosition());
                    }
                    break;

                case Run_R:
                    if (!(spriteIndex >= 50 && spriteIndex <= 56)) {
                        spriteIndex = 50;
                    }else if(spriteIndex%2==1){
                        body.applyLinearImpulse(new Vec2(150,0),body.getPosition());
                    }
                    break;

                case BR_L:
                    body.setLinearVelocity(new Vec2(0,0));
                    body.setLinearDamping(0);
                    //body.applyLinearImpulse(new Vec2(80,0),body.getPosition());
                    if (!(spriteIndex >= 46 && spriteIndex <= 48)) {
                        spriteIndex = 46;
                    }
                    if(spriteIndex==48)
                        state = State.IDLE_L;
                    break;

                case BR_R:
                    body.setLinearVelocity(new Vec2(0,0));
                    body.setLinearDamping(0);
                    //body.applyLinearImpulse(new Vec2(-80,0),body.getPosition());
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
                    if(spriteIndex==62){
                        //body.applyLinearImpulse(new Vec2(100,0),body.getPosition());
                    }else if(spriteIndex==64){

                        state = State.IDLE_L;
                    }
                    break;

                case BRK_R:
                    if (!(spriteIndex >= 65 && spriteIndex <= 69)) {
                        spriteIndex = 65;
                    }
                    if(spriteIndex==67){
                        //body.applyLinearImpulse(new Vec2(-100,0),body.getPosition());
                    }else if(spriteIndex==69) {

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
                    //Status.isDead = true;
                    spriteIndex = 77;
                    break;

                case DEADED_R:
                    //Status.isDead = true;
                    spriteIndex = 85;
                    break;

                case JUMP_L:
                    if (!(spriteIndex >= 86 && spriteIndex <= 95)) {
                        spriteIndex = 86;
                    }
                    if(spriteIndex==89){
                        Status.isLand = false;
                        body.applyLinearImpulse(new Vec2(0f,-300f),body.getPosition());
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
                        body.applyLinearImpulse(new Vec2(0f,-300f),body.getPosition());
                    }else if(spriteIndex > 101 && !Status.isLand){
                        spriteIndex=100;
                    }else if(spriteIndex==105){
                        state = State.IDLE_R;
                    }
                    break;

                case ATK1_L:
                    body.setLinearVelocity(new Vec2(0,0));
                    if (!(spriteIndex >= 106 && spriteIndex <= 115)) {
                        spriteIndex = 106;
                    }
                    if(spriteIndex==111){
                        Status.playerAttack(body,"L",5f,5f);
                        //GameScreen.sword.play();
                        //con.applyLinearImpulse(new Vec2(-100f,-10f),body.getPosition());
                        //con.applyLinearImpulse(new Vec2(-100f,-10f),con.getPosition());
                        Status.isContact = false;
                    }else if(spriteIndex == 115){
                        state = State.IDLE_L;
                    }
                    break;

                case ATK1_R:
                    body.setLinearVelocity(new Vec2(0,0));
                    if (!(spriteIndex >= 116 && spriteIndex <= 125)) {
                        spriteIndex = 116;
                    }

                    if(spriteIndex==121){
                        Status.playerAttack(body,"R",5f,5f);
                        //GameScreen.sword.play();
                        //con.applyLinearImpulse(new Vec2(100f,-10f),body.getPosition());
                        //con.applyLinearImpulse(new Vec2(100f,-10f),con.getPosition());
                        Status.isContact = false;
                    }else if(spriteIndex == 125){
                        state = State.IDLE_R;
                    }
                    break;

                case ATK2_L:
                    if (!(spriteIndex >= 126 && spriteIndex <= 136)) {
                        spriteIndex = 126;
                    }
                    if(spriteIndex==127){
                        body.applyLinearImpulse(new Vec2(-300,0),body.getPosition());
                    }else if(spriteIndex==132){
                        body.setLinearVelocity(new Vec2(0,0));
                        Status.playerAttack(body,"L",5f,10f);
                        //GameScreen.sword.play();
                        Status.isContact = false;
                    }
                    if(spriteIndex == 136){
                        state = State.IDLE_L;
                    }
                    break;
                case ATK2_R:
                    if (!(spriteIndex >= 137 && spriteIndex <= 147)) {
                        spriteIndex = 137;
                    }
                    if(spriteIndex==138){
                        body.applyLinearImpulse(new Vec2(300,0),body.getPosition());
                    }else if(spriteIndex==143){
                        body.setLinearVelocity(new Vec2(0,0));
                        Status.playerAttack(body,"R",5f,10f);
                        //GameScreen.sword.play();
                        Status.isContact = false;
                    }

                    if(spriteIndex == 147){
                        state = State.IDLE_R;
                    }
                    break;

                case ATK3_L:
                    if (!(spriteIndex >= 148 && spriteIndex <= 160)) {
                        spriteIndex = 148;
                    }
                    if(spriteIndex==150){
                        body.applyLinearImpulse(new Vec2(-350,0),body.getPosition());
                    }else if(spriteIndex==156){
                        Status.playerAttack(body,"L",5f,15f);
                        //GameScreen.sword.play();
                        Status.isContact = false;
                    }
                    if(spriteIndex == 160){
                        body.setLinearVelocity(new Vec2(0,0));
                        state = State.IDLE_L;
                    }
                    break;
                case ATK3_R:
                    if (!(spriteIndex >= 161 && spriteIndex <= 173)) {
                        spriteIndex = 161;
                    }

                    if(spriteIndex==163){
                        body.applyLinearImpulse(new Vec2(350,0),body.getPosition());
                    }else if(spriteIndex==169){
                        Status.playerAttack(body,"R",5f,15f);
                        //GameScreen.sword.play();
                        Status.isContact = false;
                    }

                    if(spriteIndex == 173){
                        body.setLinearVelocity(new Vec2(0,0));
                        state = State.IDLE_R;
                    }
                    break;

                case ATK4_L:
                    if (!(spriteIndex >= 174 && spriteIndex <= 181)) {
                        spriteIndex = 174;
                    }
                    if(spriteIndex==180){
                        spriteIndex=177;
                    }
                    /*else if(spriteIndex==176){
                        Status.playerAttack(body,"C",5.5f,5f);
                        //Status.isContact = false;
                    }*/
                    /*if(spriteIndex == 181){
                        state = State.IDLE_L;
                    }*/
                    break;
                case ATK4_R:
                    if (!(spriteIndex >= 182 && spriteIndex <= 189)) {
                        spriteIndex = 182;
                    }
                    if(spriteIndex==188){
                        spriteIndex=185;
                    }
                    /*else if(spriteIndex==184){
                        Status.playerAttack(body,"C",5.5f,5f);
                        //Status.isContact = false;
                    }*/

                    /*if(spriteIndex == 189){
                        state = State.IDLE_R;
                    }*/
                    break;
                case C_L:
                    if (!(spriteIndex >= 177 && spriteIndex <= 181)) {
                        spriteIndex = 177;
                    }
                    if(spriteIndex==179){
                        //Status.playerAttack(body,"C",5.5f);
                        //GameScreen.sword.play();
                        Status.isContact = false;
                    }
                    if(spriteIndex == 181){
                        state = State.IDLE_L;
                    }
                    break;
                case C_R:
                    if (!(spriteIndex >= 185 && spriteIndex <= 189)) {
                        spriteIndex = 185;
                    }

                    if(spriteIndex==187){
                        //Status.playerAttack(body,"C",5.5f);
                        //GameScreen.sword.play();
                        Status.isContact = false;
                    }
                    if(spriteIndex == 189){
                        state = State.IDLE_R;
                    }
                    break;

                case JM_L:
                    if(!(spriteIndex>=86 &&spriteIndex<=95)){
                        spriteIndex=86;
                    }
                    if(spriteIndex==89){
                        Status.isLand = false;
                        body.applyLinearImpulse(new Vec2(-80,-300),body.getPosition());
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
                        body.applyLinearImpulse(new Vec2(80,-300),body.getPosition());
                    }
                    else if(spriteIndex > 101 && ! Status.isLand){
                        spriteIndex=100;
                    }else if(spriteIndex==105){
                        state = State.IDLE_R;
                    }
                    break;
                case AB_L:
                    if(!(spriteIndex >= 200 && spriteIndex <= 214)){
                        body.applyLinearImpulse(new Vec2(150f,-250),body.getPosition());
                        spriteIndex=200;
                    }

                    if(spriteIndex >= 207 && spriteIndex<214){
                        body.setLinearVelocity(new Vec2(0,0));
                        body.applyLinearImpulse(new Vec2(0,300f),body.getPosition());
                    }else if(spriteIndex == 214){
                        state = State.IDLE_L;
                    }
                    break;
                case AB_R:
                    if(!(spriteIndex >= 215 && spriteIndex <= 229)){
                        body.applyLinearImpulse(new Vec2(-150f,-250),body.getPosition());
                        spriteIndex=215;
                    }
                    if(spriteIndex >= 222 && spriteIndex<229){
                        body.setLinearVelocity(new Vec2(0,0));
                        body.applyLinearImpulse(new Vec2(0,300f),body.getPosition());
                    }else if(spriteIndex == 229){
                        state = State.IDLE_R;
                    }
                    break;
                case DEF_L:
                    if(!(spriteIndex >= 190 && spriteIndex <= 194)){
                        spriteIndex=190;
                    }
                    if(spriteIndex==194){
                        spriteIndex--;
                    }
                    break;
                case DEF_R:
                    if(!(spriteIndex >= 195 && spriteIndex <= 199)){
                        spriteIndex=195;
                    }
                    if(spriteIndex==199){
                        spriteIndex--;
                    }
                    break;
                default:
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
