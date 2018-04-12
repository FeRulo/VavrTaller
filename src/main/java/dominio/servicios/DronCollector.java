package dominio.servicios;


import dominio.entidades.Dron;
import dominio.entidades.Instruccion;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class DronCollector implements Collector<Instruccion, Dron, Dron> {

    private Dron dron0 = null;

    public DronCollector(Dron dron0) {
        this.dron0 = dron0;
    }

    public DronCollector() {
        this.dron0 = new Dron();
    }

    @Override
    public Supplier<Dron> supplier() {
        return () -> this.dron0;
    }

    @Override
    public BiConsumer<Dron, Instruccion> accumulator() {
        return (Dron d, Instruccion i) -> {
            d.p = ServidorPosicion.cambiarPosicion(d.p, i);
        };
    }

    @Override
    public BinaryOperator<Dron> combiner() {
        return (Dron d1, Dron d2) -> d1;
    }

    @Override
    public Function<Dron, Dron> finisher() {
        return (Dron d1) -> {
            return d1;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.UNORDERED);
    }
}
