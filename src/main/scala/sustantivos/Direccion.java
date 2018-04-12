package sustantivos;

public enum Direccion{
    N("Norte"), S("Sur"), E("Este"), O("Oeste");

    private final String name;

    private Direccion(String s){
        this.name = s;
    }

    @Override
    public String toString() {
        return name;
    }
}