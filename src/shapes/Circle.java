package shapes;

import org.eclipse.swt.graphics.GC;

public class Circle implements Shape {

    private int top;
    private int left;
    private int width;
    private int height;
    private GC gcMain;

    private static final String toolText = "圆形";
    public static String getToolText(){
        return toolText;
    }

    public Circle(){

    }

    public Circle(int top, int left, int width, int height, GC gcMain) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
        this.gcMain = gcMain;
    }

    @Override
    public void draw() {
        gcMain.drawOval(top,left,width,height);
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