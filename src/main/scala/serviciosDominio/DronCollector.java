package serviciosDominio;


import sustantivos.Dron;
import sustantivos.Instruccion;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class DronCollector implements Collector<Instruccion, Dron, Dron> {
    @Override
    public Supplier<Dron> supplier() {
        return Dron::new;
    }

    @Override
    public BiConsumer<Dron, Instruccion> accumulator() {
        return (Dron d, Instruccion i) -> {
            d.p = ServicioPosicion.cambiarPosicion(d.p, i);
        };
    }

    @Override
    public BinaryOperator<Dron> combiner() {
        return (Dron d1, Dron d2) -> {
            //Sin implementar retorna un dron vacío
            return new Dron();
        };
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
