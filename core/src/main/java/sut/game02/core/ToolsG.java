package sut.game02.core;

        import playn.core.*;

        import tripleplay.game.UIScreen;
        import tripleplay.util.Colors;
        import javax.tools.Tool;
        import static playn.core.PlayN.assets;
        import static playn.core.PlayN.graphics;

/**
 * Created by GTX on 13/5/2559.
 */
public class ToolsG extends UIScreen {
    public Integer time=0;

    public Layer genText(String text, int fontSize, Integer fontColor, float x, float y) {
        Font font = graphics().createFont("Tahoma", Font.Style.PLAIN, fontSize);
        TextLayout layout = graphics().layoutText(
                text, new TextFormat().withFont(font).withWrapWidth(200));
        Layer textLayer = createTextLayer(layout, fontColor,x,y);
        return textLayer;
    }

    private Layer createTextLayer(TextLayout layout, Integer fontColor,float x,float y) {
        CanvasImage image = graphics().createImage(
                (int) (GameScreen.widthPX),
                (int) (GameScreen.heightPX));
        if (fontColor != null) image.canvas().setFillColor(fontColor);
        image.canvas().fillText(layout,x,y);
        //image.canvas().setStrokeColor(fontColor);
        //image.canvas().setAlpha(100);
        //image.canvas().strokeText(layout,10,10);

        return graphics().createImageLayer(image);
    }

    public static float fadeIn(float alphaTest) {
        if(alphaTest < 1f)
            return alphaTest + (float)0.05;
        else
            return 1f;
    }

    public static float fadeOut(float alphaTest) {
        if(alphaTest > 0)
            return alphaTest - (float)0.05;
        else
            return 0;
    }



}