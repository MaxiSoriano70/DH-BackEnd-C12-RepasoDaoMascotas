package dao;

public interface IDao <T>{
    T registrar(T t);
    T buscarPorCampo(String campo);
}
