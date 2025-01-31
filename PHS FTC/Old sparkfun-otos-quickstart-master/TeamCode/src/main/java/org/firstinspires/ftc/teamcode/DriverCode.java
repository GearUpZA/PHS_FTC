package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;

@TeleOp(name="DriverCode")
public class DriverCode extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        DcMotor verticalSlide = hardwareMap.dcMotor.get("verticalSlide");
        DcMotor horizontalSlide = hardwareMap.dcMotor.get("horizontalSlide");
        CRServo placeGripper = hardwareMap.get(CRServo.class,"placeGripper");
        CRServo placeArm = hardwareMap.get(CRServo.class, "placeArm");
        CRServo intakeGripper = hardwareMap.get(CRServo.class, "intakeGripper");
        CRServo intakeElbow = hardwareMap.get(CRServo.class, "intakeElbow");
        CRServo intakeWrist = hardwareMap.get(CRServo.class, "intakeWrist");

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;
            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            if(gamepad2.b){
                intakeGripper.setPower(-1);
            }
            else if(gamepad2.a){
                intakeGripper.setPower(1);
            }
            else{
                intakeGripper.setPower(0);
            }
            if(gamepad2.x){
                placeGripper.setPower(1);
            }
            else if(gamepad2.y){
                placeGripper.setPower(-1);
            }
            else{
                placeGripper.setPower(0);
            }

            if(gamepad2.dpad_left){
                intakeElbow.setPower(1);
            }
            else if(gamepad2.dpad_right){
                intakeElbow.setPower(-1);
            }
            else{
                intakeElbow.setPower(0);
            }
           if(gamepad2.left_stick_y>0){
              horizontalSlide.setPower(1);
           }
           else if (gamepad2.left_stick_y<0) {
           horizontalSlide.setPower(-1);
           }
           else{
               horizontalSlide.setPower(0);
           }

           if(gamepad2.right_stick_y > 0){
                verticalSlide.setPower(1);
            }
            else if(gamepad2.right_stick_y<0){
                verticalSlide.setPower(-1);
            }
            else {
                verticalSlide.setPower(0);
            }

            if(gamepad2.dpad_up){
                intakeWrist.setPower(1);
            }
            else if(gamepad2.dpad_down){
                intakeWrist.setPower(-1);
            }
            else {
                intakeWrist.setPower(0);

            }


        }
    }
}