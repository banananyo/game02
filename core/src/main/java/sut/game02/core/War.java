package sut.game02.core;


import playn.core.GroupLayer;
import playn.core.Layer;
import tripleplay.game.Screen;
import tripleplay.util.Colors;

public class War extends Screen {
    public static ToolsG toolsG;
    public static Layer hpTextLayer;
    public GroupLayer screenLayer;
    private static Layer gameMsg;

    public War(final GroupLayer screenLayer){
        this.screenLayer = screenLayer;

        toolsG = new ToolsG();
        hpTextLayer = toolsG.genText(Status.debugString,20,Colors.WHITE,50,50);
        layer.add(hpTextLayer);
    }
    public void playerAttack(){

    }
    public void enemyAttack(){

    }
    public void playerHit(){
        screenLayer.remove(hpTextLayer);
        hpTextLayer = toolsG.genText(Status.debugString,20, Colors.WHITE,50,50);
        screenLayer.add(hpTextLayer);
    }
    public void enemyHit(){

    }
    public void gameOver(){
        Status.hp = 0;
        Status.gameMsg = "Game Over";
        gameMsg = toolsG.genText(Status.gameMsg,100,Colors.RED,100,200);
        layer.add(gameMsg);
    }
}
