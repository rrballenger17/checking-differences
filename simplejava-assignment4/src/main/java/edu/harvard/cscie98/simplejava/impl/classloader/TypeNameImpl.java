package edu.harvard.cscie98.simplejava.impl.classloader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import edu.harvard.cscie98.simplejava.vm.VmInternalError;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public class TypeNameImpl implements TypeName {

  private static Map<String, TypeName> cache;
  private final String binaryName;
  private final boolean isArray;

  static {
    cache = new HashMap<String, TypeName>();
  }

  private TypeNameImpl(final String binaryName, final boolean isArray) {
    this.binaryName = binaryName;
    this.isArray = isArray;
  }

  private static TypeName getType(final String type, final boolean isArray) {
    final String key = isArray ? "[" + type : type;
    if (!cache.containsKey(key)) {
      cache.put(key, new TypeNameImpl(type, isArray));
    }
    return cache.get(key);
  }

  public static TypeName fromBinaryName(final String type, final boolean isArray) {
    if (type.endsWith("[]")) {
      return getType(type.substring(0, type.indexOf("[")), true);
    }
    return getType(type, isArray);
  }

  public static TypeName fromClassFileName(final String type, final boolean isArray) {
    return getType(type.replaceAll("/", "."), isArray);
  }

  public static TypeName fromDescriptor(final String type) {
    int dims = 0;
    String typeString = type;
    while (typeString.startsWith("[")) {
      dims++;
      typeString = typeString.substring(1);
    }
    if (typeString.startsWith("L")) {
      typeString = typeString.substring(1, typeString.lastIndexOf(";"));
      typeString = typeString.replaceAll("/", ".");
      return getType(typeString, dims > 0);
    } else {
      return TypeNameImpl.fromPrimitiveChar(typeString.charAt(0), dims > 0);
    }
  }

  @Override
  public String getDescriptor() {
    String desc = "";
    if (isArray) {
      desc = "[";
    }
    switch (binaryName) {
    case "byte":
      return desc + "B";
    case "char":
      return desc + "C";
    case "double":
      return desc + "D";
    case "float":
      return desc + "F";
    case "int":
      return desc + "I";
    case "long":
      return desc + "L";
    case "short":
      return desc + "S";
    case "boolean":
      return desc + "Z";
    case "void":
      return desc + "V";
    default:
      return desc + "L" + binaryName.replaceAll("\\.", "/") + ";";
    }
  }

  private static TypeName fromPrimitiveChar(final char token, final boolean isArray) {
    switch (token) {
    case 'B':
      return getType("byte", isArray);
    case 'C':
      return getType("char", isArray);
    case 'D':
      return getType("double", isArray);
    case 'F':
      return getType("float", isArray);
    case 'I':
      return getType("int", isArray);
    case 'J':
      return getType("long", isArray);
    case 'S':
      return getType("short", isArray);
    case 'Z':
      return getType("boolean", isArray);
    default:
      throw new VmInternalError("Unknown primitive type character: " + token);
    }
  }

  public static TypeName fromArrayTypeCode(final byte typeCode) {
    switch (typeCode) {
    case 4:
      return getType("boolean", false);
    case 8:
      return getType("byte", false);
    case 5:
      return getType("char", false);
    case 7:
      return getType("double", false);
    case 6:
      return getType("float", false);
    case 10:
      return getType("int", false);
    case 11:
      return getType("long", false);
    case 9:
      return getType("short", false);
    default:
      throw new VmInternalError("Unknown array type code: " + typeCode);
    }
  }

  @Override
  public int compareTo(final TypeName o) {
    return binaryName.compareTo(((TypeNameImpl) o).binaryName);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((binaryName == null) ? 0 : binaryName.hashCode());
    result = prime * result + (isArray ? 1231 : 1237);
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
    final TypeNameImpl other = (TypeNameImpl) obj;
    if (binaryName == null) {
      if (other.binaryName != null) {
        return false;
      }
    } else if (!binaryName.equals(other.binaryName)) {
      return false;
    }
    if (isArray != other.isArray) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    String str = binaryName;
    if (isArray) {
      str += "[]";
    }
    return str;
  }

  @Override
  public File getClassFile(final File directory) {
    return new File(directory, binaryName.replaceAll("\\.", "/") + ".class");
  }

  @Override
  public String getSimpleName() {
    return binaryName.substring(binaryName.lastIndexOf(".") + 1) + (isArray ? "[]" : "");
  }

  @Override
  public String getBinaryName() {
    return binaryName + (isArray ? "[]" : "");
  }

  @Override
  public String getClassFileName() {
    return binaryName.replaceAll("\\.", "/");
  }

  @Override
  public Object getDefaultValue() {
    if (!isArray) {
      if (binaryName.equals("byte") || binaryName.equals("boolean") || binaryName.equals("int")
          || binaryName.equals("long") || binaryName.equals("short") || binaryName.equals("char")) {
        return 0;
      }
      if (binaryName.equals("double") || binaryName.equals("float")) {
        return 0.0;
      }
    }
    return HeapPointer.NULL;
  }

  @Override
  public boolean isArray() {
    return isArray;
  }

}
