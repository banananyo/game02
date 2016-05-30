package sut.game02.core.character;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game02.core.GameScreen;
import sut.game02.core.Status;
import sut.game02.core.ToolsG;
import sut.game02.core.sprite.Sprite;
import sut.game02.core.sprite.SpriteLoader;
import tripleplay.game.Screen;

/**
 * Created by Baze on 27/5/2559.
 */
public class Enemy extends Screen {
    public float alpha = 1;
    public boolean isDead=false;
    public int hp=5;
    public String name;
    public int cd=0;
    public PolygonShape shape;
    public Fixture f;
    public FixtureDef fixtureDef;
    public Sprite sprite;
    public int spriteIndex = 0;
    public boolean hasLoaded = false;
    public Body body;
    public State state = State.IDLE_L;
    public int e = 0;

    public Layer layer(){
        return this.sprite.layer();
    }
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
        fixtureDef.friction = 1;
        fixtureDef.restitution = 0.5f;

        f = body.createFixture(fixtureDef);
        body.setTransform(new Vec2(x,y),0f);
        body.setFixedRotation(true);
        return body;
    }
    public enum State{
        IDLE_L, IDLE_R,
        RUN_L, RUN_R,
        DEAD_L, DEAD_R,
        DEADED_L, DEADED_R,
        HIT_L,HIT_R,
        ATK_L, ATK_R,

        JMP_L, JMP_R,
        ATK1_L, ATK1_R,
        ATK2_L, ATK2_R,
        ATK3_L, ATK3_R,
        DEF_L, DEF_R,
        BR_L, BR_R,
        DASH_L, DASH_R
    }

    public Enemy(final World world, final float x, final float y, String name, int hp){
        this.name = name;
        this.hp = hp;
        sprite = SpriteLoader.getSprite("images/enemy/"+name+"/"+name+".json");
        sprite.addCallback(new Callback<Sprite>(){
            @Override
            public void onSuccess(Sprite result){
                sprite.setSprite(spriteIndex);
                //sprite.layer().setOrigin(sprite.width()/2f, sprite.height()/2f);
                //layer().setOrigin(sprite.width()/2, sprite.height()/2);
                //sprite.layer().setTranslation(x, y);
                layer().setTranslation(x, y);

                body = initPhysicsBody(world,
                        GameScreen.M_PER_PIXEL*x,
                        GameScreen.M_PER_PIXEL*y);

                /*hpLoadImage = assets().getImage("images/hp/monhp1.png");
                hpLoad = graphics().createImageLayer(hpLoadImage);
                hpLoad.setTranslation(x, y);
                Status.add_HP_Bar(hpLoad);*/
                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable cause){
                PlayN.log().error("Error Loading image!", cause);
            }
        });
    }
    public void genIndex(){
        //System.out.println("Enemy");
    }
    public Body getBody(){
        return this.body;
    }
    //@Override
    public void update(int delta) {
        super.update(delta);
        if(hasLoaded == false) return;
        e = e + delta;
        if(isDead){
            alpha = ToolsG.fadeOut(alpha);
            //sprite.layer().setAlpha(alpha);
            layer().setAlpha(alpha);
            if(alpha==0){
                Status.eList.remove(this);
            }
        }
        genIndex();
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
}
