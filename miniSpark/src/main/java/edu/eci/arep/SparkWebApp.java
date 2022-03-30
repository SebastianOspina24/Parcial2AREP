package edu.eci.arep;

import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;


public class SparkWebApp {

    static Queue<String> variable =  new LinkedList<String>();

    public static void main(String[] args) {
        variable.add(System.getProperty("maquina1"));
        variable.add(System.getProperty("maquina2"));
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
        URL url = new URL("http://"+getdestination()+":5000/"+service+"?value="+param);
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
    static String getdestination(){
        String temp = variable.peek();
        variable.add(temp);
        return temp;
    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5001;
        //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
