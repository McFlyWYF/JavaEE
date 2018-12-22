package shapes;

import org.eclipse.swt.graphics.GC;

public class Line implements Shape {

    private int top;
    private int left;
    private int width;
    private int height;
    private GC gcMain;

    private static final String toolText = "直线";
    public static String getToolText(){
        return toolText;
    }

    public Line(){

    }

    public Line(int top, int left, int width, int height, GC gcMain) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
        this.gcMain = gcMain;
    }

    @Override
    public void draw() {
        gcMain.drawLine(top,left,width,height);
    }

    @Override
    public int getTop() {
        return top;
    }

    @Override
    public void setTop(int top) {
        this.top = top;
    }

    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public void setLeft(int left) {
        this.left = left;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public GC getGcMain() {
        return gcMain;
    }

    @Override
    public void setGcMain(GC gcMain) {
        this.gcMain = gcMain;
    }
}
