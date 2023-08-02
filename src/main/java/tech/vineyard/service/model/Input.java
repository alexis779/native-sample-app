package tech.vineyard.service.model;

import java.util.List;

public record Input(Class<?> outputClass, Parameter current, String methodName, List<Parameter> parameters) {
}
