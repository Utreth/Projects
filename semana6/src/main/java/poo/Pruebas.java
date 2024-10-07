package poo;

import java.time.LocalDateTime;

import org.json.JSONObject;

import poo.helpers.Utils;
import poo.model.Caja;
import poo.model.Cliente;
import poo.model.Envio;
import poo.model.Mercancia;
import poo.services.ClienteService;

public class Pruebas {

    public static void main(String[] args) throws Exception {

        Cliente c1 = new Cliente("01", "Juan", "calle 3a # 15.-16", "bogota", "001232");
        // Mercancia m1 = new Mercancia("0000", "ajsnd", 0, 0, 0, LocalDateTime.now(),
        // LocalDateTime.now(), "nul", c1);

        Cliente c2 = new Cliente(c1);

        c2.setNombre("juan perez");
        Cliente c3 = new Cliente("c3");
        Cliente c4 = new Cliente("andres", "avenida fundadores", "manizales", "3028765431");
        Mercancia mm = new Mercancia("1055", "juguetes", 10.0, 120.6, 30.0, LocalDateTime.now(),
                LocalDateTime.now().plusDays(1), "Fundadores", c4);

        JSONObject mercaJson = mm.toJSONObject();

        // System.out.println(mercaJson.toString(2));

        Envio envio1 = new Caja(0, 0, 0, null, 0, false, null, 0, c1, c4, null);
        // System.out.println(envio1.toJSON());

        
        
        

       

        // JSONObject json = new JSONObject(
        // "{\"ciudad\":\"Bogota\",\"direccion\":\"Ricaurte\",\"id\":\"6H9XA\",\"telefono\":\"3028765431\",\"nombre\":\"Antonio\"}");
        // Cliente c5 = new Cliente(json);

        // Mercancia m2 = new Mercancia("0000", "null", 0, 0, 0, LocalDateTime.now(),
        // LocalDateTime.now(), "null", c5);
        // JSONObject clienteJson = c4.toJSONObjetc();
        // JSONObject mercaJson2 = new JSONObject(
        // "{\"id\":\"001\",\"contenido\":\"documentos\",\"ancho\":\"10.0\",\"alto\":\"10.0\",\"largo\":\"10.0\","
        // +
        // "\"fechaHoraIngreso\":\"2017-02-26T09:10:20\",\"fechaHoraSalida\":\"2017-02-26T09:10:20\","
        // + "\"bodega\":\"bodega 1\"}");

        // System.out.println(mercaJson2.toString(2));
        // Mercancia m3 = new Mercancia(mercaJson);
        // System.out.println(m3.toString());

        // System.out.println(c1.toString());
        // System.out.println(c2.toString());
        // System.out.println(c3.toString());
        // System.out.println(c1 == c2);
        // String str = Utils.getRandomKey(8);
        // System.out.println(str);
        // System.out.println(c4);
        // System.out.println(c5);
        // System.out.println(c1.equals(c2));
        // System.out.println(c1.equals(null));
        // System.out.println(c1.equals(new String("Juan")));
        // System.out.println(c1.equals(c5));
        // ClienteService cs = new ClienteService();
        // cs.add(c1.toJSONObjetc().toString());
        // System.out.println(c1.hashCode());
        // System.out.println(c2.hashCode());

    }
}
