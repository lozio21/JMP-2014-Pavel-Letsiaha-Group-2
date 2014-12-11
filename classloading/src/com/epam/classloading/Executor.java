package com.epam.classloading;

import com.epam.classloading.modules.IModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Executor {

    private static String pathToJar = "/modules/modules.jar";

    private static final String CLASS_EXTENSION = ".class";

    public static void main(String[] args) {

        List<String> commands = new ArrayList();
        commands.add("Exit");
        JarClassLoader classLoader = new JarClassLoader(pathToJar);

            try (JarFile jarFile = new JarFile(pathToJar)){
                Enumeration entries = jarFile.entries();

                while(entries.hasMoreElements())

                {
                    JarEntry jarEntry = (JarEntry) entries.nextElement();
                    if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(CLASS_EXTENSION)) {
                        continue;
                    }

                    String className = jarEntry.getName().substring(0, jarEntry.getName().length() - CLASS_EXTENSION.length());
                    className = className.replace('/', '.');

                    commands.add(className);
                }

            } catch (IOException ex)
            {
                System.err.println("Unexpected error: " + ex.getMessage());
            }

            for (String command : commands) {
                System.out.println(commands.indexOf(command) + ". " + command);
            }

            for (; ; ) {
                try {
                    Integer i = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                    if (i == 0) {
                        System.exit(0);
                    }
                    //reload
                    //JarClassLoader classLoader = new JarClassLoader(pathToJar);
                    String className = commands.get(i);
                    Class clazz = Class.forName(className, true, classLoader);
                    ((IModule) clazz.newInstance()).execute();
                    Thread.sleep(5000);
                } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                    System.out.println("Please enter correct command.");
                } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException |InterruptedException ex) {
                    System.out.println("Unexpected error: " + ex.getMessage());
                }
            }
        }
}