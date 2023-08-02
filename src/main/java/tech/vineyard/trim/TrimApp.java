package tech.vineyard.trim;

public class TrimApp {
    public static void main(String[] args) {
        final String input = args[0];
        final String output = new MethodTrim().trim(input);
        System.out.println(String.format("'%s' -> '%s'", input, output));
    }
}
