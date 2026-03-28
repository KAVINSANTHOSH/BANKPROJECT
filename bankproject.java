import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.util.*;
import java.sql.*;

public class bankproject extends HttpServlet{


public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{

res.setContentType("text/html");
PrintWriter out=res.getWriter();

if( req.getParameter("create")!=null && req.getParameter("username")!=null && req.getParameter("password")!=null && req.getParameter("phonenumber")!=null && req.getParameter("Accountnumber")!=null && req.getParameter("initialamount")!=null){

String s1,s2,s3,s4,s5;
s1=req.getParameter("username");
s2=req.getParameter("password");
s3=req.getParameter("phonenumber");
s4=req.getParameter("Accountnumber");
s5=req.getParameter("initialamount");

System.out.println("user :"+s1 );
System.out.println("password :"+s2 );
System.out.println("phonenumber :"+s3 );
System.out.println("accountnumber :"+s4 );
System.out.println("initialamount :"+s5 );

usercreate(s1,s2,s3,s4,s5,req,res);

}


if( req.getParameter("searchuser")!=null && req.getParameter("username")!=null && req.getParameter("password")!=null && req.getParameter("phonenumber")!=null && req.getParameter("Accountnumber")!=null){

String s1,s2,s3,s4,s5;
s1=req.getParameter("username");
s2=req.getParameter("password");
s3=req.getParameter("phonenumber");
s4=req.getParameter("Accountnumber");

System.out.println("searchuser");

System.out.println("user :"+s1 );
System.out.println("password :"+s2 );
System.out.println("phonenumber :"+s3 );
System.out.println("accountnumber :"+s4 );

HttpSession session = req.getSession(); 
String username = (String) session.getAttribute("username");
System.out.println("username :"+username);

searchuser(s1,s2,s3,s4,req,res);
}

if(req.getParameter("amount")!=null && req.getParameter("withdrawAmount")!=null && req.getParameter("withdraw")!=null) {

String amount=req.getParameter("amount");
HttpSession session = req.getSession(); 
String username = (String) session.getAttribute("username");
System.out.println("username :"+username);
System.out.println("amount :"+amount);
withdraw(amount,username,req,res);
}

if(req.getParameter("amount")!=null && req.getParameter("deposit")!=null){
String s1=req.getParameter("amount");
HttpSession session = req.getSession(); 
String username = (String) session.getAttribute("username");
System.out.println("deposit username :"+username);
System.out.println("deposit amount :"+s1);
deposit(s1,username,req,res);
}

if(req.getParameter("username")!=null && req.getParameter("Accountnumber")!=null && req.getParameter("procedtochange")!=null ){
String s1,s2,s3,s4,s5,s6;
System.out.println("update / change");
s1=req.getParameter("username");
s3=req.getParameter("Accountnumber");
System.out.println(s1);
System.out.println(s3);
editprofile(s1,s3,req,res);
}
/*else{
System.out.println("innom varula");
}*/

if(req.getParameter("username")!=null && req.getParameter("password")!=null && req.getParameter("phonenumber")!=null && req.getParameter("update")!=null){
System.out.println("bapitha");
HttpSession session = req.getSession(); 
String username = (String) session.getAttribute("username");
System.out.println(username);
String s1,s2,s3;
s1=req.getParameter("username");
s2=req.getParameter("password");
s3=req.getParameter("phonenumber");
updateprofile(username,s1,s2,s3,req,res);
}

if(req.getParameter("balance")!=null){

try {
HttpSession session = req.getSession();
String username = (String) session.getAttribute("username");
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root@123"); 
PreparedStatement ps = con.prepareStatement("SELECT amount FROM user WHERE username = ?");
ps.setString(1, username);
ResultSet rs = ps.executeQuery();
if (rs.next()) {
int balance = rs.getInt("amount");
                out.println("<html>");
                out.println("<head><title>Your Balance</title></head>");
                out.println("<body style='text-align:center;font-family:Arial;'>");

                out.println("<h2>🏦 Your Account Balance</h2>");
                out.println("<h1>₹ " + balance + "</h1>");

                out.println("<br><br>");
                out.println("<a href='accoutdashboard.html' " +
                        "style='padding:10px 20px;background:#0984e3;color:white;" +
                        "border-radius:8px;text-decoration:none;'>Go Back</a>");

                out.println("</body></html>");
} else {
 System.out.println("User not found!");
 }

} catch (Exception e) {
    e.printStackTrace();
}

}

}
public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
doGet(req,res);

}

static void usercreate(String s1,String s2,String s3,String s4,String s5,HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
PrintWriter out=res.getWriter();
try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root@123"); 

String query = "INSERT INTO user(username, password, phone_num,acc_no,amount) VALUES(?, ?, ?,?,?)";
PreparedStatement ps = con.prepareStatement(query);
ps.setString(1,s1);
ps.setString(2, s2);
ps.setString(3, s3);
ps.setString(4,s4);
ps.setString(5,s5);
int rows = ps.executeUpdate();
if(rows > 0) {
out.println("<h3>Registration Successful!</h3>");
} else {
out.println("<h3>Failed to Register.</h3>");
}

}
catch(Exception e){
System.out.println("Error "+e);
}
}


