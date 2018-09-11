package Wires;

import Wires.Cell.State;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class DrawBoardFX {
    private int size;
    private int scale;
    private Board board;
    private Canvas drawingCanvas;
    private AnchorPane drawingPane;

    public DrawBoardFX(int scale, Board board, Canvas drawingCanvas, AnchorPane drawingPane) {
        this.scale = scale;
        this.board = board;
        this.drawingCanvas = drawingCanvas;
        this.drawingPane = drawingPane;
        size = board.getX_size();
    }

    private void drawNet() {
        GraphicsContext gc = drawingCanvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.setLineCap(StrokeLineCap.SQUARE);
        for (int i = 0; i <= size; i++) {
            gc.strokeLine(snap(i * scale), 0, snap(i * scale), scale * size);
            gc.strokeLine(0, snap(i * scale), scale * size, snap(i * scale));
        }
    }

    public void draw() throws Exception {
        calculateMaxScale();
        GraphicsContext gc = drawingCanvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                State state = board.getBoardCellState(i,j);
                if (state.equals(State.CONDUCTOR)) gc.setFill(Color.YELLOW);
                else if (state.equals(State.ELEHEAD)) gc.setFill(Color.RED);
                else if (state.equals(State.ELETAIL)) gc.setFill(Color.BLUE);
                else gc.setFill(Color.LIGHTGREY);
                gc.fillRect(i * scale, j * scale, scale, scale);
            }
        }
        drawNet();
    }

    void clearBoard() {
        GraphicsContext gc = drawingCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
    }

    private void calculateMaxScale() throws Exception {
        drawingCanvas.setHeight(drawingPane.getHeight());
        drawingCanvas.setWidth(drawingPane.getWidth());
        double width = drawingPane.getWidth();
        double height = drawingPane.getHeight();
        if (height < width) {
            scale = (int) height / (size);
        } else {
            scale = (int) width / (size);
        }
        System.out.println("scale:" + scale + " size:" + size + " height:" + height + " width:" + width);
        if (scale < 1) throw new Exception("Window too small to draw");
    }


    private double snap(double y) {
        return ((int) y) + .5;
    }
}