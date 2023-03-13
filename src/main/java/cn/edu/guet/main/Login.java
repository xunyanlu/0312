package cn.edu.guet.main;


import cn.edu.guet.ulit.PasswordEncoder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Base64;

public class Login extends JFrame {
    private JPanel jPanel;
    private JTextField account;
    private JTextField password;
    private JButton login;
    private JButton zhanghao;
    private JButton mima;

    public  Login(String title){
        super(title);//设定界面大小
        setSize(400,280);
        setResizable(false);//禁止窗口缩放
        jPanel=(JPanel) this.getContentPane();
        jPanel.setLayout(null);//布局为空

        account=new JTextField("zhangsan");
        account.setBounds(105,130,190,35);
        jPanel.add(account);

        password=new JTextField("123456");
        String passes=password.getText();
        password.setBounds(105,160,190,35);
        jPanel.add(password);

        login = new JButton("登录");
        login.setBounds(105,200,190,35);
        jPanel.add(login);

        /*zhanghao = new JButton("账号");
        zhanghao.setBounds(10,130,80,35);
        jPanel.add(zhanghao);

        mima = new JButton("密码");
        mima.setBounds(10,160,80,35);
        jPanel.add(mima);*/

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //给登录按钮增加监听
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("准备登录");
                String username=account.getText();
                String pass=password.getText();
                //如何与数据库比对
                String sql="SELECT *"+
                        "FROM users"+
                        " WHERE username=? ";//SQL语句

                PreparedStatement pstmt;
                Connection conn = null;
                ResultSet resultSet;
                String url="jdbc:oracle:thin:@106.55.182.14:1521:orcl";






                //1、加载驱动
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    conn= DriverManager.getConnection(url,"hls","Grcl1234HlS");
                    if (conn!=null){
                        System.out.println("链接成功");

                        pstmt=conn.prepareStatement(sql);//把PSTMT和SQL语句关联
                        pstmt.setString(1,username);

                        //执行SQL语句
                        resultSet = pstmt.executeQuery();//执行查询，得到一个结果集
                        resultSet.next();
                        String PASS=resultSet.getString("PASSWORD");
                        PasswordEncoder encoder = new PasswordEncoder("www.guet.edu.cn");
                        //System.out.println(PASS);

                        //String pw=encoder.encode(passes);
                        //判断成功或失败？
                        if(encoder.matches(PASS,passes)){
                            System.out.println("登录成功");
                            setVisible(false);
                            Main main = new Main();
                            main.getjFrame().setVisible(true);
                        }else {
                            System.out.println("用户名或密码错误");
                        }

                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }finally {
                    try {
                        conn.close();//释放数据库链接资源
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });


        setVisible(true);//窗口可见
    }
    public static void main(String[] args) {
        new Login("登录窗口");

    }
}
