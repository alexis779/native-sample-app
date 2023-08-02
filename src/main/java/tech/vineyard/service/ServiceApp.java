package tech.vineyard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import tech.vineyard.service.model.Input;

import java.io.FileInputStream;

public class ServiceApp {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws Throwable {
        final Service service = new LambdaMetafactoryService<>();

        final Input input = OBJECT_MAPPER.readValue(new FileInputStream(args[0]), Input.class);
        final Object output = service.executeMethod(input);

        System.out.println(output);
    }
}
