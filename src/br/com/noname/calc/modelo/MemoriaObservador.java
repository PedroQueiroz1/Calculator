package br.com.noname.calc.modelo;

@FunctionalInterface
public interface MemoriaObservador {

 void valorAlterado(String novoValor);
}
