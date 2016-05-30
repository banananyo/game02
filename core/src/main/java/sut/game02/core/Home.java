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

import java.util.Set;

public class Home extends UIScreen {
  public static final Font TITLE_FONT = graphics().createFont("Helvetica", Font.Style.PLAIN, 24);

  private final ScreenStack ss;
  private Root root;
  private final Button start = new Button("Play");
  private final Button setting = new Button("About");

    Image bgImage;
    ImageLayer bg;

    Image playImage;
    ImageLayer play;

    Image howImage;
    ImageLayer how;

    Image aboutImage;
    ImageLayer about;

  public Home(final ScreenStack ss){
      this.ss = ss;

      bgImage = assets().getImage("images/home.png");
      bg = graphics().createImageLayer(bgImage);
      bg.setTranslation(0, 0);

      playImage = assets().getImage("images/button/play.png");
      play = graphics().createImageLayer(playImage);
      play.setTranslation(350, 200);

      howImage = assets().getImage("images/button/how.png");
      how = graphics().createImageLayer(howImage);
      how.setTranslation(350, 250);

      aboutImage = assets().getImage("images/button/about.png");
      about = graphics().createImageLayer(aboutImage);
      about.setTranslation(350, 300);

      root = iface.createRoot(AxisLayout.vertical().gap(15), SimpleStyles.newSheet(), layer);
      root.addStyles(Style.BACKGROUND.is(Background.bordered(0xFFCCCCCC, 0xFF99CCFF, 5).inset(5, 10)));
      root.setSize(width(), height());

      root.layer.add(bg);
      root.layer.add(play);
      root.layer.add(how);
      root.layer.add(about);

      play.addListener(new Mouse.LayerAdapter() {
          @Override
          public void onMouseUp(Mouse.ButtonEvent event) {
              MyGame.ss.push(new ChooseScreen(MyGame.ss));
          }});
      how.addListener(new Mouse.LayerAdapter() {
          @Override
          public void onMouseUp(Mouse.ButtonEvent event) {
              MyGame.ss.push(new How(MyGame.ss));
          }});
      about.addListener(new Mouse.LayerAdapter() {
          @Override
          public void onMouseUp(Mouse.ButtonEvent event) {
              MyGame.ss.push(new About(MyGame.ss));
          }});
      /*root.add(new Label("RUNNING SAMURAI!!!").addStyles(Style.FONT.is(Home.TITLE_FONT)));
      root.add(start.onClick(new UnitSlot() {
        public void onEmit() {
            //Status.gameControl = "play";
          //ss.push(new ChooseScreen(ss));
            MyGame.ss.push(new ChooseScreen(MyGame.ss));
        }
      }));
      root.add(setting.onClick(new UnitSlot() {
        public void onEmit() {
          //ss.push(new SettingScreen(ss));
            MyGame.ss.push(new SettingScreen(MyGame.ss));
        }
      }));*/
  }

  public void wasShown (){
    super.wasShown();

  }
}