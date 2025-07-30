package MainCode; //testing

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class ClawTestingTeleop extends LinearOpMode {
    private Servo claw = null;

    final double CLAW_IN   = 0.5; //0.675
    final double CLAW_OUT  = 0.5; // 1

    double clawPosition = CLAW_OUT;


    @Override
    public void runOpMode() {
        claw = hardwareMap.get(Servo.class, "claw");

        waitForStart();

        while (opModeIsActive()){
            if(gamepad1.dpad_up){
                clawPosition = CLAW_IN;
            } else if(gamepad1.dpad_down){
                clawPosition = CLAW_OUT;
            }

            if(clawPosition == CLAW_IN){
                claw.setPosition(CLAW_IN);
            } else if (clawPosition == CLAW_OUT){
                claw.setPosition(CLAW_OUT);
            }
        }
    }
}
