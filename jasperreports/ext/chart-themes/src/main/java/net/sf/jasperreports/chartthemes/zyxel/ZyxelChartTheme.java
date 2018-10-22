package net.sf.jasperreports.chartthemes.zyxel;

import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RectangleEdge;

import net.sf.jasperreports.charts.ChartContext;
import net.sf.jasperreports.charts.JRBarPlot;
import net.sf.jasperreports.charts.JRItemLabel;
import net.sf.jasperreports.charts.fill.JRFillTimeSeriesDataset;
import net.sf.jasperreports.charts.type.EdgeEnum;
import net.sf.jasperreports.charts.type.PlotOrientationEnum;
import net.sf.jasperreports.charts.util.ChartUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.fill.DefaultChartTheme;
import net.sf.jasperreports.engine.fonts.FontUtil;
import net.sf.jasperreports.engine.type.ModeEnum;

/**
 * @author Daikuei Liu (daikuei@zyxel.com.tw)
 */
public class ZyxelChartTheme extends DefaultChartTheme {

	@Override
	protected JFreeChart createBarChart() throws JRException {

		JFreeChart barChart = super.createBarChart();

		CategoryPlot categoryPlog = barChart.getCategoryPlot();
		BarRenderer categoryRenderer = (BarRenderer) categoryPlog.getRenderer();
		categoryRenderer.setMaximumBarWidth(.1);

		return barChart;

	}

	@Override
	protected void configureChart(JFreeChart jfreeChart) throws JRException {

		super.configureChart(jfreeChart);

		LegendTitle legend = jfreeChart.getLegend();
		if (legend != null) {
			legend.setBorder(0, 0, 0, 0);
		}

	}

	/**
	 * For a given axis, adjust the tick unit size, in order to have a customizable
	 * number of ticks on that axis
	 */
	@Override
	protected void calculateTickUnits(Axis axis, boolean isRangeAxis) {

		super.calculateTickUnits(axis, isRangeAxis);
		if (axis instanceof DateAxis) {
			JRFillTimeSeriesDataset timeDataSet = null;

			System.out.println("instanceog" + getChart().getDataset().getClass().getName());
			if (getChart().getDataset() instanceof JRFillTimeSeriesDataset) {
				timeDataSet = (JRFillTimeSeriesDataset) getChart().getDataset();
			}

			DateAxis dateAxis = (DateAxis) axis;
			int axisRange = (int) dateAxis.getRange().getLength();
			if (dateAxis.getDateFormatOverride() != null) {
				if (timeDataSet != null) {
					System.out.println("DateFormatOverride axisRange:" + " timeDataSet:" + timeDataSet.getTimePeriod());

					if (timeDataSet.getTimePeriod() == org.jfree.data.time.Hour.class) {
						System.out.println("DateFormatOverride set day");
						dateAxis.setTickUnit(
								new DateTickUnit(DateTickUnitType.HOUR, 1, dateAxis.getDateFormatOverride()));
					} else {
						System.out.println("DateFormatOverride set Day");
						dateAxis.setTickUnit(
								new DateTickUnit(DateTickUnitType.DAY, 1, dateAxis.getDateFormatOverride()));
					}
				} else {

					dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1, dateAxis.getDateFormatOverride()));
				}

			} else {
				dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1));
			}

		}

	}

}