static void searchuser(String s1,String s2,String s3,String s4,HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
/*
System.out.println("user :"+s1 );
System.out.println("password :"+s2 );
System.out.println("phonenumber :"+s3 );
System.out.println("accountnumber :"+s4 );*/

PrintWriter out=res.getWriter();
try {
Class.forName("com.mysql.cj.jdbc.Driver"); 
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root@123");
// Check if user already exists
PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE username=? AND password=? AND phone_num=? AND acc_no=?");
ps.setString(1, s1);
ps.setString(2, s2);
ps.setString(3, s3);
ps.setString(4, s4);
ResultSet rs = ps.executeQuery();
if (rs.next()) {
res.sendRedirect("accoutdashboard.html"); 
System.out.println(" kavin :");
HttpSession session = req.getSession();
session.setAttribute("username",s1);

} else {
out.println("user didnot found");
}
} catch (Exception e) {
e.printStackTrace();
out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
}
}

static void withdraw(String s1,String username,HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
System.out.println("with draw");
String name=username;
PrintWriter out=res.getWriter();
System.out.println("username"+username);
String money=s1;
int a=Integer.parseInt(money);
try {
Class.forName("com.mysql.cj.jdbc.Driver"); 
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root@123");
// Check if user already exists
PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE username=? ");
ps.setString(1, username);
ResultSet rs = ps.executeQuery();
if (rs.next()) {
String balance = rs.getString("amount");
System.out.println("balance"+balance);
int b=Integer.parseInt(balance);
int sum=b-a;
System.out.println("name "+name);
if(a<b){
System.out.println("sum :"+sum);
String newamount=String.valueOf(sum);
System.out.println("newamount :"+newamount);
System.out.println("name "+name);
ps = con.prepareStatement("UPDATE user SET amount = ? WHERE username = ?");
ps.setString(1, newamount);
ps.setString(2, name);
int rowsUpdated = ps.executeUpdate();
if (rowsUpdated > 0) {
    System.out.println("Withdraw updated successfully!");
out.println("<html>");
                out.println("<head><title>go to dashboard</title></head>");
                out.println("<body style='text-align:center;font-family:Arial;'>");

               
                out.println("<br><br>");
                out.println("<a href='accoutdashboard.html' " +
                        "style='padding:10px 20px;background:#0984e3;color:white;" +
                        "border-radius:8px;text-decoration:none;'>Go Back</a>");

                out.println("</body></html>");
} else {
    System.out.println("Failed to Withdraw balance!");
}
}
else{
out.println("<h3 style='color:red;'>You Enter Too Much Amount</h3>");
}
System.out.println("a :"+a+" b :"+b);
}

}
catch(Exception e){
System.out.println("error"+e);
}

}

static void deposit(String s1,String username,HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
String user=username;
String amount=s1;
System.out.println("username "+user);
System.out.println("deposit amount"+s1);
PrintWriter out=res.getWriter();
int a=Integer.parseInt(amount);
try {
Class.forName("com.mysql.cj.jdbc.Driver"); 
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root@123");
// Check if user already exists
PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE username=? ");
ps.setString(1, user);
ResultSet rs = ps.executeQuery();
if (rs.next()) {
String  alamount= rs.getString("amount");
System.out.println("balance"+alamount);
int b=Integer.parseInt(alamount);
int sum=b+a;
System.out.println("name "+user);
System.out.println("sum :"+sum);
String newamount=String.valueOf(sum);
System.out.println("newamount :"+newamount);
System.out.println("name "+user);
ps = con.prepareStatement("UPDATE user SET amount = ? WHERE username = ?");
ps.setString(1, newamount);
ps.setString(2, user);
int rowsUpdated = ps.executeUpdate();
if (rowsUpdated > 0) {
out.println("<html>");
                out.println("<head><title>go to dashboard</title></head>");
                out.println("<body style='text-align:center;font-family:Arial;'>");

               
                out.println("<br><br>");
                out.println("<a href='accoutdashboard.html' " +
                        "style='padding:10px 20px;background:#0984e3;color:white;" +
                        "border-radius:8px;text-decoration:none;'>Go Back</a>");

                out.println("</body></html>");
System.out.println("Deposit updated successfully!");
} else {
    System.out.println("Failed to Deposit balance!");
}

System.out.println("a :"+a+" b :"+b);
}

}
catch(Exception e){
System.out.println("error"+e);
}
}

static void editprofile(String s1,String s2,HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
System.out.println("edit Profile");

PrintWriter out=res.getWriter();
try {
Class.forName("com.mysql.cj.jdbc.Driver"); 
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root@123");
// Check if user already exists
PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE username=? AND acc_no=?");
ps.setString(1, s1);
ps.setString(2, s2);
ResultSet rs = ps.executeQuery();
if (rs.next()) {
res.sendRedirect("profileupdate.html"); 
} else {
out.println("user didnot found");
}
} catch (Exception e) {
e.printStackTrace();
out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
}
}

static void updateprofile(String s1,String s2,String s3,String s4,HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
System.out.println("updateprofile");
String username=s1;
String newuser=s2;
String pass=s3;
String num=s4;
System.out.println(username);
System.out.println(newuser);
System.out.println(pass);
System.out.println(num);
PrintWriter out=res.getWriter();
try {
Class.forName("com.mysql.cj.jdbc.Driver"); 
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root@123");
// Check if user already exists
String query = "UPDATE user SET username = ?, password = ?, phone_num = ? WHERE username = ?";
PreparedStatement ps = con.prepareStatement(query);
ps.setString(1, newuser);
ps.setString(2, pass);
ps.setString(3, num);
ps.setString(4, username);
int rowsUpdated = ps.executeUpdate();
if (rowsUpdated > 0) {
    System.out.println("updated successfully!");
} else {
    System.out.println("Failed to Update");
}
} catch (Exception e) {
e.printStackTrace();
out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
}



}

}