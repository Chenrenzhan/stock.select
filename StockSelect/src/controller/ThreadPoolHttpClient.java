package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class ThreadPoolHttpClient {
	//递增 ： asc
	//递减 ： desc
	private static final String URL = "http://xueqiu.com/stock/screener/screen.json"
			+ "?category=SH&exchange=&areacode=&indcode=&orderby=symbol&order=asc"
			+ "&size=300&current=ALL&pettm=ALL&pelyr=ALL&pb=ALL&pct=All";
	private static final String GET_COOKIE_URL = "http://xueqiu.com/hq/screener";
	
    // 线程池
    private ExecutorService exe = null;
    // 线程池的容量
    private static final int POOL_SIZE = 20;
    private HttpClient client = null;
    String[] urls=null;
    public ThreadPoolHttpClient(String[] urls){
        this.urls=urls;
    }
    
    public static String[] urlList(){
    	String[] arrStr = new String[10];
    	for(int i = 0; i < 10; ++i){
    		arrStr[i] = URL + "&page=" + (i+1);
    		System.out.println(arrStr[i]);
    	}
    	return arrStr;
    }
    
    public void test() throws Exception {
        exe = Executors.newFixedThreadPool(POOL_SIZE);
        HttpParams params =new BasicHttpParams();
        /* 从连接池中取连接的超时时间 */ 
        ConnManagerParams.setTimeout(params, 1000);
        /* 连接超时 */ 
        HttpConnectionParams.setConnectionTimeout(params, 2000); 
        /* 请求超时 */
        HttpConnectionParams.setSoTimeout(params, 4000);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(
                new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

        //ClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
        PoolingClientConnectionManager cm=new PoolingClientConnectionManager(schemeRegistry);
        cm.setMaxTotal(10);
        final HttpClient httpClient = new DefaultHttpClient(cm,params);

        
        getCookie(httpClient);
        
        
        // URIs to perform GETs on
        final String[] urisToGet = urls;

        for (int i = 0; i < urisToGet.length; i++) {
            final int j=i;
            System.out.println(j);
            HttpGet httpget = new HttpGet(urisToGet[i]);
            exe.execute( new GetThread(httpClient, httpget));
        }
        
        exe.shutdown();
        System.out.println("Done");
    }

	public void getCookie(final HttpClient httpClient) {
		HttpResponse response = null;
		HttpGet hg = new HttpGet(GET_COOKIE_URL);
//		hg.setHeader(name, value);
		try {
			response = httpClient.execute(hg);
			System.out.println(response.toString());
//			response.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
//			getContent.close();
			System.out.println("finally");
			try {
				response.getEntity().getContent().close();
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		CookieStore cookieStore = ((AbstractHttpClient) httpClient).getCookieStore();
		((AbstractHttpClient) httpClient).setCookieStore(cookieStore);
		System.out.println(cookieStore);
	}
    static class GetThread extends Thread{
        
        private final HttpClient httpClient;
        private final HttpContext context;
        private final HttpGet httpget;
        
        public GetThread(HttpClient httpClient, HttpGet httpget) {
            this.httpClient = httpClient;
            this.context = new BasicHttpContext();
            this.httpget = httpget;
        }
        @Override
        public void run(){
            this.setName("threadsPoolClient");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            get();
        }
        static int aa = 1;
        public void get() {
            try {
                HttpResponse response = this.httpClient.execute(this.httpget, this.context);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println(this.httpget.getURI()+": status"+response.getStatusLine().toString());
                    
                    String str = InputStream2String(entity, "utf-8");
                    
                    String path = "data/" + aa + ".html";
                    write(path, str);
                    System.out.println("finish write   " + aa);
                    ++aa;
                }
                // ensure the connection gets released to the manager
                EntityUtils.consume(entity);
            } catch (Exception ex) {
                this.httpget.abort();
            }finally{
                httpget.releaseConnection();
            }
        }
    }
    
    public static void write(String filePath, String content)
			throws IOException {
    	System.out.println(content);
    	System.out.println(filePath);
		File file = new File(filePath); 
		if(file.exists()){
			file.delete(); 
		}
         
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(  
                new FileOutputStream(file), "utf-8"));  
        writer.write(content);  
        writer.close();  
	}
    
    public static String InputStream2String(HttpEntity entity,String charset) 
    		throws IOException{
    	InputStream htmlConten = null;

		if (entity != null) {
			htmlConten = entity.getContent();
//			String htmlStr = InputStream2String(htmlConten, charset);
//			return htmlStr;
		}
    	
    	
    	BufferedReader buff = new BufferedReader(new InputStreamReader(htmlConten, charset));
        StringBuffer res = new StringBuffer();
        String line = "";
        while((line = buff.readLine()) != null){
            res.append(line);
        }
        return res.toString();
    }
    
    public static void main(String[] argv){
    	//http://www.cnblogs.com/wasp520/archive/2012/06/28/2568897.html
    	//http://blog.csdn.net/dancen/article/details/7574634
    	//http://my.oschina.net/wfire/blog/352205
    	String[] urls = new String[]{"http://my.oschina.net/wfire/blog/352205",
    			"http://blog.csdn.net/dancen/article/details/7574634",
    			"http://www.cnblogs.com/wasp520/archive/2012/06/28/2568897.html"};
    	ThreadPoolHttpClient tphc = 
    			new ThreadPoolHttpClient(ThreadPoolHttpClient.urlList());
    	try {
			tphc.test();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("finidh");
    }
}