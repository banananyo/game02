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
import tripleplay.util.Randoms;
//import tripleplay.game.ScreenStack;

import java.util.Random;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;


public class E7 extends Enemy{
    Random rand = new Random();

    public Layer layer(){
        return this.sprite.layer();
    }
    public Body initPhysicsBody(World world, float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        //bodyDef.type = BodyType.STATIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);

        Vec2[] vertices = {
                new Vec2(  0, +  3),
                new Vec2(+ 1, + -1),
                new Vec2(- 1, + -1)
        };
        PolygonShape shape = new PolygonShape();
        shape.set(vertices,vertices.length);
        //shape.setAsBox(1,1.5f);
        //shape.setRadius(1.4f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2;
        fixtureDef.friction = 0.5f;
        //fixtureDef.restitution = 0.2f;

        body.createFixture(fixtureDef);
        //body.setLinearDamping(0f);
        body.setTransform(new Vec2(x,y),0f);
        body.setFixedRotation(true);
        layer().setOrigin(sprite.width()/2, sprite.height()/2+10);
        return  body;
    }
    public  void action(String action){
        if(action == "IDLE_L"){
            this.state = State.IDLE_L;
        }

        else if(action == "IDLE_R"){
            this.state = State.IDLE_R;
        }

        else if(action == "RUN_L"){
            this.state = State.RUN_L;
        }

        else if(action == "RUN_R"){
            this.state = State.RUN_R;
        }

        else if(action == "DASH_L" &&
                (state==State.IDLE_L || state==State.IDLE_R ||
                        state==State.RUN_L || state==State.RUN_R)){
            this.state = State.DASH_L;
        }

        else if(action == "DASH_R" &&
                (state==State.IDLE_R || state==State.IDLE_L||
                        state==State.RUN_L || state==State.RUN_R)){
            this.state = State.DASH_R;
        }

        else if(action == "ATK1_L" && state==State.IDLE_L){
            //Status.enemyAttack(this,"L",5,4);
            this.state = State.ATK1_L;
        }

        else if(action == "ATK1_R" && state==State.IDLE_R){
            //Status.enemyAttack(this,"R",5,4);
            this.state = State.ATK1_R;
        }

        else if(action == "ATK2_L" && state==State.IDLE_L){
            //Status.enemyAttack(this,"L",5,8);
            this.state = State.ATK2_L;
        }

        else if(action == "ATK2_R" && state==State.IDLE_R){
            //Status.enemyAttack(this,"R",5,8);
            this.state = State.ATK2_R;
        }

        else if(action == "ATK3_L" && state==State.IDLE_L){
            //Status.enemyAttack(this,"L",5,10);
            this.state = State.ATK3_L;
        }

        else if(action == "ATK3_R" && state==State.IDLE_R){
            //Status.enemyAttack(this,"R",5,10);
            this.state = State.ATK3_R;
        }
    }

