package org.firstinspires.ftc.teamcode.opmodes.teleop;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import org.firstinspires.ftc.teamcode.opmodes.LiveTeleopBase;

import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Teleop Auto5", group="autonomous")
//@Disabled
public class LiveAuto extends LiveTeleopBase {

    private static final double angle_acc = Math.PI/130.0;
    private static final double distance_acc = 1.0;

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
        robot.wobble.closeWobbleServo(true);
//        robot.wobble.middleWobbleLift(true);

        int pattren = robot.phone_camera.get_pattern();
        robot.phone_camera.stop_streaming();

        aroundRings(pattren);
        gotoRectBasedOnPattren(pattren);
        robot.wobble.dropWobble(true);
        robot.shooter.spin(true);

        if(pattren==1){
            robot.drive_train.odo_move(robot.drive_train.get_X(), robot.drive_train.get_Y()-5, Math.PI, 1, distance_acc, angle_acc);
            robot.drive_train.odo_move(robot.drive_train.get_X()+5, robot.drive_train.get_Y()-3, Math.PI, 1, distance_acc, angle_acc);
        }

        if(pattren == 3){
            robot.drive_train.odo_move(-15, -3, Math.PI + ((Math.PI/180)*4), 1, distance_acc, angle_acc);
        } else {
            robot.drive_train.odo_move(-12, -3, Math.PI, 1, distance_acc, angle_acc);
        }


        //waitForTime(1.0);
        if(!isOpmodeActive()) return;
        //robot.drive_train.odo_move(robot.drive_train.get_X(), robot.drive_train.get_Y(), robot.drive_train.get_A()+ ((Math.PI/180)*4), 1, distance_acc, angle_acc);
        //waitForTime(0.5);
        robot.shooter.shoot(robot.shooter.getShooterSpped() - 17);
        //sleep(150);
        robot.shooter.shoot(robot.shooter.getShooterSpped() - 48);
        //sleep(100);
        robot.shooter.shoot(robot.shooter.getShooterSpped() - 56);
        robot.shooter.unshoot();

        //catchNShhotInitialRings(pattren);



        if(pattren > 1){
            elapsedtimer.reset();
            intake(pattren);
            //rightBeforeLine();
            if(pattren == 2){
                //shootpos(pattren);
                robot.drive_train.odo_move(-10, -3, Math.PI, 1, distance_acc, angle_acc);
                robot.shooter.shoot();
                robot.shooter.unshoot();
            }

            if(pattren == 3){
                robot.drive_train.odo_move(-14, -3, Math.PI + ((Math.PI/180)*4.5), 1, distance_acc, angle_acc);
                robot.shooter.shoot();
                robot.shooter.shoot();
                robot.shooter.shoot();
                robot.shooter.unshoot();
               }

        }

        secondWobble();
        gotoRectBasedOnPattren(pattren);
        robot.wobble.dropWobble(true);
        gotoLine();

    }
    private void aroundRings(int pattren) {
        if(pattren == 3){
            robot.drive_train.odo_move(-25.0, robot.drive_train.get_Y(), robot.drive_train.get_A() -  ((Math.PI/180)*4), 1, distance_acc, angle_acc);
        } else{
            robot.drive_train.odo_move(-27.5, robot.drive_train.get_Y(), robot.drive_train.get_A(), 1, distance_acc, angle_acc);
        }
    }

    private void secondWobble(){
        robot.wobble.middleWobbleLift(true);
        robot.wobble.openWobbleServo(true);
        robot.drive_train.odo_move(-6.0, -36, -0, 1, distance_acc, angle_acc);
        robot.wobble.closeWobbleServo(true);
    }

    private void shootpos(int pattren){
        if(pattren == 3){
            robot.drive_train.odo_move(-14.0, -50.0, robot.drive_train.get_A() + ((Math.PI/180)*4), 1, distance_acc, angle_acc);
        }
        robot.drive_train.odo_move(-14.0, -50.0, robot.drive_train.get_A() + ((Math.PI/180)*8), 1, distance_acc, angle_acc);
    }
    private void intake(int pattren){
        ElapsedTime elapsedtimer = new ElapsedTime();
        if(pattren == 3){
            robot.drive_train.odo_move(-8.3, 0, Math.PI + ((Math.PI/180)*7), 1, distance_acc, Math.PI/360);
            robot.drive_train.odo_move(robot.drive_train.get_X(), -31.0, robot.drive_train.get_A(), 1, distance_acc, Math.PI / 360);
        }else{
            robot.drive_train.odo_move(-4, 0, robot.drive_train.get_A(), 1, distance_acc, Math.PI/360);
        }
        robot.intake.setIntakePower(1);
        waitForTime(1);
        while(elapsedtimer.seconds()<6.0){
            robot.intake.setIntakePower(1);
            if(pattren == 3){
                //robot.drive_train.odo_move(robot.drive_train.get_X(), -25.0, robot.drive_train.get_A(), 1, distance_acc, Math.PI / 360);
                robot.drive_train.odo_move(robot.drive_train.get_X(), robot.drive_train.get_Y()-1, robot.drive_train.get_A(), 1, distance_acc, Math.PI / 360);
                robot.drive_train.odo_move(robot.drive_train.get_X(), robot.drive_train.get_Y(), robot.drive_train.get_A(), 1, distance_acc, Math.PI / 360);
            }else {
                robot.drive_train.odo_move(robot.drive_train.get_X(), -30.0, robot.drive_train.get_A(), 1, distance_acc, Math.PI / 360);
            }
        }
        robot.intake.setIntakePower(0);
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
        robot.drive_train.odo_move(-28.0, -12.0, robot.drive_train.get_A() + (Math.PI/80), 1, distance_acc, angle_acc);
        switch (pattren) {
            case 2:
                robot.drive_train.odo_move(-2.0, 12.0, robot.drive_train.get_A(), 1, distance_acc, angle_acc);
                break;
            case 3:
                robot.drive_train.odo_move(-16, 33.0, robot.drive_train.get_A(), 1, distance_acc, angle_acc);
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
