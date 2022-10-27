import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;

import com.sun.faces.config.AnnotationScanner;

public class HackAnnotationProvider extends AnnotationScanner {
   public HackAnnotationProvider(final ServletContext sc) {
      super(sc);
   }
   
   @Override
   public Map<Class<? extends Annotation>, Set<Class<?>>> getAnnotatedClasses(final Set<URL> urls) {
      final Set<URL> hacked = new HashSet<URL>(urls.size());
      for(URL url : urls) {
         if("vfs".equals(url.getProtocol())) {
            try {
               VirtualFile virtualFile = VFS.getChild(url);
               VirtualFile metaInf = virtualFile.getParent();
               VirtualFile jarFile = metaInf.getParent();
               VirtualFile jarContainer = jarFile.getParent();
               hacked.add(
                  new File(jarContainer.getPhysicalFile(),
                     jarFile.getName() + File.pathSeparatorChar +
                     metaInf.getName() + File.pathSeparatorChar + virtualFile.getName()
                  ).toURI().toURL()
               );
            } catch(Exception e) {
            }
         }
      }
      return super.getAnnotatedClasses(hacked);
   }
}