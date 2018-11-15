package edu.harvard.cscie98.simplejava.impl.objectmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.harvard.cscie98.simplejava.config.HeapParameters;
import edu.harvard.cscie98.simplejava.config.Log;
import edu.harvard.cscie98.simplejava.config.SimpleJavaOutOfScopeException;
import edu.harvard.cscie98.simplejava.impl.memory.heap.HeapImpl;
import edu.harvard.cscie98.simplejava.vm.VmInternalError;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.classloader.VmField;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.execution.Interpreter;
import edu.harvard.cscie98.simplejava.vm.memory.Heap;
import edu.harvard.cscie98.simplejava.vm.memory.MemoryManager;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ArrayTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectHeader;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ReferenceLocation;

public class ObjectBuilderImpl implements ObjectBuilder {

  private VmClassLoader classLoader;
  private MemoryManager mm;
  private HeapImpl heap;
  private final Map<String, HeapObject> internTable;
  private final Map<HeapPointer, HeapPointer> creationMap;
  private Interpreter interpreter;

  public ObjectBuilderImpl() {
    internTable = new HashMap<String, HeapObject>();
    creationMap = new HashMap<HeapPointer, HeapPointer>();
  }

  @Override
  public void setMemoryManager(final MemoryManager mm) {
    this.mm = mm;
  }

  @Override
  public void setInterpreter(final Interpreter interpreter) {
    this.interpreter = interpreter;
  }

  @Override
  public void setClassLoader(final VmClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  @Override
  public void setHeap(final Heap heap) {
    this.heap = (HeapImpl) heap;
  }

  @Override
  public HeapObject createArray(final TypeName elementType, final int length) {
    if (elementType.isArray()) {
      throw new SimpleJavaOutOfScopeException("Multidimensional arrays not supported");
    }
    final VmClass cls = classLoader.loadClass(elementType);
    final ArrayTypeDescriptor desc = cls.getArrayTypeDescriptor();
    final long size = (HeapParameters.BYTES_IN_WORD * (length + ObjectHeader.HEADER_SIZE_WORDS));
    final HeapPointer allocated = mm.allocate(size);
    if (allocated == HeapPointer.NULL) {
      throw new VmInternalError("Out of memory");
    }
    Log.alloc("Allocated " + size + " bytes at " + allocated);
    creationMap.put(allocated, allocated);
    final HeapObject obj = new HeapObjectImpl(allocated, desc, length);
    final Object defaultValue = desc.getTypeName().getDefaultValue();
    for (int i = 0; i < length; i++) {
      obj.setValueAtOffset(i, defaultValue);
    }
    heap.addObject(obj);
    obj.getHeader().setWord(ObjectHeader.ARRAY_LENGTH_WORD, length);
    return creationMap.remove(allocated).dereference();
  }

  @Override
  public HeapObject createObject(final TypeName type, final boolean callDefaultConstructor) {
    if (type.isArray()) {
      throw new VmInternalError("createObject can't be used to create arrays");
    }
    final VmClass cls = classLoader.loadClass(type);
    final ObjectTypeDescriptor desc = cls.getObjectTypeDescriptor();
    final long size = desc.getSize()
        + (ObjectHeader.HEADER_SIZE_WORDS * HeapParameters.BYTES_IN_WORD);
    final HeapPointer allocated = mm.allocate(size);
    if (allocated == HeapPointer.NULL) {
      throw new VmInternalError("Out of memory");
    }
    Log.alloc("Allocated " + size + " bytes at " + allocated);
    final HeapObject obj = new HeapObjectImpl(allocated, desc);
    creationMap.put(obj.getAddress(), obj.getAddress());
    final List<VmField> fields = desc.getFields();
    for (int i = 0; i < fields.size(); i++) {
      obj.setValueAtOffset(i, fields.get(i).getType().getDefaultValue());
    }
    heap.addObject(obj);
    if (callDefaultConstructor) {
      final VmMethod constructor = cls.getMethod("<init>", "()V");
      interpreter.executeMethod(constructor, obj.getAddress());
    }
    // Get from the map in case the object was moved during a GC triggered by
    // the constructor
    return creationMap.remove(obj.getAddress()).dereference();
  }

  @Override
  public HeapObject internString(final String str) {
    if (!internTable.containsKey(str)) {
      HeapObject obj = createObject(TypeFactory.fromBinaryName("java.lang.String"), false);
      creationMap.put(obj.getAddress(), obj.getAddress());
      final byte[] bytes = str.getBytes();
      final HeapObject byteArray = createArray(TypeFactory.fromDescriptor("B"), bytes.length);
      creationMap.put(byteArray.getAddress(), byteArray.getAddress());
      for (int i = 0; i < bytes.length; i++) {
        byteArray.setValueAtOffset(i, (int) bytes[i]);
      }
      obj = creationMap.remove(obj.getAddress()).dereference();
      obj.setValueAtOffset(0, creationMap.remove(byteArray.getAddress()));
      internTable.put(str, obj);
    }
    return internTable.get(str);
  }

  @Override
  public List<ReferenceLocation> getInternTableReferences() {
    final List<ReferenceLocation> refs = new ArrayList<ReferenceLocation>();
    for (final String key : internTable.keySet()) {
      refs.add(new InternTableReferenceLocation(key, internTable));
    }
    for (final HeapPointer key : creationMap.keySet()) {
      refs.add(new MapReferenceLocation(key, creationMap));
    }
    return refs;
  }

}
