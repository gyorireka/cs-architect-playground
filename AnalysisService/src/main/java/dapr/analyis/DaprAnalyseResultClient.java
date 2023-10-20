package dapr.analyis;

import dapr.model.AnalysisResult;
import io.dapr.client.DaprClient;

import java.util.UUID;

public class DaprAnalyseResultClient implements AnalyseResultClient {
	private final DaprClient daprClient;

	public DaprAnalyseResultClient(final DaprClient daprClient) {
	   this.daprClient = daprClient;
	}
	
	@Override
	public void analyseResultSend() {

		AnalysisResult analysisResult = new AnalysisResult(UUID.randomUUID(), 3);
		daprClient.publishEvent("pubsub",  "analysis", analysisResult).block();
	}

}
