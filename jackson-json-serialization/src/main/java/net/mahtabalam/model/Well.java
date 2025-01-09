package net.mahtabalam.model;

import java.util.Arrays;

public class Well {
    private String id;
    private AlleleType alleleType;
    private double[] allele1;
    private double[] allele2;

    public Well() {
    }

    public Well(String id, AlleleType alleleType) {
        this.id = id;
        this.alleleType = alleleType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AlleleType getAlleleType() {
        return alleleType;
    }

    public void setAlleleType(AlleleType alleleType) {
        this.alleleType = alleleType;
    }

    public double[] getAllele1() {
        return allele1;
    }

    public void setAllele1(double[] allele1) {
        this.allele1 = allele1;
    }

    public double[] getAllele2() {
        return allele2;
    }

    public void setAllele2(double[] allele2) {
        this.allele2 = allele2;
    }

    @Override
    public String toString() {
        return "Well{" +
                "id='" + id + '\'' +
                ", alleleType=" + alleleType +
                ", allele1=" + Arrays.toString(allele1) +
                ", allele2=" + Arrays.toString(allele2) +
                '}';
    }
}
