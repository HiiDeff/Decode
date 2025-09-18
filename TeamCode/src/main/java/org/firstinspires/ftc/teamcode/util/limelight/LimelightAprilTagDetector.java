package org.firstinspires.ftc.teamcode.util.limelight;

import android.util.Log;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LimelightAprilTagDetector {
    protected final Limelight3A limelight;
    protected LLResult result = null;
    protected List<LLResultTypes.FiducialResult> tagResults;
    public LimelightAprilTagDetector(Limelight3A limelight) {
        this.limelight = limelight;
    }
    public synchronized void updateLimelight() {
        result = limelight.getLatestResult();
        if(result!=null) {tagResults = result.getFiducialResults();}
    }

    public int checkAprilTags(){
        if(tagResults != null){
            for(LLResultTypes.FiducialResult tag:tagResults){
                if(tag.getFiducialId() > 20 && tag.getFiducialId() < 24){
                    return tag.getFiducialId();
                }
            }
        }

        return 0;
    }
}
