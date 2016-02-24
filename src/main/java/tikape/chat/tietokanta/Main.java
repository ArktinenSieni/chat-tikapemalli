package tikape.chat.tietokanta;

import java.sql.SQLException;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.chat.tietokanta.database.Database;
import tikape.chat.tietokanta.database.KayttajaDao;
import tikape.chat.tietokanta.database.ViestiDao;
import tikape.chat.tietokanta.domain.Kayttaja;

/**
 *
 * @author mcraty
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:chat.db");
        ViestiDao viestiDao = new ViestiDao(database);
        KayttajaDao kayttajaDao = new KayttajaDao(database);

        get("/viesti", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", viestiDao.findAll());
            map.put("kayttajat", kayttajaDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        post("/viesti", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", viestiDao.findAll());
            
            String nimi = req.queryParams("kayttaja");
            String sisalto = req.queryParams("sisalto");
            
            lisaaViesti(nimi, sisalto, kayttajaDao, viestiDao);
            
            map.put("viestit", viestiDao.findAll());
            map.put("kayttajat", kayttajaDao.findAll());
            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
    }

    public static void lisaaViesti(String tunnus, String sisalto, KayttajaDao kayttajaDao, ViestiDao viestiDao) throws SQLException {
        Kayttaja kayttaja = kayttajaDao.findOrCreateByTunnus(tunnus);
        viestiDao.createViesti(kayttaja.getId(), sisalto);
    }
}
