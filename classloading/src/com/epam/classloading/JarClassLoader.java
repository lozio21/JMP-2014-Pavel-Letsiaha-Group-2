package com.epam.classloading;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarClassLoader extends ClassLoader {

    private String pathToJarFile;

    public JarClassLoader(ClassLoader parent) {
        super(parent);
    }

    public JarClassLoader(String pathToJarFile) {
        this(JarClassLoader.class.getClassLoader());
        this.pathToJarFile = pathToJarFile;
    }

    public Class loadClass(String className) throws ClassNotFoundException {
        return findClass(className);
    }

    public Class findClass(String className) {
        byte classByte[];
        Class result = null;

        result = findLoadedClass(className);
        if (result != null) {
            System.out.println("The Class " + className + "has been found in cache.");
            return result;
        }

        try {
            return findSystemClass(className);
        } catch (ClassNotFoundException e) {
            System.err.println("The Class " + className + " has not been loaded.");
        }

        try (JarFile jarFile = new JarFile(pathToJarFile)){
            JarEntry jarEntry = jarFile.getJarEntry(className.replace(".", "/") + ".class");
            InputStream is = jarFile.getInputStream(jarEntry);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextValue = is.read();
            while (-1 != nextValue) {
                byteStream.write(nextValue);
                nextValue = is.read();
            }

            classByte = byteStream.toByteArray();
            result = defineClass(className, classByte, 0, classByte.length, null);
            System.out.println("The class " + className + " has been loaded.");
            return result;
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }
}