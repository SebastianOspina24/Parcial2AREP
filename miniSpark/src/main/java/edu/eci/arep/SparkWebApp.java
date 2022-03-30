package edu.eci.arep;

import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SparkWebApp {

    public static void main(String[] args) {
        port(getPort());
        get("/log",(req, res)->{
            res.type("application/json");
            return callBack("log",req.queryParams("value"));
        });
        get("/asin",(req, res)->{
            res.type("application/json");
            return callBack("asin",req.queryParams("value"));
        });
        
    }
    static String callBack(String service,String param) throws IOException{
        URL url = new URL(getdestination(service)+service+"?value="+param);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
        return sb.toString();
    }
    static String getdestination(String a){
        return a=="log"?"http://ec2-44-202-22-195.compute-1.amazonaws.com:5000/":"http://ec2-54-234-160-165.compute-1.amazonaws.com:5000/";
    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5001;
        //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
