package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name ="TestClub")
public class Testing extends LinearOpMode {
    private DcMotor RFDrive;
    private DcMotor RBDrive;
    private DcMotor LFDrive;
    private DcMotor LBDrive;

    private DcMotor dclift;

    private DcMotor flywheel;
    private DcMotor intake;
    private Servo lift;

    private Servo cage;
    private Servo cagelift;
    private Servo wobbleservo;
    private Servo sidewobblearm;
    private Servo sideWClaw;

    @Override
    public void runOpMode() throws InterruptedException{

        flywheel = hardwareMap.get(DcMotor.class,"flywheel");
        intake = hardwareMap.get(DcMotor.class, "intake");
        lift = hardwareMap.get(Servo.class, "lift");

        RFDrive = hardwareMap.get(DcMotor.class, "rf");
        RBDrive = hardwareMap.get(DcMotor.class, "rb");
        LFDrive = hardwareMap.get(DcMotor.class, "lf");
        LBDrive = hardwareMap.get(DcMotor.class, "lb");

        dclift = hardwareMap.get(DcMotor.class," dclift");

        cage = hardwareMap.get(Servo.class, "cage");
        cagelift = hardwareMap.get(Servo.class, "cagelift");
        wobbleservo = hardwareMap.get(Servo.class, "wobbleservo");
        sidewobblearm = hardwareMap.get(Servo.class, "sidew");
        sideWClaw = hardwareMap.get(Servo.class, "sidec");


        LFDrive.setDirection(DcMotor.Direction.REVERSE);
        LBDrive.setDirection(DcMotor.Direction.REVERSE);


        waitForStart();

        while(opModeIsActive()){
            double lfPower = -gamepad1.left_stick_y;
            double lbPower = -gamepad1.left_stick_y;
            double rfPower = -gamepad1.right_stick_y;
            double rbPower = -gamepad1.right_stick_y;

            if(gamepad2.left_bumper){
                wobbleservo.setPosition(0.5f);
            } else { // will always be closed and will open as long as bumper is pressed
                wobbleservo.setPosition(1.0f);
            }


            float move = gamepad1.left_stick_y *-1;
            float mecanum = gamepad1.left_stick_x;
            float turn = gamepad1.right_stick_x;

            double LfPower = move + mecanum + turn;
            double LbPower = move - mecanum + turn;
            double RfPower = move - mecanum - turn;
            double RbPower = move + mecanum - turn;



            // dpad down = 0.0
            // dpad left = 0.25
            // dpad up = 0.5
            // dpad right = 1.0


            LFDrive.setPower(lfPower);
            LBDrive.setPower(lbPower);
            RFDrive.setPower(rfPower);
            RBDrive.setPower(rbPower);

        }


    }
}
