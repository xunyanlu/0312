package cn.edu.guet.main;

import cn.edu.guet.bean.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private JFrame jFrame;
    private JPanel jPanel;


    JMenu jMenu = new JMenu("基础信息管理");
    JMenuBar jMenuBar = new JMenuBar();
    JMenuItem item01 = new  JMenuItem("查看商品");



    JTable table;
    JScrollPane jscrollpane = new JScrollPane();
    private String columnNames[] = {"id", "商品名称", "单价", "类型"};
    private Object[][] rowData = null;

    public JFrame getjFrame() {
        return jFrame;
    }

    public Main(){


        jFrame = new JFrame("主页面");
        jPanel= (JPanel) jFrame.getContentPane();
        jFrame.setSize(700,400);
        jPanel.setLayout(null);

        item01.addActionListener(e -> {
            Connection conn = null;
            String url="jdbc:oracle:thin:@106.55.182.14:1521:orcl";
            String sql = "SELECT * FROM product";
            PreparedStatement pstmt;
            ResultSet rs;

            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn= DriverManager.getConnection(url,"hls","Grcl1234HlS");
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                List<Product> productList=new ArrayList<>();

                while (rs.next()) {
                    //取出的数据，放入一个容器（数据的载体）
                    Product product=new Product();
                    product.setId(rs.getInt("ID"));
                    product.setName(rs.getString("NAME"));
                    product.setPrice(rs.getFloat("PRICE"));
                    product.setType(rs.getString("TYPE"));

                    productList.add(product);//每循环一次把拿到的商品的数据存入集合，好比每摘一个芒果，把芒果放入桶里
                }
                rowData=new Object[productList.size()][columnNames.length];

                for (int i=0;i<rowData.length;i++){

                    Product product = productList.get(i);

                    rowData[i]=new Object[]{product.getId(),product.getName(),product.getPrice(),product.getType()};

                    System.out.println(rowData[i]);


                }



            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            //创建菜单表格
            table = new JTable(rowData, columnNames);
            jscrollpane.setBounds(0, 31, 700, 370);
            jscrollpane.setViewportView(table);
            table.setRowHeight(35);

            DefaultTableCellRenderer r = new DefaultTableCellRenderer();
            r.setHorizontalAlignment(JLabel.CENTER);
            table.setDefaultRenderer(Object.class, r);
            jPanel.add(jscrollpane);

        });


        jMenu.add(item01);
        jMenuBar.add(jMenu);
        jMenuBar.setBounds(0,0,100,30);
        jPanel.add(jMenuBar);


       //Frame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



}
