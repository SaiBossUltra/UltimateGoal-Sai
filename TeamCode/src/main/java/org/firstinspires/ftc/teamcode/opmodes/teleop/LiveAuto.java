package org.firstinspires.ftc.teamcode.opmodes.teleop;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import org.firstinspires.ftc.teamcode.components.live.Wobble;
import org.firstinspires.ftc.teamcode.opmodes.LiveTeleopBase;

import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Main Auto 69", group="autonomous")
//@Disabled
public class LiveAuto extends LiveTeleopBase {

    private static final double angle_acc = Math.PI/180.0;
    private static final double distance_acc = 1;

    @Override
    public void on_init() {
        robot.drive_train.setInitPos(-15.0, -61.0, Math.PI);
        robot.phone_camera.start_streaming();
    }

    @Override
    public void on_start() {
        ElapsedTime elapsedtimer = new ElapsedTime();

        //        robot.wobble.closeWobbleLift(true);
        //        robot.wobble.openWobbleServo(true);
        robot.wobble.SecondWobbleClose(true);
        //        robot.wobble.middleWobbleLift(true);
        int pattren = robot.phone_camera.get_pattern();
        robot.phone_camera.stop_streaming();

        SCWobble(pattren);

        aroundRings(pattren); // move around the starting stack of rings
        gotoRectBasedOnPattren(pattren); //Travel to the correct box for the wobble
        robot.wobble.downWobble(true); // drop the wobble
        robot.wobble.openWobbleServo(true); // open the servo to let go of wobble
        //robot.wobble.dropWobble(true);
        if(pattren == 2){
            robot.drive_train.odo_move(robot.drive_train.get_X(), robot.drive_train.get_Y(), robot.drive_train.get_A() - ((Math.PI/180)*70), 1, distance_acc, angle_acc);
        }else {
            robot.drive_train.odo_move(robot.drive_train.get_X(), robot.drive_train.get_Y(), robot.drive_train.get_A() - ((Math.PI / 180) * 35), 1, distance_acc, angle_acc);
        }
        //robot.wobble.SecondWobbleDown(true);
        robot.wobble.dropWobble(true);
        robot.wobble.SecondWobbleOpen(true);
        robot.shooter.spin(true); // prematurely spin shooter to allow time for it to reach a good speed
        robot.wobble.SecondWobbleUp(false);

        if(pattren==1){ // Go back and right if dropped wobble at first box to create space so we do not tip the wobble over
            robot.drive_train.odo_move(robot.drive_train.get_X(), robot.drive_train.get_Y()-5, Math.PI, 1, distance_acc, angle_acc);
            robot.drive_train.odo_move(robot.drive_train.get_X()+5, robot.drive_train.get_Y()-3, Math.PI, 1, distance_acc, angle_acc);
        }

        if(pattren == 3) { // seperate shooting positions for 4-ring auto and the other set ups
            robot.drive_train.odo_move(-9, -2, Math.PI, 1, distance_acc, angle_acc);
        } else {
            robot.drive_train.odo_move(-9, -3, Math.PI, 1, distance_acc, angle_acc);
        }

        //waitForTime(1.0);
        if(!isOpmodeActive()) return;
        //robot.drive_train.odo_move(robot.drive_train.get_X(), robot.drive_train.get_Y(), robot.drive_train.get_A()+ ((Math.PI/180)*4), 1, distance_acc, angle_acc);
        //waitForTime(0.5);
        robot.shooter.shoot(robot.shooter.getShooterSpped() - 30); // continue to slowly decrase speed of shooter to make sure we are always consistent
        //sleep(150);
        robot.shooter.shoot(robot.shooter.getShooterSpped() - 55);
        //sleep(100);
        robot.shooter.shoot(robot.shooter.getShooterSpped() - 63);
        robot.shooter.unshoot();

        //catchNShhotInitialRings(pattren);



        if(pattren > 1){ //make sure it is either 1 or 4 ring starting stack before trying to intake to shoot rings
            elapsedtimer.reset();
            intake(pattren); // collect rings
            //rightBeforeLine();
            if(pattren == 2){
                //shootpos(pattren);
                robot.drive_train.odo_move(-11, -6, Math.PI , 1, distance_acc, angle_acc); // go to shooting position
                robot.intake.setIntakePower(0); // stop intake
                robot.shooter.shoot(); // shoot the 1 ring
                robot.shooter.unshoot(); // stop shooting
            }

            if(pattren == 3){
                robot.drive_train.odo_move(-13, -6, Math.PI , 1, distance_acc, angle_acc); // go to shooting position
                robot.intake.setIntakePower(0); // stop intake
                robot.shooter.shoot(); // shoot hypothetical 3 rings, could also just have 1 or 2 but we are being safe
                robot.shooter.shoot();
                robot.shooter.shoot();
                robot.shooter.unshoot(); // stop shooting
            }

        }

        gotoLine(); // park on the white line

    }
    private void aroundRings(int pattren) {
        robot.drive_train.odo_move(-27.5, robot.drive_train.get_Y(), Math.PI, 1, distance_acc, angle_acc);
    }
    private void SCWobble(int pattren){
        //robot.wobble.closeWobbleServo(false);
        //robot.drive_train.odo_move(robot.drive_train.get_X() - 7, robot.drive_train.get_Y(), robot.drive_train.get_A(), 1, distance_acc, angle_acc);
        //sleep(50);
        robot.wobble.downWobble(false);
        robot.drive_train.odo_move(robot.drive_train.get_X() - 7 , robot.drive_train.get_Y() + 10, robot.drive_train.get_A(), 1, distance_acc, angle_acc);
        sleep(1000);
        //robot.wobble.openWobbleServo(false);
        //robot.wobble.SimpleOpen(true);
        robot.drive_train.odo_move(robot.drive_train.get_X(), robot.drive_train.get_Y(), robot.drive_train.get_A() + Math.PI/2, 1, distance_acc, Math.PI/360);
        sleep(1000);
        robot.drive_train.odo_move(robot.drive_train.get_X() + 9, robot.drive_train.get_Y(), robot.drive_train.get_A(), 1, distance_acc, angle_acc);
        sleep(1000);
        robot.wobble.closeWobbleServo(true);
        sleep(1000);
        robot.wobble.upWobble(true);
        sleep(50);
    }


