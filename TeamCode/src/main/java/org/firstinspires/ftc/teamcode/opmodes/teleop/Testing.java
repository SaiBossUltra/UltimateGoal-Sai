package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name ="Test")
public class Testing extends LinearOpMode {
    private DcMotor lfdrive;
    private DcMotor lbdrive;
    private DcMotor rfdrive;
    private DcMotor rbdrive;

    private Servo servo;

    @Override
    public void runOpMode() throws InterruptedException{
        lfdrive = hardwareMap.get(DcMotor.class,"lf");
        lbdrive = hardwareMap.get(DcMotor.class,"lb");
        rfdrive = hardwareMap.get(DcMotor.class,"rf");
        rbdrive = hardwareMap.get(DcMotor.class,"rb");

        servo = hardwareMap.get(Servo.class, "servo1");

        lfdrive.setDirection(DcMotor.Direction.REVERSE);
        lbdrive.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        double lfPower = -gamepad1.left_stick_y;
        double lbPower = -gamepad1.left_stick_y;
        double rfPower = -gamepad1.right_stick_y;
        double rbPower = -gamepad1.right_stick_y;

        if(gamepad1.left_bumper){
            // if this bumper is pressed
            servo.setPosition(0.5); // 0.0 —> 1.0
        } else if (gamepad1.right_bumper) {
            servo.setPosition(0);
        }



        lfdrive.setPower(lfPower);
        lbdrive.setPower(lbPower);
        rfdrive.setPower(rfPower);
        rbdrive.setPower(rbPower);

    }
}
