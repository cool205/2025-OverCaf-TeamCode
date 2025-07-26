package MainCode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="OCETeamCodeTeleop", group="Linear OpMode")
//Comment out @Disabled to put it into Driver Station OPMode List
//@Disabled
public class OCETeamCodeTeleop extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor armMotor = null;

    //Time Control Variables
    public double tick = 0;
    public double armTicksPerDegree = 0; // change this number

    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftDrive = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightDrive = hardwareMap.get(DcMotor.class, "backRightMotor");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            double max;
            double limit = 0.7;

            double axial   = -gamepad1.left_stick_y;  // pushing stick forward gives negative value
            double yaw     =  gamepad1.right_stick_x;
            double lateral = 0;

            if (gamepad1.left_stick_x == 0){
                if (gamepad1.left_trigger > 0){
                    lateral = -1 * gamepad1.left_trigger;
                } else if (gamepad1.right_trigger > 0){
                    lateral = gamepad1.right_trigger;
                }
            } else {
                lateral = gamepad1.left_stick_x;
            }

            double frontLeftPower  = axial + lateral + yaw;
            double frontRightPower = axial - lateral - yaw;
            double backLeftPower   = axial - lateral + yaw;
            double backRightPower  = axial + lateral - yaw;

            max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
            max = Math.max(max, Math.abs(backLeftPower));
            max = Math.max(max, Math.abs(backRightPower));

            if (max > limit) {
                frontLeftPower  = frontLeftPower  / max * limit;
                frontRightPower = frontRightPower / max * limit;
                backLeftPower   = backLeftPower   / max * limit;
                backRightPower  = backRightPower  / max * limit;
            }

            frontLeftDrive.setPower(frontLeftPower);
            frontRightDrive.setPower(frontRightPower);
            backLeftDrive.setPower(backLeftPower);
            backRightDrive.setPower(backRightPower);

            tick += 1;
        }
    }
}