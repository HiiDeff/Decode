package org.firstinspires.ftc.teamcode.common;

import org.firstinspires.ftc.teamcode.drive.ArtifactState;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

public class AutoStates {

    public ArtifactState.ArtifactStateColor[] inventory = {ArtifactState.ArtifactStateColor.EMPTY, ArtifactState.ArtifactStateColor.EMPTY, ArtifactState.ArtifactStateColor.EMPTY};
    private int motifNumber;
    private int spikeMarkNumber;
    private int cycleNumber;
    private boolean isRed;

    public int getSpikeMarkNumber() {
        return spikeMarkNumber;
    }

    public void setSpikeMarkNumber(int spikeMarkNumber) {
        this.spikeMarkNumber = spikeMarkNumber;
    }

    public int getCycleNumber() {
        return cycleNumber;
    }

    public void setMotifNumber(int motifNumber) {
        this.motifNumber = motifNumber;
    }

    public int getMotifNumber() {
        return motifNumber;
    }

    public void setCycleNumber(int cycleNumber) {
        this.cycleNumber = cycleNumber;
    }

    public void updateArtifactPos(ArtifactState.ArtifactStateColor artifactState1, ArtifactState.ArtifactStateColor artifactState2, ArtifactState.ArtifactStateColor artifactState3) {
        inventory[2] = artifactState3;
        inventory[1] = artifactState2;
        inventory[0] = artifactState1;
    }

    public boolean isRed() {
        return isRed;
    }

    public void setAllianceColor(boolean isRed) {
        this.isRed = isRed;
    }
}
