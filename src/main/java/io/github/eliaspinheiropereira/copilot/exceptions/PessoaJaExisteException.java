package io.github.eliaspinheiropereira.copilot.exceptions;

public class PessoaJaExisteException extends RuntimeException{
    public PessoaJaExisteException(String message) {
        super(message);
    }
}
