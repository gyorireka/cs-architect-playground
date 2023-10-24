package dapr.analyis;

import dapr.model.ImageAddress;

import java.util.ArrayList;

public interface AnalyseResultClient {
    void analyseResultSend(ArrayList<ImageAddress> images);
}
