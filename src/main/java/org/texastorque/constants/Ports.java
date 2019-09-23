package org.texastorque.constants;

public class Ports {

    private static boolean isSwerve = true;

    // DriveBase // first one is true, second is false
    // robot front left = 1, front right = 2, back left = 3, back right = 4
    public static final int TRANS_1 = isSwerve ? 1 : 0;
    // public static final int TRANS_2 = isSwerve ? 2 : 0;
    // public static final int TRANS_3 = isSwerve ? 3 : 0;
    // public static final int TRANS_4 = isSwerve ? 4 : 0;

    public static final int ROT_1 = isSwerve ? 1 : 0;
    // public static final int ROT_2 = isSwerve ? 6 : 0;
    // public static final int ROT_3 = isSwerve ? 7 : 0;
    // public static final int ROT_4 = isSwerve ? 8 : 0;

    // ============ drivebase rotation encoders ===========

    // db = drivebase, rot = rotation
    public static final int DB_ROT_1_A = 0;
    public static final int DB_ROT_1_B = 1;

    // public static final int DB_ROT_2_A = 0;
    // public static final int DB_ROT_2_B = 0;
    
    // public static final int DB_ROT_3_A = 0;
    // public static final int DB_ROT_3_B = 0;
    
    // public static final int DB_ROT_4_A = 0;
    // public static final int DB_ROT_4_B = 0;

    // Sensors

    public static final int RELAY = 0; // needs to be in one of the relay ports (0-3)
}