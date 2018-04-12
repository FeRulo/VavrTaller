package dominio.entidades;

public class Dron {
    public int id;
    public Posicion p;

    public Dron (){
        this.id = 0;
        this.p = new Posicion(0,0, Direccion.N);
    }
    public Dron(int id, Posicion p){
        this.id = id;
        this.p = p;
    }

}
