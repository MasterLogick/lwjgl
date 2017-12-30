public enum Orientation {
    NORTH, EAST, SOUTH, WEST;
    private static Orientation ORIENTATION = NORTH;

    public static String getOrientationS() {
        return new String[]{"NORTH", "EAST", "SOUTH", "WEST"}[ORIENTATION.getOrientation()];
    }

    public static int getOrientation() {
        return ORIENTATION.ordinal();
    }

    public static void increment() {
        ORIENTATION.setOrientation(ORIENTATION.getOrientation() + 1);
    }

    public static void decrement() {
        ORIENTATION.setOrientation(ORIENTATION.getOrientation() - 1);
    }

    public static void setOrientation(int orientation) {
        ORIENTATION = Orientation.values()[orientation >= 0 ? orientation % 4 : 4 - Math.abs(orientation)];
    }
}
