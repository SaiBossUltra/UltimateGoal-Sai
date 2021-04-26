package org.firstinspires.ftc.teamcode.components.live;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.Component;
import org.firstinspires.ftc.teamcode.robots.Robot;

class WobbleConfig {

    public static final double HOLD_WOBBLE_POS = 0.964f;
    public static final double OPEN_WOBBLE_POS = 0.6f;

    public static final double SECOND_WOBBLE_HOLD = 0.8f;
    public static final double SECOND_WOBBLE_OPEN = 0.0f;

    public static final double LIFT_DOWN_POS = 0.2f;
    public static final double LIFT_MIDDLE_POS = 0.49f;
    public static final double LIFT_UP_POS = 0.65f;

    public static final double SECOND_LIFT_DOWN = 0.0f;
    public static final double SECOND_LIFT_UP = 1.0f;

}

public class Wobble extends Component {

    //// SERVOS ////
    private Servo lift;
    private Servo wobbleservo;
    private DcMotor dclift;
    private Servo sideWClaw;
    private Servo SCLift;
    private boolean useHardWait = true;
    private long hardWaitMills = 800;

    {
        name = "Wobble";
    }

    public Wobble(Robot robot) {
        super(robot);
    }

    @Override
    public void registerHardware(HardwareMap hwmap) {
        super.registerHardware(hwmap);

        //// SERVOS ////
        lift        = hwmap.get(Servo.class, "lift");
        wobbleservo    = hwmap.get(Servo.class, "wobbleservo");
        dclift = hwmap.get(DcMotor.class, "dclift");
        sideWClaw = hwmap.get(Servo.class, "scgrab");
        SCLift = hwmap.get(Servo.class, "sclift");
    }


    public void closeWobbleServo(boolean wait) {
        wait = wait && Math.abs(wobbleservo.getPosition() - WobbleConfig.HOLD_WOBBLE_POS) > SERVO_POS_ERROR_BOUNDARY;
        wobbleservo.setPosition(WobbleConfig.HOLD_WOBBLE_POS);
        while(wait && isOpmodeActive() && Math.abs(wobbleservo.getPosition() - WobbleConfig.HOLD_WOBBLE_POS) > SERVO_POS_ERROR_BOUNDARY) {
            wobbleservo.setPosition(WobbleConfig.HOLD_WOBBLE_POS);
        }
        if(wait && isOpmodeActive() && useHardWait) {
            waitForTime(hardWaitMills/1000.0);
        }
    }

    public void openWobbleServo(boolean wait) {
        wait = wait && Math.abs(wobbleservo.getPosition() - WobbleConfig.OPEN_WOBBLE_POS) > SERVO_POS_ERROR_BOUNDARY;
        wobbleservo.setPosition(WobbleConfig.OPEN_WOBBLE_POS);
        while(wait && isOpmodeActive() && Math.abs(wobbleservo.getPosition() - WobbleConfig.OPEN_WOBBLE_POS) > SERVO_POS_ERROR_BOUNDARY) {
            wobbleservo.setPosition(WobbleConfig.OPEN_WOBBLE_POS);
        }
        if(wait && useHardWait) {
            waitForTime(hardWaitMills/1000.0);
        }


    }

    public void SecondWobbleClose(boolean wait){
        wait = wait && Math.abs(sideWClaw.getPosition() - WobbleConfig.SECOND_WOBBLE_HOLD) > SERVO_POS_ERROR_BOUNDARY;
        sideWClaw.setPosition(WobbleConfig.SECOND_WOBBLE_HOLD);
        while(wait && isOpmodeActive() && Math.abs(sideWClaw.getPosition() - WobbleConfig.SECOND_WOBBLE_HOLD) > SERVO_POS_ERROR_BOUNDARY) {
            sideWClaw.setPosition(WobbleConfig.SECOND_WOBBLE_HOLD);
        }
        if(wait && useHardWait) {
            waitForTime(hardWaitMills/1000.0);
        }
    }
    public void SecondWobbleOpen(boolean wait){
        wait = Math.abs(sideWClaw.getPosition() - WobbleConfig.SECOND_WOBBLE_OPEN) > SERVO_POS_ERROR_BOUNDARY;
        sideWClaw.setPosition(WobbleConfig.SECOND_WOBBLE_OPEN);
        while(wait && isOpmodeActive() && Math.abs(sideWClaw.getPosition() - WobbleConfig.SECOND_WOBBLE_OPEN) > SERVO_POS_ERROR_BOUNDARY) {
            sideWClaw.setPosition(WobbleConfig.SECOND_WOBBLE_OPEN);
        }
        if(wait && useHardWait) {
            waitForTime(hardWaitMills/1000.0);
        }
    }


    public void closeWobbleLift(boolean wait) {
        wait = Math.abs(lift.getPosition() - WobbleConfig.LIFT_DOWN_POS) > SERVO_POS_ERROR_BOUNDARY;
        lift.setPosition(WobbleConfig.LIFT_DOWN_POS);
        while(wait && isOpmodeActive() && Math.abs(lift.getPosition() - WobbleConfig.LIFT_DOWN_POS) > SERVO_POS_ERROR_BOUNDARY) {
            lift.setPosition(WobbleConfig.LIFT_DOWN_POS);
        }
        if(wait && useHardWait) {
            waitForTime(hardWaitMills/1000.0);
        }
    }

