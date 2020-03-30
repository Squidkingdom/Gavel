package com.squidkingdom.Gavel;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.Pair;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.*;
import net.dv8tion.jda.api.hooks.*;
import org.omg.PortableInterceptor.ACTIVE;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

@SuppressWarnings("")

//TODO? Comment system?
public class Main {
    public static TeamManager manager = new TeamManager();
    public static Scanner in = new Scanner(System.in);
    public static final int roomNum = 30;
    public static File tokenKey = new File("Token.txt");
    public static int lastRoundStarted = 0;
    public static final boolean hookBot = true;
    public static TextChannel boundChannel;
    public static JDA Gavel;

    public static DiscordHook dch = new DiscordHook();

    public static void main(String[] args) throws LoginException, IOException {
        boolean running = true;

        if (hookBot) {
            //If this file is missing make it
            if (tokenKey.createNewFile()) {
                print("File Created");
                print("PLease put the client secret in the file in the project directory and run this again");
                System.exit(69);
            }
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
    }




