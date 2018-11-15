package edu.harvard.cscie98.simplejava.impl.interpreter;

public class BytecodeUtils {
  public static String getOpcodeName(final byte opcode) {
    switch (opcode) {
    case (byte) (0x00):
      return "nop";
    case (byte) (0x01):
      return "aconst_null";
    case (byte) (0x02):
      return "iconst_m1";
    case (byte) (0x03):
      return "iconst_0";
    case (byte) (0x04):
      return "iconst_1";
    case (byte) (0x05):
      return "iconst_2";
    case (byte) (0x06):
      return "iconst_3";
    case (byte) (0x07):
      return "iconst_4";
    case (byte) (0x08):
      return "iconst_5";
    case (byte) (0x09):
      return "lconst_0";
    case (byte) (0x0a):
      return "lconst_1";
    case (byte) (0x0b):
      return "fconst_0";
    case (byte) (0x0c):
      return "fconst_1";
    case (byte) (0x0d):
      return "fconst_2";
    case (byte) (0x0e):
      return "dconst_0";
    case (byte) (0x0f):
      return "dconst_1";
    case (byte) (0x10):
      return "bipush";
    case (byte) (0x11):
      return "sipush";
    case (byte) (0x12):
      return "ldc";
    case (byte) (0x13):
      return "ldc_w";
    case (byte) (0x14):
      return "ldc2_w";
    case (byte) (0x15):
      return "iload";
    case (byte) (0x16):
      return "lload";
    case (byte) (0x17):
      return "fload";
    case (byte) (0x18):
      return "dload";
    case (byte) (0x19):
      return "aload";
    case (byte) (0x1a):
      return "iload_0";
    case (byte) (0x1b):
      return "iload_1";
    case (byte) (0x1c):
      return "iload_2";
    case (byte) (0x1d):
      return "iload_3";
    case (byte) (0x1e):
      return "lload_0";
    case (byte) (0x1f):
      return "lload_1";
    case (byte) (0x20):
      return "lload_2";
    case (byte) (0x21):
      return "lload_3";
    case (byte) (0x22):
      return "fload_0";
    case (byte) (0x23):
      return "fload_1";
    case (byte) (0x24):
      return "fload_2";
    case (byte) (0x25):
      return "fload_3";
    case (byte) (0x26):
      return "dload_0";
    case (byte) (0x27):
      return "dload_1";
    case (byte) (0x28):
      return "dload_2";
    case (byte) (0x29):
      return "dload_3";
    case (byte) (0x2a):
      return "aload_0";
    case (byte) (0x2b):
      return "aload_1";
    case (byte) (0x2c):
      return "aload_2";
    case (byte) (0x2d):
      return "aload_3";
    case (byte) (0x2e):
      return "iaload";
    case (byte) (0x2f):
      return "laload";
    case (byte) (0x30):
      return "faload";
    case (byte) (0x31):
      return "daload";
    case (byte) (0x32):
      return "aaload";
    case (byte) (0x33):
      return "baload";
    case (byte) (0x34):
      return "caload";
    case (byte) (0x35):
      return "saload";
    case (byte) (0x36):
      return "istore";
    case (byte) (0x37):
      return "lstore";
    case (byte) (0x38):
      return "fstore";
    case (byte) (0x39):
      return "dstore";
    case (byte) (0x3a):
      return "astore";
    case (byte) (0x3b):
      return "istore_0";
    case (byte) (0x3c):
      return "istore_1";
    case (byte) (0x3d):
      return "istore_2";
    case (byte) (0x3e):
      return "istore_3";
    case (byte) (0x3f):
      return "lstore_0";
    case (byte) (0x40):
      return "lstore_1";
    case (byte) (0x41):
      return "lstore_2";
    case (byte) (0x42):
      return "lstore_3";
    case (byte) (0x43):
      return "fstore_0";
    case (byte) (0x44):
      return "fstore_1";
    case (byte) (0x45):
      return "fstore_2";
    case (byte) (0x46):
      return "fstore_3";
    case (byte) (0x47):
      return "dstore_0";
    case (byte) (0x48):
      return "dstore_1";
    case (byte) (0x49):
      return "dstore_2";
    case (byte) (0x4a):
      return "dstore_3";
    case (byte) (0x4b):
      return "astore_0";
    case (byte) (0x4c):
      return "astore_1";
    case (byte) (0x4d):
      return "astore_2";
    case (byte) (0x4e):
      return "astore_3";
    case (byte) (0x4f):
      return "iastore";
    case (byte) (0x50):
      return "lastore";
    case (byte) (0x51):
      return "fastore";
    case (byte) (0x52):
      return "dastore";
    case (byte) (0x53):
      return "aastore";
    case (byte) (0x54):
      return "bastore";
    case (byte) (0x55):
      return "castore";
    case (byte) (0x56):
      return "sastore";
    case (byte) (0x57):
      return "pop";
    case (byte) (0x58):
      return "pop2";
    case (byte) (0x59):
      return "dup";
    case (byte) (0x5a):
      return "dup_x1";
    case (byte) (0x5b):
      return "dup_x2";
    case (byte) (0x5c):
      return "dup2";
    case (byte) (0x5d):
      return "dup2_x1";
    case (byte) (0x5e):
      return "dup2_x2";
    case (byte) (0x5f):
      return "swap";
    case (byte) (0x60):
      return "iadd";
    case (byte) (0x61):
      return "ladd";
    case (byte) (0x62):
      return "fadd";
    case (byte) (0x63):
      return "dadd";
    case (byte) (0x64):
      return "isub";
    case (byte) (0x65):
      return "lsub";
    case (byte) (0x66):
      return "fsub";
    case (byte) (0x67):
      return "dsub";
    case (byte) (0x68):
      return "imul";
    case (byte) (0x69):
      return "lmul";
    case (byte) (0x6a):
      return "fmul";
    case (byte) (0x6b):
      return "dmul";
    case (byte) (0x6c):
      return "idiv";
    case (byte) (0x6d):
      return "ldiv";
    case (byte) (0x6e):
      return "fdiv";
    case (byte) (0x6f):
      return "ddiv";
    case (byte) (0x70):
      return "irem";
    case (byte) (0x71):
      return "lrem";
    case (byte) (0x72):
      return "frem";
    case (byte) (0x73):
      return "drem";
    case (byte) (0x74):
      return "ineg";
    case (byte) (0x75):
      return "lneg";
    case (byte) (0x76):
      return "fneg";
    case (byte) (0x77):
      return "dneg";
    case (byte) (0x78):
      return "ishl";
    case (byte) (0x79):
      return "lshl";
    case (byte) (0x7a):
      return "ishr";
    case (byte) (0x7b):
      return "lshr";
    case (byte) (0x7c):
      return "iushr";
    case (byte) (0x7d):
      return "lushr";
    case (byte) (0x7e):
      return "iand";
    case (byte) (0x7f):
      return "land";
    case (byte) (0x80):
      return "ior";
    case (byte) (0x81):
      return "lor";
    case (byte) (0x82):
      return "ixor";
    case (byte) (0x83):
      return "lxor";
    case (byte) (0x84):
      return "iinc";
    case (byte) (0x85):
      return "i2l";
    case (byte) (0x86):
      return "i2f";
    case (byte) (0x87):
      return "i2d";
    case (byte) (0x88):
      return "l2i";
    case (byte) (0x89):
      return "l2f";
    case (byte) (0x8a):
      return "l2d";
    case (byte) (0x8b):
      return "f2i";
    case (byte) (0x8c):
      return "f2l";
    case (byte) (0x8d):
      return "f2d";
    case (byte) (0x8e):
      return "d2i";
    case (byte) (0x8f):
      return "d2l";
    case (byte) (0x90):
      return "d2f";
    case (byte) (0x91):
      return "i2b";
    case (byte) (0x92):
      return "i2c";
    case (byte) (0x93):
      return "i2s";
    case (byte) (0x94):
      return "lcmp";
    case (byte) (0x95):
      return "fcmpl";
    case (byte) (0x96):
      return "fcmpg";
    case (byte) (0x97):
      return "dcmpl";
    case (byte) (0x98):
      return "dcmpg";
    case (byte) (0x99):
      return "ifeq";
    case (byte) (0x9a):
      return "ifne";
    case (byte) (0x9b):
      return "iflt";
    case (byte) (0x9c):
      return "ifge";
    case (byte) (0x9d):
      return "ifgt";
    case (byte) (0x9e):
      return "ifle";
    case (byte) (0x9f):
      return "if_icmpeq";
    case (byte) (0xa0):
      return "if_icmpne";
    case (byte) (0xa1):
      return "if_icmplt";
    case (byte) (0xa2):
      return "if_icmpge";
    case (byte) (0xa3):
      return "if_icmpgt";
    case (byte) (0xa4):
      return "if_icmple";
    case (byte) (0xa5):
      return "if_acmpeq";
    case (byte) (0xa6):
      return "if_acmpne";
    case (byte) (0xa7):
      return "goto";
    case (byte) (0xa8):
      return "jsr";
    case (byte) (0xa9):
      return "ret";
    case (byte) (0xaa):
      return "tableswitch";
    case (byte) (0xab):
      return "lookupswitch";
    case (byte) (0xac):
      return "ireturn";
    case (byte) (0xad):
      return "lreturn";
    case (byte) (0xae):
      return "freturn";
    case (byte) (0xaf):
      return "dreturn";
    case (byte) (0xb0):
      return "areturn";
    case (byte) (0xb1):
      return "return";
    case (byte) (0xb2):
      return "getstatic";
    case (byte) (0xb3):
      return "putstatic";
    case (byte) (0xb4):
      return "getfield";
    case (byte) (0xb5):
      return "putfield";
    case (byte) (0xb6):
      return "invokevirtual";
    case (byte) (0xb7):
      return "invokespecial";
    case (byte) (0xb8):
      return "invokestatic";
    case (byte) (0xb9):
      return "invokeinterface";
    case (byte) (0xba):
      return "xxxunusedxxx1";
    case (byte) (0xbb):
      return "new";
    case (byte) (0xbc):
      return "newarray";
    case (byte) (0xbd):
      return "anewarray";
    case (byte) (0xbe):
      return "arraylength";
    case (byte) (0xbf):
      return "athrow";
    case (byte) (0xc0):
      return "checkcast";
    case (byte) (0xc1):
      return "instanceof";
    case (byte) (0xc2):
      return "monitorenter";
    case (byte) (0xc3):
      return "monitorexit";
    case (byte) (0xc4):
      return "wide";
    case (byte) (0xc5):
      return "multianewarray";
    case (byte) (0xc6):
      return "ifnull";
    case (byte) (0xc7):
      return "ifnonnull";
    case (byte) (0xc8):
      return "goto_w";
    case (byte) (0xc9):
      return "jsr_w";
    case (byte) (0xca):
      return "breakpoint";
    case (byte) (0xfe):
      return "impdep1";
    case (byte) (0xff):
      return "impdep2";
    default:
      return "ERROR: Unknown opcode " + opcode;
    }
  }
}