    @Override
    public void genIndex(){
        if(e > 120) {
            if (this.hp < 0) {
                state = State.DEAD_L;
            }
            switch (state) {
                case IDLE_L:
                    body.setLinearVelocity(new Vec2(0,0));
                    if (!(spriteIndex >= 0 && spriteIndex <= 9)) {
                        spriteIndex = 0;
                    }
                    break;
                case IDLE_R:
                    body.setLinearVelocity(new Vec2(0,0));
                    if (!(spriteIndex >= 10 && spriteIndex <= 19)) {
                        spriteIndex = 10;
                    }
                    break;
                case JMP_L:
                    if (!(spriteIndex >= 20 && spriteIndex <= 28)) {
                        spriteIndex = 20;
                    }
                    break;
                case JMP_R:
                    if (!(spriteIndex >= 29 && spriteIndex <= 37)) {
                        spriteIndex = 29;
                    }
                    break;
                case RUN_L:
                    if (!(spriteIndex >= 38 && spriteIndex <= 44)) {
                        spriteIndex = 38;
                    }
                    if(spriteIndex==40){
                        body.applyLinearImpulse(new Vec2(-100,0),body.getPosition());
                    }else if(spriteIndex==44){
                        this.state = State.IDLE_L;
                    }
                    break;
                case RUN_R:
                    if (!(spriteIndex >= 45 && spriteIndex <= 51)) {
                        spriteIndex = 45;
                    }
                    if(spriteIndex==47){
                        body.applyLinearImpulse(new Vec2(100,0),body.getPosition());
                    }else if(spriteIndex==51){
                        this.state = State.IDLE_R;
                    }
                    break;
                case ATK1_L:
                    if (!(spriteIndex >= 52 && spriteIndex <= 58)) {
                        spriteIndex = 52;
                    }if(spriteIndex==53){
                        Status.enemyAttack(this,"L",5,10);
                    }else if(spriteIndex==58){
                        state = State.IDLE_L;
                    }
                    break;
                case ATK1_R:
                    if (!(spriteIndex >= 59 && spriteIndex <= 65)) {
                        spriteIndex = 59;
                    }
                    if(spriteIndex==60){
                        Status.enemyAttack(this,"R",5,10);
                    }else if(spriteIndex==65){
                        state = State.IDLE_R;
                    }
                    break;
                case ATK2_L:
                    if (!(spriteIndex >= 66 && spriteIndex <= 73)) {
                        spriteIndex = 66;
                    }
                    if(spriteIndex==68){
                        Status.enemyAttack(this,"L",5,10);
                    }else if(spriteIndex==73){
                        state = State.IDLE_L;
                    }
                    break;
                case ATK2_R:
                    if (!(spriteIndex >= 74 && spriteIndex <= 81)) {
                        spriteIndex = 74;
                    }
                    if(spriteIndex==76){
                        Status.enemyAttack(this,"R",5,10);
                    }else if(spriteIndex==81){
                        state = State.IDLE_R;
                    }
                    break;
                case ATK3_L:
                    if (!(spriteIndex >= 82 && spriteIndex <= 90)) {
                        spriteIndex = 82;
                    }
                    if(spriteIndex==84){
                        Status.enemyAttack(this,"L",5,10);
                    }else if(spriteIndex==90){
                        state = State.IDLE_L;
                    }
                    break;
                case ATK3_R:
                    if (!(spriteIndex >= 91 && spriteIndex <= 99)) {
                        spriteIndex = 91;
                    }
                    if(spriteIndex==93){
                        Status.enemyAttack(this,"R",5,10);
                    }else if(spriteIndex==99){
                        state = State.IDLE_R;
                    }
                    break;
                case BR_L:
                    if (!(spriteIndex >= 100 && spriteIndex <= 102)) {
                        spriteIndex = 100;
                    }
                    break;
                case BR_R:
                    if (!(spriteIndex >= 103 && spriteIndex <= 105)) {
                        spriteIndex = 103;
                    }
                    break;
                case DEF_L:
                    if (!(spriteIndex >= 106 && spriteIndex <= 110)) {
                        spriteIndex = 106;
                    }
                    break;
                case DEF_R:
                    if (!(spriteIndex >= 111 && spriteIndex <= 115)) {
                        spriteIndex = 111;
                    }
                    break;
                case HIT_L:
                    if (!(spriteIndex >= 126 && spriteIndex <= 130)) {
                        spriteIndex = 126;
                    }
                    if(spriteIndex==130){
                        state = State.IDLE_L;
                    }
                    break;
                case HIT_R:
                    if (!(spriteIndex >= 131 && spriteIndex <= 135)) {
                        spriteIndex = 131;
                    }
                    if(spriteIndex==135){
                        state = State.IDLE_R;
                    }
                    break;
                case DASH_L:
                    if (!(spriteIndex >= 136 && spriteIndex <= 139)) {
                        spriteIndex = 136;
                    }
                    if(spriteIndex>=136){
                        body.applyLinearImpulse(new Vec2(-20,0),body.getPosition());
                    }else if(spriteIndex==139){
                        body.setLinearVelocity(new Vec2(0,0));
                        this.state = State.IDLE_L;
                    }
                    break;
                case DASH_R:
                    if (!(spriteIndex >= 140 && spriteIndex <= 143)) {
                        spriteIndex = 140;
                    }
                    if(spriteIndex>=140){
                        body.applyLinearImpulse(new Vec2(20,0),body.getPosition());
                    }else if(spriteIndex==143){
                        body.setLinearVelocity(new Vec2(0,0));
                        this.state = State.IDLE_R;
                    }
                    break;
                case DEAD_L:
                    if (!(spriteIndex >= 144 && spriteIndex <= 151)) {
                        spriteIndex = 144;
                    }
                    if(spriteIndex==151){
                        state = State.DEADED_L;
                        isDead = true;
                    }
                    break;
                case DEAD_R:
                    if (!(spriteIndex >= 152 && spriteIndex <= 159)) {
                        spriteIndex = 152;
                    }
                    if(spriteIndex==159){
                        state = State.DEADED_R;
                        isDead = true;
                    }
                    break;
                case DEADED_L:
                    spriteIndex = 151;
                    break;
                case DEADED_R:
                    spriteIndex = 159;
                    break;
                default:
                    break;

            }
            e = 0;
            sprite.setSprite(spriteIndex);
            if (!isDead) {
                spriteIndex++;
                try {
                    //System.out.println("cd = "+cd);
                    if (rand.nextInt(5) == 3) {
                        //cd =0;
                        if (this.getBody().getPosition().x - Player.body.getPosition().x > 4) {
                            switch (rand.nextInt(4)) {
                                case 0:
                                    action("RUN_L");
                                    //this.state = State.RUN_L;
                                    break;
                                case 1:
                                    action("IDLE_L");
                                    //this.state = State.IDLE_L;
                                    break;
                                case 2:
                                    if(this.body.getPosition().x<24){
                                        action("RUN_R");
                                    }
                                    break;
                                case 4:
                                    action("RUN_L");
                                    //this.state = State.RUN_L;
                                    break;
                            }

                        } else if (Player.body.getPosition().x - this.getBody().getPosition().x > 4) {
                            switch (rand.nextInt(4)) {
                                case 0:
                                    action("RUN_R");
                                    //this.state = State.RUN_R;
                                    break;
                                case 1:
                                    action("IDLE_R");
                                    //this.state = State.IDLE_R;
                                    break;
                                case 2:
                                    if(body.getPosition().x>5){
                                        action("RUN_L");
                                    }
                                    break;
                                case 4:
                                    action("RUN_R");
                                    //this.state = State.RUN_R;
                                    break;
                            }
                        } else if (this.getBody().getPosition().x - Player.body.getPosition().x <= 4 &&
                                this.getBody().getPosition().x - Player.body.getPosition().x >= 0) {
                            //state = State.IDLE_L;
                            switch (rand.nextInt(8)) {
                                case 0:
                                    action("ATK1_L");
                                    //this.state = State.ATK1_L;
                                    break;
                                case 1:
                                    action("ATK2_L");
                                    //this.state = State.ATK2_L;
                                    break;
                                case 2:
                                    action("ATK3_L");
                                    //this.state = State.ATK3_L;
                                    break;
                                case 3:
                                    if(body.getPosition().x<24){
                                        action("RUN_R");
                                    }
                                    //this.state = State.ATK3_L;
                                    break;
                                case 4:
                                    action("IDLE_L");
                                    break;
                                case 5:
                                    action("ATK1_L");
                                    //this.state = State.ATK1_L;
                                    break;
                                case 6:
                                    action("ATK2_L");
                                    //this.state = State.ATK2_L;
                                    break;
                                case 7:
                                    action("ATK3_L");
                                    //this.state = State.ATK3_L;
                                    break;
                            }
                        } else if (Player.body.getPosition().x - this.getBody().getPosition().x <= 4 &&
                                Player.body.getPosition().x - this.getBody().getPosition().x >= 0) {
                            //state = State.IDLE_R;
                            switch (rand.nextInt(8)) {
                                case 0:
                                    action("ATK1_R");
                                    //this.state = State.ATK1_R;
                                    break;
                                case 1:
                                    action("ATK2_R");
                                    //this.state = State.ATK2_R;
                                    break;
                                case 2:
                                    action("ATK3_R");
                                    //this.state = State.ATK3_R;
                                    break;
                                case 3:
                                    if(body.getPosition().x>5){
                                        action("RUN_L");
                                    }
                                    //this.state = State.ATK3_R;
                                    break;
                                case 4:
                                    action("IDLE_R");
                                    break;
                                case 5:
                                    action("ATK1_R");
                                    //this.state = State.ATK1_R;
                                    break;
                                case 6:
                                    action("ATK2_R");
                                    //this.state = State.ATK2_R;
                                    break;
                                case 7:
                                    action("ATK3_R");
                                    //this.state = State.ATK3_R;
                                    break;
                            }
                        }
                    }
                } catch (Exception ex) {

                }
            }else{
                Status.enemyDead(this);
            }
        }
    }
    @Override
    public void update(int delta) {
        super.update(delta);
        if(hasLoaded == false) return;
        e = e + delta;
        genIndex();
    }

    public Body getBody(){
        return this.body;
    }

    @Override
    public void paint(Clock clock){
        super.paint(clock);
        if(!hasLoaded)return;
        /*hpLoad.setTranslation(
                body.getPosition().x / GameScreen.M_PER_PIXEL -40,
                body.getPosition().y / GameScreen.M_PER_PIXEL -60);*/
        /*sprite.layer().setTranslation(
                (body.getPosition().x / GameScreen.M_PER_PIXEL),
                (body.getPosition().y / GameScreen.M_PER_PIXEL));*/
        this.layer().setTranslation(
                (body.getPosition().x / GameScreen.M_PER_PIXEL),
                (body.getPosition().y / GameScreen.M_PER_PIXEL));
        //layer().setRotation(body.getAngle());
    }
    public E7(final World world, final float x, final float y, String name, int hp){
        super(world,x,y,name,hp);
    }
}
