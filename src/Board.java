import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import shapes.Shape;


public class Board {
    private Shell shell;
    private List<Shape> shapes;
    private GC _gc;

    public Board(Shell shell, GC gc){
        shapes = new ArrayList<Shape>();
        this.shell = shell;
        _gc = gc;
    }

    public void InsertShape(Shape shape){
        shapes.add(shape);
    }

    public void Refresh(){
        Point pt = shell.getSize();
        _gc.fillRectangle(0, 0, pt.x, pt.y);;
        for (Shape shape : shapes) {
            shape.draw();
        }
    }

    public void Save(String filename) throws IOException{
//			DataOutputStream out=new DataOutputStream(
//			                     new BufferedOutputStream(
//			                     new FileOutputStream(filename)));
        PrintStream out = new PrintStream(new File(filename));
        out.println(shapes.size());

        for(Shape shape: shapes){
            writeShape(out, shape);
        }

        out.close();
    }

    public void Open(String filename) throws IOException{
        shapes.clear();

        String line;
        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(filename)));
        line = reader.readLine();
        int shapeCount = Integer.parseInt(line);

        for(int i=0;i<shapeCount;i++){
            Shape shape = readShape(reader);
            InsertShape(shape);
        }
        reader.close();

        Refresh();
    }
    private  void writeShape(
            PrintStream out, Shape shape) throws IOException{
        out.println(shape.getClass().getName());

        out.println(shape.getLeft());
        out.println(shape.getTop());
        out.println(shape.getWidth());
        out.println(shape.getHeight());
    }

    private  Shape readShape(BufferedReader in) throws IOException{
        String className = in.readLine();
        String left = in.readLine();
        String top = in.readLine();
        String width = in.readLine();
        String height = in.readLine();

        int l = Integer.parseInt(left);
        int t = Integer.parseInt(top);
        int w = Integer.parseInt(width);
        int h = Integer.parseInt(height);

        Shape shape = null;
        try{
            Class<?> shapeClass = Class.forName(className);
            Object oShape = shapeClass.newInstance();
            shape = (Shape)oShape;
            shape.setTop(t);
            shape.setLeft(l);
            shape.setWidth(w);
            shape.setHeight(h);
            shape.setGcMain(_gc);
        }
        catch(Exception e){

        }
        return shape;
    }
}
