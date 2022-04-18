package methods;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import org.jfree.chart.annotations.XYLineAnnotation;

public class DrawingStuff extends JFrame {

    public DrawingStuff(ArrayList<Point> points, ArrayList<Point> points2, String title) {

        initUI(points, points2, title);
    }

    private void initUI(ArrayList<Point> points, ArrayList<Point> points2, String title) {

        XYDataset dataset = createDataset(points);
        JFreeChart chart = createChart(dataset, points2, title);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        chartPanel.setMouseZoomable(true, true);
        add(chartPanel);

        pack();
        setTitle("Graph");
        setLocation( 900 ,100);
    }

    private XYDataset createDataset(ArrayList<Point> points) {

        XYSeries series = new XYSeries("f(x)");
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).y != -6000 && points.get(i).y != 6000) {
                series.add(points.get(i).x, points.get(i).y);
            }
        }

        return dataset;
    }

    private JFreeChart createChart(final XYDataset dataset, ArrayList<Point> points2, String title) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "",
                "",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.BLACK);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        renderer.setSeriesShapesFilled(0, false);
        renderer.setDrawOutlines(false);

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);
        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);
        
        if(points2.size()==1){
            XYLineAnnotation line = new XYLineAnnotation(-10000000, -10000000, 10000000, 10000000, new BasicStroke(2.0f), Color.RED);
            plot.addAnnotation(line);
        }else{
            
        for (int i = 0; i < points2.size(); i++) {
            if(i==points2.size()-1){
            XYLineAnnotation line = new XYLineAnnotation(points2.get(i).x, -10000000, points2.get(i).x, 10000000, new BasicStroke(2.0f), Color.YELLOW);
            plot.addAnnotation(line);
                
            }else{
            XYLineAnnotation line = new XYLineAnnotation(points2.get(i).x, -10000000, points2.get(i).x, 10000000, new BasicStroke(2.0f), (i % 2 == 0) ? Color.RED : Color.BLUE);
            plot.addAnnotation(line);   
            }
        }
        }

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle(title,
                new Font("Serif", Font.BOLD, 18)
        )
        );

        return chart;
    }

}
