package tech.vineyard.trim;

public class DirectTrim implements Trim {
    @Override
    public String trim(String input) {
        return input.trim();
    }
}
