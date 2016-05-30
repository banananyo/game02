package sut.game02.core;

import org.jbox2d.common.Vec2;
import org.omg.PortableInterceptor.SUCCESSFUL;
import playn.core.*;
import sut.game02.core.character.Player;
import tripleplay.game.Screen;


public class PageControl extends Screen {
    public PageControl(final Player player){
            PlayN.keyboard().setListener(new Keyboard.Adapter() {
                @Override
                public void onKeyDown(Keyboard.Event event) {
                /*if(event.key() == Key.BACK && ss.size()>1){
                    ss.remove(ss.top()); //pop
                }else if(event.key() == Key.F1 && ss.size() <=1){
                    ss.push(gameScreen);
                }else if(event.key() == Key.F2 && ss.size() <=1){
                    ss.push(settingScreen);
                }*/
                    if (event.key() == Key.Z) {
                        player.action(1);
                    } else if (event.key() == Key.RIGHT) {
                        player.action(2);
                    } else if (event.key() == Key.LEFT) {
                        player.action(3);
                    } else if (event.key() == Key.S) {
                        player.action(6);
                    } else if (event.key() == Key.E) {
                        player.action(7);
                    } else if (event.key() == Key.R) {
                        player.action(8);
                    } else if (event.key() == Key.SPACE) {
                        player.action(9);
                    } else if (event.key() == Key.X) {
                        player.action(10);
                    } else if (event.key() == Key.C) {
                        player.action(11);
                    } else if (event.key() == Key.D) {
                        player.action(12);
                    }

                    if (event.key() == Key.ESCAPE && Status.isPaused == false) {
                        Status.pause();
                    } else if (event.key() == Key.ESCAPE && Status.isPaused == true && Status.hp >= 0) {
                        Status.play();
                    } else if (event.key() == Key.Q) {
                        Status.hp += 5f;
                    } else if (event.key() == Key.W) {
                        Status.hp -= 5f;
                    }
                }

                public void onKeyUp(Keyboard.Event event) {
                /*if(event.key() == Key.UP && (sho.state == Samurai.State.JUMP||sho.state == Samurai.State.RUN)){
                    sho.action("Down");
                    //samurai.state = Samurai.State.IDLE;
                }*/
                    if (event.key() == Key.RIGHT) {
                        player.action(4);
                    } else if (event.key() == Key.LEFT) {
                        player.action(5);
                    } else if(event.key() == Key.SPACE){
                        player.action(13);
                    } else if(event.key() == Key.D){
                        player.action(12);
                    }
                }
            });
    }
    public void update(int delta){
        super.update(delta);
    }
}
