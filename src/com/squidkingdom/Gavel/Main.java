package com.squidkingdom.Gavel;


import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

@SuppressWarnings("")

//TODO? Comment system?
public class Main {
    Properties prop = new Properties();

    public static Scanner in = new Scanner(System.in);
    public static File tokenKey = new File("Token.txt");
    public static int lastRoundStarted = 0;
    public static final boolean hookBot = true;
    public static JDA Gavel;
    public static File properties = new File("config.properties");

    public static DiscordHook dch = new DiscordHook();

    public static void main(String[] args) throws LoginException, IOException {


        boolean running = true;

        if (hookBot) {
            //If this file is missing make it
            if(properties.canRead() && properties.canWrite()){

            }
           //
            Scanner fileInputStream = new Scanner(tokenKey);
            String token = fileInputStream.nextLine();
            print("Token: " + token);

            Gavel = new JDABuilder(AccountType.BOT).setToken(token).build();
            DiscordHook dch = new DiscordHook();
            Gavel.addEventListener(dch);
            Gavel.getPresence().setActivity(Activity.listening("fake news"));

        }}



        public static void print (String print){
            System.out.println(print);
        }

        public static void loadPrefs(){

        }
    }




