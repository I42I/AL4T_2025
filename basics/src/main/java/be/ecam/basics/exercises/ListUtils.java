package be.ecam.basics.exercises;

import java.util.List;

public class ListUtils {
    public static List<String> removeShortStrings(List<String> list, int minLen) {
        list.removeIf(s -> s.length() < minLen);
        return list;
    }
}
