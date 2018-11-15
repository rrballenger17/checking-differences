package edu.harvard.cscie98.simplejava.impl.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.MethodGen;

import edu.harvard.cscie98.simplejava.impl.objectmodel.ArrayTypeDescriptorImpl;
import edu.harvard.cscie98.simplejava.impl.objectmodel.ObjectTypeDescriptorImpl;
import edu.harvard.cscie98.simplejava.vm.VmInternalError;
import edu.harvard.cscie98.simplejava.vm.classloader.ClassLoadingException;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.classloader.VmField;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ArrayTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectTypeDescriptor;

public class BcelVmClass implements VmClass {

  private final TypeName className;
  private JavaClass jc;
  private ClassGen cg;
  private final List<VmMethod> methods;
  private final List<VmField> fields;
  private final List<VmField> staticFields;
  private final List<Object> staticValues;
  private final File classFile;
  private VmClass superClass;
  private ObjectTypeDescriptor classDescriptor;
  private ArrayTypeDescriptor arrayDescriptor;

  public BcelVmClass(final File classFile, final TypeName className) {
    this.className = className;
    this.classFile = classFile;
    this.methods = new ArrayList<VmMethod>();
    this.fields = new ArrayList<VmField>();
    this.staticFields = new ArrayList<VmField>();
    this.staticValues = new ArrayList<Object>();
  }

  @Override
  public void parse(final VmClassLoader classLoader) throws ClassLoadingException {
    if (classFile == null) {
      return;
    }
    final File f = className.getClassFile(classFile);
    try {
      final InputStream in = new FileInputStream(f);
      final ClassParser parser = new ClassParser(in, className.getSimpleName() + ".class");
      jc = parser.parse();
      if (!jc.getClassName().equals("java.lang.Object")) {
        final TypeName spr = TypeFactory.fromBinaryName(jc.getSuperclassName());
        superClass = classLoader.loadClass(spr);
      }
    } catch (ClassFormatException | IOException e) {
      throw new ClassLoadingException(e);
    }
    cg = new ClassGen(jc);

    parseMethods();
    parseFields();
    classDescriptor = new ObjectTypeDescriptorImpl(fields, className, this);
    arrayDescriptor = new ArrayTypeDescriptorImpl(classDescriptor, className);
  }

  private void parseMethods() {
    for (final Method m : jc.getMethods()) {
      final MethodGen mg = new MethodGen(m, jc.getClassName(), cg.getConstantPool());
      methods.add(new BcelVmMethod(mg, this));
    }
  }

  private void parseFields() {
    if (superClass != null) {
      for (final VmField fld : ((BcelVmClass) superClass).fields) {
        if (!fld.isStatic()) {
          fields.add(fld);
        }
      }
    }
    for (final Field f : jc.getFields()) {
      if (f.isStatic()) {
        staticFields.add(new BcelVmField(f, this));
      } else {
        fields.add(new BcelVmField(f, this));
      }
    }
    for (final VmField fld : staticFields) {
      staticValues.add(fld.getType().getDefaultValue());
    }
  }

  @Override
  public List<VmMethod> getMethods() {
    return methods;
  }

  @Override
  public ObjectTypeDescriptor getObjectTypeDescriptor() {
    return classDescriptor;
  }

  @Override
  public ArrayTypeDescriptor getArrayTypeDescriptor() {
    return arrayDescriptor;
  }

  @Override
  public TypeName getName() {
    return className;
  }

  @Override
  public String getFileName() {
    return cg.getFileName();
  }

  @Override
  public VmField getField(final String fieldName, final TypeName fieldSignature) {
    for (final VmField fld : fields) {
      if (fld.getName().equals(fieldName) && fld.getType().equals(fieldSignature)
          && fld.getDefiningClass().equals(this)) {
        return fld;
      }
    }
    return null;
  }

  @Override
  public VmMethod getMethod(final String methodName, final String methodSignature) {
    for (final VmMethod mthd : methods) {
      if (mthd.getName().equals(methodName) && mthd.getSignature().equals(methodSignature)) {
        return mthd;
      }
    }
    return null;
  }

  @Override
  public VmClass getSuperClass() {
    return superClass;
  }

  @Override
  public void setStaticField(final String fieldName, final TypeName fieldSignature,
      final Object value) {
    final int idx = getStaticIndex(fieldName, fieldSignature);
    staticValues.set(idx, value);
  }

  @Override
  public Object getStaticField(final String fieldName, final TypeName fieldSignature) {
    final int idx = getStaticIndex(fieldName, fieldSignature);
    return staticValues.get(idx);
  }

  @Override
  public List<VmField> getStaticFields() {
    return staticFields;
  }

  public void replaceMethod(final MethodGen mthd) {
    final BcelVmMethod newMthd = new BcelVmMethod(mthd, this);
    int idx = -1;
    for (int i = 0; i < methods.size(); i++) {
      final VmMethod m = methods.get(i);
      if (m.getName().equals(newMthd.getName())
          && m.getSignature().equals(newMthd.getSignature())) {
        idx = i;
      }
    }
    if (idx > -1) {
      methods.set(idx, newMthd);
    } else {
      throw new VmInternalError("Can't replace method " + newMthd);
    }
  }

  private int getStaticIndex(final String fieldName, final TypeName sig) {
    for (int i = 0; i < staticFields.size(); i++) {
      final VmField fld = staticFields.get(i);
      if (fld.getName().equals(fieldName) && fld.getType().equals(sig)) {
        return i;
      }
    }
    throw new VmInternalError(
        "No static variable " + fieldName + " " + sig + " found in class " + className);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((className == null) ? 0 : className.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final BcelVmClass other = (BcelVmClass) obj;
    if (className == null) {
      if (other.className != null) {
        return false;
      }
    } else if (!className.equals(other.className)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    String str = "Class " + className;
    for (int i = 0; i < staticFields.size(); i++) {
      final VmField fld = staticFields.get(i);
      str += "\n   " + fld.getType() + " " + fld.getName() + ": " + staticValues.get(i);
    }
    return str;
  }

}
