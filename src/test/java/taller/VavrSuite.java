package taller;

import dominio.entidades.Direccion;
import dominio.entidades.Dron;
import dominio.entidades.Instruccion;
import dominio.entidades.Posicion;
import dominio.servicios.DistribuidorAlmuerzos;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.stream.Stream;

import static dominio.servicios.DistribuidorAlmuerzosVavr.*;
import static dominio.servicios.ServidorArchivos.exportarReporte;
import static dominio.servicios.ServidorArchivos.importarInstrucciones;
import static dominio.servicios.ServidorRutas.*;
import static dominio.servicios.ServidorPosicion.posicionToString;
import static io.vavr.API.Success;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(fullyQualifiedNames = "DistribuidorAlmuerzos")
public class VavrSuite {

    @Test
    public void validacionFallidaEntregarTresPedidos(){
        List<String> pedidos = List.of("AAAAI","AAAAI","AAAAI","AAAAI");
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N),3);
        String reporte = reportarEntregasDronVavr(dron, pedidos);
        assertEquals("== Reporte de entregas ==\n" +
                "El Número de Rutas Excede la capacidad del drón\n",reporte);
    }

    @Test
    public void validacionEntregarTresPedidos(){
        List<String> pedidos = List.of("AAAAI","AAAAD","AAAAI");
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N),3);
        String reporte = reportarEntregasDronVavr(dron, pedidos);
        assertEquals("== Reporte de entregas ==\n" +
                "(0,4) Dirección Oeste\n" +
                "(-4,4) Dirección Norte\n" +
                "(-4,8) Dirección Oeste\n",reporte);
    }
    @Test
    public void validacionEntregarTresPedidosSaliendoseLímites(){
        List<String> pedidos = List.of("AAAAI","AAAAD","AAAAAAAAI");
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N),3);
        String reporte = reportarEntregasDronVavr(dron, pedidos);
        assertEquals("== Reporte de entregas ==\n" +
                "(0,4) Dirección Oeste\n" +
                "(-4,4) Dirección Norte\n" +
                "El dron se ha devuelto a la posición (-4,4) Dirección Norte porque:\n" +
                "\t-La Posición resultante:[(-4,12) Dirección Oeste] está fuera del límite de cuadras\n",reporte);
    }

    @Test
    public void validacionEntregarPrimerPedidoError(){
        List<String> pedidos = List.of("AAAAAAAAAAAAAAAI","AAAAD","AAAAAAAAI");
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N),3);
        String reporte = reportarEntregasDronVavr(dron, pedidos);
        assertEquals("== Reporte de entregas ==\n" +
                "El dron se ha devuelto a la posición (0,0) Dirección Norte porque:\n" +
                "\t-La Posición resultante:[(0,15) Dirección Oeste] está fuera del límite de cuadras\n" +
                "(0,4) Dirección Este\n" +
                "(8,4) Dirección Norte\n",reporte);
    }

    @Test
    public void validacionEntregarNingúnPedido(){
        List<String> pedidos = List.empty();
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N),3);
        String reporte = reportarEntregasDronVavr(dron, pedidos);
        assertEquals("== Reporte de entregas ==\n" +
                "No hay rutas que ejecutar\n",reporte);
    }

    @Test
    public void leerArchivoYReportar(){
        Try<List<String>> instrucciones = importarInstrucciones("src/main/resources/inPruebas.txt");
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N),3);
        Try<String> reporte = instrucciones.flatMap(l->Try.of(()->reportarEntregasDronVavr(dron,l)));
        assertEquals(reporte, Success("== Reporte de entregas ==\n" +
                        "El Número de Rutas Excede la capacidad del drón\n"
                ));
    }

    @Test
    public void leerArchivoReportarYArchivar(){
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N),3);
        Try<String> resultado = importarInstrucciones("src/main/resources/inPruebas.txt")
                .flatMap(instrucciones -> Try.of(()->reportarEntregasDronVavr(dron, instrucciones))
                        .flatMap(reporte ->exportarReporte(reporte,"src/main/resources/outPruebas.txt")
                        ))
                .recover(FileNotFoundException.class, "Especificación de ruta inválida");
        assertEquals("Escritura exitosa",resultado.get());
    }

    @Test
    public void leerArchivoFalsoReportarYArchivar(){
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N),3);
        Try<String> resultado = importarInstrucciones("src/main/resources/inFalso.txt")
                .flatMap(instrucciones -> Try.of(()->reportarEntregasDronVavr(dron, instrucciones))
                .flatMap(reporte ->exportarReporte(reporte,"src/main/resources/outPruebas.txt"))
                )
                .recover(FileNotFoundException.class, "Especificación de ruta inválida");
        assertEquals("Especificación de ruta inválida", resultado.get());
    }

    @Test
    public void leerArchivoMaloReportarYArchivar(){
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N),3);
        Try<String> resultado = importarInstrucciones("src/main/resources/inMalas.txt")
                .flatMap(instrucciones -> Try.of(()->reportarEntregasDronVavr(dron, instrucciones))
                        .flatMap(reporte ->exportarReporte(reporte,"src/main/resources/outPruebasMalas.txt"))
                )
                .recover(FileNotFoundException.class, "Especificación de ruta inválida");
        assertEquals("Escritura exitosa",resultado.get());
    }

    @Test
    public void probarRevertirRuta(){
        String ruta = "AAAIAAAAAAAAAADAAAIAAAADAAIA";
        Dron dron = new Dron(0, new Posicion(0,0,Direccion.N),3);
        assertEquals("(-15,8) Dirección Oeste",DistribuidorAlmuerzos.entregarPedido(dron, ruta));
        ruta = revertirRuta(ruta);
        assertEquals("(0,0) Dirección Norte",DistribuidorAlmuerzos.entregarPedido(dron, ruta));
    }

}
