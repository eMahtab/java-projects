package net.mahtabalam.model;

import java.util.Arrays;

public class RawDataResult {

    private Well[] wellResults;

    public RawDataResult() {
    }

    public Well[] getWellResults() {
        return wellResults;
    }

    public void setWellResults(Well[] wellResults) {
        this.wellResults = wellResults;
    }

    @Override
    public String toString() {
        return "RawDataResult{" +
                "wellResults=" + Arrays.toString(wellResults) +
                '}';
    }
}
