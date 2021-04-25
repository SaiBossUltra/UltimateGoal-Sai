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

    private DcMotor dclift;

    private DcMotor flywheel;
    private DcMotor intake;
    private Servo lift;
    private Servo SCLift;

    private Servo cage;

    private Servo wobbleservo;

    private Servo sideWClaw;

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

        SCLift = hardwareMap.get(Servo.class, "sclift");


        RFDrive = hardwareMap.get(DcMotor.class, "rf");
        RBDrive = hardwareMap.get(DcMotor.class, "rb");
        LFDrive = hardwareMap.get(DcMotor.class, "lf");
        LBDrive = hardwareMap.get(DcMotor.class, "lb");

        dclift = hardwareMap.get(DcMotor.class," dclift");

        cage = hardwareMap.get(Servo.class, "cage");

        wobbleservo = hardwareMap.get(Servo.class, "wobbleservo");

        sideWClaw = hardwareMap.get(Servo.class, "scgrab");

        flywheel.setDirection(DcMotor.Direction.FORWARD);

        LFDrive.setDirection(DcMotor.Direction.REVERSE);
        LBDrive.setDirection(DcMotor.Direction.REVERSE);

        //wobbleservo.setDirection(Servo.Direction.REVERSE);

        lift.setDirection(Servo.Direction.REVERSE);
        sideWClaw.setDirection(Servo.Direction.REVERSE);
        SCLift.setDirection(Servo.Direction.REVERSE);

        RFDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LFDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RBDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LBDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        dclift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    @Override
    public void on_loop() {
        ElapsedTime elapsedtimer = new ElapsedTime();

        double xLength = robot.drive_train.get_X() - (-12); //Get the base length of the Triangle
        double theta = Math.atan(60/xLength); //Find theta from the x value of the robots position
        double compTheta = (Math.PI/2) - theta; //Use the complementary angle
        double PosXTurnRadians = Math.PI-compTheta; //Change directions based on if its on the left side or right side of the high goal
        double NegXTurnRadians = Math.PI+compTheta; //" "

        if(gamepad1.dpad_down){
            if(xLength>0){
                //Turn Clockwise by turnRadians
                robot.drive_train.odo_move(robot.drive_train.get_X(), robot.drive_train.get_Y(), PosXTurnRadians, 1.0, 1, Math.PI/180);
            }else if(xLength<0){
                //Turn Counter Clockwise by turnRadians
                robot.drive_train.odo_move(robot.drive_train.get_X(), robot.drive_train.get_Y(), NegXTurnRadians, 1.0, 1, Math.PI/180);
            } else{
                robot.drive_train.odo_move(robot.drive_train.get_X(), robot.drive_train.get_Y(), Math.PI, 1.0, 1, Math.PI/180);
            }
        }


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
            flywheelpower = (gamepad1.right_trigger-gamepad1.left_trigger)*0.72;
        }

        double intakePower = gamepad2.left_trigger-gamepad2.right_trigger;

        double dcliftPower = -gamepad2.left_stick_y;
        double dcextraPower = -gamepad2.right_stick_y;



        while(gamepad2.right_bumper){
                cage.setPosition(0.185f);//close
                sleep(410); // give time to go to position
                cage.setPosition(0.35f);//open
                sleep(410);
        }



//        if(gamepad2.right_bumper) {
//            cage.setPosition(0.0f);
//        } else {
//            cage.setPosition(0.35f);
//        }



        if(gamepad2.left_bumper){
            wobbleservo.setPosition(0.6f);
        } else { // will always be closed and will open as long as bumper is pressed
            wobbleservo.setPosition(1.0f);
        }

        if(gamepad2.dpad_up) {
            lift.setPosition(0.75f);
        } else if(gamepad2.dpad_down) {
            lift.setPosition(0.0f);
        } else if(gamepad2.dpad_right){
            lift.setPosition(0.35);
        }

        if(gamepad1.dpad_left) {
            SCLift.setPosition(1.0f);
        } else if(gamepad1.dpad_right) {
            SCLift.setPosition(0.0f);
        }



        if(gamepad2.a) {
            sideWClaw.setPosition(0.8);
        } else if (gamepad2.b){
            sideWClaw.setPosition(0.0);
        }



        flywheel.setPower(flywheelpower);
        intake.setPower(intakePower);

        dclift.setPower(dcliftPower);
        dclift.setPower(dcextraPower);


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

//        if(gamepad1.dpad_up){
//            robot.drive_train.odo_move(-9, -6, Math.PI, 1, 1, Math.PI/180);
//        }





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
