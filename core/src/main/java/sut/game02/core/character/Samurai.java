package sut.game02.core.character;

import playn.core.*;
import playn.core.util.Callback;
import sut.game02.core.GameScreen;
import sut.game02.core.SettingScreen;
import sut.game02.core.sprite.Sprite;
import sut.game02.core.sprite.SpriteLoader;
import tripleplay.game.ScreenStack;


public class Samurai {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    public enum State{
        IDLE, ATTK, RUN
    };

    private static State state = State.IDLE;

    private int e = 0;
    private int offset = 0;
    public static void action(String key){
        if(key == "Z"){
            switch(state){
                case IDLE: state = State.ATTK; break;
                case RUN: state = State.ATTK; break;
                case ATTK: state = State.IDLE; break;
            }
        }else if(key == "Right"){
            switch(state){
                case IDLE: state = State.RUN; break;
                case RUN: state = State.IDLE; break;
                case ATTK: state = State.RUN; break;
            }
        }
    }
    public Samurai(final float x, final float y){
        sprite = SpriteLoader.getSprite("images/Samurai.json");
        sprite.addCallback(new Callback<Sprite>(){
            @Override
            public void onSuccess(Sprite result){
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f, sprite.height()/2f);
                sprite.layer().setTranslation(x, y);
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
    public void update(int delta) {
        if(hasLoaded == false) return;

        e = e + delta;
        if(e > 150){
            switch (state){
                case IDLE: offset = 0; break;
                case RUN: offset = 4; break;
                case ATTK: offset = 8;
                    if(spriteIndex == 10){
                        state = State.IDLE;
                    }break;
            }
            spriteIndex = offset + ((spriteIndex + 1) % 4);
            sprite.setSprite(spriteIndex);
            e=0;
        }
    }
}
