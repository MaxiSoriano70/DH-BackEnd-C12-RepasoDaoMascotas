package dao.implementacion;

import dao.IDao;
import db.H2Connection;
import model.Mascota;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.Locale;

public class MascotaIDaoH2 implements IDao<Mascota> {
    public static Logger LOGGER = Logger.getLogger(MascotaIDaoH2.class);
    public static String SQLINSERT = "INSERT INTO MASCOTAS VALUES (DEFAULT, ?, ?, ? ,?, ?);";
    public static String SQL_SELECT_N0MBRE = "SELECT * FROM MASCOTAS WHERE NOMBRE = ?;";

    @Override
    public Mascota registrar(Mascota mascota) {
        Connection connection = null;
        Mascota mascotaRetornar = null;
        try{
            /*CONEXION*/
            connection = H2Connection.getConnection();
            /*DESACTIVAMOS EL AUTOCOMMIT*/
            connection.setAutoCommit(false);

            /*PASAMOS LA CUNSULTA Y SUS PARAMETROS PARA REGISTRAR*/
            PreparedStatement preparedStatement = connection.prepareStatement(SQLINSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, mascota.getNombre());
            preparedStatement.setString(2, mascota.getDireccion());
            preparedStatement.setDate(3, Date.valueOf(mascota.getFechaDeNacimiento()));
            preparedStatement.setInt(4, mascota.getEdad());
            preparedStatement.setString(5, mascota.getPropietario());

            /*EJECUTAMOS LA CONSULTA*/
            preparedStatement.executeUpdate();

            /*VERIFICAMOS SI SE HIZO EL INSERT*/
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                mascotaRetornar = new Mascota(id,
                        mascota.getNombre(),
                        mascota.getDireccion(),
                        mascota.getFechaDeNacimiento(),
                        mascota.getPropietario()
                );
            }

            LOGGER.info("Mascotas persistida o registrada = "+ mascotaRetornar);

            /*EJECUTA EL COMMIT*/
            connection.commit();
            /*ACTIVAR COMMIT*/
            connection.setAutoCommit(true);
        }catch (Exception e){
            if(connection!=null){
                try{
                    connection.rollback();
                }catch (SQLException ex) {
                    LOGGER.error(ex.getMessage());
                    ex.printStackTrace();
                }
            }
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return mascotaRetornar;
    }

    @Override
    public Mascota buscarPorCampo(String campo) {
        Connection connection = null;
        Mascota mascotaRetornar = null;

        try {
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_N0MBRE);
            String auxCampo = campo.toLowerCase();
            preparedStatement.setString(1, auxCampo);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String nombre = resultSet.getString(2);
                String direccion = resultSet.getString(3);
                LocalDate fecha_de_nacimiento = resultSet.getDate(4).toLocalDate();
                String propietario = resultSet.getString(6);
                mascotaRetornar = new Mascota(id, nombre, direccion, fecha_de_nacimiento, propietario);
            }


            LOGGER.info("Mascotas encontrada = "+ mascotaRetornar);


        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return mascotaRetornar;
    }
}
