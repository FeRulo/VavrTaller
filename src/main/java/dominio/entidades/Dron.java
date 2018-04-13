package dominio.entidades;

public class Dron {
    public int id;
    public Posicion p;
    public int capacidad;

    public Dron (){
        this.id = 0;
        this.p = new Posicion(0,0, Direccion.N);
        this.capacidad = 3;
    }
    public Dron(int id, Posicion p, int capacidad){
        this.id = id;
        this.p = p;
        this.capacidad = capacidad;
    }

}
