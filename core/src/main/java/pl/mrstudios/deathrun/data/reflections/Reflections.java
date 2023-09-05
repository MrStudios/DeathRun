package pl.mrstudios.deathrun.data.reflections;

import lombok.RequiredArgsConstructor;
import pl.mrstudios.deathrun.DeathRun;

import java.io.File;
import java.lang.annotation.Annotation;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@RequiredArgsConstructor
public class Reflections {

    private final String packageName;

    public List<Class<?>> getClassesAnnotatedWith(Class<? extends Annotation> annotation) throws Exception {

        List<Class<?>> classes = new ArrayList<>();
        CodeSource codeSource = DeathRun.class.getProtectionDomain().getCodeSource();
        if (codeSource == null)
            throw new ClassNotFoundException("Unable to get classes annotated with '@" + annotation.getSimpleName() + "' because CodeSource is null.");

        try (JarFile jarFile = new JarFile(new File(codeSource.getLocation().toURI()))) {

            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {

                JarEntry entry = entries.nextElement();
                if (!entry.getName().endsWith(".class"))
                    continue;

                if (!entry.getName().startsWith(this.packageName.replace('.', '/')))
                    continue;

                Class<?> clazz = Class.forName(entry.getName().replace('/', '.').replace(".class", ""));
                if (clazz.isAnnotationPresent(annotation))
                    classes.add(clazz);

            }

        }

        return classes;

    }

}
