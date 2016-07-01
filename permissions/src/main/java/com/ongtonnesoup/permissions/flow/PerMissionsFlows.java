package com.ongtonnesoup.permissions.flow;

public class PerMissionsFlows {

    private PerMissionsContinueFlow continueFlow;
    private PerMissionsDeniedFlow deniedFlow;

    public PerMissionsFlows(PerMissionsContinueFlow continueFlow, PerMissionsDeniedFlow deniedFlow) {
        this.continueFlow = continueFlow;
        this.deniedFlow = deniedFlow;
    }

    public PerMissionsContinueFlow getContinueFlow() {
        return continueFlow;
    }

    public PerMissionsDeniedFlow getDeniedFlow() {
        return deniedFlow;
    }
}
