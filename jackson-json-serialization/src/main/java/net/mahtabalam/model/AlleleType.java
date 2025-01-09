package net.mahtabalam.model;

public enum AlleleType {
    HOMOZYGOUS(0),
    HETEROZYGOUS(1);

    private final int alleleType;

    AlleleType(int alleleType) {
        this.alleleType = alleleType;
    }

    public int getAlleleType() {
        return alleleType;
    }
}