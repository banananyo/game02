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

public class SettingScreen extends Screen {
  private final ScreenStack ss;
  private final Image bbImage;
  private final ImageLayer bb;
    //private  PageControl control;
  public SettingScreen(final ScreenStack ss){
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
  }
  public void wasShown (){
    super.wasShown();
    //control = new PageControl();
  }

}