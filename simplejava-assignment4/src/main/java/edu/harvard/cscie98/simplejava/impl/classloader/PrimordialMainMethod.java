package edu.harvard.cscie98.simplejava.impl.classloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.bcel.Constants;

import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmConstantPool;
import edu.harvard.cscie98.simplejava.vm.classloader.VmExceptionHandler;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;

public class PrimordialMainMethod implements VmMethod {

  private final List<VmExceptionHandler> exceptionHandlers;

  public PrimordialMainMethod() {
    this.exceptionHandlers = new ArrayList<VmExceptionHandler>();
    exceptionHandlers.add(new PrimordialExceptionHandler(0, 3, 3));
  }

  @Override
  public VmClass getDefiningClass() {
    return null;
  }

  @Override
  public byte[] getInstructionStream() {
    return new byte[] { 0, 0, 0, (byte) Constants.RETURN };
  }

  @Override
  public String getSignature() {
    return null;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public int getMaxStack() {
    return 2;
  }

  @Override
  public int getMaxLocals() {
    return 0;
  }

  @Override
  public VmConstantPool getConstantPool() {
    return null;
  }

  @Override
  public List<TypeName> getParamters() {
    return null;
  }

  @Override
  public boolean isFinal() {
    return false;
  }

  @Override
  public boolean isStatic() {
    return false;
  }

  @Override
  public List<VmExceptionHandler> getExceptionHandlers() {
    return exceptionHandlers;
  }

  @Override
  public Map<Integer, Integer> getLineNumberTable() {
    return null;
  }

  @Override
  public String getInstructionsAsString() {
    return "";
  }

}
