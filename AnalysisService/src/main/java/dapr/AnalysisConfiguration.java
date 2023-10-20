package dapr;

import dapr.analyis.AnalyseResultClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dapr.analyis.DaprAnalyseResultClient;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.serializer.DefaultObjectSerializer;



@Configuration
public class AnalysisConfiguration {
	
	static class JsonObjectSerializer extends DefaultObjectSerializer {
	    public JsonObjectSerializer() {
	        OBJECT_MAPPER.registerModule(new JavaTimeModule());
	        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	    }
	}

    @Bean
    public AnalyseResultClient fineCollectionClient(final DaprClient daprClient) {
        return new DaprAnalyseResultClient(daprClient);
    }
    
    @Bean
    public DaprClient daprClient() {
        return new DaprClientBuilder()
                .withObjectSerializer(new JsonObjectSerializer())
                .build();
    }
}
