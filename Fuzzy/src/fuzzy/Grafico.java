package fuzzy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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

public class Grafico extends JFrame {

    public Grafico(Variavel v) {

        initUI(v);
    }

    private void initUI(Variavel v) {

        XYDataset dataset = createDataset(v);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Gráfico");
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDataset(Variavel v) {
    	
    	Conjunto conj1 = v.getConjuntos()[0];
    	Conjunto conj2 = v.getConjuntos()[1];
    	Conjunto conj3 = v.getConjuntos()[2];
    	
        XYSeries series1 = new XYSeries(v.getConjuntos()[0].getNome());
        series1.add(conj1.getSuporteMin(), 0);
        series1.add(conj1.getNucleoMin(), 1);
        series1.add(conj1.getNucleoMax(), 1);
        series1.add(conj1.getSuporteMax(), 0);
        
        XYSeries series2 = new XYSeries(v.getConjuntos()[1].getNome());
        series2.add(conj2.getSuporteMin(), 0);
        series2.add(conj2.getNucleoMin(), 1);
        series2.add(conj2.getNucleoMax(), 1);
        series2.add(conj2.getSuporteMax(), 0);
        
        XYSeries series3 = new XYSeries(v.getConjuntos()[2].getNome());
        series3.add(conj3.getSuporteMin(), 0);
        series3.add(conj3.getNucleoMin(), 1);
        series3.add(conj3.getNucleoMax(), 1);
        series3.add(conj3.getSuporteMax(), 0);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Gráfico de pertinências", 
                "Entrada", 
                "Pertinência", 
                dataset, 
                PlotOrientation.VERTICAL,
                true, 
                true, 
                false 
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        
        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Gráfico de pertinências",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;

    }

//    public static void main(String[] args) {
//
//        SwingUtilities.invokeLater(() -> {
//            Grafico ex = new Grafico();
//            ex.setVisible(true);
//        });
//    }
}