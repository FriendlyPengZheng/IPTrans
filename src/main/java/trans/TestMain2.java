package trans;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TestMain2 {
	private static String span = null;
	private static String start = null;
	private static String end = null;
	private static String country = null;
	private static String province = null;
	private static String city = null;
	private static String isp = null;
	private static String startIp = null;
	private static String endIp = null;
	private static String flagCountry = null;

	public static void main(String[] args) {
		readFile();
	}
	public static void readFile(){
		File file = new File("D:\\city_ip");
		InputStream in = null;
		InputStreamReader inReader = null;
		BufferedReader bufferedReader = null;
		try {
			in = new FileInputStream(file);
			inReader = new InputStreamReader(in);
			bufferedReader = new BufferedReader(inReader);
			String line = null;
			int i = 0;
			int j = 0;
			Map<String, String> map= new HashMap<String, String>();
			StringBuffer sbCoutry = new StringBuffer();
			while((line = bufferedReader.readLine()) != null){
				String[] items = line.split("#");
				span = items[0];
				start = items[1];
				end = items[2];
				country = items[3];
				province = items[4];
				city = items[5];
				isp = items[6];
				startIp = items[7];
				endIp = items[8];
                String code2 = DbUtil.selectCode2(country);
                if(code2==null || "".equals(code2)){
                    sbCoutry.append(line+"\n\r");
					System.out.println(i+++":   "+line);
                }
            }
			writeFile(sbCoutry.toString(),new File("D:\\UnKnown"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				bufferedReader.close();
				inReader.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void writeFile(String sqlString,File file){
		//File file = new File("D:\\sqlString");
		OutputStream out = null;
		OutputStreamWriter oStreamWriter = null;
		BufferedWriter writer = null;
		try {
			out = new FileOutputStream(file,true);
			oStreamWriter = new OutputStreamWriter(out);
			writer = new BufferedWriter(oStreamWriter);
			writer.write(sqlString);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				writer.close();
				oStreamWriter.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
