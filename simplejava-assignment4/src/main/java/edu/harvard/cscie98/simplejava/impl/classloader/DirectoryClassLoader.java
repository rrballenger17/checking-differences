package edu.harvard.cscie98.simplejava.impl.classloader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.harvard.cscie98.simplejava.config.Log;
import edu.harvard.cscie98.simplejava.vm.classloader.ClassLoadingException;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.classloader.VmField;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.execution.Interpreter;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ReferenceLocation;

public class DirectoryClassLoader implements VmClassLoader {

  private List<File> classpath;
  private final Map<TypeName, VmClass> classes;
  private Interpreter interpreter;

  public DirectoryClassLoader() {
    classes = new HashMap<TypeName, VmClass>();
    for (final String p : new String[] { "byte", "char", "short", "int", "long", "float", "double",
    "boolean" }) {
      final TypeName name = TypeFactory.fromBinaryName(p);
      classes.put(name, new PrimitiveVmClass(name));
    }
  }

  @Override
  public void setClassPath(final String cp) {
    this.classpath = new ArrayList<File>();
    for (final String s : cp.split(File.pathSeparator)) {
      classpath.add(new File(s));
      Log.cl("Class path entry: " + s);
    }
  }

  @Override
  public void setInterpreter(final Interpreter interpreter) {
    this.interpreter = interpreter;
  }

  @Override
  public VmClass loadClass(final TypeName className) throws ClassLoadingException {
    if (classes.containsKey(className)) {
      return classes.get(className);
    }
    for (final File f : classpath) {
      final File classFile = className.getClassFile(f);
      if (classFile.exists()) {
        Log.cl("Loading " + className);
        final BcelVmClass cls = new BcelVmClass(f, className);
        cls.parse(this);
        parseClass(cls);
        classes.put(className, cls);
        for (final VmMethod mthd : cls.getMethods()) {
          if (mthd.getName().equals("<clinit>") && mthd.getSignature().equals("()V")) {
            Log.cl("Running static init for " + className);
            interpreter.executeMethod(mthd, null);
            Log.cl("Static init for " + className + " exited");
          }
        }
        return cls;
      }
    }
    throw new NoClassDefFoundError(className.getBinaryName());
  }

  private void parseClass(final VmClass cls) throws ClassLoadingException {
    final Map<String, VmMethod> methods = new HashMap<String, VmMethod>();
    for (final VmMethod m : cls.getMethods()) {
      methods.put(m.getName() + m.getSignature(), m);
    }
  }

  @Override
  public List<ReferenceLocation> getStaticReferenceLocations() {
    final List<ReferenceLocation> refs = new ArrayList<ReferenceLocation>();
    for (final TypeName key : classes.keySet()) {
      final VmClass cls = classes.get(key);
      if (!(cls instanceof PrimitiveVmClass)) {
        for (final VmField fld : cls.getStaticFields()) {
          if (fld.isReference()) {
            refs.add(new StaticReferenceLocation(cls, fld));
          }
        }
      }
    }
    return refs;
  }

  @Override
  public Set<VmClass> getLoadedClasses() {
    final Set<VmClass> set = new HashSet<VmClass>();
    for (final VmClass cls : classes.values()) {
      if (!(cls instanceof PrimitiveVmClass)) {
        set.add(cls);
      }
    }
    return set;
  }

}
