package utils;

// FIX: Counters now start from values based on existing users to avoid duplicate IDs
// For a full fix, integrate counter persistence into DataStore. For now,
// we derive starting counts from existing records at runtime.
public class IDGenerator {

    public static String generateStudentId() {
        long count = DataStore.getInstance().getUsers().values().stream()
                .filter(u -> u.getRole().equals("STUDENT")).count();
        return "242-" + (1000 + count + 1);
    }

    public static String generateTeacherId() {
        long count = DataStore.getInstance().getUsers().values().stream()
                .filter(u -> u.getRole().equals("TEACHER")).count();
        return "TEA-" + (500 + count + 1);
    }
}