package dapr.analyis;

import dapr.model.AnalysisResult;
import io.dapr.client.DaprClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static dapr.Constants.*;

public class AnalyseResultClientImpl implements AnalyseResultClient {
	private final DaprClient daprClient;

	@Autowired
	public AnalyseResultClientImpl(final DaprClient daprClient) {
	   this.daprClient = daprClient;
	}
	
	@Override
	public void analyseResultSend() {

		AnalysisResult analysisResult = new AnalysisResult(UUID.randomUUID(), 3);
		daprClient.publishEvent(PUBSUB, ANALYSIS_RESULT_TOPIC, analysisResult).block();
	}

}
