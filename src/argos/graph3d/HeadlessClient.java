package argos.graph3d;

import org.jzy3d.analysis.AnalysisLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import argos.graph3d.datasource.DataSource;
import argos.graph3d.datasource.StepDataPoint;

public class HeadlessClient {

	public static void main(String[] args) {
		// Use Spring dependency injection to configure the application
		// appropriate filters should be configured in the Spring context
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"clientApplicationContext.xml");
		context.registerShutdownHook();
		
		DataSource d = context.getBean("datasource", DataSource.class);

		d.init();

		boolean cont = true;
		while (cont) {
		    StepDataPoint datapoint = d.getNextStepData();
		}
		d.destroy();
		context.close();
	}

}
