package com.prosper.logpic;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

public class LineChart extends ApplicationFrame {

	private static final long serialVersionUID = 1L;

	public LineChart(String title) {
		super(title);
		setContentPane(createDemoLine());
	}

	public static void main(String[] args) {
		LineChart LineChart= new LineChart("曲线图");
		LineChart.pack();
		RefineryUtilities.centerFrameOnScreen(LineChart);
		LineChart.setVisible(true);
	}

	// 生成显示图表的面板
	public static JPanel createDemoLine() {
		JFreeChart jfreechart = createChart(createDataset());
		return new ChartPanel(jfreechart);
	}

	public static JFreeChart createChart(XYDataset linedataset) {
		JFreeChart chart = ChartFactory.createXYLineChart("time-value", "X", "Y", linedataset, PlotOrientation.VERTICAL, true, true, false);

		// 设置消除字体的锯齿渲染(解决中文问题)
//		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
//				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

		// 设置主标题
		chart.setTitle(new TextTitle("time-value", new Font("Arial", Font.ITALIC, 20)));
		chart.setAntiAlias(true);

		XYPlot plot = (XYPlot) chart.getPlot();
		XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) plot.getRenderer();

		// 设置网格背景颜色
		plot.setBackgroundPaint(Color.white);
		// 设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.pink);
		// 设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);
		// 设置曲线与xy轴的距离
		plot.setAxisOffset(new RectangleInsets(0D, 0D, 0D, 10D));
		// 设置曲线是否显示数据点
//		xylineandshaperenderer.setBaseShapesVisible(true);
		// 设置曲线显示各项数据点的值
		XYItemRenderer xyitem = plot.getRenderer();
//		xyitem.setBaseItemLabelsVisible(true);
		xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 14));
		plot.setRenderer(xyitem);

		NumberAxis axis = (NumberAxis) plot.getDomainAxis();

		ValueAxis valueAxis = plot.getDomainAxis();
		// 设置x轴上面的字体
		valueAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 12));
		// 设置X轴的标题文字
		valueAxis.setLabelFont(new Font("Arial", Font.PLAIN, 12));
		
		NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
		// 设置y轴上的字体
		numberAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 13));
		// 设置y轴上的标题字体
		numberAxis.setLabelFont(new Font("Arial", Font.PLAIN, 13));
		// 设置底部的字体
		chart.getLegend().setItemFont(new Font("Arial", Font.PLAIN, 12));

		return chart;
	}

	// 生成数据
	public static XYDataset createDataset() {
		// 访问量统计时间线
		XYSeries XYSeries1 = new XYSeries("acce-x");
		XYSeries XYSeries2 = new XYSeries("acce-y");
		XYSeries XYSeries3 = new XYSeries("acce-z");
//		XYSeries XYSeries4 = new XYSeries("acce-t");	

		try {
//			File file = new File("D:\\workspace\\others\\doc\\fake\\gyroscope.log");
			File file = new File("D:\\workspace\\others\\doc\\fake\\accelerometer.log");
			BufferedReader acceReader = new BufferedReader(new FileReader(file));
			
			String line = "";
			while ((line = acceReader.readLine()) != null) {
				String[] values = line.split("\t");
				XYSeries1.add(Long.parseLong(values[0]), Float.parseFloat(values[1]));
				XYSeries2.add(Long.parseLong(values[0]), Float.parseFloat(values[2]));
				XYSeries3.add(Long.parseLong(values[0]), Float.parseFloat(values[3]));
//				XYSeries4.add(Long.parseLong(values[0]), Math.sqrt(Float.parseFloat(values[4])));
			}
			acceReader.close();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}

		XYSeriesCollection lineDataset = new XYSeriesCollection();
		lineDataset.addSeries(XYSeries1);
		lineDataset.addSeries(XYSeries2);
		lineDataset.addSeries(XYSeries3);
//		lineDataset.addSeries(XYSeries4);

		return lineDataset;
	}

}
