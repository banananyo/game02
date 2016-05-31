package sut.game02.core;

import com.sun.javafx.scene.control.skin.LabeledImpl;
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
import sut.game02.core.character.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.util.Colors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;


public class Status extends Screen {
    //static  int cd;
    public static boolean isOver = false;
    public static boolean isMsg = false;
    public static boolean isPaused = false;
    public static boolean isLand = false;
    public static boolean isDead = false;
    public static boolean isContact = false;
    public static String gameControl = "play";
    public static String hpString ="100/100";
    public static float hp=100f;
    public static Boolean showDebugDraw = true;
    public static String gameMsg = "alive";
    public static ImageLayer msg;

    public static ToolsG toolsG;
    public static Layer hpTextLayer;
    public static GroupLayer screenLayer;
    private static Layer gameMsgLayer;

    private static Player player;
    private ScreenStack ss;
    public static HashMap<Body,String> enemies = new HashMap<Body, String>();
    public static List<Enemy> eList = new ArrayList<Enemy>();

    public static Image image;
    public static ImageLayer imageLayer;


    public Status(final ScreenStack ss,final GroupLayer screenLayer, final Player player) {
        this.ss = ss;
        this.screenLayer = screenLayer;
        this.player = player;
        toolsG = new ToolsG();
        hpTextLayer = toolsG.genText("100/100",14, Colors.WHITE,25,20);
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
                }else  if((b == player.getBody() && a != GameScreen.ground && a!=GameScreen.wallRight && a!=GameScreen.wallLeft) ||
                        (a == player.getBody() && b != GameScreen.ground && b!=GameScreen.wallRight && b!=GameScreen.wallLeft)){
                    /*if(player.state != Player.State.AB_L &&
                            player.state != Player.State.AB_R &&
                            player.state != Player.State.BRK_L&&
                            player.state != Player.State.BRK_R){
                        if(player.state == Player.State.DEF_L || player.state == Player.State.DEF_R){
                            playerHit(4);
                        }else{
                            playerHit(12);
                        }
                    }*/
                    b.setLinearVelocity(new Vec2(0,0));
                    a.setLinearVelocity(new Vec2(0,0));
                }else if((b==player.getBody() && a==GameScreen.wallRight ||
                        a==player.getBody() && b==GameScreen.wallRight) &&
                    eList.isEmpty()){
                    isMsg = false;
                    nextStage(GameScreen.stage);
                    playerHit(0);
                }/*else if((b==player.getBody() && a==GameScreen.wallLeft ||
                        a==player.getBody() && b==GameScreen.wallLeft) &&
                    eList.isEmpty()){
                    isMsg = false;
                    backStage();
                }*/
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

    public static void playerAttack(Body playerBody,String LR,float range,float dmg){

        for(Enemy e: eList){
            if(e.getBody().getPosition().x>playerBody.getPosition().x
                    && e.getBody().getPosition().x-playerBody.getPosition().x<range && LR=="R"){

                enemyHit(LR,dmg,e);
            }
            else if(e.getBody().getPosition().x<playerBody.getPosition().x
                    && playerBody.getPosition().x-e.getBody().getPosition().x<range && LR=="L"){

                enemyHit(LR,dmg,e);
            }
        }
    }
    public static void enemyAttack(Enemy e,String LR,float range,int dmg){
            if(e.getBody().getPosition().x-Player.body.getPosition().x<range && LR=="L"){
                Player.body.applyLinearImpulse(new Vec2(-50,-10),e.getBody().getPosition());
                playerHit(dmg);
                //Player.state = Player.State.BRK_R;
            }
            else if(Player.body.getPosition().x-e.getBody().getPosition().x<range && LR=="R"){
                Player.body.applyLinearImpulse(new Vec2(50,-10),e.getBody().getPosition());
                playerHit(dmg);
                //Player.state = Player.State.BRK_L;
            }
    }
    public static void playerHit(float dmg){
        if(Player.state == Player.State.DEF_L){
            hp -= dmg/2;
        }else if(Player.state == Player.State.DEF_R){
            hp -= dmg/2;
        }else if(dmg>10) {
            player.action(14);
            hp -= dmg;
        }else{
            player.action(7);
            hp -= dmg;
        }

        hpString = Status.hp+"/100";
        screenLayer.remove(hpTextLayer);
        hpTextLayer = toolsG.genText(hpString,14, Colors.WHITE,25,20);
        screenLayer.add(hpTextLayer);
    }
    public static void enemyHit(String lr,float dmg,Enemy e){
        e.hp-=dmg;
        if(lr == "L"){
            e.getBody().applyLinearImpulse(new Vec2(-50,-10),e.getBody().getPosition());
            e.state = Enemy.State.HIT_L;
        }else if(lr == "R"){
            e.getBody().applyLinearImpulse(new Vec2(50,-10),e.getBody().getPosition());
            e.state = Enemy.State.HIT_R;
        }
    }
    public static void gameOver(){
        player.action(8);
        hp = 0;
        //isDead = true;
        hpString = Status.hp+"/100";
        screenLayer.remove(hpTextLayer);
        hpTextLayer = toolsG.genText(hpString,14, Colors.WHITE,25,20);
        screenLayer.add(hpTextLayer);

        /*gameMsg = "Game Over.";
        gameMsgLayer = toolsG.genText(Status.gameMsg,60,Colors.RED,100,200);
        screenLayer.add(gameMsgLayer);*/
        if(!isMsg){
            genMsgLayer("over");
        }
    }

