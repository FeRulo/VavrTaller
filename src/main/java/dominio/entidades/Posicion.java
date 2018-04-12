package dominio.entidades;

public class Posicion {

    public int x;
    public int y;
    public Direccion d;

    public Posicion (){
        this.x = 0;
        this.y = 0;
        this.d = Direccion.N;
    }

    public Posicion (int x, int y, Direccion d){
        this.x = x;
        this.y = y;
        this.d = d;
    }

}
