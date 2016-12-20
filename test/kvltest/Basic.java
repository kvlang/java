package kvltest;

import kvl.KVL;
import java.io.File;

public class Basic {

  public static void main(String[] args) throws Exception {
    File file = new File(System.getProperty("user.home") + File.seperator() + "basic.kvl");
    KVL kvl = KVL.loadFromFile(file);
    for(String key : kvl.keys()) {
      System.out.println("Key: \"" + key + "\" Value: \"" + kvl.get(key) + "\"");
    }
  }

}
