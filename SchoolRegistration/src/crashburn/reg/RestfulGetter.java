package crashburn.reg;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;

public class RestfulGetter extends AsyncTask <Void, Void, String> {
	
	private String uri = "";
	
	public RestfulGetter(String uri) {
		this.uri = uri;
	}

	@Override
	protected String doInBackground(Void... params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(uri);
		String output = null;
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			HttpEntity entity = response.getEntity();
			output = entityToString(entity);
		} catch (Exception e) {
			output = e.getMessage();
		}
		return output;
	}

	protected String entityToString(HttpEntity entity) throws IllegalStateException, IOException {
		InputStream in = entity.getContent();
		StringBuilder out = new StringBuilder();
		int bytesRead = 1;
		while (bytesRead > 0) {
			byte[] b = new byte[1024];
			bytesRead =  in.read(b);
			if (bytesRead > 0) {
				out.append(new String(b, 0, bytesRead));
			}
		} 
		return out.toString();
	}
}
