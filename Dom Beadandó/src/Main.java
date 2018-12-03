import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Main {

	public static void main(String[] args) {
DocumentBuilderFactory fact= DocumentBuilderFactory.newInstance();
        
        try{
            DocumentBuilder builder = fact.newDocumentBuilder();
            File file = new File("Gamecompany.xml");
            Document doc = builder.parse(file);
            Element gyoker = doc.getDocumentElement();
            System.out.println(gyoker.getNodeName());
            
            String keres_ezen_belul1 = new String("GameName");
            String keresett_szoveg1 = new String("Fighters");
            String keres_ezen_belul2 = new String("Account");
            String keresett_szoveg2 = new String("Sephiroth");
            String csere_erre = new String("Subidubiduuu");
            String keres_ezen_belul3 = new String("Characters");
            String keresett_szoveg3 = new String("Warrior");
            
            NodeList gyerekek = gyoker.getChildNodes();
            for(int i = 0; i < gyerekek.getLength(); i++)
            {
                Node elem = gyerekek.item(i);
                if (elem.getNodeType() == Node.ELEMENT_NODE)  
                {
                outputy("",elem); 
                }  
            }
            
            Node keres = kereses(doc,keres_ezen_belul1, keresett_szoveg1);
            if(keres != null){
            	System.out.println("\n A keresés eredménye: \n");
            	outputy("", keres);
            }
            else
            {
            	System.out.println("\n Nem sikerült megtalálni");
            }
            	
            Node keres_update = kereses(doc, keres_ezen_belul2, keresett_szoveg2);
            if(keres_update != null){
                keres_update.setTextContent(csere_erre);
                System.out.println("\n Az update eredménye: \n");
            	outputy("", keres_update);
            }
            else
            {
            	System.out.println("\n Nem sikerült megtalálni");
            }
            
            Node keres_beszur = kereses(doc, keres_ezen_belul3, keresett_szoveg3);
            if(keres_beszur != null){
            	Node uj = doc.createElement("Skill");
            	uj.setTextContent("béna");
                keres_beszur.appendChild(uj);
                System.out.println("\n A beszúrás eredménye: \n");
            	outputy("", keres_beszur);
            }
            else
            {
            	System.out.println("\n Nem sikerült megtalálni");
            }
           keres_beszur.removeChild(keres_beszur.getLastChild());
           System.out.println("\n A törlés eredménye: \n");
        	outputy("", keres_beszur);
           
           mentes(doc);
                  
        }catch(Exception e){
            System.out.println(e);
        }
           
	}
    
        
    public static void outputy(String tab, Node node){
        short tipus = node.getNodeType();
        
        switch (tipus){
            case Node.ELEMENT_NODE:
                System.out.print(tab + "<" + node.getNodeName());
                if (node.getAttributes().getLength() > 0)
                {
                    for(int i = 0; i < node.getAttributes().getLength(); i++)
                    {
                        outputy(" ", node.getAttributes().item(i));
                    }
                }
                System.out.println(">");
                for(int i = 0; i < node.getChildNodes().getLength(); i++){
                    outputy(tab + "\t", node.getChildNodes().item(i));
                }
                System.out.println(tab + "</" + node.getNodeName() + ">");
                break;
                
            case Node.ATTRIBUTE_NODE:
                System.out.print(tab + node.getNodeName() + "=" + node.getTextContent().trim());
                break;
                
            case Node.TEXT_NODE:
                String str = node.getTextContent().trim();
                if(str.length() > 0)
                System.out.println(tab + str);
                break;
               
        }
    }
    
    public static Node kereses(Document docum, String szoveg1, String szoveg2){
    	 NodeList node = docum.getElementsByTagName(szoveg1);
         for(int i = 0; i < node.getLength(); i++)
         {
             Node elem = node.item(i);
             if (elem.getNodeType() == Node.ELEMENT_NODE)  
             {
           	  NodeList kolkok = elem.getChildNodes();
           			  for(int j=0; j<kolkok.getLength(); j++){
           				  Node kolok = kolkok.item(j);
           				  if(kolok.getNodeType() == Node.ELEMENT_NODE){
           					  
           				  if(kolok.getTextContent().equals(szoveg2)) { 
           					  return kolok;
           				  }
           					else;
           			  }
           				  else; 
           			  }
           
             }
         }
		return null;
        
    }
    
    private static void mentes(Document document) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			File mentes_file = new File("save.xml");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(mentes_file);
			
			transformer.transform(source, result);
		}
		catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	

	}


