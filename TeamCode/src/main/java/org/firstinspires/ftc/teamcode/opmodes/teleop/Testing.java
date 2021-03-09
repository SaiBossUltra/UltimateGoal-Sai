package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name ="Test")
public class Testing extends LinearOpMode {
    private DcMotor lfdrive;
    private DcMotor lbdrive;
    private DcMotor rfdrive;
    private DcMotor rbdrive;

    @Override
    public void runOpMode() throws InterruptedException{
        lfdrive = hardwareMap.get(DcMotor.class,"lf");
        lbdrive = hardwareMap.get(DcMotor.class,"lb");
        rfdrive = hardwareMap.get(DcMotor.class,"rf");
        rbdrive = hardwareMap.get(DcMotor.class,"rb");

        lfdrive.setDirection(DcMotor.Direction.REVERSE);
        lbdrive.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        double lfPower = -gamepad1.left_stick_y;
        double lbPower = -gamepad1.left_stick_y;
        double rfPower = -gamepad1.right_stick_y;
        double rbPower = -gamepad1.right_stick_y;

        lfdrive.setPower(lfPower);
        lbdrive.setPower(lbPower);
        rfdrive.setPower(rfPower);
        rbdrive.setPower(rbPower);

    }
}
