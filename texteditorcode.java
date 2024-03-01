import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

class components extends JFrame  //abstract class
{
   JFrame f; //container
   JTextArea textArea;
   JScrollPane scrollPane;
   JLabel fontLabel;
   JSpinner fontSizeSpinner;
   JButton fontColorButton;
   protected JToolBar mainToolbar;
   JButton boldButton, italicButton, clearButton;
   JComboBox fontBox;

   protected final ImageIcon boldIcon = new ImageIcon("bold.png");
   protected final ImageIcon italicIcon = new ImageIcon("italic.png");
   protected final ImageIcon clearIcon = new ImageIcon("clear.png");

   JMenuBar menuBar;
   JMenu fileMenu;
   JMenu themesMenu,editMenu, findMenu ;
   JMenuItem openItem;
   JMenuItem saveItem;
   JMenuItem exitItem;
   JMenuItem cutItem;
   JMenuItem copyItem;
   JMenuItem pasteItem;
   JMenuItem clearItem;
   JMenuItem darkItem;
   JMenuItem lightItem;

}

public class TextEditor extends components implements ActionListener   //inheritance and interface
{
   TextEditor()
   {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("Text Editor");
      this.setSize(500, 500);
      this.setLayout(new FlowLayout());
      this.setLocationRelativeTo(null);

      textArea = new JTextArea();
      textArea.setLineWrap(true);
      textArea.setWrapStyleWord(true);
      textArea.setFont(new Font("Arial",Font.PLAIN,20));
      textArea.addKeyListener(
              new KeyAdapter() {

                 @Override
                 public void keyReleased(KeyEvent e)
                 {
                    setTitle("Untitled |     [ Length: " + textArea.getText().length()
                            + "    Lines: " + (textArea.getText() + "|").split("\n").length
                            + "    Words: " + textArea.getText().trim().split("\\s+").length + " ]");
                 }
              });

      scrollPane = new JScrollPane(textArea);
      scrollPane.setPreferredSize(new Dimension(450,450));
      scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);


      mainToolbar = new JToolBar();
      this.add(mainToolbar, BorderLayout.NORTH);

      boldButton = new JButton(boldIcon);
      boldButton.setToolTipText("Bold");
      boldButton.addActionListener(this);
      mainToolbar.add(boldButton);
      mainToolbar.addSeparator();

      italicButton = new JButton(italicIcon);
      italicButton.setToolTipText("Italic");
      italicButton.addActionListener(this);
      mainToolbar.add(italicButton);
      mainToolbar.addSeparator();

      clearButton = new JButton(clearIcon);
      clearButton.setToolTipText("Clear All");
      clearButton.addActionListener(this);
      mainToolbar.add(clearButton);
      mainToolbar.addSeparator();


      fontLabel = new JLabel("Font: ");