    public void update(int delta){
        super.update(delta);

    }
    public static void pause(){
        genMsgLayer("pause");
        Status.gameControl = "pause";
        Status.isPaused = true;
        /*gameMsg = "Game Paused.";
        gameMsgLayer = toolsG.genText(Status.gameMsg,60,Colors.RED,100,200);
        screenLayer.add(gameMsgLayer);*/
    }
    public  static void play(){
        //screenLayer.remove(gameMsgLayer);
        delMsgLayer();
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
    public static void createEnemy(final World world,float x,float y, String name,int hp){
        if(name == "e1"){
            System.out.println("create e1");
            E1  e = new E1(world,x,y,name,hp);
            //enemies.put(e.getBody(),name);
            eList.add(e);
        }else if(name == "e2"){
            System.out.println("create e2");
            E2 e = new E2(world,x,y,name,hp);
            //enemies.put(e.getBody(),name);
            eList.add(e);
        }else if(name == "e3"){
            System.out.println("create e3");
            E3 e = new E3(world,x,y,name,hp);
            //enemies.put(e.getBody(),name);
            eList.add(e);
        }else if(name == "e4"){
            System.out.println("create e4");
            E4 e = new E4(world,x,y,name,hp);
            //enemies.put(e.getBody(),name);
            eList.add(e);
        }else if(name == "e5"){
            System.out.println("create e5");
            E5 e = new E5(world,x,y,name,hp);
            //enemies.put(e.getBody(),name);
            eList.add(e);
        }else if(name == "e6"){
            System.out.println("create e6");
            E6 e = new E6(world,x,y,name,hp);
            //enemies.put(e.getBody(),name);
            eList.add(e);
        }else if(name == "e7"){
            System.out.println("create e7");
            E7 e = new E7(world,x,y,name,hp);
            eList.add(e);
        }
    }

    public static ImageLayer genMsgLayer(String msg){
        image           = assets().getImage("images/msg/"+msg+".png");
        imageLayer = graphics().createImageLayer(image);
        //imageLayer.setWidth(800);
        //imageLayer.setHeight(400);
        imageLayer.setTranslation(250,125);
        screenLayer.add(imageLayer);
        isMsg=true;
        return imageLayer;
    }

    public static void delMsgLayer() {
        screenLayer.remove(imageLayer);
    }

    public static void enemyDead(Enemy e){
        GameScreen.world.destroyBody(e.getBody());
    }

    public static void nextStage(int stage){
        if(stage+1<=7){
            System.out.println("next Stage");
            screenLayer.remove(imageLayer);
            screenLayer.remove(player.layer());
            MyGame.ss.push(new GameScreen(MyGame.ss, ++stage));
        }
    }

    public static void backStage(){
        System.out.println("back Stage");
        //this.ss.push(new GameScreen(this.ss, stage+1));
        MyGame.ss.remove(MyGame.ss.top());
    }

}
