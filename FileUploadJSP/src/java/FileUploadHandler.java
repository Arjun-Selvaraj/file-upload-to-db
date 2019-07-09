import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
public class FileUploadHandler extends HttpServlet {
    private final String UPLOAD_DIRECTORY = "C:/uploads";
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(ServletFileUpload.isMultipartContent(request)){
            try {
                String name = null;
                List<FileItem> multiparts = new ServletFileUpload(
                                         new DiskFileItemFactory()).parseRequest(request);
                int flag = 1;
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                        name = new File(item.getName()).getName();
                        String ext = FilenameUtils.getExtension(name);
                        System.out.println(ext);
                        if(ext.equals("csv") || ext.equals("xml")) {
                          item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
                              
                        }
                        else {
                         flag = 0;
                         break;
                        }
                    }
                }
            
               if(flag  == 1){
                  String path;
                  path="C:\\\\uploads\\\\"+name;   
                  
                  request.setAttribute("message", "File Uploaded Successfully");
                  request.setAttribute("message1", path);
               }
               else{
               request.setAttribute("message", "File Not Supported. Upload only csv or xml files.");
               request.setAttribute("message1", "null");
               }
             } catch (Exception ex) {
               
               request.setAttribute("message", "File Upload Failed due to " + ex);
               request.setAttribute("message1", "null");
             }          
          
        }else{
            request.setAttribute("message",
                                 "Sorry this Servlet only handles file upload request");
        }
     
        request.getRequestDispatcher("/result.jsp").forward(request, response);
      
    }
   
     
}