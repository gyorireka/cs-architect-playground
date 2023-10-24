package dapr.analyis;

import dapr.model.AnalysisResult;
import dapr.model.ImageAddress;
import io.dapr.client.DaprClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static dapr.Constants.*;

public class AnalyseResultClientImpl implements AnalyseResultClient {
	private final DaprClient daprClient;

	@Autowired
	public AnalyseResultClientImpl(final DaprClient daprClient) {
	   this.daprClient = daprClient;
	}
	
	@Override
	public void analyseResultSend(ArrayList<ImageAddress> images) {
		List<AnalysisResult> result = new ArrayList<>();
		Random random = new Random();

		for (ImageAddress image : images) {
			result.add(new AnalysisResult(UUID.randomUUID(), image.address(), random.nextInt(20), random.nextInt(10)));
		}
		daprClient.publishEvent(PUBSUB, ANALYSIS_RESULT_TOPIC, result).block();
	}

}
