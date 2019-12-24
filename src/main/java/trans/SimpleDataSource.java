package trans;

import java.sql.*;
import java.util.Iterator;
import java.util.LinkedList;

public class SimpleDataSource {

    private static String USER = "root";
    private static String PWD = "root";
    private static String HOST = "jdbc:mysql://10.1.1.34:3306/db_ip_distribution_15_Q2?characterEncoding=UTF-8";

    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;

    private static LinkedList<Connection> connPool=null;

   /* public static void main(String[] args){
        new SimpleDataSource();

    }*/

    public SimpleDataSource(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connPool=new LinkedList<Connection>();
            for(int i=0;i<100;i++){
                Connection conn= DriverManager.getConnection( HOST,USER,PWD);
                connPool.add(conn);
                //System.out.println(conn);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Connection getconn(){//从linkedlist 返回一个connection对象
        return connPool.getFirst();
    }

    public void closeConn(Connection conn){//"关闭"数据库连接，不是正真意义上的关闭
        connPool.add(conn);
    }

    public void printPoolSize(){
        System.out.println("连接池size："+connPool.size());
    }

    public void releasePool()throws Exception{//关闭每一个链接;关闭数据库连接，真正的关闭
        Iterator it=connPool.iterator();
        Connection conn=null;
        while(it.hasNext()){}
        conn=(Connection)it.next();
        conn.close();
    }

    public String selectCode2(String countryCn){
        String code2 = null;
        if("保留地址".equals(countryCn)){
            code2 = "IA";
        }else{
            String sql = "select c2code from t_code_country where country_cn = ?";
            try {
                conn = getconn();
                ps = conn.prepareStatement(sql);
                ps.setString(1, countryCn);
                rs = ps.executeQuery();
               // System.out.println(ps.toString());
                while(rs.next()){
                    code2 = rs.getString(1);
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                try {
                    rs.close();
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return code2;
    }
}