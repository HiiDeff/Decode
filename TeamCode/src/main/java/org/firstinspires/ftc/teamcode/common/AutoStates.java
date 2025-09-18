package org.firstinspires.ftc.teamcode.common;

import java.util.Queue;

public class AutoStates {

    private Queue<Artifact> inventory;
    private int motifNumber;
    private int spikeMarkNumber;
    private int cycleNumber;
    private int intakenSampleCnt;
    private boolean isRed;
    public int getSpikeMarkNumber() { return spikeMarkNumber; }
    public void setSpikeMarkNumber(int spikeMarkNumber) { this.spikeMarkNumber = spikeMarkNumber; }
    public int getCycleNumber() {
        return cycleNumber;
    }

    public void setMotifNumber(int motifNumber) {this.motifNumber = motifNumber;}

    public int getMotifNumber() {return motifNumber;}

    public void setCycleNumber(int cycleNumber) {
        this.cycleNumber = cycleNumber;
    }
    public int getIntakenSampleCnt() { return intakenSampleCnt; }
    public void setIntakenSampleCnt(int intakenSampleCnt) { this.intakenSampleCnt = intakenSampleCnt; }

    public boolean isRed() {
        return isRed;
    }

    public void setAllianceColor(boolean isRed) {
        this.isRed = isRed;
    }
}

enum Artifact{
    GREEN,
    PURPLE
}
