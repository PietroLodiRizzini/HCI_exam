/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

import DBAccess.NavegacionDAOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Navegacion;
import model.Session;
import model.User;

/**
 *
 * @author pietro
 */
public class AppInfo {
    private static User user;    
    private static int hits;
    private static int faults;
    
    public static final boolean DEBUG = true;
    public static final String DEBUG_USERNAME = "debuguser";
    public static final String DEBUG_PASSWORD = "DebugPwd+001";
    public static final String DEBUG_EMAIL = "debug@mail.es";
    
    /**
     * Allows to avoid logging in every time when DEBUG is true.
     */
    public static void initDebug() {
        hits = 0;
        faults = 0;
        
        if(! AppInfo.DEBUG)
            return;
        
        Navegacion n = null;
        try {
            n = Navegacion.getSingletonNavegacion();           
        } catch (NavegacionDAOException e) {
            e.printStackTrace();
            return;
        }
        
        
        /*Session s = new Session(LocalDateTime.of(2022, 05, 01, 0, 0), 10, 3);
        try {
            n.getUser(DEBUG_USERNAME).addSession(s);
        } catch (NavegacionDAOException ex) {
            ex.printStackTrace();
        }*/
        
        //User u = n.loginUser(DEBUG_USERNAME, DEBUG_PASSWORD);
        
        if(n.exitsNickName(DEBUG_USERNAME))
            return;
        
        System.out.println("Register user");
        
        try {
            n.registerUser(DEBUG_USERNAME, DEBUG_EMAIL, DEBUG_PASSWORD, LocalDate.of(2000, 1, 1));
        } catch (NavegacionDAOException ex) {
            ex.printStackTrace();
            return;
        }
        
        
    }

    public static void setUser(User user) {
        AppInfo.user = user;
    }

    public static User getUser() {
        return user;
    }
    
    public static boolean isLoggedIn() {
        return user != null;
    }
    
    public static Navegacion getSingletonNavegacion() {
        try {
            return Navegacion.getSingletonNavegacion();
        } catch (NavegacionDAOException ex) {
            ex.printStackTrace();            
        }
        return null;
    }
    
    public static void storeSession() {
        System.out.println("Store session: " + hits + " hits, " + faults + " faults");
        try {
            if(user == null)
                return;
            
        Session s = new Session(LocalDateTime.now(), AppInfo.getHits(), AppInfo.getFaults());
            user.addSession(s);
        } catch (NavegacionDAOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static int getHits() {
        return hits;
    }

    public static void incrementHits() {
        AppInfo.hits += 1;
    }

    public static int getFaults() {
        return faults;
    }

    public static void incrementFaults() {
        AppInfo.faults += 1;
    }
}