    public void dropwobble(){
        ElapsedTime elapsedtimer = new ElapsedTime();
        elapsedtimer.startTime();
        while(elapsedtimer.seconds() < 1.5){
            dclift.setPower(0.45);
        }
        dclift.setPower(0.0);
    }

    public void middleWobbleLift(boolean wait) {
        wait = Math.abs(lift.getPosition() - WobbleConfig.LIFT_MIDDLE_POS) > SERVO_POS_ERROR_BOUNDARY;
        lift.setPosition(WobbleConfig.LIFT_MIDDLE_POS);
        while(wait && isOpmodeActive() && Math.abs(lift.getPosition() - WobbleConfig.LIFT_MIDDLE_POS) > SERVO_POS_ERROR_BOUNDARY) {
            lift.setPosition(WobbleConfig.LIFT_MIDDLE_POS);
        }
        if(wait && useHardWait) {
            waitForTime(hardWaitMills/1000.0);
        }
    }

    public void upWobbleLift(boolean wait) {
        wait = Math.abs(lift.getPosition() - WobbleConfig.LIFT_UP_POS) > SERVO_POS_ERROR_BOUNDARY;
        lift.setPosition(WobbleConfig.LIFT_UP_POS);
        while(wait && isOpmodeActive() && Math.abs(lift.getPosition() - WobbleConfig.LIFT_UP_POS) > SERVO_POS_ERROR_BOUNDARY) {
            lift.setPosition(WobbleConfig.LIFT_UP_POS);
        }
        if(wait && useHardWait) {
            waitForTime(hardWaitMills/1000.0);
        }
    }

    public void SecondWobbleDown(boolean wait){
        wait = Math.abs(SCLift.getPosition() - WobbleConfig.SECOND_LIFT_DOWN) > SERVO_POS_ERROR_BOUNDARY;
        SCLift.setPosition(WobbleConfig.SECOND_LIFT_DOWN);
        while(wait && isOpmodeActive() && Math.abs(SCLift.getPosition() - WobbleConfig.SECOND_LIFT_DOWN) > SERVO_POS_ERROR_BOUNDARY) {
            SCLift.setPosition(WobbleConfig.SECOND_LIFT_DOWN);
        }
        if(wait && useHardWait) {
            waitForTime(hardWaitMills/1000.0);
        }
    }

    public void SecondWobbleUp(boolean wait){
        wait = wait && Math.abs(SCLift.getPosition() - WobbleConfig.SECOND_LIFT_UP) > SERVO_POS_ERROR_BOUNDARY;
        SCLift.setPosition(WobbleConfig.SECOND_LIFT_UP);
        while(wait && isOpmodeActive() && Math.abs(SCLift.getPosition() - WobbleConfig.SECOND_LIFT_UP) > SERVO_POS_ERROR_BOUNDARY) {
            SCLift.setPosition(WobbleConfig.SECOND_LIFT_UP);
        }
        if(wait && useHardWait) {
            waitForTime(hardWaitMills/1000.0);
        }
    }

    public void catchWoble(boolean wait) {
        closeWobbleServo(wait);
        upWobbleLift(wait);
    }

    public void upWobble(boolean wait){
        wait = Math.abs(lift.getPosition() - WobbleConfig.LIFT_UP_POS) > SERVO_POS_ERROR_BOUNDARY;
        lift.setPosition(WobbleConfig.LIFT_UP_POS);
        while(wait && isOpmodeActive() && Math.abs(lift.getPosition() - WobbleConfig.LIFT_UP_POS) > SERVO_POS_ERROR_BOUNDARY) {
            lift.setPosition(WobbleConfig.LIFT_UP_POS);
        }
        if(wait && useHardWait) {
            waitForTime(hardWaitMills/1000.0);
        }
    }
    public void downWobble(boolean wait){
        wait = wait && Math.abs(lift.getPosition() - WobbleConfig.LIFT_DOWN_POS) > SERVO_POS_ERROR_BOUNDARY;
        lift.setPosition(WobbleConfig.LIFT_DOWN_POS);
        while(wait && isOpmodeActive() && Math.abs(lift.getPosition() - WobbleConfig.LIFT_DOWN_POS) > SERVO_POS_ERROR_BOUNDARY) {
            lift.setPosition(WobbleConfig.LIFT_DOWN_POS);
        }
        if(wait && useHardWait) {
            waitForTime(hardWaitMills/1000.0);
        }
    }

    public void scClodwob(boolean wait){
        sideWClaw.setPosition(0.8);
    }
    public void SimpleOpen(boolean wait){
        wobbleservo.setPosition(WobbleConfig.OPEN_WOBBLE_POS);
    }

    public void dropWobble(boolean wait) {
        SCLift.setPosition(WobbleConfig.SECOND_LIFT_DOWN);
    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);
    }

    @Override
    public void startup() {
        super.startup();

        wobbleservo.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    @Override
    public void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);
        telemetry.addData("WOBBLE LIFT", lift.getPosition());
        telemetry.addData("WOBBLE HOLD", wobbleservo.getPosition());
    }
}
