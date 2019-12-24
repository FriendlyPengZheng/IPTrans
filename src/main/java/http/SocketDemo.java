package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketDemo {

    public static void main(String[] args) {

    }

    public static String doRequest(String url) {
        String s = null;
        Socket client = new Socket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("10.1.4.11", 8080);
        // 建立TCP连接，虚拟机的地址为192.168.194.129
        // Nginx监听的端口设置为8080
        try {
            client.connect(inetSocketAddress, 1000);
            String request = "GET /index.html HTTP/1.1\r\n" +
                    "Host: 192.168.194.129:8080\r\n";
            PrintWriter pWriter = new PrintWriter(client.getOutputStream(), true);
            pWriter.println(request);
            // 这里要注意二进制字节流转换为字符流编码要使用UTF-8
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"));
            /*while ((s = bufferedReader.readLine()) != null) {
                System.out.println(s);
            }*/
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return s;
    }
}
