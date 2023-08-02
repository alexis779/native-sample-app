package tech.vineyard.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tech.vineyard.service.model.Input;
import tech.vineyard.service.model.Parameter;

public abstract class AbstractService implements Service {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    protected Object getParameterObject(Parameter parameter) {
        try {
            return OBJECT_MAPPER.readValue(parameter.json(), parameter.klass());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected Class<?>[] getParamClasses(Input input) {
        return input.parameters()
                .stream()
                .map(Parameter::klass)
                .toArray(Class[]::new);
    }

    protected Object[] getParamObjects(Input input) {
        return input.parameters()
                .stream()
                .map(this::getParameterObject)
                .toArray();
    }
}
