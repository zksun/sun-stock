package com.sun.stock.core.common.internal;


import com.sun.stock.core.common.IOHandler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Created by zksun on 16-1-8.
 */
public final class IOUtil {

    public static InputStream getFileInputStream(String path) throws FileNotFoundException {
        if (StringUtil.isBlank(path)) {
            throw new NullPointerException();
        }

        File file = new File(path);
        if (!file.exists() || !file.canRead() || file.isDirectory()) {
            throw new IllegalArgumentException("file not exists, or is a directory.");
        }

        return new FileInputStream(file);
    }

    public static <T> T getDataFromFileByBuffer(File file, IOHandler<T, byte[]> handler, int bufSize) throws FileNotFoundException {
        if (null == file || !file.exists() || !file.canRead() || file.isDirectory()) {
            throw new IllegalArgumentException("file not exists, or is a directory.");
        }

        return getDataFromFileByBuffer(new FileInputStream(file), handler, bufSize);
    }

    public static <T> T getDataFromFileByBuffer(String path, IOHandler<T, byte[]> handler, int bufSize) throws FileNotFoundException {
        InputStream fileInputStream = getFileInputStream(path);

        return getDataFromFileByBuffer(fileInputStream, handler, bufSize);
    }

    private static <T> T getDataFromFileByBuffer(InputStream fileInputStream, IOHandler<T, byte[]> handler, int bufSize) throws FileNotFoundException {
        byte[] buf = new byte[bufSize];
        T t = null;
        try {
            while (-1 != fileInputStream.read(buf)) {
                if (null != handler) {
                    handler.execute((T) buf, null);
                }
            }
        } catch (IOException e) {
            PlatformSupport.throwException(e);
        } finally {
            if (null != fileInputStream) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    PlatformSupport.throwException(e);
                }
            }
        }
        return t;
    }

    public static <T> T getFileByLine(String path, IOHandler<T, String> handler) {
        if (StringUtil.isBlank(path)) {
            throw new NullPointerException();
        }

        File file = new File(path);

        if (!file.exists() || !file.canRead() || file.isDirectory()) {
            throw new IllegalArgumentException("file not exists, or is a directory.");
        }


        return getFileByLine(file, handler);
    }

    public static <T> T getFileByLine(File file, IOHandler<T, String> handler) {
        if (null == file) {
            throw new NullPointerException();
        }

        if (!file.exists() || !file.canRead() || file.isDirectory()) {
            throw new IllegalArgumentException("file not exists, or is a directory.");
        }

        BufferedReader reader = null;
        T t = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tmp;
            while (null != (tmp = reader.readLine())) {
                if (null != handler) {
                    handler.execute((T) tmp, null);
                }
            }
        } catch (IOException e) {
            PlatformSupport.throwException(e);
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    PlatformSupport.throwException(e);
                }
            }
        }

        return t;
    }

    public static List<File> getFilesByPath(String path) {
        if (StringUtil.isBlank(path)) {
            throw new NullPointerException();
        }
        File directory = new File(path);

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("not directory");
        }
        return Arrays.asList(directory.listFiles());
    }

    public static List<File> getFilesByDirectory(File directory) {
        if (null == directory) {
            throw new NullPointerException();
        }

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("not directory");
        }
        return Arrays.asList(directory.listFiles());
    }

    public static File getFileByDirectory(File directory, String fileName) {
        if (null == directory || StringUtil.isBlank(fileName)) {
            throw new NullPointerException();
        }
        if (!directory.isDirectory()) {

            throw new IllegalArgumentException("not directory");
        }
        List<File> files = getFilesByDirectory(directory);
        if (null != files && !files.isEmpty()) {
            for (File file : files) {
                if (file.isDirectory() && file.getName().equals(fileName)) {
                    return file;
                }
            }
        }
        return null;
    }

    public static InputStream getInputStreamByClassLoader(ClassLoader loader, String resourceName) {
        if (null == loader || StringUtil.isBlank(resourceName)) {
            throw new NullPointerException();
        }
        return loader.getResourceAsStream(resourceName);
    }

    public static URL getStreamByClassLoader(ClassLoader loader, String resourceName) {
        if (null == loader || StringUtil.isBlank(resourceName)) {
            throw new NullPointerException();
        }
        return loader.getResource(resourceName);
    }

    public static Enumeration<URL> getStreamsByClassLoader(ClassLoader loader, String resourceName) throws IOException {
        if (null == loader || StringUtil.isBlank(resourceName)) {
            throw new NullPointerException();
        }
        return loader.getResources(resourceName);
    }

    public static Enumeration<URL> getStreamsByDefaultClassLoader(String resourceName) throws IOException {
        if (StringUtil.isBlank(resourceName)) throw new NullPointerException();
        return getStreamsByClassLoader(SystemUtil.getCurrentClassLoader(), resourceName);
    }

    public static URL getStreamByDefaultClassLoader(String resourceName) {
        if (StringUtil.isBlank(resourceName)) throw new NullPointerException();
        return getStreamByClassLoader(SystemUtil.getCurrentClassLoader(), resourceName);
    }

    public static InputStream getInputStreamByDefaultClassLoader(String resourceName) {
        if (StringUtil.isBlank(resourceName)) throw new NullPointerException();
        return getInputStreamByClassLoader(SystemUtil.getCurrentClassLoader(), resourceName);
    }

    public static InputStream cloneInputStream(InputStream inputStream) throws IOException {
        if (null == inputStream || inputStream.available() < 1) {
            throw new IllegalArgumentException();
        }
        inputStream.mark(0);
        byte[] buf = new byte[inputStream.available()];
        inputStream.read(buf);
        inputStream.reset();
        return new BufferedInputStream(new ByteArrayInputStream(buf));
    }

    public static Iterator<URL> getResources(String resourceName, Class callingClass, boolean aggregate) throws IOException {
        AggregateIterator<URL> iterator = new AggregateIterator<URL>();
        iterator.addEnumeration(getStreamsByDefaultClassLoader(resourceName));
        if (!iterator.hasNext() || aggregate) {
            iterator.addEnumeration(getStreamsByClassLoader(IOUtil.class.getClassLoader(), resourceName));
        }

        if (!iterator.hasNext() || aggregate) {
            ClassLoader cl = callingClass.getClassLoader();

            if (cl != null) {
                iterator.addEnumeration(cl.getResources(resourceName));
            }
        }

        if (!iterator.hasNext() && (resourceName != null) && ((resourceName.length() == 0) || (resourceName.charAt(0) != '/'))) {
            return getResources('/' + resourceName, callingClass, aggregate);
        }

        return iterator;
    }

    static class AggregateIterator<E> implements Iterator<E> {
        LinkedList<Enumeration<E>> enums = new LinkedList<Enumeration<E>>();
        Enumeration<E> cur = null;
        E next = null;
        Set<E> loaded = new HashSet<E>();

        public AggregateIterator<E> addEnumeration(Enumeration<E> e) {
            if (e.hasMoreElements()) {
                if (null == cur) {
                    cur = e;
                    next = e.nextElement();
                    loaded.add(next);
                } else {
                    enums.add(e);
                }
            }
            return this;
        }

        @Override
        public boolean hasNext() {
            return (null != next);
        }

        @Override
        public E next() {
            if (null != next) {
                E prev = next;
                next = loadNext();
                return prev;
            } else {
                throw new NoSuchElementException();
            }
        }

        private Enumeration<E> determineCurrentEnumeration() {
            if (cur != null && !cur.hasMoreElements()) {
                if (enums.size() > 0) {
                    cur = enums.removeLast();
                } else {
                    cur = null;
                }
            }
            return cur;
        }

        private E loadNext() {
            if (null != determineCurrentEnumeration()) {
                E tmp = cur.nextElement();
                int loadedSize = loaded.size();
                while (loaded.contains(tmp)) {
                    tmp = loadNext();
                    if (null == tmp || loaded.size() > loadedSize) {
                        break;
                    }
                }
                if (null != tmp) {
                    loaded.add(tmp);
                }
                return tmp;
            }
            return null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void write(byte[] data, OutputStream output)
            throws IOException {
        if (data != null) {
            output.write(data);
        }
    }

}
