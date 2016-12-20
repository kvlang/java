package kvltest;

import kvl.KVL;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Channels;

public class Basic {

  public static void main(String[] args) throws Exception {
    File file = new File(new File(System.getProperty("user.home")), "basic.kvl");
		if(!file.exists()) {
			URL basicKvlUrl = new URL("https://raw.githubusercontent.com/kvlang/java/master/test/samples/basic.kvl");
			ReadableByteChannel bc = Channels.newChannel(basicKvlUrl.openStream());
			FileOutputStream os = new FileOutputStream(file);
			os.getChannel().transferFrom(bc, 0, Integer.MAX_VALUE);
		}
    KVL kvl = KVL.loadFromFile(file);
    for(String key : kvl.keys()) {
      System.out.println("Key: \"" + key + "\" Value: \"" + kvl.get(key) + "\"");
    }
  }

}