package sut.game02.core;

import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.Mouse.*;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.game.*;
import tripleplay.ui.Root;
import tripleplay.ui.*;
import playn.core.Font;
import tripleplay.ui.layout.*;
import react.UnitSlot;

public class ChooseScreen extends UIScreen {
    private final ScreenStack ss;
    private final Image bbImage;
    private final ImageLayer bb;

    private Root root;
    private final Button stage_01 = new Button("stage 1");
    private final Button stage_02 = new Button("stage 2");

    private static String stage = "";

    public ChooseScreen(final ScreenStack ss){
        this.ss = ss;

        bbImage = assets().getImage("images/backbutt.png");
        this.bb = graphics().createImageLayer(bbImage);
        bb.setTranslation(10, 400);

        bb.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top()); //pop
            }
        });
        this.layer.add(bb);


        root = iface.createRoot(AxisLayout.vertical().gap(15), SimpleStyles.newSheet(), layer);
        root.addStyles(Style.BACKGROUND.is(Background.bordered(0xFFCCCCCC, 0xFF99CCFF, 5).inset(5, 10)));
        root.setSize(width(), height());
        root.add(new Label("Choose Stage").addStyles(Style.FONT.is(Home.TITLE_FONT)));
        //----------------------------------------------------------------------
        root.add(stage_01.onClick(new UnitSlot() {
            public void onEmit() {
                ss.push(new GameScreen(ss,"01"));
            }
        }));
        root.add(stage_02.onClick(new UnitSlot() {
            public void onEmit() {
                ss.push(new GameScreen(ss,"02"));
            }
        }));

    }
    public void wasShown (){
        super.wasShown();
        //control = new PageControl();
    }

}