package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LogEntry<HttpMethod> {


    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public int getResponceCode() {
        return responceCode;
    }

    public void setResponceCode(int responceCode) {
        this.responceCode = responceCode;
    }

    public int getResponceSize() {
        return responceSize;
    }

    public void setResponceSize(int responceSize) {
        this.responceSize = responceSize;
    }

   /* public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }*/

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    String ipAddr,path,refer,userAgent;
    int responceCode,responceSize;
  //  DateTimeFormatter formatter;
    String time;
    HttpMethod method;

    public LogEntry(String str) {
        final String regex = "^(\\S+) (\\S+) (\\S+) " +
                "\\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)" +
                " (\\S+)\\s*(\\S+)?\\s*\" (\\d{3}) (\\S+) \"(\\S+)\" \"(\\S+.+)\"";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(str);
        HashMap<LocalDateTime,Integer> countTime = new HashMap<LocalDateTime,Integer>();
        while (matcher.find()) {
            this.ipAddr = matcher.group(1);
          //  this.formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss +z");
            this.time = matcher.group(4);
            this.method = (HttpMethod) matcher.group(5);
            this.path = matcher.group(6);
            this.responceCode = Integer.parseInt(matcher.group(8));
            this.responceSize = Integer.parseInt(matcher.group(9));
            this.refer = matcher.group(10);
            this.userAgent = matcher.group(11);

        }
        }

    public static void main(String[] args)
    {
        String path;
        System.out.println("Введите путь: ");
        path = new Scanner(System.in).nextLine();
        File file = new File(path);
        while (true) {
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (fileExists == true && isDirectory == true) {
                System.out.println("Путь корректен, но ведет к папке");
                break;
            } else if (fileExists == true && isDirectory == false) {
                System.out.println("Путь указан верно");
                break;
            } else {
                System.out.println("Путь некорректен");
                break;
            }

        }
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            int k1=0,k2=0;
            while ((line = reader.readLine()) != null) {
                LogEntry logEntry = new LogEntry(line);
                if (line.contains("Googlebot")) k2++;
                if (line.contains("YandexBot")) k1++;
                //System.out.println(logEntry.getUserAgent());
            }
            int count = k1+k2;
            System.out.println("доля запросов с Googlebot: "+(double)k2/count);
            System.out.println("доля запросов с YandexBot: "+(double)k1/count);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // final String log = "37.231.123.209 - - [25/Sep/2022:06:25:04 +0300] \"GET /engine.php?rss=1&json=1&p=156&lg=1 HTTP/1.0\" 200 61096 \"https://nova-news.ru/search/?rss=1&lg=1\" \"Mozilla/5.0 (Linux; Android 6.0.1; SM-J500M Build/MMB29M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.91 Mobile Safari/537.36\"\n";

    }
}