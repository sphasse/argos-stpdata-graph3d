package argos.graph3d.datasource;

public class StepByStepFilter implements DataSource {

	private DataSource upstream;
	private boolean previousButton = StepDataPoint.FOOT_DOWN;
	
	public StepByStepFilter(DataSource upstream) {
		this.upstream = upstream;
	}
	
	@Override
	public void init() {
		upstream.init();
		
	}

	@Override
	public StepDataPoint getNextStepData() {
		boolean readMore = true;
		StepDataPoint current = null;
		while (readMore) {
			current = upstream.getNextStepData();
			if (current != null) {
				if (previousButton == StepDataPoint.FOOT_DOWN && current.footSwitch == StepDataPoint.FOOT_UP) {
					//just started stepping, send point
					readMore = false;
				} else if (previousButton == StepDataPoint.FOOT_UP && current.footSwitch == StepDataPoint.FOOT_DOWN) {
					//just finished stepping, send point
					readMore = false;
				}
			}
		}
		previousButton = current.footSwitch;
		return current;
	}

	@Override
	public void destroy() {
		upstream.destroy();
		
	}

	
}
