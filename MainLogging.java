import java.io.File;
import javax.lang.model.element.Element;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class MainLogging{
  public MainLogging() {
    JFrame frame = new JFrame("Login");
    frame.setSize(200, 200);  
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
    // Username label and field
    JLabel userLabel = new JLabel("Username:");
    userLabel.setBounds(50, 30, 100, 20);  
    frame.add(userLabel);
  
    JTextField userField = new JTextField();
    userField.setBounds(50, 50, 100, 20);  
    frame.add(userField);
  
    // Password label and field
    JLabel passLabel = new JLabel("Password:");
    passLabel.setBounds(50, 80, 100, 20); 
    frame.add(passLabel);
  
    JPasswordField passwordField = new JPasswordField();
    passwordField.setBounds(50, 100, 100, 20);  
    frame.add(passwordField);
  
    // Login button
    JButton loginButton = new JButton("Login");
    loginButton.setBounds(50, 130, 100, 20);  
    frame.add(loginButton);
  
    // Action listener for the login button
    loginButton.addActionListener(e -> {
        String username = userField.getText();
        char[] password = passwordField.getPassword();
        String passwordString = new String(password);
       if(this.checkUserCredentialsinXML(username,passwordString) == true){

        JOptionPane.showMessageDialog(null,"Confirmed User");

       } 
       else{

        JOptionPane.showMessageDialog(null,"Wrong Login Credentials","Error",JOptionPane.ERROR_MESSAGE);

       } 
        

    });
  
    frame.setLayout(null);
    frame.setVisible(true);
  }
  private boolean checkUserCredentialsinXML(String username, String password) {
    File inputFile = new File("C:/Users/kevin.graessler/Desktop/LoginWindowJava/userlist.xml");
    try {
        if (!inputFile.exists()) {
            JOptionPane.showMessageDialog(null, "XML file not found at the specified path.");
            return false;
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = (Document) dBuilder.parse(inputFile);
        ((org.w3c.dom.Document) doc).getDocumentElement().normalize();

        NodeList nList = ((org.w3c.dom.Document) doc).getElementsByTagName("user");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String xmlUsername = ((org.w3c.dom.Document) eElement).getElementsByTagName("username").item(0).getTextContent();
                String xmlPassword = ((org.w3c.dom.Document) eElement).getElementsByTagName("password").item(0).getTextContent();
                if (xmlUsername.equals(username) && xmlPassword.equals(password)) {
                    return true;
                }
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error parsing XML: " + e.getMessage());
        e.printStackTrace();
    }
    return false;
}
}