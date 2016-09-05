package argos.graph3d;

import java.io.PrintStream;
import java.util.Random;
import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartScene;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import argos.graph3d.datasource.DataSource;
import argos.graph3d.datasource.StepDataPoint;

public class PathTracker extends AbstractAnalysis {
	public static float duration = 600.0F;
	public static int interval = 500;
	private Scatter scatter;
	protected static long start;
	private DataSource datasource;

	public static void main(String[] args) throws Exception {

		// Use Spring dependency injection to configure the application
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		context.registerShutdownHook();
		
		DataSource d = context.getBean("datasource", DataSource.class);

		d.init();

		PathTracker tracker = new PathTracker(d);
		AnalysisLauncher.open(tracker);
		tracker.graphRealTime();
		d.destroy();
		context.close();
	}

	public PathTracker(DataSource d) {
		this.datasource = d;
	}

	public void init() {
		int size = 5;

		Coord3d[] points = new Coord3d[size];
		Color[] colors = new Color[size];

		Random r = new Random();
		r.setSeed(0L);
		for (int i = 0; i < size; i++) {
			float x = r.nextFloat() - 0.5F;
			float y = r.nextFloat() - 0.5F;
			float z = r.nextFloat() - 0.5F;
			points[i] = new Coord3d(x, y, z);
			float a = 0.25F;
			colors[i] = new Color(x, y, z, a);
		}
		Scatter scatter = new Scatter(points, colors);

		scatter.setWidth(10.0F);
		this.chart = AWTChartComponentFactory.chart(Quality.Advanced, "newt");
		this.chart.getScene().add(scatter);
		this.scatter = scatter;
	}

	public void init2() {
		int size = 3;

		Coord3d[] points = new Coord3d[size];
		Color[] colors = new Color[size];

		Random r = new Random();
		for (int i = 0; i < size; i++) {
			float x = r.nextFloat() - 0.5F;
			float y = r.nextFloat() - 0.5F;
			float z = r.nextFloat() - 0.5F;
			points[i] = new Coord3d(x, y, z);
			float a = 0.25F;
			colors[i] = new Color(x, y, z, a);
		}
		Scatter scatter = new Scatter(points, colors);

		scatter.setWidth(3.0F);
		this.chart.getScene().add(scatter);
		this.scatter = scatter;
	}

	public void init3() {
		for (int j = 0; j < 50; j++) {
			int size = 3;

			Coord3d[] points = new Coord3d[size];
			Color[] colors = new Color[size];

			Random r = new Random();
			for (int i = 0; i < size; i++) {
				float x = r.nextFloat() - 0.5F;
				float y = r.nextFloat() - 0.5F;
				float z = r.nextFloat() - 0.5F;
				points[i] = new Coord3d(x, y, z);
				float a = 0.3F;
				colors[i] = new Color(x, y, z, a);
			}
			Scatter scatter = new Scatter(points, colors);

			scatter.setWidth(3.0F);
			this.chart.getScene().add(scatter);
			this.scatter = scatter;
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void init4() {
		float x = 0.0F;
		float y = 0.0F;
		float z = 0.0F;

		for (int j = 0; j < 2000; j++) {
			int size = 1;

			Coord3d[] points = new Coord3d[size];
			Color[] colors = new Color[size];

			Random r = new Random();
			for (int i = 0; i < size; i++) {
				float dx = 0.05F * (r.nextFloat() - 0.5F);
				float dy = 0.05F * (r.nextFloat() - 0.5F);
				float dz = 0.05F * (r.nextFloat() - 0.5F);

				x = x + dx;
				y = y + dy;
				z = z + dz;

				points[i] = new Coord3d(x, y, z);
				float a = 0.3F;
				colors[i] = new Color(x, y, z, a);
			}
			Scatter scatter = new Scatter(points, colors);

			scatter.setWidth(3.0F);
			this.chart.getScene().add(scatter);
			this.scatter = scatter;
			try {
				Thread.sleep(10L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void graphRealTime() {
		Coord3d previous = null;
		//for (int j = 0; j < 2000; j++) {
		while (true) {

			StepDataPoint datapoint = datasource.getNextStepData();
			Coord3d point = new Coord3d(datapoint.x, datapoint.y, datapoint.z);

			if (previous == null) {
				previous = point;
			}
			LineStrip scatter = new LineStrip(new Point(previous, new Color(0, 255, 0)),
					new Point(point, new Color(255, 0, 0)));
			previous = point;

			scatter.setWidth(3.0F);
			this.chart.getScene().add(scatter);
			// this.scatter = scatter;
		}
	}

	private void generateSamplesInTime() throws InterruptedException {
		System.out.println("will generate approx. " + duration * 1000.0F / interval + " samples");

		start();
		while (elapsed() < duration) {
			Random r = new Random();

			float x = r.nextFloat() - 0.5F;
			float y = r.nextFloat() - 0.5F;
			float z = r.nextFloat() - 0.5F;
			Coord3d point = new Coord3d(x, y, z);
			float a = 0.25F;
			Color color = new Color(x, y, z, a);

			Coord3d[] coords = new Coord3d[1];
			coords[0] = point;

			Color[] colors = new Color[1];
			colors[0] = color;

			Scatter scatter = new Scatter(coords, colors);
			this.chart.getScene().add(scatter);

			Thread.sleep(interval);
		}
		System.out.println("done");
	}

	public static void start() {
		start = System.nanoTime();
	}

	public static double elapsed() {
		return (System.nanoTime() - start) / 1.0E9D;
	}

	public Scatter getScatter() {
		return this.scatter;
	}
}
