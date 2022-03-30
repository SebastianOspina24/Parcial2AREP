package edu.eci.arep;

import static spark.Spark.*;
import java.lang.Math;

public class SparkWebApp {

    public static void main(String[] args) {
        port(getPort());
        get("/log",(req, res)->{
            res.type("application/json");
            return "{\"operation\":\"log\",\"input\":"+Double.valueOf(req.queryParams("value"))+            
                ",\"output\":"+Math.log10(Double.valueOf(req.queryParams("value")))+"}";
        });
        get("/asin",(req, res)->{
            res.type("application/json");
            return "{\"operation\":\"asin\",\"input\":"+Double.valueOf(req.queryParams("value"))+            
                ",\"output\":"+Math.asin(Double.valueOf(req.queryParams("value")))+"}";
        });
    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
        //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
