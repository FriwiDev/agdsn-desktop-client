package net.friwi.agdsn.desktop.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.bouncycastle.crypto.tls.CertificateRequest;
import org.bouncycastle.crypto.tls.DefaultTlsClient;
import org.bouncycastle.crypto.tls.TlsAuthentication;
import org.bouncycastle.crypto.tls.TlsClientProtocol;
import org.bouncycastle.crypto.tls.TlsCredentials;
import org.json.JSONObject;

import net.friwi.agdsn.desktop.util.StreamUtil;

public class JSONQuery {
	String host, url;
	public JSONQuery(String host, String url){
		this.host = host;
		this.url = url;
	}
	/**
	 * Contains copy-paste from: http://stackoverflow.com/questions/4308554 & http://stackoverflow.com/questions/8171802
	 */
	public JSONObject execute() throws Exception{
		
		
		java.security.SecureRandom secureRandom = new java.security.SecureRandom();
        Socket socket = new Socket(java.net.InetAddress.getByName(host), 443);
        TlsClientProtocol protocol = new TlsClientProtocol(socket.getInputStream(), socket.getOutputStream(),secureRandom);
        DefaultTlsClient client = new DefaultTlsClient() {
            public TlsAuthentication getAuthentication() throws IOException {
                TlsAuthentication auth = new TlsAuthentication() {
                    // Capture the server certificate information!
                    public void notifyServerCertificate(org.bouncycastle.crypto.tls.Certificate serverCertificate) throws IOException {
                    }

                    public TlsCredentials getClientCredentials(CertificateRequest certificateRequest) throws IOException {
                        return null;
                    }
                };
                return auth;
            }
        };
        protocol.connect(client);

        java.io.OutputStream output = protocol.getOutputStream();
        output.write(("GET "+url+" HTTP/1.1\r\n").getBytes("UTF-8"));
        output.write(("Host: "+host+"\r\n").getBytes("UTF-8"));
//        output.write("Connection: close\r\n".getBytes("UTF-8")); // So the server will close socket immediately.
        output.write("\r\n".getBytes("UTF-8")); // HTTP1.1 requirement: last line must be empty line.
        output.flush();

        java.io.InputStream input = protocol.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line;
        while ((line = reader.readLine()) != null)
        {
        	if(line.length()<=3)break;
        }

//		BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream(), Charset.forName("UTF-8")));
	    String jsonText = StreamUtil.readAll(reader);
	    socket.close();
//	    System.out.println(jsonText);
	    return new JSONObject(jsonText.trim());
	}
}
