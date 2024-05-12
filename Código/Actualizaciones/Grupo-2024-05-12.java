import java.util.List;

public class Grupo {
    private String idGrupo;
    private String nombreGrupo;
    private String administrador;
    private List <Usuario> mienbros;
    private List <Gasto> gastosGrupo;

    public Grupo(String idGrupo, String nombreGrupo, String administrador, List<Usuario> mienbros, List<Gasto> gastosGrupo) {//Creamos el contructor de Grupo
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.administrador = administrador;
        this.mienbros = mienbros;
        this.gastosGrupo = gastosGrupo;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public List<Usuario> getMienbros() {
        return mienbros;
    }

    public void setMienbros(List<Usuario> mienbros) {
        this.mienbros = mienbros;
    }

    public List<Gasto> getGastosGrupo() {
        return gastosGrupo;
    }

    public void setGastosGrupo(List<Gasto> gastosGrupo) {
        this.gastosGrupo = gastosGrupo;
    }


    public void agregarGasto(Gasto gasto){//metodo para añadir gasto
        gastosGrupo.add(gasto);
    }

    public void eliminarGasto(Gasto gasto){//metodo para quitar gasto
        gastosGrupo.remove(gasto);
    }


    public double calcularSaldo() {
        double totalGastos = 0.0;

        // Calcular el total de gastos
        for (Gasto gasto : gastosGrupo) {
            totalGastos += gasto.getCantidad();//metodo que esta en gastos y devuelve la cantidad// public double getCantidad(){ return cantidad};
        }

        // Calcular la media de gastos entre los miembros del grupo
        int numUsuario=mienbros.size();

        double mediaGastos = numUsuario> 0 ? totalGastos/ numUsuario :0.0     ;

        return mediaGastos;
    }
}