    private void shootpos(int pattren){
        if(pattren == 3){
            robot.drive_train.odo_move(-16.0, -50.0, Math.PI, 1, distance_acc, angle_acc);
        }
        robot.drive_train.odo_move(-16.0, -50.0, Math.PI, 1, distance_acc, angle_acc);
    }
    private void intake(int pattren){
        ElapsedTime elapsedtimer = new ElapsedTime();
        robot.intake.setIntakePower(-1);
        if(pattren == 3){
            robot.drive_train.odo_move(-10, 0, robot.drive_train.get_A() + ((Math.PI/180)*4.5) , 1, distance_acc, angle_acc);
            robot.drive_train.odo_move(robot.drive_train.get_X(), -28.0, robot.drive_train.get_A(), 1, distance_acc, angle_acc);
        }else{
            robot.drive_train.odo_move(-8, 0, Math.PI, 1, distance_acc, angle_acc);
        }


            robot.intake.setIntakePower(-1);
            robot.drive_train.odo_move(robot.drive_train.get_X(), -36.0, robot.drive_train.get_A(), 1, distance_acc, angle_acc);
            if(pattren == 3){
                sleep(500);
                robot.drive_train.odo_move(robot.drive_train.get_X() + 1.5, robot.drive_train.get_Y(), robot.drive_train.get_A(), 0.5, distance_acc, angle_acc);
            }else {
                robot.drive_train.odo_move(robot.drive_train.get_X(), -37.0, robot.drive_train.get_A(), 0.6, distance_acc, angle_acc, 0, 3);
            }
        //robot.intake.setIntakePower(0);
    }



    private void gotoLine() {
        robot.drive_train.odo_move(-12.0, 6.0, Math.PI, 1, distance_acc, angle_acc);
    }
    private void lessShooter() {
        robot.shooter.spin(true);
        robot.shooter.triggerOn(true);
    }

    private void gotoRectBasedOnPattren(int pattren) {
        //robot.drive_train.odo_move(12.0, -36.0, 0.0, 1, -1, -1);
        //robot.drive_train.odo_move(12.0, -12.0, 0.0, 1, -1, -1);
        robot.drive_train.odo_move(-28.0, -12.0, Math.PI, 1, distance_acc, angle_acc);
        switch (pattren) {
            case 2:
                robot.drive_train.odo_move(-1.0, 12.0, Math.PI, 1, distance_acc, angle_acc);
                break;
            case 3:
                robot.drive_train.odo_move(-24, 36.0, Math.PI, 1, distance_acc, angle_acc);
                break;
        }
    }


    private void catchNShhotInitialRings(int patteren) {
        if(patteren == 1) return;
        robot.drive_train.odo_move(robot.drive_train.get_X() - 5.5, robot.drive_train.get_Y(), 0.0, 1, -1, angle_acc);
        robot.intake.setIntakePower(-1.0);
        robot.drive_train.odo_move(-12.0, -12.0, 0.0, 1, -1, angle_acc);
        if(patteren == 2) {
            waitForTime(2.0);
            robot.intake.setIntakePower(0.0);
            robot.shooter.shoot();
        } else {
            waitForTime(4.0);
            robot.intake.setIntakePower(0.0);
            robot.shooter.shoot();
            robot.shooter.shoot();
            robot.shooter.shoot();
        }
        robot.shooter.unshoot();
    }

    public void waitForTime(double sec) {
        double expTime = getRuntime() + sec;
        try {
            while (isOpmodeActive() && getRuntime() < expTime) {
                //idle();
                Thread.sleep(50);
            }
        }catch (Exception ex){}
    }


    @Override
    public void on_stop() {

    }

    @Override
    public void on_loop() {

    }

    public boolean isOpmodeActive() {
        return opModeIsActive() && !isStopRequested();
    }
}