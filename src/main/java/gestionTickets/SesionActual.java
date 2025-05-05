package gestionTickets;

public class SesionActual {
    private static int usuarioId;
    private static String nombreUsuario;
    private static String rol;

    public static void iniciarSesion(int id, String nombre, String rolUsuario) {
        usuarioId = id;
        nombreUsuario = nombre;
        rol = rolUsuario;
    }

    public static int getUsuarioId() {
        return usuarioId;
    }

    public static String getNombreUsuario() {
        return nombreUsuario;
    }

    public static String getRol() {
        return rol;
    }

    public static void cerrarSesion() {
        usuarioId = 0;
        nombreUsuario = null;
        rol = null;
    }
}
