package dominio.servicios;

import io.vavr.collection.List;

import java.io.*;

public class ServidorArchivos {
    public static List<String> importarInstrucciones(String ruta) throws FileNotFoundException, IOException {
        List<String> instrucciones = List.empty();
        File archivo = new File(ruta);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        String linea;
        while ((linea = br.readLine()) != null)
            instrucciones = instrucciones.append(linea);
        return instrucciones;
    }

    public static void exportarReporte(String reporte, String ruta) throws IOException{
        File archivo = new File(ruta);
        BufferedWriter bw;
        if (archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(reporte);
        } else {
            bw = new BufferedWriter(
                    new FileWriter(archivo));
            bw.write(reporte);
        }
        bw.close();
    }

}
