package sut.game02.core;

import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Camera;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import org.omg.PortableInterceptor.SUCCESSFUL;
import playn.core.*;
import sut.game02.core.character.E1;
import sut.game02.core.character.E2;
import sut.game02.core.character.Enemy;
import sut.game02.core.character.Player;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.util.Colors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Status extends Screen {
    //static  int cd;
    public static boolean isPaused = false;
    public static boolean isLand = false;
    public static boolean isDead = false;
    public static boolean isContact = false;
    public static String gameControl = "play";
    public static String debugString ="100/100";
    public static int hp=100;
    public static Boolean showDebugDraw = false;
    public static String gameMsg = "alive";


    public static ToolsG toolsG;
    public static Layer hpTextLayer;
    public static GroupLayer screenLayer;
    private static Layer gameMsgLayer;

    private static Player player;

    public static HashMap<Body,String> enemies = new HashMap<Body, String>();
    public static List<Enemy> eList = new ArrayList<Enemy>();

    public Status(final GroupLayer screenLayer, final Player player) {
        this.screenLayer = screenLayer;
        this.player = player;
        toolsG = new ToolsG();
        hpTextLayer = toolsG.genText(debugString,14, Colors.WHITE,25,20);
        screenLayer.add(hpTextLayer);


        GameScreen.world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                if((a == player.getBody() && b == GameScreen.ground) ||
                        (b == player.getBody() && a == GameScreen.ground)){
                    isLand = true;
                    //status.isJump=false;
                }else  if((b == player.getBody() && a != GameScreen.ground && a!= GameScreen.wallLeft)/* ||
                        (b == player.getBody() && a != GameScreen.ground && a!= GameScreen.wallLeft)*/){
                    playerHit(15);
                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });

    }

    public static void playerAttack(Body playerBody,String LR){

        for(Enemy e: eList){
            if(e.getBody().getPosition().x>playerBody.getPosition().x
                    && e.getBody().getPosition().x-playerBody.getPosition().x<5 && LR=="R"){
                e.getBody().applyLinearImpulse(new Vec2(20,-50),e.getBody().getPosition());
                e.hp-=1;
                e.state = Enemy.State.HIT_L;
            }
            else if(e.getBody().getPosition().x<playerBody.getPosition().x
                    && playerBody.getPosition().x-e.getBody().getPosition().x<5 && LR=="L"){
                e.getBody().applyLinearImpulse(new Vec2(-20,-50),e.getBody().getPosition());
                e.hp-=1;
                e.state = Enemy.State.HIT_R;
            }
        }
        /*if(LR == "R"){
            body.setTransform(new Vec2(playerBody.getPosition().x+2,playerBody.getPosition().y),0f);
            body.applyLinearImpulse(new Vec2(200,0),body.getPosition());
        }else {
            body.setTransform(new Vec2(playerBody.getPosition().x-2,playerBody.getPosition().y),0f);
            body.applyLinearImpulse(new Vec2(-200,0),body.getPosition());
        }*/
    }
    public  static void enemyAttack(){

    }
    public static void playerHit(int dmg){
        player.action(7);
        hp -= dmg;
        debugString = Status.hp+"/100";
        screenLayer.remove(hpTextLayer);
        hpTextLayer = toolsG.genText(debugString,14, Colors.WHITE,25,20);
        screenLayer.add(hpTextLayer);
    }
    public static void enemyHit(){

    }
    public static void gameOver(){
        player.action(8);
        hp = 0;
        debugString = Status.hp+"/100";
        screenLayer.remove(hpTextLayer);
        hpTextLayer = toolsG.genText(debugString,14, Colors.WHITE,25,20);
        screenLayer.add(hpTextLayer);

        gameMsg = "Game Over.";
        gameMsgLayer = toolsG.genText(Status.gameMsg,60,Colors.RED,100,200);
        screenLayer.add(gameMsgLayer);
    }

    public void update(int delta){
        super.update(delta);

    }
    public static void pause(){
        Status.gameControl = "pause";
        Status.isPaused = true;
        gameMsg = "Game Paused.";
        gameMsgLayer = toolsG.genText(Status.gameMsg,60,Colors.RED,100,200);
        screenLayer.add(gameMsgLayer);
    }
    public  static void play(){
        screenLayer.remove(gameMsgLayer);
        Status.gameControl = "play";
        //cd_play();
        Status.isPaused = false;
    }
    /*public static  void cd_play(){
        int i=3;
        if(cd > 400){
            screenLayer.remove(gameMsgLayer);
            gameMsg = ""+i--;
            gameMsgLayer = toolsG.genText(Status.gameMsg,60,Colors.RED,100,200);
            screenLayer.add(gameMsgLayer);
        }if(cd > 800){
            screenLayer.remove(gameMsgLayer);
            gameMsg = ""+i--;
            gameMsgLayer = toolsG.genText(Status.gameMsg,60,Colors.RED,100,200);
            screenLayer.add(gameMsgLayer);
        }if(cd > 1200){
            screenLayer.remove(gameMsgLayer);
            gameMsg = ""+i--;
            gameMsgLayer = toolsG.genText(Status.gameMsg,60,Colors.RED,100,200);
            screenLayer.add(gameMsgLayer);
            cd=0;
        }
    }*/
    public  static void add_HP_Bar(Layer hpLayer){
        screenLayer.add(hpLayer);
    }
    public static void createEnemy(World world,float x,float y, String name,int hp){
        if(name == "e1"){
            E1  e = new E1(world,x,y,name,hp);
            enemies.put(e.getBody(),name);
            eList.add(e);
        }else if(name == "e2"){
            E2 e = new E2(world,x,y,name,hp);
            enemies.put(e.getBody(),name);
            eList.add(e);
        }
    }

    public static void enemyDead(Enemy e){
        GameScreen.world.destroyBody(e.getBody());
    }

}
