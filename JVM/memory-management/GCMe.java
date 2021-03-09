import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class GCMe {
    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long addressOf(Object obj) throws Exception {
        Object[] array = new Object[]{obj};

        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        switch (addressSize) {
            case 4:
                objectAddress = unsafe.getInt(array, baseOffset);
                break;
            case 8:
                objectAddress = unsafe.getLong(array, baseOffset);
                break;
            default:
                throw new Error("unsupported address size: " + addressSize);
        }

        return (objectAddress);
    }


    public static void main(String... args) throws Exception {
        for (int i = 0; i < 32000; i++) {
            Object largeObject = new LargeObj144bytes();
            long address = addressOf(largeObject);
            System.out.println(address);
        }


        //Verify address works - should see the characters in the array in the output
        //printBytes(address, 31);

    }

    public static void printBytes(long objectAddress, int num) {
        for (long i = 0; i < num; i++) {
            int cur = unsafe.getByte(objectAddress + i);
            System.out.print((char) cur);
        }
        System.out.println();
    }

    static class LargeObj144bytes {
        long data;
        long __;
        long ___;
        long ____;
        long _____;
        long ______;
        long _______;
        long ________;
        long _________;
        long __________;
        long ___________;
        long ____________;
        long _____________;
        long ______________;
        long _______________;
        long _________________;
        long __________________;
        long ___________________;
    }
}

