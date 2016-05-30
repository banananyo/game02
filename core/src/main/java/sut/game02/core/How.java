package sut.game02.core;

import static playn.core.PlayN.*;

import playn.core.*;
import playn.core.Font;
import playn.core.Image;
import playn.core.Mouse.*;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.game.*;
import tripleplay.ui.Box;
import tripleplay.ui.Button;
import tripleplay.ui.Icon;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.*;
import tripleplay.ui.layout.*;
import react.UnitSlot;

import javax.swing.*;
import java.awt.*;

public class How extends UIScreen {
    public static ScreenStack ss;
    private final Image bbImage;
    private final ImageLayer bb;

    Image bgImage;
    ImageLayer bg;
    Image mImage;
    ImageLayer m;
    Image tImage;
    ImageLayer t;
    int z=0;
    int y=0;
    private Root root;



    public How(final ScreenStack ss){
        this.ss = ss;

        bgImage = assets().getImage("images/how.png");
        bg = graphics().createImageLayer(bgImage);
        bg.setTranslation(0, 0);

        tImage = assets().getImage("images/how_top.png");
        t = graphics().createImageLayer(tImage);
        t.setTranslation(0, 0);

        mImage = assets().getImage("images/manual.png");
        m = graphics().createImageLayer(mImage);
        m.setTranslation(0, 100);

        bbImage = assets().getImage("images/backbutt.png");
        this.bb = graphics().createImageLayer(bbImage);
        bb.setTranslation(750,5);
        bb.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top()); //pop
            }
        });

        root = iface.createRoot(AxisLayout.vertical().gap(15), SimpleStyles.newSheet(), layer);
        root.setSize(width(), height());
        root.layer.add(bg);
        root.layer.add(m);
        root.layer.add(t);
        root.layer.add(bb);

        PlayN.keyboard().setListener(new Keyboard.Adapter() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
                if (event.key() == Key.UP && y<0) {
                    z=500;
                    up();
                } else if (event.key() == Key.DOWN) {
                    z=-500;
                    down();
                }
            }
            public void onKeyUp(Keyboard.Event event) {
                if (event.key() == Key.UP && y>-1300) {
                    z=0;
                } else if (event.key() == Key.DOWN) {
                    z=0;
                }
            }
        });
    }
    public void wasShown (){
        super.wasShown();
    }
    @Override
    public void update(int delta){
        super.update(delta);
        if(z>0 && y<0){
            up();
        }else if(z<0 && y>-1300){
            down();
        }
    }
    private void up(){
        z--;
        y+=5;
        m.setTranslation(0,y+100);
    }
    private void down(){
        z++;
        y-=5;
        m.setTranslation(0,y+100);
    }
}