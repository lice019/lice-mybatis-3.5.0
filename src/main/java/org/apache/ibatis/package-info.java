
package org.apache.ibatis;

/**
 * DBC操作数据库的步骤：
 *
 * （1）注册驱动：Class.forName("com.mysql.jdbc.Driver");
 *
 * （2）建立连接(Connectoin)： Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb","root","root");
 *
 *  String sql = "insert into t_user(username,address) values(?,?)";
 *
 * （3）创建执行SQL的语句(Statement)：PreparedStatement sta = con.prepareStatement(sql);
 * 设置占位符的值：
 *          sta.setString(1, "需要添加到数据库的值");
 *          sta.setString(2, "需要添加到数据库的值");
 *
 * （4）执行SQL语句（通过Statement或PreparedStatement）： int rows = sta.executeUpdate();
 *
 * 如果是查询：需要将结果集返回。
 * （5）处理执行结果（resultSet）：ResultSet rs = ps.executeQuery();
 *     while(rs.next()){
 *           System.out.println(rs.getString("数据库字段名"));
 *     }
 *
 * （6）释放资源
 *
 *
 */
