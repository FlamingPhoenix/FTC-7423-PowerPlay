package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(35, 52.48291908330528, Math.toRadians(180), Math.toRadians(180), 9.47).followTrajectorySequence(drive ->
                drive.trajectorySequenceBuilder(new Pose2d(7, 0, Math.toRadians(0)))
                        .splineTo(new Vector2d(39.00, -0.00), Math.toRadians(-14.04))
                        .splineTo(new Vector2d(65.00, -5.00), Math.toRadians(-35.10))
                        .lineToSplineHeading(new Pose2d(59.87, 23.47, Math.toRadians(90.00)))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}