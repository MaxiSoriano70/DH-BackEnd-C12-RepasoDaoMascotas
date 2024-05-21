package service;

import dao.IDao;
import model.Mascota;

public class MascotaService {
    private IDao<Mascota> mascotaIDao;

    public MascotaService(IDao<Mascota> mascotaIDao){
        this.mascotaIDao = mascotaIDao;
    }

    public Mascota resgistrarMascota(Mascota mascota){
        return mascotaIDao.registrar(mascota);
    }

    public Mascota buscarPorCampo(String nombre){
        return mascotaIDao.buscarPorCampo(nombre);
    }
}
