package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="test2")
public class Test2 extends LinearOpMode {

    private DcMotor lfmotor;
    private DcMotor lbmotor;
    private DcMotor rfmotor;
    private DcMotor rbmotor;

    @Override
    public void runOpMode() throws InterruptedException{

        lfmotor = hardwareMap.get(DcMotor.class,"lf");
        lbmotor = hardwareMap.get(DcMotor.class,"lb");
        rfmotor = hardwareMap.get(DcMotor.class,"rf");
        rbmotor = hardwareMap.get(DcMotor.class,"rb");

        lfmotor.setDirection(DcMotor.Direction.REVERSE);
        lbmotor.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        double lfPower = -gamepad1.left_stick_y;
        double lbPower = -gamepad1.left_stick_y;
        double rfPower = -gamepad1.right_stick_y;
        double rbPower = -gamepad1.right_stick_y;

        lfmotor.setPower(lfPower);
        lbmotor.setPower(lbPower);
        rfmotor.setPower(rfPower);
        rbmotor.setPower(rbPower);
    }
}
