import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.geometry.*;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

	//THIS PROGRAM MADE FOR STUDY ABOUT Cohen Sutherland Algorithm
	//CPE361 Computer Graphics @KMUTT
	//GITHUB: github.com/s4nthiti

public class Viewing2D extends Application
{
    public static final int INSIDE = 0;
    public static final int LEFT   = 1;
    public static final int RIGHT  = 2;
    public static final int BOTTOM = 4;
    public static final int TOP    = 8;

	private double xMax = 0;
	private double yMax = 0;
	private double xMin = 0;
	private double yMin = 0;
	private ArrayList<Double> intersectionPos = new ArrayList();

	@Override
	public void start(Stage primaryStage)
	{
		try{
			primaryStage.setTitle("2D Viewing Port");
			BorderPane border = new BorderPane();
			GridPane grid = new GridPane();
			StackPane layout1 = new StackPane();
			border.setCenter(layout1);
			border.setBottom(grid);

			Label resX = new Label("xMax");
			Label resY = new Label("yMax");
			TextField tresX = new TextField();
	        TextField tresY = new TextField();
	        tresX.setText("20");
	        tresY.setText("20");

			Label x1 = new Label("x1");
	        Label y1 = new Label("y1");
	        TextField tx1 = new TextField();  
	        TextField ty1 = new TextField();
	        Label x2 = new Label("x2");
	        Label y2 = new Label("y2");
	        TextField tx2 = new TextField();  
	        TextField ty2 = new TextField();
	        Label category = new Label("category");
	        Label result = new Label("result");
	        TextField tcategory = new TextField();
	        TextField tresult = new TextField();
	        tcategory.setDisable(true);
	        tresult.setDisable(true);
	        Button submitbtn = new Button ("Submit");
	        Button clearbtn = new Button ("Clear");
			LineChart<Number, Number> graphPlot = fgGraph(-30, 30, 5, -30, 30, 5);
			Scene scene = new Scene(border, 800, 800);

        	submitbtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {

	            	xMax = Double.valueOf(tresX.getText());
	                yMax = Double.valueOf(tresY.getText());
	            	String category = CategoryChecker(Double.valueOf(tx1.getText()), Double.valueOf(ty1.getText()), Double.valueOf(tx2.getText()), Double.valueOf(ty2.getText()));
	                //defining a series
	                XYChart.Series fseries = new XYChart.Series();
	                XYChart.Series gseries = new XYChart.Series();
	                XYChart.Series hseries = new XYChart.Series();
	                XYChart.Series jseries = new XYChart.Series();
	                XYChart.Series kseries = new XYChart.Series();
	                //populating the series with data
	                fseries.getData().add(new XYChart.Data(Double.valueOf(tx1.getText()), Double.valueOf(ty1.getText())));
	                fseries.getData().add(new XYChart.Data(Double.valueOf(tx2.getText()), Double.valueOf(ty2.getText())));

	                gseries.getData().add(new XYChart.Data(0,0));
	                gseries.getData().add(new XYChart.Data(0, Double.valueOf(tresY.getText())));

	                hseries.getData().add(new XYChart.Data(0, Double.valueOf(tresY.getText())));
	                hseries.getData().add(new XYChart.Data(Double.valueOf(tresX.getText()), Double.valueOf(tresY.getText())));

	                jseries.getData().add(new XYChart.Data(Double.valueOf(tresX.getText()), Double.valueOf(tresY.getText())));
	                jseries.getData().add(new XYChart.Data(Double.valueOf(tresX.getText()), 0));

	                kseries.getData().add(new XYChart.Data(Double.valueOf(tresX.getText()), 0));
	                kseries.getData().add(new XYChart.Data(0, 0));

	                tcategory.setText(category);
	                graphPlot.setCreateSymbols(true);
					graphPlot.setAlternativeRowFillVisible(false);
					graphPlot.setAnimated(false);
					graphPlot.setLegendVisible(false);
	                if(intersectionPos.size() == 2)
	                {
	                	XYChart.Series lseries = new XYChart.Series();
	                	lseries.getData().add(new XYChart.Data(intersectionPos.get(0), intersectionPos.get(1)));
	                	tresult.setText("( " + intersectionPos.get(0) + ", " + intersectionPos.get(1) + ")");
	                	graphPlot.getData().addAll(fseries, gseries, hseries, jseries, kseries, lseries);
	                }
	                else if(intersectionPos.size() == 4)
	                {
	                	XYChart.Series lseries = new XYChart.Series();
	                	XYChart.Series mseries = new XYChart.Series();
	                	lseries.getData().add(new XYChart.Data(intersectionPos.get(0), intersectionPos.get(1)));
	                	mseries.getData().add(new XYChart.Data(intersectionPos.get(2), intersectionPos.get(3)));
	                	tresult.setText("( " + intersectionPos.get(0) + ", " + intersectionPos.get(1) + ") " + "( " + intersectionPos.get(2) + ", " + intersectionPos.get(3) + ")");
	                	graphPlot.getData().addAll(fseries, gseries, hseries, jseries, kseries, lseries, mseries);
	                }
	                else
	                	graphPlot.getData().addAll(fseries, gseries, hseries, jseries, kseries);
	                tx1.setDisable(true);
	                tx2.setDisable(true);
	                ty1.setDisable(true);
	                ty2.setDisable(true);
	                submitbtn.setDisable(true);
	            }
        	});

