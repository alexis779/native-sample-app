package tech.vineyard.service;

import tech.vineyard.service.model.Input;

public interface Service {
    Object executeMethod(Input input) throws Throwable;
}
