package sut.game02.core.character;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import playn.core.util.Clock;
import sut.game02.core.GameScreen;
import tripleplay.game.Screen;

/**
 * Created by Baze on 27/5/2559.
 */
public class SwordBeam extends Screen{
    public Body body;
    private Body playerBody;
    public SwordBeam(Body body){
        this.playerBody = body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        //bodyDef.type = BodyType.STATIC;
        bodyDef.position = new Vec2(0,0);
        body = GameScreen.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f,1f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 3;

        body.createFixture(fixtureDef);
        body.setTransform(new Vec2(playerBody.getPosition().x,playerBody.getPosition().y),0f);
    }
    public void update(int delta){
        super.update(delta);
        //layer.setTranslation(playerBody.getPosition().x,playerBody.getPosition().y+5);
    }
    public void paint(Clock clock){
        super.paint(clock);
        layer.setTranslation(
                (playerBody.getPosition().x / GameScreen.M_PER_PIXEL),
                (playerBody.getPosition().y+1 / GameScreen.M_PER_PIXEL));
    }
}