			EventHandler<ActionEvent> eventClear = new EventHandler<ActionEvent>() { 
            	public void handle(ActionEvent e) 
            	{ 
                	tx1.setText("");
					ty1.setText("");
					tx2.setText("");
					ty2.setText("");
					tcategory.setText("");
					tresult.setText("");
					graphPlot.getData().clear();
					tx1.setDisable(false);
	                tx2.setDisable(false);
	                ty1.setDisable(false);
	                ty2.setDisable(false);
	                intersectionPos.clear();
					submitbtn.setDisable(false);
            	} 
        	}; 
        	clearbtn.setOnAction(eventClear);

			layout1.getChildren().addAll(graphPlot);
			grid.setVgap(10);
			grid.setHgap(10);
			grid.setAlignment(Pos.CENTER);

	        grid.add(x1, 0, 0);
	        grid.add(tx1, 1, 0);
	        grid.add(y1, 2, 0);
	        grid.add(ty1, 3, 0);
	        grid.add(category, 4, 0);
	        grid.add(tcategory, 5, 0);
	        grid.add(resX, 6, 0);
	        grid.add(tresX, 7, 0);

	        grid.add(x2, 0, 1);
	        grid.add(tx2, 1, 1);
	        grid.add(y2, 2, 1);
	        grid.add(ty2, 3, 1);
	        grid.add(result, 4, 1);
	        grid.add(tresult, 5, 1);
	        grid.add(resY, 6, 1);
	        grid.add(tresY, 7, 1);

	        grid.add(submitbtn, 1, 2);
	        grid.add(clearbtn, 3, 2);

			scene.getStylesheets().add("style.css");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	private LineChart<Number, Number> fgGraph(double xFrom, double xTo, double xStep,double yFrom, double yTo, double yStep)
	{
		NumberAxis xAxis = new NumberAxis(xFrom, xTo, xStep);
		NumberAxis yAxis = new NumberAxis(yFrom, yTo, yStep);
		LineChart<Number, Number> graphChart = new LineChart<>(xAxis, yAxis);
		graphChart.setCreateSymbols(false);
		return graphChart;
	}

	private int computeOutCode(double x, double y) {
        int code = INSIDE;

        if (x < xMin) {
            code |= LEFT;
        } else if (x > xMax) {
            code |= RIGHT;
        }
        if (y < yMin) {
            code |= BOTTOM;
        } else if (y > yMax) {
            code |= TOP;
        }
        return code;
    }

	private String CategoryChecker(double x1, double y1, double x2, double y2)
	{
		if(x1 > 0 && y1 > 0 && x1 < xMax && y1 < yMax)
		{
			if(x2 > 0 && y2 > 0 && x2 < xMax && y2 < yMax)
				return "Visible";
		}
		int outCode0 = computeOutCode(x1, y1);
        int outCode1 = computeOutCode(x2, y2);
		String binaryXY1 = "";
		String binaryXY2 = "";
		String binaryOrResult = "";
		String binaryAndResult = "";
		Integer sumOr = 0;
		Integer sumAnd = 0;
		boolean accept = false;
		while(true){
			if ((outCode0 | outCode1) == 0) {
				accept = true;
				break;
			} else if ((outCode0 & outCode1) != 0) {
				break;
			} else {
				double x,y;

				// Pick at least one point outside rectangle
				int outCodeOut = (outCode0 != 0) ? outCode0 : outCode1;
				// Now find the intersection point;
				// use formulas y = y0 + slope * (x - x0), x = x0 + (1 / slope) * (y - y0)
				if ((outCodeOut & TOP) != 0) {
					x = x1 + (x2 - x1) * (yMax - y1) / (y2 - y1);
					y = yMax;
				} else if ((outCodeOut & BOTTOM) != 0) {
					x = x1 + (x2 - x1) * (yMin - y1) / (y2 - y1);
 					y = yMin;
				} else if ((outCodeOut & RIGHT) != 0) {
					y = y1 + (y2 - y1) * (xMax - x1) / (x2 - x1);
					x = xMax;
				} else {
					y = y1 + (y2 - y1) * (xMin - x1) / (x2 - x1);
					x = xMin;
				}

				// Now we move outside point to intersection point to clip
				if (outCodeOut == outCode0) {
					x1 = x;
					y1 = y;
					intersectionPos.add(x);
					intersectionPos.add(y);
					outCode0 = computeOutCode(x1, y1);
				} else {
					x2 = x;
					y2 = y;
					intersectionPos.add(x);
					intersectionPos.add(y);
					outCode1 = computeOutCode(x2, y2);
 				}
			}
		}
		if(accept)
			return "Clipping Candidate";
		return "Invisible";
	}

	public static void main(String[] args) {
		launch(args);
	}
}