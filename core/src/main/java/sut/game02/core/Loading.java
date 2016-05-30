package sut.game02.core;

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
//import tripleplay.game.ScreenStack;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;


public class Loading extends Screen{
    public float alpha = 1;
    public Sprite sprite;
    public int spriteIndex = 0;
    public boolean hasLoaded = false;
    public State state = State.WALK;
    public int e = 0;

    public Layer layer(){
        return this.sprite.layer();
    }
    public enum State{
        WALK
    }

    public Loading(final float x, final float y){
        sprite = SpriteLoader.getSprite("images/loading/loading.json");
        sprite.addCallback(new Callback<Sprite>(){
            @Override
            public void onSuccess(Sprite result){
                sprite.setSprite(spriteIndex);
                layer().setOrigin(sprite.width()/2f, sprite.height()/2f);
                layer().setTranslation(x, y);
                hasLoaded = true;
            }
            @Override
            public void onFailure(Throwable cause){
                PlayN.log().error("Error Loading image!", cause);
            }
        });
    }
    @Override
    public void update(int delta) {
        super.update(delta);
        if(hasLoaded == false) return;
        e += delta;
        if(e > 50) {
            switch (state){
                case WALK:
                    if (!(spriteIndex >= 1 && spriteIndex <= 10)) {
                        spriteIndex = 1;
                    }
                    break;
            }
            sprite.setSprite(spriteIndex);
            spriteIndex++;
            e = 0;
        }
    }
    @Override
    public void paint(Clock clock){
        super.paint(clock);
        if(!hasLoaded)return;
    }
}
