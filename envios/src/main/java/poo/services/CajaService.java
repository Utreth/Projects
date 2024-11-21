package poo.services;


import poo.model.Cliente;
import poo.model.Envio;

public class CajaService extends EnvioService {

    public CajaService(Class<? extends Envio> subclase, Service<Cliente> clientes) throws Exception {

        super(subclase, clientes);
    }


   
}
