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

public class ChooseScreen extends UIScreen {
    //private final ScreenStack ss;
    public static ScreenStack ss;
    private final Image bbImage;
    private final ImageLayer bb;
    private Root root;
    Field field;

    public ChooseScreen(final ScreenStack ss){
        this.ss = ss;

        Image bgImage = assets().getImage("images/choose.png");
        ImageLayer bg = graphics().createImageLayer(bgImage);
        bg.setTranslation(0, 0);

        Image newImage = assets().getImage("images/button/newgame.png");
        ImageLayer n = graphics().createImageLayer(newImage);
        n.setTranslation(350, 150);

        Image subImage = assets().getImage("images/button/submit.png");
        ImageLayer sub = graphics().createImageLayer(subImage);
        sub.setTranslation(350, 215);

        bbImage = assets().getImage("images/backbutt.png");
        this.bb = graphics().createImageLayer(bbImage);
        bb.setTranslation(750,5);

        n.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                MyGame.ss.push(new GameScreen( MyGame.ss,0));
            }
        });
        sub.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                System.out.println(field.text.get());
                //ss.push(new GameScreen(ss,1));
                if(field.text.get().equalsIgnoreCase("461K")){
                    /*num=1;
                    genPage();
                    System.out.println("if");*/
                    MyGame.ss.push(new GameScreen( MyGame.ss,1));
                }else if(field.text.get().equalsIgnoreCase("Y7UD")){
                    MyGame.ss.push(new GameScreen( MyGame.ss,2));
                }else if(field.text.get().equalsIgnoreCase("NAPQ")){
                    MyGame.ss.push(new GameScreen( MyGame.ss,3));
                }else if(field.text.get().equalsIgnoreCase("9B3Z")){
                    MyGame.ss.push(new GameScreen( MyGame.ss,4));
                }else if(field.text.get().equalsIgnoreCase("8765")){
                    MyGame.ss.push(new GameScreen( MyGame.ss,5));
                }else if(field.text.get().equalsIgnoreCase("FGHJ")){
                    MyGame.ss.push(new GameScreen( MyGame.ss,6));
                }else if(field.text.get().equalsIgnoreCase("W25G")){
                    MyGame.ss.push(new GameScreen( MyGame.ss,7));
                }
            }
        });
        bb.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top()); //pop
            }
        });

        root = iface.createRoot(AxisLayout.vertical().gap(15), SimpleStyles.newSheet(), layer);
        //root.addStyles(Style.BACKGROUND.is(Background.bordered(0xFFCCCCCC, 0xFF99CCFF, 5).inset(5, 10)));
        root.setSize(width(), height());
        root.layer.add(bg);
        root.layer.add(n);
        root.layer.add(sub);
        root.layer.add(bb);
        //root.add(new Label("Choose Stage or type stage key.").addStyles(Style.FONT.is(Home.TITLE_FONT)));
        //----------------------------------------------------------------------
        /*root.add(stage_1.onClick(new UnitSlot() {
            public void onEmit() {
                //ss.push(new GameScreen(ss,1));
                MyGame.ss.push(new GameScreen( MyGame.ss,1));
            }
        }));*/
        /*root.add(stage_2.onClick(new UnitSlot() {
            public void onEmit() {
                //ss.push(new GameScreen(ss,2));
                MyGame.ss.push(new GameScreen(MyGame.ss,2));
            }
        }));*/
        field = new Field("Stage Key");
        field.maxLength(4);
        field.setPopupLabel("Insert Stage Key");
        root.add(field);
        /*root.add(new Button("Submit").onClick(new UnitSlot() {
            public void onEmit() {
                System.out.println(field.text.get());
                //ss.push(new GameScreen(ss,1));
                if(field.text.get().equalsIgnoreCase("1234")){
                    System.out.println("if");
                    MyGame.ss.push(new GameScreen( MyGame.ss,1));
                }else if(field.text.get().equalsIgnoreCase("5678")){
                    System.out.println("else");
                    MyGame.ss.push(new GameScreen( MyGame.ss,2));
                }

            }
        }));*/
    }
    public void wasShown (){
        super.wasShown();
    }
    @Override
    public void update(int delta){
        super.update(delta);
        /*loading.update(delta);
        if(GameScreen.hasLoaded){
            wait = false;
        }
        genPage();*/
    }
    /*public void genPage(){
        if(!wait && num!=0){
            MyGame.ss.push(new GameScreen( MyGame.ss,num));
        }
    }*/

}