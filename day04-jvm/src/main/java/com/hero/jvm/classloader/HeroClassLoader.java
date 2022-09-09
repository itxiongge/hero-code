package com.hero.jvm.classloader;

import java.io.*;

public class HeroClassLoader extends ClassLoader {

    private String classpath;

    public HeroClassLoader(String classpath) {
        this.classpath = classpath;
    }



    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            //输入流，通过类的全限定名称加载文件到字节数组
            byte[] classDate = getData(name);
            if (classDate != null) {
                //defineClass方法将字节数组数据 转为 字节码对象
                return defineClass(name, classDate, 0, classDate.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    //加载类的字节码数据
    private byte[] getData(String className) throws IOException {
        String path = classpath + File.separatorChar +
                className.replace('.', File.separatorChar) + ".class";
        try (InputStream in = new FileInputStream(path);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
