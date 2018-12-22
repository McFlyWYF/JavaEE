import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;

import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import shapes.Shape;

public class Tt {
    private static GC gcMain = null;
    private static int startX;
    private static int startY;
    private static boolean leftButtonDown = false;
    private static int lastWidth;
    private static int lastHeight;

    private static String shapeType = "Rect";
    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
        List listClass = null;
        String pkg = "shapes";
        listClass = ClassUtil.getClassList(pkg, true, null);

        ArrayList<String> shapeTypes = new ArrayList<String>();
        for (Object object : listClass) {
            String name = ((Class<?>)object).getName();
            if(!name.equals("shapes.Shape")){
                shapeTypes.add(name);
            }
        }

        Display display = Display.getDefault();
        Shell shell = new Shell();

        gcMain = new GC(shell);
        Board board = new Board(shell, gcMain);

        shell.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent arg0) {
                board.Refresh();
            }
        });

        // Init vars
        shell.addMouseMoveListener(new MouseMoveListener() {
            public void mouseMove(MouseEvent arg0) {
                if(leftButtonDown){
                    gcMain.setLineStyle(SWT.LINE_DOT);
                    gcMain.setForeground(shell.getBackground());
                    gcMain.drawRectangle(startX, startY, lastWidth, lastHeight);
                    gcMain.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
                    gcMain.drawRectangle(startX, startY, arg0.x - startX, arg0.y - startY);
                    lastWidth = arg0.x - startX;
                    lastHeight = arg0.y - startY;
                    gcMain.setLineStyle(SWT.LINE_SOLID);
                }
            }
        });
        shell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseUp(MouseEvent e) {
                if(e.button == 1){
                    leftButtonDown = false;
                    int width = e.x - startX;
                    int height = e.y - startY;

                    ////////////////////////////////////////////////////////////////
                    // erase range rect
                    gcMain.setLineStyle(SWT.LINE_DOT);
                    gcMain.setForeground(shell.getBackground());
                    gcMain.drawRectangle(startX, startY, width, height);
                    gcMain.setLineStyle(SWT.LINE_SOLID);
                    ////////////////////////////////////////////////////////////////

                    gcMain.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
                    Shape shape;
                    try{
                        Class<?> shapeClass = Class.forName(shapeType);
                        Object oShape = shapeClass.newInstance();
                        shape = (Shape)oShape;
                        shape.setTop(startX);
                        shape.setLeft(startY);
                        shape.setWidth(e.x - startX);
                        shape.setHeight(e.y - startY);
                        shape.setGcMain(gcMain);
                    }
                    catch (Exception ex) {
                        shape = null;
                    }

                    if(shape != null){
                        board.InsertShape(shape);
                        board.Refresh();
                    }
                    shell.setCursor(new Cursor(null, 0));
                }
            }
            @Override
            public void mouseDown(MouseEvent e) {
                if(e.button == 1){
                    shell.setCursor(new Cursor(null, SWT.CURSOR_CROSS));
                    leftButtonDown = true;
                    startX = e.x;
                    startY = e.y;
                }

            }
        });
        shell.setSize(800, 600);
        shell.setText("MyDraw");
        shell.setLayout(null);

        Button btnOpen = new Button(shell, SWT.NONE);
        btnOpen.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try{
                    board.Open("F:\\JavaEE\\shape");
                }
                catch(Exception ex){
                    return;
                }
            }
        });
        btnOpen.setBounds(10, 525, 80, 27);
        btnOpen.setText("Open");

        Button btnSave = new Button(shell, SWT.NONE);
        btnSave.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try{
                    board.Save("F:\\JavaEE\\shape");
                }
                catch(Exception ex){
                    System.out.println(ex.getMessage());
                    return;
                }

            }
        });
        btnSave.setBounds(107, 525, 80, 27);
        btnSave.setText("Save");

        ////////////////////////////////////////////////////////
        // add button by shapeTypes
        int indexButton = 0;
        for (String strClass: shapeTypes) {
            Button btn = new Button(shell, SWT.NONE);
            btn.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    shapeType = strClass;
                }
            });
            btn.setBounds(84 * indexButton, 0, 80, 27);
            indexButton++;
            try {
                Class<?> shapeClass = Class.forName(strClass);
                Method method = shapeClass.getMethod("getToolText");
                btn.setText(method.invoke(null, null).toString());
                btn.setData("shapeType", strClass);
            } catch (Exception e) {
                btn.setText(strClass);
                btn.setData("shapeType", strClass);
            }


        }

        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }


}