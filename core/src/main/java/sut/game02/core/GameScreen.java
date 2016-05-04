package sut.game02.core;

import static playn.core.PlayN.*;

import javafx.scene.input.KeyEvent;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.Mouse.*;
import playn.core.util.Clock;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class GameScreen extends Screen {
  private final ScreenStack ss;
    private final Image bbImage;
    private final ImageLayer bb;
    public static float M_PER_PIXEL=1/26.666667f;
    private static int width=24;
    private static int height=18;
    private World world;
  private Samurai samurai;
    private PageControl control;
    private DebugDrawBox2D debugDraw;
    private Boolean showDebugDraw = true;
    private Body body;
    private BodyDef bodyDef;

  public GameScreen(final ScreenStack ss){
      this.ss = ss;
      //----------------------------------------------------------------------
      bbImage = assets().getImage("images/backbutt.png");
      this.bb = graphics().createImageLayer(bbImage);
      bb.setTranslation(10, 400);
      bb.addListener(new Mouse.LayerAdapter() {
          @Override
          public void onMouseUp(Mouse.ButtonEvent event) {
              ss.remove(ss.top()); //pop
          }
      });
      //----------------------------------------------------------------------
      this.layer.add(bb);
      //----------------------------------------------------------------------
      Vec2 gravity = new Vec2(0.0f, 10.0f);
      world=new World(gravity);
      world.setWarmStarting(true);
      world.setAutoClearForces(true);
      //----------------------------------------------------------------------
      Body ground = world.createBody(new BodyDef());
      EdgeShape groundShape = new EdgeShape();
      groundShape.set(new Vec2(0,height/2), new Vec2(width,height/2));
      ground.createFixture(groundShape, 0.0f);
      //----------------------------------------------------------------------




  }
  @Override
  public void wasShown (){
    super.wasShown();
    samurai = new Samurai(350f,height/2/M_PER_PIXEL-25);
    control = new PageControl(ss);
    this.layer.add(samurai.layer());

      //----------------------------------------------------------------------
      if(showDebugDraw){
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
      mouse().setListener(new Mouse.Adapter(){
          @Override
          public  void onMouseUp(Mouse.ButtonEvent event){
              bodyDef = new BodyDef();
              bodyDef.type = BodyType.DYNAMIC;
              bodyDef.position = new Vec2(
                      event.x()*M_PER_PIXEL,
                      event.y()*M_PER_PIXEL);
              body = world.createBody(bodyDef);
              CircleShape shape = new CircleShape();
              shape.setRadius(0.4f);

              FixtureDef fixtureDef = new FixtureDef();
              fixtureDef.shape = shape;
              fixtureDef.density = 1f;
              fixtureDef.friction = 0.1f;
              fixtureDef.restitution = 1f;

              body.createFixture(fixtureDef);
              body.setLinearDamping(0.2f);
              //body.setTransform(new Vec2(event.x(),event.y()),0f);

          }
      });
  }
@Override
    public void update(int delta) {
        super.update(delta);
        world.step(0.033f, 10, 10);
        this.samurai.update(delta);

    }
    @Override
    public void paint(Clock clock){
        super.paint(clock);
        if(showDebugDraw) {
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
    }

}