      fontSizeSpinner = new JSpinner();
      fontSizeSpinner.setPreferredSize(new Dimension(50,25));
      fontSizeSpinner.setValue(20);
      fontSizeSpinner.addChangeListener(
              new ChangeListener()
              {
                 @Override
                 public void stateChanged(ChangeEvent e)
                 {
                    textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int) fontSizeSpinner.getValue()));
                 }
              });

      fontColorButton = new JButton("Color");
      fontColorButton.addActionListener(this);

      String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

      fontBox = new JComboBox(fonts);
      fontBox.addActionListener(this);
      fontBox.setSelectedItem("Arial");

      // ------ menubar ------
      menuBar = new JMenuBar();
      fileMenu = new JMenu("File");
      editMenu = new JMenu("Edit");
      themesMenu = new JMenu("Themes");

      //filemenu
      openItem = new JMenuItem("Open");
      saveItem = new JMenuItem("Save");
      exitItem = new JMenuItem("Exit");

      //edit menu
      cutItem = new JMenuItem("Cut");
      copyItem = new JMenuItem("Copy");
      pasteItem = new JMenuItem("Paste");
      clearItem = new JMenuItem("Clear");

      //themes menu
      darkItem = new JMenuItem("Dark");
      lightItem = new JMenuItem("Light");


      //action listeners
      openItem.addActionListener(this);
      saveItem.addActionListener(this);
      exitItem.addActionListener(this);
      cutItem.addActionListener(this);
      copyItem.addActionListener(this);
      pasteItem.addActionListener(this);
      clearItem.addActionListener(this);
      darkItem.addActionListener(this);
      lightItem.addActionListener(this);


      //menubar
      fileMenu.add(openItem);
      fileMenu.add(saveItem);
      fileMenu.add(exitItem);
      editMenu.add(cutItem);
      editMenu.add(copyItem);
      editMenu.add(pasteItem);
      editMenu.add(clearItem);
      themesMenu.add(darkItem);
      themesMenu.add(lightItem);

      menuBar.add(fileMenu);
      menuBar.add(editMenu);
      menuBar.add(themesMenu);

      // ------ menubar ------
      this.setJMenuBar(menuBar);
      this.add(fontLabel);
      this.add(fontSizeSpinner);
      this.add(fontColorButton);
      this.add(fontBox);
      this.add(scrollPane);
      this.setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {

      if (e.getSource()==cutItem)
         textArea.cut();
      if (e.getSource()==copyItem)
         textArea.copy();
      if (e.getSource()==pasteItem)
         textArea.paste();


      if (e.getSource()==darkItem)
      {

         textArea.setBackground(Color.DARK_GRAY);        //dark Theme

         textArea.setForeground(Color.WHITE);
      }

      if (e.getSource()==lightItem)
      {

         textArea.setBackground(new Color(107, 169, 255)); //light(blue) theme
         textArea.setForeground(Color.black);
      }


      if(e.getSource()==fontColorButton)
      {
         JColorChooser colorChooser = new JColorChooser();
         Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
         textArea.setForeground(color);
      }

      if(e.getSource()==fontBox)
      {
         textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
      }

      if(e.getSource()==openItem)
      {
         JFileChooser fileChooser = new JFileChooser();
         fileChooser.setCurrentDirectory(new File("."));
         FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
         fileChooser.setFileFilter(filter);

         int response = fileChooser.showOpenDialog(null);

         if(response == JFileChooser.APPROVE_OPTION)
         {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            Scanner fileIn = null;

            try {
               fileIn = new Scanner(file);
               if(file.isFile()) {
                  while(fileIn.hasNextLine()) {
                     String line = fileIn.nextLine()+"\n";
                     textArea.append(line);
                  }
               }
            }
            catch (FileNotFoundException e1)
            {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
            finally
            {
               fileIn.close();
            }
         }
      }
      if(e.getSource()==saveItem)
      {
         JFileChooser fileChooser = new JFileChooser();
         fileChooser.setCurrentDirectory(new File("."));

         int response = fileChooser.showSaveDialog(null);

         if(response == JFileChooser.APPROVE_OPTION)
         {
            File file;
            PrintWriter fileOut = null;

            file = new File(fileChooser.getSelectedFile().getAbsolutePath());

            try {
               fileOut = new PrintWriter(file);
               fileOut.println(textArea.getText());
            }
            catch (FileNotFoundException e1)
            {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
            finally
            {
               fileOut.close();
            }
         }
      }
      if(e.getSource()==exitItem)
      {
         System.exit(0);
      }

      else if (e.getSource() == boldButton)
      {
         if (textArea.getFont().getStyle() == Font.BOLD)
         {
            textArea.setFont(textArea.getFont().deriveFont(Font.PLAIN));
         } else
         {
            textArea.setFont(textArea.getFont().deriveFont(Font.BOLD));
         }
      }

      else if (e.getSource() == italicButton)
      {
         if (textArea.getFont().getStyle() == Font.ITALIC)
         {
            textArea.setFont(textArea.getFont().deriveFont(Font.PLAIN));
         } else
         {
            textArea.setFont(textArea.getFont().deriveFont(Font.ITALIC));
         }
      }

      else if (e.getSource() == clearItem || e.getSource() == clearButton)
      {
         Object[] options = {"Yes", "No"};
         int n = JOptionPane.showOptionDialog(this, "Are you sure to clear the text Area ?", "Question",
                 JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
         if (n == 0)
         {
            textArea.setText(null);

         }
      }
   }

   public static void main(String[] args)
   {
      new TextEditor();
   }
}