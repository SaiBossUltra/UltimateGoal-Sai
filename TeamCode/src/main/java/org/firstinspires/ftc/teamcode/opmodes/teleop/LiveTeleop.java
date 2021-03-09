package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.opmodes.LiveTeleopBase;

@TeleOp(name="Teleop Live", group="driver control")
//@Disabled
public class LiveTeleop extends LiveTeleopBase {

    private DcMotor RFDrive;
    private DcMotor RBDrive;
    private DcMotor LFDrive;
    private DcMotor LBDrive;

    private DcMotor flywheel;
    private DcMotor intake;
    private Servo lift;

    private Servo cage;
    private Servo cagelift;
    private Servo wobbleservo;

    @Override
    public void on_init() {
        robot.drive_train.setInitPos(-12.0, 6, Math.PI);
    }

    @Override
    public void on_start() {
        this.getRuntime();
        robot.drive_train.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        flywheel = hardwareMap.get(DcMotor.class,"flywheel");
        intake = hardwareMap.get(DcMotor.class, "intake");
        lift = hardwareMap.get(Servo.class, "lift");

        RFDrive = hardwareMap.get(DcMotor.class, "rf");
        RBDrive = hardwareMap.get(DcMotor.class, "rb");
        LFDrive = hardwareMap.get(DcMotor.class, "lf");
        LBDrive = hardwareMap.get(DcMotor.class, "lb");

        cage = hardwareMap.get(Servo.class, "cage");
        cagelift = hardwareMap.get(Servo.class, "cagelift");
        wobbleservo = hardwareMap.get(Servo.class, "wobbleservo");

        flywheel.setDirection(DcMotor.Direction.FORWARD);

        LFDrive.setDirection(DcMotor.Direction.REVERSE);
        LBDrive.setDirection(DcMotor.Direction.REVERSE);

        wobbleservo.setDirection(Servo.Direction.REVERSE);
        lift.setDirection(Servo.Direction.REVERSE);

        RFDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LFDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RBDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LBDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



    }

    @Override
    public void on_loop() {
        ElapsedTime elapsedtimer = new ElapsedTime();



        double flywheelpower = 0;
        if(gamepad1.b){
            flywheelpower = (gamepad1.right_trigger-gamepad1.left_trigger)*0.80;
        } else if(gamepad1.y){
            flywheelpower = (gamepad1.right_trigger-gamepad1.left_trigger)*0.83;
        } else if(gamepad1.x){
            flywheelpower = (gamepad1.right_trigger-gamepad1.left_trigger)*0.68;
        } else if(gamepad1.a){
            flywheelpower = (gamepad1.right_trigger-gamepad1.left_trigger)*0.65;
        } else {
            flywheelpower = (gamepad1.right_trigger-gamepad1.left_trigger)*0.74;
        }

        double intakePower = gamepad2.left_trigger-gamepad2.right_trigger;



        if(gamepad2.right_bumper) {
            cage.setPosition(0.0f);
        } else {
            cage.setPosition(0.35f);
        }

        if(gamepad2.b){
            cagelift.setPosition(1.0f);
        } else if(gamepad2.a){
            cagelift.setPosition(0.7f);
        }
        // I am also Sai

        if(gamepad2.left_bumper){
            wobbleservo.setPosition(0.13f);
        } else if (gamepad2.y){
            wobbleservo.setPosition(1.0f);
        }

        if(gamepad2.dpad_up) {
            lift.setPosition(0.75f);
        } else if(gamepad2.dpad_down) {
            lift.setPosition(0.0f);
        } else if(gamepad2.dpad_right){
            lift.setPosition(0.35);
        }



        flywheel.setPower(flywheelpower);
        intake.setPower(intakePower);

        telemetry.addData("Wobble Grab Power: ", gamepad2.left_bumper);
        telemetry.addData("Flywheel Speed: ", flywheelpower);
        telemetry.update();

        elapsedtimer.reset();

        if(gamepad1.right_bumper){
            while(elapsedtimer.seconds() < 0.06){
                setWheeldPower(0.4f, 0.4f, -0.4f, -0.4f);
            }
            setWheeldPower(0.0f,0.0f,0.0f,0.0f);

        }
        if(gamepad1.left_bumper){
            while(elapsedtimer.seconds() < 0.06){
                setWheeldPower(-0.4f, -0.4f, 0.4f, 0.4f);
            }
            setWheeldPower(0.0f,0.0f,0.0f,0.0f);

        }

        if(gamepad1.dpad_up){
            robot.drive_train.odo_move(-12, -2, Math.PI, 1, 1, Math.PI/360);
        }




        /// GAMEPAD 1 CONTROLS ///

        double speed_mod = 1;
//        if(gamepad1.left_bumper) {
//            speed_mod = 0.25;
//        } else if(gamepad1.right_bumper) {
//            speed_mod = 0.5;
//        }
        robot.drive_train.mechanum_drive(gamepad1.left_stick_x * speed_mod, gamepad1.left_stick_y * speed_mod, gamepad1.right_stick_x * speed_mod);




    }
    private void setWheeldPower(float lf, float lb, float rf, float rb) {

        RFDrive.setPower(rf);
        RBDrive.setPower(rb);
        LFDrive.setPower(lf);
        LBDrive.setPower(lb);
    }


    @Override
    public void on_stop() {

    }

}
