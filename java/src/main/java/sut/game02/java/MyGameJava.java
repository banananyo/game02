package sut.game02.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;
import sut.game02.core.MyGame;

public class MyGameJava {

  public static void main(String[] args) {
    /*JavaPlatform.Config config = new JavaPlatform.Config();
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new MyGame());*/

    JavaPlatform.Config config = new JavaPlatform.Config();
      config.width = 800;
      config.height = 400;
    JavaPlatform platform = JavaPlatform.register(config);
    //platform.assets().setPathPrefix("your/package/resources");
    PlayN.run(new MyGame());
  }
}
