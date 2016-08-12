package cn.imeixi.java.io.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 将xml+xslt 转换为 html
 * @author xiaoyue
 *
 */
public class ConvertXmlToHtml {
   
    public void ConvertJtlToHtml(String xmlFile,File xslFile) {
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new File(xmlFile));
            TransformerFactory factory = TransformerFactory.newInstance();  
            //XSLT样式表是静态存储在文件中的，  
            //javax.XML.transform.Templates接口把它们解析好了进内存里缓存起来    
            StreamSource xsl = new StreamSource(xslFile);
            Transformer transformer = factory.newTransformer(xsl);  
            Properties props = transformer.getOutputProperties();  
            props.setProperty(OutputKeys.ENCODING,"GBK");  
            props.setProperty(OutputKeys.METHOD,"html");  
            props.setProperty(OutputKeys.VERSION,"4.0");  

            transformer.setOutputProperties(props);   
            DocumentSource docSource = new DocumentSource(document);  
            StringWriter strWriter = new StringWriter();  
            StreamResult docResult =new StreamResult(strWriter) ;  
            transformer.transform(docSource, docResult);  
            System.out.println("strWriter:"+strWriter.toString()); 
        } catch(Exception e) {
           
        }
    }

    public static Document transformDocument(Document document,File styleSheet)  
      throws TransformerException  
    {  
      TransformerFactory factory = TransformerFactory.newInstance();  
      Transformer transformer = factory.newTransformer(new StreamSource(styleSheet));  
    
      DocumentSource source = new DocumentSource(document);  
      DocumentResult result = new DocumentResult();  
      transformer.transform(source, result);  
    
      Document transformedDoc = result.getDocument();  
    
      return transformedDoc;  
    }  
  /** 
   * 读取需要转换的xml文件 
   * @return Document 
   * @throws UnsupportedEncodingException 
   * @throws FileNotFoundException 
   */  
    public Document getRoleXML() throws UnsupportedEncodingException, FileNotFoundException   
    {  
          InputStream is=null;  
          URL url=this.getClass().getClassLoader().getResource("resources.xml");  
          if(url==null) return null;  
          String path = url.getFile();  
          path = URLDecoder.decode(path,"UTF-8");  
          is = new FileInputStream(new File(path));  
          System.out.println("读到的menuConfig.xml位置在："+path);  
          SAXReader reader = new SAXReader();  
            
          try {  
              return reader.read(is);  
          } catch (DocumentException e) {  
              // TODO Auto-generated catch block  
              e.printStackTrace();  
              return null;  
          }     
      }  
    
    /** 
     * 将XML转换成HTML 
     * @throws Exception 
     */  
    public static void translate() throws Exception{  
        //创建XML的文件输入流  
        FileInputStream fis=new FileInputStream("F:/123.xml");  
        Source source=new StreamSource(fis);  
          
        //创建XSL文件的输入流  
        FileInputStream fis1=new FileInputStream("F:/123.xsl");  
        Source template=new StreamSource(fis1);  
          
        PrintStream stm=new PrintStream(new File("F:/123.html"));  
        //讲转换后的结果输出到 stm 中即 F:\123.html  
        Result result=new StreamResult(stm);  
        //根据XSL文件创建准个转换对象  
        Transformer transformer=TransformerFactory.newInstance().newTransformer(template);  
        //处理xml进行交换  
        transformer.transform(source, result);   
        //关闭文件流  
        fis1.close();  
        fis.close();  
    }  
    
    public static void main(String args[]) throws IOException  
    {  
      try {  
          Document doc = new ConvertXmlToHtml().getRoleXML();          
          Document resultDoc = ConvertXmlToHtml.transformDocument(doc, new File("transfer-menu-before.xslt"));  
            
          OutputFormat format = OutputFormat.createPrettyPrint();                                           
          format.setEncoding("GBK");   
            
          XMLWriter writer = new XMLWriter(new FileWriter(new File("output.xml")),format);  
          writer.write(resultDoc);   
          writer.close();   
          System.out.println("用xslt转换xml文件成功!");  
    
            
      } catch (UnsupportedEncodingException e) {  
          // TODO Auto-generated catch block  
          e.printStackTrace();  
      } catch (FileNotFoundException e) {  
          // TODO Auto-generated catch block  
          e.printStackTrace();  
      } catch (TransformerException e) {  
          // TODO Auto-generated catch block  
          e.printStackTrace();  
      }    
      }  
}

