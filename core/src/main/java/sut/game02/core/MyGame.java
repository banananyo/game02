package sut.game02.core;

import static playn.core.PlayN.*;

import playn.core.*;
import playn.core.util.Clock;
import playn.core.util.*;
import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import tripleplay.game.ScreenStack;

public class MyGame extends Game.Default {
  //private final ScreenStack ss = new ScreenStack();
  public static ScreenStack ss = new ScreenStack();
  public static final int UPDATE_RATE = 33;
  protected final Clock.Source clock = new Clock.Source(UPDATE_RATE);
  public MyGame() {
    super(UPDATE_RATE); // call update every 33ms (30 times per second)
  }

  @Override
  public void init() {

      ss.push(new Home(ss));

  }

  @Override
  public void update(int delta) {

      ss.update(delta);
  }

  @Override
  public void paint(float alpha) {

      ss.paint(clock);
  }
}
