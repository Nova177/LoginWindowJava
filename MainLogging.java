import java.io.File;
import javax.lang.model.element.Element;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MainLogging{
/**
 * Constructs and displays a login window using Java Swing.
 * 
 * This constructor sets up a simple graphical user interface (GUI) for user login,
 * consisting of a JFrame containing fields for username and password input,
 * and a login button to submit the credentials. When the login button is clicked,
 * it checks the entered credentials against those stored in an XML file.
 * If the credentials are valid, a confirmation dialog is shown; otherwise,
 * an error message is displayed.
 *
 * The layout is manually set, and the JFrame is sized at 200x200 pixels. The GUI includes:
 * - A JLabel and a JTextField for the username.
 * - A JLabel and a JPasswordField for the password.
 * - A JButton that triggers the authentication process.
 *
 * The JFrame is set to exit the application on close. All components are positioned using absolute coordinates.
 *
 * @author Nova177
 */
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
  /**
 * Checks user credentials against an XML file to verify if the provided username
 * and password match any user entry in the file.
 *
 * This method reads from a predefined XML file located at a specified path on the disk.
 * It searches through each "user" element in the XML to find a matching username and password.
 * If a match is found, the method returns true, indicating successful authentication.
 * If no match is found or if any error occurs during the process (such as file not found or XML parsing errors),
 * it returns false.
 *
 * @param username the username entered by the user, to be verified against the XML.
 * @param password the password entered by the user, to be verified against the XML.
 * @return true if the username and password match an entry in the XML file; false otherwise.
 * @throws Exception if there is any issue accessing or parsing the XML file. Specific errors such as
 *         file not found or XML parsing errors are caught and handled within the method,
 *         with relevant error messages displayed to the user.
 * @author Nova177
 */
  private boolean checkUserCredentialsinXML(String username, String password) {
    String path = System.getProperty("user.dir");
    String fileName = "userList.xml";
    File inputFile = new File(path+"\\"+fileName);
    try {
        if (!inputFile.exists()) {
            JOptionPane.showMessageDialog(null, "XML file not found at the specified path.");
            return false;
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        org.w3c.dom.Document doc = dBuilder.parse(inputFile);  // Directly use as org.w3c.dom.Document
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("user");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String xmlUsername = ((org.w3c.dom.Document) eElement).getElementsByTagName("username").item(0).getTextContent();  // Correctly access 'username'
                String xmlPassword = ((org.w3c.dom.Document) eElement).getElementsByTagName("password").item(0).getTextContent();  // Correctly access 'password'
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




