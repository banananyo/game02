package sut.game02.core;

import static playn.core.PlayN.*;


import playn.core.*;
import playn.core.Mouse.*;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.game.*;
import tripleplay.ui.Root;
import tripleplay.ui.*;
import tripleplay.ui.layout.*;
import react.UnitSlot;

public class Home extends UIScreen {
  public static final Font TITLE_FONT = graphics().createFont("Helvetica", Font.Style.PLAIN, 24);

  private final ScreenStack ss;
  private Root root;
  private final Button start = new Button("Play");
  private final Button setting = new Button("setting");

  public Home(final ScreenStack ss){
      this.ss = ss;
      root = iface.createRoot(AxisLayout.vertical().gap(15), SimpleStyles.newSheet(), layer);
      root.addStyles(Style.BACKGROUND.is(Background.bordered(0xFFCCCCCC, 0xFF99CCFF, 5).inset(5, 10)));
      root.setSize(width(), height());
      root.add(new Label("RUNNING SAMURAI!!!").addStyles(Style.FONT.is(Home.TITLE_FONT)));
      //----------------------------------------------------------------------
      root.add(start.onClick(new UnitSlot() {
        public void onEmit() {
          ss.push(new GameScreen(ss));
        }
      }));
      root.add(setting.onClick(new UnitSlot() {
        public void onEmit() {
          ss.push(new SettingScreen(ss));
        }
      }));
  }

  public void wasShown (){
    super.wasShown();
      //control = new PageControl(ss);
  }
}