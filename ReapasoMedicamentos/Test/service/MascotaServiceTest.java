package service;

import dao.implementacion.MascotaIDaoH2;
import model.Mascota;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MascotaServiceTest {

    @BeforeAll
    static void crearTablas(){
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/mascotasrepaso;INIT=RUNSCRIPT from 'create.sql'","sa","sa");
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
    }
    public static Logger LOGGER = Logger.getLogger(MascotaServiceTest.class);

    @Test
    @DisplayName("Testear que una Mascota persiste en la BD")
    void testerMedicamentoEnBD(){
        Mascota mascota = new Mascota("margo","calle false 123",LocalDate.of(2019, 6, 3),"MAxi Soriano");
        MascotaService mascotaService = new MascotaService(new MascotaIDaoH2());
        Mascota mascotaPersistida = mascotaService.resgistrarMascota(mascota);
        assertNotNull(mascotaPersistida);
    }

    @Test
    @DisplayName("Testear que una mascota pasandole el nombre")
    void testearMascotaPorNombre(){
        MascotaService mascotaService = new MascotaService(new MascotaIDaoH2());
        String nombre = "margo";
        Mascota mascotaEncontrada = mascotaService.buscarPorCampo(nombre);

        assertEquals("margo", mascotaEncontrada.getNombre());
    }

    @Test
    @DisplayName("Testear que una mascota pasandole el nombre")
    void testearMascotaPorNombreNoEncontrado(){
        MascotaService mascotaService = new MascotaService(new MascotaIDaoH2());
        String nombre = "zeus";
        Mascota mascotaEncontrada = mascotaService.buscarPorCampo(nombre);

        assertNull(mascotaEncontrada);
    }
}