package sut.game02.core;

import static playn.core.PlayN.*;

import javafx.scene.input.KeyEvent;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.Font;
import playn.core.Image;
import playn.core.Mouse.*;
import playn.core.util.Clock;
import playn.core.util.TextBlock;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.game.*;
import tripleplay.ui.Root;
import tripleplay.ui.*;
import tripleplay.ui.layout.*;
import react.UnitSlot;
import sut.game02.core.character.*;

import playn.core.util.Callback;
import sut.game02.core.sprite.Sprite;
import sut.game02.core.sprite.SpriteLoader;
import tripleplay.util.Colors;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class GameScreen extends Screen {
    public static Clock.Source c = new Clock.Source(0);
    public static Body wallLeft;
    public static Body wallRight;
    public static Body ceil;
    public static Body ground;
    //public static Body slideGround;
    private ScreenStack ss/* = new ScreenStack()*/;
    //private final ScreenStack ss;
    private final Image bbImage;
    private final ImageLayer bb;

    private final Image bgImage;
    private final ImageLayer bg;

    private final Image landImage;
    private final ImageLayer land;

    private final Image hpLoadImage;
    private final ImageLayer hpLoad;
    private final Image hpBarImage;
    private final ImageLayer hpBar;

    public static float M_PER_PIXEL=1/26.666667f;
    public static float width=800*M_PER_PIXEL;
    public static float height=400*M_PER_PIXEL;
    public static float widthPX=800;
    public static float heightPX=400;
    public static  World world;

    private Body a;
    private Body b;

    private PageControl control;
    //private Enemy A;

    private static Player player;
    private static float pos_x;

    public static String stage = "01";

    private DebugDrawBox2D debugDraw;

    public static Status status;

  public GameScreen(final ScreenStack ss,String stage){
      this.ss = ss;
      this.stage = stage;
      hpLoadImage = assets().getImage("images/hp/hp2.png");
      hpLoad = graphics().createImageLayer(hpLoadImage);
      hpLoad.setTranslation(77, 15);
      //----------------------------------------------------------------------
      hpBarImage = assets().getImage("images/hp/hp1.png");
      hpBar = graphics().createImageLayer(hpBarImage);
      hpBar.setTranslation(5, 5);
      //----------------------------------------------------------------------
      bbImage = assets().getImage("images/backbutt.png");
      this.bb = graphics().createImageLayer(bbImage);
      bb.setTranslation(width/M_PER_PIXEL-50,5);
      bb.addListener(new Mouse.LayerAdapter() {
          @Override
          public void onMouseUp(Mouse.ButtonEvent event) {
              //status.pause();
              ss.remove(ss.top()); //pop
              //layer.removeAll();
              /*for(Enemy e: status.eList){
                  try {
                      e.layer.removeAll();
                      e.layer().destroy();
                      world.destroyBody(e.getBody());
                  }catch (Exception ex){

                  }
              }*/
              status.eList.clear();
              //player.layer.removeAll();
          }
      });

      bgImage = assets().getImage("images/stage/stage"+stage+".png");
      this.bg = graphics().createImageLayer(bgImage);

      landImage = assets().getImage("images/stage/g_stage"+stage+".png");
      this.land = graphics().createImageLayer(landImage);
      //----------------------------------------------------------------------
      this.layer.add(bg);
      this.layer.add(land);
      this.layer.add(bb);
      this.layer.add(hpLoad);
      this.layer.add(hpBar);
      //----------------------------------------------------------------------
      final Vec2 gravity = new Vec2(0.0f, 10.0f);
      world=new World(gravity);
      world.setWarmStarting(true);
      world.setAutoClearForces(true);

      //----------------------------------------------------------------------
      ceil = world.createBody(new BodyDef());
      EdgeShape ceilShape = new EdgeShape();
      ceilShape.set(new Vec2(0,0), new Vec2(width,0));
      ceil.createFixture(ceilShape, 0.0f);
      //----------------------------------------------------------------------
      /*BodyDef s = new BodyDef();
      s.type = BodyType.DYNAMIC;
      slideGround = world.createBody(s);
      PolygonShape slideGroundShape = new PolygonShape();
      slideGroundShape.setAsBox(60, 0.02f);
      FixtureDef fixtureDef = new FixtureDef();
      fixtureDef.density = 0.5f;
      fixtureDef.friction = 1;
      fixtureDef.shape = slideGroundShape;
      slideGround.createFixture(fixtureDef);
      slideGround.setTransform(new Vec2(0,height-0.8f),0f);
      slideGround.setFixedRotation(true);*/
      //----------------------------------------------------------------------
      ground = world.createBody(new BodyDef());
      EdgeShape groundShape = new EdgeShape();
      groundShape.set(new Vec2(0,height-0.8f), new Vec2(width+30,height-0.8f));
      FixtureDef fixtureDef = new FixtureDef();
      fixtureDef.friction = 1.2f;
      fixtureDef.shape = groundShape;
      ground.createFixture(fixtureDef);
      //----------------------------------------------------------------------
      wallLeft = world.createBody(new BodyDef());
      EdgeShape wallLShape = new EdgeShape();
      wallLShape.set(new Vec2(-1,0), new Vec2(-1,height));
      wallLeft.createFixture(wallLShape, 0.0f);
      //----------------------------------------------------------------------
      wallRight = world.createBody(new BodyDef());
      EdgeShape wallRShape = new EdgeShape();
      wallRShape.set(new Vec2(width+1,0), new Vec2(width+1,height));
      wallRight.createFixture(wallRShape, 0.0f);
      //----------------------------------------------------------------------
      player = new Player(world,40f,350f);
      if(stage == "01"){
          status.createEnemy(world,400f,300f,"e1",2);
          status.createEnemy(world,600f,300f,"e1",2);
      }else if(stage == "02"){
          status.createEnemy(world,500f,300f,"e2",5);
          status.createEnemy(world,700f,300f,"e2",5);
      }
      control = new PageControl(player);
      //A = new Enemy(world,0,0,"A",1);
      //----------------------------------------------------------------------
  }

    @Override
  public void wasShown (){
    super.wasShown();
        //----------------------------------------------------------------------
        //sho.layer().setScale(0.6f);
        status = new Status(layer,player);
        layer.add(player.layer());
        try{
            for(Enemy e : status.eList)
                layer.add(e.layer());
        }catch (Exception ex){

        }
        //----------------------------------------------------------------------


      if(status.showDebugDraw){
          CanvasImage canvasImage = graphics().createImage(
                  (int) (width/GameScreen.M_PER_PIXEL),
                  (int) (height/GameScreen.M_PER_PIXEL));
          layer.add(graphics().createImageLayer(canvasImage));
          debugDraw=new DebugDrawBox2D();
          debugDraw.setCanvas(canvasImage);
          debugDraw.setFlipY(false);
          debugDraw.setStrokeAlpha(150);
          debugDraw.setFillAlpha(75);
          debugDraw.setStrokeWidth(2.0f);
          debugDraw.setFlags(DebugDraw.e_shapeBit |
                  DebugDraw.e_jointBit |
                  DebugDraw.e_aabbBit);
          debugDraw.setCamera(0,0,1f/GameScreen.M_PER_PIXEL);
          world.setDebugDraw(debugDraw);
      }
      //----------------------------------------------------------------------
  }
    @Override
    public void update(int delta) {
        if(status.gameControl == "play"){
            try{
                super.update(delta);
                world.step(0.1f, 10, 10);
                this.player.update(delta);
                for (Enemy e: status.eList) {
                    e.update(delta);
                }
                //A.update(delta);
            }catch (Exception ex){}

            if(status.hp <= 0){
                status.gameOver();
            }else if(status.hp>100){
                status.hp = 100;
            }

            if(Status.eList.isEmpty()){
                this.ss.remove(ss.top());
            }
        }else if(status.gameControl == "pause"){

        }
    }
    @Override
    public void paint(Clock clock){
        if(status.gameControl == "play"){
            super.paint(clock);
            this.player.paint(clock);
            for (Enemy e: status.eList) {
                e.paint(clock);
            }
            //A.paint(clock);
            if(status.showDebugDraw) {
                debugDraw.getCanvas().clear();
                world.drawDebugData();
                debugDraw.getCanvas().drawText(status.debugString,50,200);
                //debugDraw.getCanvas().setFillColor(Color.rgb(255,255,255));
                //debugDraw.getCanvas().setStrokeColor(Color.rgb(255,255,255));
            }
            try{
                pos_x = -(128+player.getBody().getPosition().x*5);
                this.bg.setTranslation(pos_x,0f);
            }catch (Exception ex){

            }
            //this.hpLoad.setTranslation((hp)-25,15);
            hpLoad.setWidth(status.hp*2.5f);
        }else if(status.gameControl == "pause"){
            super.paint(c);
            this.player.paint(c);
            for (Enemy e: status.eList) {
                e.paint(c);
            }
        }
    }
}