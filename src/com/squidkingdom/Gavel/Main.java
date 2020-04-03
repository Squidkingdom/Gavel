package com.squidkingdom.Gavel;


import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.util.Properties;
import java.util.Scanner;

@SuppressWarnings("")

//TODO? Comment system?
public class Main {
    Properties prop = new Properties();

    public static Scanner in = new Scanner(System.in);
    public static File tokenKey = new File("Token.txt");
    public static int lastRoundStarted = 0;
    public static JDA Gavel;


    public static DiscordHook dch = new DiscordHook();
    //Configuation
    public static File properties = new File("config.properties");
    public static int roomNum = 35;
    public static String parings;
    public static String event;
    public static String token;


    public static void main(String[] args) throws LoginException, IOException {
        //If this file is missing make it
        if (properties.createNewFile()) {
            OutputStream outputStream = new FileOutputStream(properties);
            print("Created new Properties file\n ");
            Properties prop = new Properties();

            prop.setProperty("Rooms", "30");
            prop.setProperty("Pairing", "HILO");
            prop.setProperty("Event", "CX");
            prop.setProperty("Bot_Token", "");
            prop.store(outputStream, "Beta 1");

        }
        loadPrefs();
         print("Token: " + !token.isEmpty());

            Gavel = new JDABuilder(AccountType.BOT).setToken(token).build();
            DiscordHook dch = new DiscordHook();
            Gavel.addEventListener(dch);
            Gavel.getPresence().setActivity(Activity.listening("fake news"));

    }


    public static void print(String print) {
        System.out.println(print);
    }

    public static void loadPrefs() throws IOException {

        RoomManager.refreshRoomNum(roomNum);
        Properties prop = new Properties();
        InputStream inputStream = new FileInputStream(properties);
        prop.load(inputStream);
        roomNum = Integer.parseInt(prop.getProperty("Rooms", "35"));
        parings = prop.getProperty("Pairing", "HILO");
        event = prop.getProperty("Event", "CX");
        token = prop.getProperty("Bot_Token");



    }
}




