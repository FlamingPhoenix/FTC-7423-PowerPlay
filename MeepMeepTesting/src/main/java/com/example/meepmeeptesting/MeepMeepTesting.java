package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(35, 52.48291908330528, Math.toRadians(180), Math.toRadians(180), 9.47).followTrajectorySequence(drive ->
                drive.trajectorySequenceBuilder(new Pose2d(0, 0, Math.toRadians(90)))
                .lineToSplineHeading(new Pose2d(53, -3, Math.toRadians(315)))
                .lineToSplineHeading(new Pose2d(55, 18, Math.toRadians(90)))
                .lineToSplineHeading(new Pose2d(53, -3, Math.toRadians(315)))
                .lineToSplineHeading(new Pose2d(55, 18, Math.toRadians(90)))
                .lineToSplineHeading(new Pose2d(53, -3, Math.toRadians(315)))
                .lineToSplineHeading(new Pose2d(55, 18, Math.toRadians(90)))

                .lineToSplineHeading(new Pose2d(53, -3, Math.toRadians(315)))

                .lineToSplineHeading(new Pose2d(55, 18, Math.toRadians(90)))

                .lineToSplineHeading(new Pose2d(53, -3, Math.toRadians(315)))
                .lineToSplineHeading(new Pose2d(55, 18, Math.toRadians(90)))

                .lineToSplineHeading(new Pose2d(53, -3, Math.toRadians(315)))

                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}