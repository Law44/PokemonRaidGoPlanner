package com.example.raidgoplanner;


import java.util.ArrayList;

public class User {
    public String usuario;
    public String password;
    public String equipo;
    public String email;
    public String id;
    public ArrayList<String> amigos;

    public User () {

    }

    public User (String user, String team, String correo, ArrayList<String> lista, String id){
        this.usuario = user;
        this.equipo = team;
        this.email = correo;
        this.amigos = lista;
        this.id = id;
    }

    public String getId(){
        return this.id;
    }
}
