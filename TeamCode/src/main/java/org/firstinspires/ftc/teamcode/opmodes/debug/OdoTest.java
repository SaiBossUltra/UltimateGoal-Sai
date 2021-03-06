package org.firstinspires.ftc.teamcode.opmodes.debug;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robots.LiveRobot;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

@Autonomous(name="Odo Test", group="autonomous")
//@Disabled
public class OdoTest extends LinearOpMode {

    LiveRobot robot;

    int pattern;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new LiveRobot(this);
        robot.startup();

        int color = RED;

        robot.drive_train.color = color;


        waitForStart();

        robot.drive_train.setInitPos(0, 0, Math.PI);

//        robot.drive_train.odo_move(48, 0, 2*Math.PI, 1, -1, -1);

//        robot.drive_train.odo_move(0, 48, 0, 1, -1, -1);

        //robot.drive_train.odo_move(0, 0, Math.PI/4, 1, -1, Math.PI/60.0);

        robot.wobble.downWobble(true);
        robot.drive_train.odo_move(robot.drive_train.get_X() + 3, robot.drive_train.get_Y(), robot.drive_train.get_A(), 1, 2, Math.PI/360);
        robot.wobble.openWobbleServo(true);
        robot.drive_train.odo_move(robot.drive_train.get_X() + 3, robot.drive_train.get_Y(), robot.drive_train.get_A(), 1, 2, Math.PI/360);
        robot.wobble.closeWobbleServo(true);


        robot.shutdown();
    }
}
