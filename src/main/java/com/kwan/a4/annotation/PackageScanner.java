package com.kwan.a4.annotation;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 * Scan a package and return classes in the package
 */

public enum PackageScanner {

    INSTANCE;

    /**
     * Search the directory and store classes in the list
     *
     * @param directory   File directory
     * @param packageName target package name
     * @param classList   List object to store classes
     */
    private void checkFileURL(File directory, String packageName,
                              List<Class<?>> classList) {

        if (directory.isDirectory()) {

            Stream.of(directory.list()).forEach(file -> {

                if (file.endsWith(".class")) {
                    try {
                        classList.add(Class.forName(packageName + '.' +
                            file.substring(0, file.length() - 6)));
                    } catch (ClassNotFoundException ex) {
                        // ignore classes that can not be found by the loader
                    }
                } else {
                    checkFileURL(new File(directory, file),
                        packageName + "." + file, classList);
                }
            });
        }
    }

    /**
     * Search the jar file and store classes in the list
     *
     * @param connection  Jar file connection
     * @param packageName target package name
     * @param classList   List object to store classes
     */
    private void checkJarFile(JarURLConnection connection,
                              String packageName, List<Class<?>> classList)
        throws ClassNotFoundException, IOException {

        JarFile jarFile = connection.getJarFile();

        for (Enumeration<JarEntry> jarEntries = jarFile.entries();
             jarEntries.hasMoreElements(); ) {

            String name = jarEntries.nextElement().getName();

            if (name.contains(".class")) {
                name = name.substring(0, name.length() - 6).replace('/', '.');

                if (name.contains(packageName))
                    classList.add(Class.forName(name));
            }
        }
    }

    /**
     * scan a package and return all class in the package
     *
     * @param packageName full package name
     * @return A list of classes in the package
     * @throws ClassNotFoundException Not able to load a class
     */
    public List<Class<?>> scan(String packageName)
        throws ClassNotFoundException {

        final List<Class<?>> classList = new ArrayList<>();

        try {

            final ClassLoader classLoader =
                Thread.currentThread().getContextClassLoader();

            if (classLoader == null)
                throw new ClassNotFoundException("Can not get class loader.");

            // check all resource in the package
            for (Enumeration<URL> enumeration =
                 classLoader.getResources(packageName.replace('.', '/'));
                 enumeration.hasMoreElements(); ) {

                URL url = enumeration.nextElement();
                URLConnection connection = url.openConnection();

                if (connection instanceof JarURLConnection) {
                    checkJarFile((JarURLConnection) connection, packageName, classList);

                } else if (connection instanceof URLConnection) {
                    checkFileURL(new File(url.getPath()), packageName, classList);

                } else {
                    throw new ClassNotFoundException(
                        packageName + " (" + url.getPath() + ") " +
                            "does not appear to be a valid package");
                }
            }
        } catch (IOException ex) {
            throw new ClassNotFoundException(
                "IOException was thrown when trying to get all resources for "
                    + packageName, ex);
        }

        return classList;
    }
}
