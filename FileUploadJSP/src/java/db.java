import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class db extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
        try {
            String path=(String) request.getParameter("pathname");
            int len=path.length();
            String sub=path.substring(len-3,len);
            if(sub.equals("xml")){
            File fXmlFile;
            fXmlFile = new File(path);
            int temp1=0;
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);		
	doc.getDocumentElement().normalize();
	NodeList nList = doc.getElementsByTagName("student");
	for (int temp = 0; temp < nList.getLength(); temp++) {
		Node nNode = nList.item(temp);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;

			System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
			System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
			System.out.println("email : " + eElement.getElementsByTagName("email").item(0).getTextContent());
			System.out.println("phone number:"+ eElement.getElementsByTagName("phonenumber").item(0).getTextContent());
                        String fn=eElement.getElementsByTagName("firstname").item(0).getTextContent();
			String ln= eElement.getElementsByTagName("lastname").item(0).getTextContent();
			String email=eElement.getElementsByTagName("email").item(0).getTextContent();
			String phno=eElement.getElementsByTagName("phonenumber").item(0).getTextContent();
                        
          Class.forName("org.apache.derby.jdbc.ClientDriver");  
          Connection con =DriverManager.getConnection("jdbc:derby://localhost:1527/logindetails","arjun","arjun");
          Statement ps = con.createStatement();
          if(fn!=null&&ln!=null&&email!=null&&phno!=null &&phno.length()==10){
          String sql = "INSERT INTO STUDENT  values ('"+fn+"','"+ln+"','"+email+"','"+phno+"')";
          ps.executeUpdate(sql);
          ps.close(); 
            con.close(); 
          temp1=1;   
          }
          
		}
	}
            if(temp1==1){
            PrintWriter out = response.getWriter(); 
            out.println("<html><body><b>Successfully Inserted"+path
                        + "</b></body></html>"); 
            
             Class.forName("org.apache.derby.jdbc.ClientDriver");  
             Connection con1 =DriverManager.getConnection("jdbc:derby://localhost:1527/logindetails","arjun","arjun"); 
             Statement stmt = con1.createStatement();
             ResultSet rs = stmt.executeQuery("select * from student");
             out.println("<table border=1 width=50% height=50%>");
             out.println("<tr><th>Firstname</th><th>lastname</th><th>email</th><th>phone number</th><tr>");
             while (rs.next()) {
                 String n1 = rs.getString("firstname");
                 String n2 = rs.getString("lastname");
                 String n3=  rs.getString("email");
                 String n4= rs.getString("phno");
                 out.println("<tr><td>" + n1 + "</td><td>" + n2 + "</td><td>" + n3+ "</td><td>" + n4 + "</td></tr>"); 
             }
             con1.close();
             out.println("</table>");
             out.println("</html></body>");
             
            
            }
            else{
           PrintWriter out = response.getWriter(); 
            out.println("<html><body><b>INVALID data in file"
                        + "</b></body></html>");      
            }
            }
            else{
                String[] store_csv=new String[4];
                int itr=0;
                PrintWriter out = response.getWriter(); 
            out.println("<html><body><b> CSV file"
                        + "</b>"); 
            
            
               Scanner scanner = new Scanner(new File(path));
               scanner.useDelimiter(",");
               while (scanner.hasNext())
           {
               store_csv[itr]=scanner.next();
               itr++;
           }
               
          Class.forName("org.apache.derby.jdbc.ClientDriver");  
          Connection con =DriverManager.getConnection("jdbc:derby://localhost:1527/logindetails","arjun","arjun");
          Statement ps = con.createStatement();
          if(store_csv[0]!=null&&store_csv[1]!=null&&store_csv[2]!=null&&store_csv[3]!=null &&store_csv[3].length()==10){
          String sql = "INSERT INTO STUDENT  values ('"+store_csv[0]+"','"+store_csv[1]+"','"+store_csv[2]+"','"+store_csv[3]+"')";
          ps.executeUpdate(sql);
          ps.close(); 
            con.close(); 
            Class.forName("org.apache.derby.jdbc.ClientDriver");  
             Connection con1 =DriverManager.getConnection("jdbc:derby://localhost:1527/logindetails","arjun","arjun"); 
             Statement stmt = con1.createStatement();
             ResultSet rs = stmt.executeQuery("select * from student");
             out.println("<table border=1 width=50% height=50%>");
             out.println("<tr><th>Firstname</th><th>lastname</th><th>email</th><th>phone number</th><tr>");
             while (rs.next()) {
                 String n1 = rs.getString("firstname");
                 String n2 = rs.getString("lastname");
                 String n3=  rs.getString("email");
                 String n4= rs.getString("phno");
                 out.println("<tr><td>" + n1 + "</td><td>" + n2 + "</td><td>" + n3+ "</td><td>" + n4 + "</td></tr>"); 
             }
             con1.close();
             out.println("</table>");
             
        scanner.close();
          out.println("</body></html>");  
            
            
            }   
            }
        }
       catch (Exception e) {
            PrintWriter out = response.getWriter(); 
            out.println("<html><body><b>UNsuccesful"+
                  "</b></body></html>"); 

    }
    }
    }

