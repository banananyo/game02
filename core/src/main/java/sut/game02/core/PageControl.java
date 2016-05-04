package sut.game02.core;

import org.omg.PortableInterceptor.SUCCESSFUL;
import playn.core.*;
import sut.game02.core.character.Samurai;
import tripleplay.game.ScreenStack;


public class PageControl {
    private Samurai.State state = Samurai.State.IDLE;
    public PageControl(final ScreenStack ss){
        final GameScreen gameScreen = new GameScreen(ss);
        final SettingScreen settingScreen = new SettingScreen(ss);

        PlayN.keyboard().setListener(new Keyboard.Adapter(){
            @Override
            public void onKeyDown (Keyboard.Event event){
                if(event.key() == Key.BACK && ss.size()>1){
                    ss.remove(ss.top()); //pop
                }else if(event.key() == Key.F1 && ss.size() <=1){
                    ss.push(gameScreen);
                }else if(event.key() == Key.F2 && ss.size() <=1){
                    ss.push(settingScreen);
                }
                if(event.key() == Key.Z){
                    Samurai.action("Z");
                }else if(event.key() == Key.RIGHT){
                    Samurai.action("Right");
                }
            }
        });
    }
}
