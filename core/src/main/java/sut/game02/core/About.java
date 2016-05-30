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

public class About extends UIScreen {
    public static ScreenStack ss;
    private final Image bbImage;
    private final ImageLayer bb;
    private Root root;

    public About(final ScreenStack ss){
        this.ss = ss;

        Image bgImage = assets().getImage("images/about.png");
        ImageLayer bg = graphics().createImageLayer(bgImage);
        bg.setTranslation(0, 0);

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
        root.layer.add(bb);
    }
    public void wasShown (){
        super.wasShown();
    }
    @Override
    public void update(int delta){
        super.update(delta);
    }
}