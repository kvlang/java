package kvl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class KVL {
    public static class KVSet extends HashMap<String, String> {
        public KVSet() {super();}
    }
    KVSet internal;
    String delim;


    public KVL(KVSet kvSet) {
        internal = kvSet;
        delim = "-";
    }
    public KVL() {
        this(new KVSet());
    }
    public KVL(File file) throws Exception {
        this();
        if(!file.isFile()) {
            System.err.println("ERROR: File " + file.getAbsolutePath() + " is not a file");
            return;
        }
        if(!file.canRead()) {
            System.err.println("ERROR: Cannot read " + file.getAbsolutePath());
            return;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        int lineNum = 0;
        if(!(line =reader.readLine()).startsWith("_delim-")) {
            lineNum++;
            System.out.println("WARN: Delimiter not defined, assuming \"-\" (#" + lineNum + ")");
            delim = "-";
        } else {
            delim = line.replace("_delim-", "");
        }
        boolean comment = false;

        while((line = reader.readLine()) != null) {
            lineNum++;
            line.trim();
            if (line.startsWith("++")) {
                comment = !comment;
                continue;
            }
            if (line.endsWith("++") && comment) {
                comment = false;
                continue;
            }
            if (comment)
                continue;
            line = line.replace("<.*", "");
            if(line == "")
                continue;
            if(line.indexOf(delim) == -1) {
                System.out.println("WARN: Skipped line (#" + lineNum + ") \"" + line + "\", no delimiter found (delim = \"" + delim + "\")");
                continue;
            }
            if(line.startsWith(delim)) {
                System.out.println("WARN: Skipped line (#" + lineNum + ") \"" + line + "\", starts with delimiter (delim = \"" + delim + "\")");
                continue;
            }
            if(line.startsWith("_delim" + delim)) {
                delim = line.replace("_delim" + delim, "");
                System.out.println("INFO: Set delimiter to \"" + delim + "\" (#" + lineNum + ")");
            }
            String[] keyValue = line.split(delim);
            if(keyValue.length < 2)
                continue;
            String key = keyValue[0];
            if(internal.containsKey(key)) {
                System.out.println("WARN: Skipped line \"" + line + "\", duplicate key (key = \"" + key + "\", delim = \"" + delim + "\")");
                continue;
            }
            String value = keyValue[1];
            if(keyValue.length > 2)
                for(int i = 2 ; i < keyValue.length ; i++)
                    value += delim + keyValue[i];
            internal.put(key, value);
            System.out.println("INFO: Successfully put key = \"" + key + "\" value = \"" + value + "\" in this KVL object (delim = \"" + delim + "\")");
        }
        reader.close();
    }

    public String get(String key) {
        return internal.get(key);
    }
    public List<String> keys() {
        return Arrays.asList(internal.keySet().toArray(new String[]{}));
    }
    public List<String> values() {
        return Arrays.asList(internal.values().toArray(new String[]{}));
    }
    public String delim() {
        return delim;
    }
    public KVSet internal() {
        return internal;
    }
    public static KVL loadFromFile(File file) throws Exception {
        return new KVL(file);
    }

}
