package sut.game02.core.character;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import playn.core.Layer;
import playn.core.util.Clock;

/**
 * Created by Baze on 27/5/2559.
 */
public interface Wall {
    public Body initPhysicsBody(World world, float x, float y);
    public enum State{};
    public Body getBody();
    public void update(int delta);
    public void paint(Clock clock);
    public Layer layer();
}
