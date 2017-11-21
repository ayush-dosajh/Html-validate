/**
 * @name        Java NotePad
 * @package     notepad
 * main @file        UI.java
 * This java programm puts all the functionality on the pane with gui 
 *All the user INTERFACE is here
 *Programming tools project 
 * batch 2015-19
 *
 *group members
 *Ayush Dosajh- 
 *chinmaya K.bansal
 *Ankit Pahuja
 *
 */
 

package simplejavatexteditor;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.text.DefaultEditorKit;
import java.io.PrintWriter;

/**
 * <h1>A class where all the modules are integrated .</h1>
 *
 * <p>basic UI + buttons+ validator.</p>
 */

public class UI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private final Container container;
    private final JTextArea textArea;
    private final JMenuBar menuBar;
    private final JComboBox fontSize, fontType;
    private final JMenu menuFile, menuEdit, menuFind, menuAbout;
    private final JMenuItem newFile, openFile, saveFile, close, cut, copy, paste, clearFile, selectAll, quickFind,
            vali, aboutSoftware, wordWrap, mini;
    private final JToolBar mainToolbar;
    JButton newButton, openButton, saveButton, clearButton, quickButton, valButton, aboutButton, closeButton,minibutton;
    private final Action selectAllAction;

    // setup icons - File Menu
    private final ImageIcon newIcon = new ImageIcon("icons/new.png");
    private final ImageIcon openIcon = new ImageIcon("icons/open.png");
    private final ImageIcon saveIcon = new ImageIcon("icons/save.png");
    private final ImageIcon closeIcon = new ImageIcon("icons/close.png");

    // setup icons - Edit Menu
    private final ImageIcon clearIcon = new ImageIcon("icons/clear.png");
    private final ImageIcon cutIcon = new ImageIcon("icons/cut.png");
    private final ImageIcon copyIcon = new ImageIcon("icons/copy.png");
    private final ImageIcon pasteIcon = new ImageIcon("icons/paste.png");
    private final ImageIcon selectAllIcon = new ImageIcon("icons/selectall.png");
    private final ImageIcon wordwrapIcon = new ImageIcon("icons/wordwrap.png");

    // setup icons - Search Menu
    private final ImageIcon searchIcon = new ImageIcon("icons/search.png");

    // setup icons - Help Menu
    //private final ImageIcon aboutMeIcon = new ImageIcon("icons/about_me.png");
    private final ImageIcon aboutIcon = new ImageIcon("icons/about.png");
    private final ImageIcon validatebutton = new ImageIcon("icons/about_me.png");
    private final ImageIcon miniicon = new ImageIcon("icons/mini.png");
    private SupportedKeywords kw = new SupportedKeywords();
    private HighlightText languageHighlighter = new HighlightText(Color.GRAY);//can change hieghlight color here.
    AutoComplete autocomplete;
    private boolean hasListener = false;


    public UI()
    {
        container = getContentPane();

        // Set the initial size of the window
        setSize(700, 500);

        // Set the title of the window
        setTitle("Untitled | " + SimpleJavaTextEditor.NAME);

        // Set the default close operation (exit when it gets closed)
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // center the frame on the monitor
        setLocationRelativeTo(null);

        // Set a default font for the TextArea
        textArea = new JTextArea("", 0,0);
        textArea.setFont(new Font("Century Gothic", Font.BOLD, 12));
        textArea.setTabSize(2);
        textArea.setFont(new Font("Century Gothic", Font.BOLD, 12));
        textArea.setTabSize(2);

        /* SETTING BY DEFAULT WORD WRAP ENABLED OR TRUE */
        textArea.setLineWrap(true);

        // Set an higlighter to the JTextArea
        textArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                languageHighlighter.highLight(textArea, kw.gethtmlKeywords());
                languageHighlighter.highLight(textArea, kw.getcssKeywords());
            }
        });

        // This is why we didn't have to worry about the size of the TextArea!
        getContentPane().setLayout(new BorderLayout()); // the BorderLayout bit makes it fill it automatically
        getContentPane().add(textArea);

        // Set the Menus
        menuFile = new JMenu("File");
        menuEdit = new JMenu("Edit");
        menuFind = new JMenu("Search");
        menuAbout = new JMenu("About");
        //Font Settings menu

        // Set the Items Menu
        newFile = new JMenuItem("New", newIcon);
        openFile = new JMenuItem("Open", openIcon);
        saveFile = new JMenuItem("Save", saveIcon);
        close = new JMenuItem("Quit", closeIcon);
        clearFile = new JMenuItem("Clear", clearIcon);
        quickFind = new JMenuItem("Quick", searchIcon);
        vali = new JMenuItem("Validate", validatebutton);
        aboutSoftware = new JMenuItem("About Software", aboutIcon);
        mini = new JMenuItem("minimize", miniicon);

        menuBar = new JMenuBar();
        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuFind);

        menuBar.add(menuAbout);

        this.setJMenuBar(menuBar);

        // Set Actions:
        selectAllAction = new SelectAllAction("Select All", clearIcon, "Select all text", new Integer(KeyEvent.VK_A),
                textArea);

        this.setJMenuBar(menuBar);

        // New File
        newFile.addActionListener(this);  // Adding an action listener (so we know when it's been clicked).
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK)); // Set a keyboard shortcut
        menuFile.add(newFile); // Adding the file menu

        // Open File
        openFile.addActionListener(this);
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        menuFile.add(openFile);

        // Save File
        saveFile.addActionListener(this);
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        menuFile.add(saveFile);

        // Close File
        /*
         * Along with our "CTRL+F4" shortcut to close the window, we also have
         * the default closer, as stated at the beginning of this tutorial. this
         * means that we actually have TWO shortcuts to close:
         * 1) the default close operation (example, Alt+F4 on Windows)
         * 2) CTRL+F4, which we are
         * about to define now: (this one will appear in the label).
         */
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        close.addActionListener(this);
        menuFile.add(close);

        // Select All Text
        selectAll = new JMenuItem(selectAllAction);
        selectAll.setText("Select All");
        selectAll.setIcon(selectAllIcon);
        selectAll.setToolTipText("Select All");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        menuEdit.add(selectAll);

        // Clear File (Code)
        clearFile.addActionListener(this);
        clearFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));
        menuEdit.add(clearFile);

        // Cut Text
        cut = new JMenuItem(new DefaultEditorKit.CutAction());
        cut.setText("Cut");
        cut.setIcon(cutIcon);
        cut.setToolTipText("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));//shrtcut = crtl+x
        menuEdit.add(cut);

        // WordWrap
        wordWrap = new JMenuItem();
        wordWrap.setText("Word Wrap");
        wordWrap.setIcon(wordwrapIcon);
        wordWrap.setToolTipText("Word Wrap");

        //Short cut key or key stroke
        wordWrap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
        menuEdit.add(wordWrap);

        /* CODE FOR WORD WRAP OPERATION
         * BY DEFAULT WORD WRAPPING IS ENABLED.
        */
        wordWrap.addActionListener(new ActionListener()
        {
                public void actionPerformed(ActionEvent ev) {
                    // If wrapping is false then after clicking on menuitem the word wrapping will be enabled
                    if(textArea.getLineWrap()==false) {
                        /* Setting word wrapping to true */
                        textArea.setLineWrap(true);
                    } else {
                        // else  if wrapping is true then after clicking on menuitem the word wrapping will be disabled
                        /* Setting word wrapping to false */
                        textArea.setLineWrap(false);
                }
            }
        });

        // Copy Text
        copy = new JMenuItem(new DefaultEditorKit.CopyAction());
        copy.setText("Copy");
        copy.setIcon(copyIcon);
        copy.setToolTipText("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        menuEdit.add(copy);

        // Paste Text
        paste = new JMenuItem(new DefaultEditorKit.PasteAction());
        paste.setText("Paste");
        paste.setIcon(pasteIcon);
        paste.setToolTipText("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        menuEdit.add(paste);

        // Find Word
        quickFind.addActionListener(this);
        quickFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        menuFind.add(quickFind);

        // validate
        vali.addActionListener(this);
        vali.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        menuFind.add(vali);
    
         //minimize
         mini.addActionListener(this);
        mini.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
        menuFind.add(mini);
    
        
        
        // About Software
        aboutSoftware.addActionListener(this);
        aboutSoftware.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
        menuAbout.add(aboutSoftware);

        mainToolbar = new JToolBar();
        this.add(mainToolbar, BorderLayout.NORTH);
        // used to create space between button groups
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 50);

        newButton = new JButton(newIcon);
        newButton.setToolTipText("New");
        newButton.addActionListener(this);
        mainToolbar.add(newButton);
        mainToolbar.addSeparator();

        openButton = new JButton(openIcon);
        openButton.setToolTipText("Open");
        openButton.addActionListener(this);
        mainToolbar.add(openButton);
        mainToolbar.addSeparator();

        saveButton = new JButton(saveIcon);
        saveButton.setToolTipText("Save");
        saveButton.addActionListener(this);
        mainToolbar.add(saveButton);
        mainToolbar.addSeparator();

        clearButton = new JButton(clearIcon);
        clearButton.setToolTipText("Clear All");
        clearButton.addActionListener(this);
        mainToolbar.add(clearButton);
        mainToolbar.addSeparator();

        quickButton = new JButton(searchIcon);
        quickButton.setToolTipText("Quick Search");
        quickButton.addActionListener(this);
        mainToolbar.add(quickButton);
        mainToolbar.addSeparator();


        valButton = new JButton(validatebutton);
        valButton.setToolTipText("validate-it");
        valButton.addActionListener(this);
        mainToolbar.add(valButton);
        mainToolbar.addSeparator();

        minibutton = new JButton(miniicon);
        minibutton.setToolTipText("MINIMIZE");
        minibutton.addActionListener(this);
        mainToolbar.add(minibutton);
        mainToolbar.addSeparator();

        
        
        aboutButton = new JButton(aboutIcon);
        aboutButton.setToolTipText("About NotePad ");
        aboutButton.addActionListener(this);
        mainToolbar.add(aboutButton);
        mainToolbar.addSeparator();


        closeButton = new JButton(closeIcon);
        closeButton.setToolTipText("Quit");
        closeButton.addActionListener(this);
        mainToolbar.add(closeButton);
        mainToolbar.addSeparator();

  /****************** FONT SETTINGS SECTION ***********************/

        //FONT FAMILY SETTINGS SECTION START

        fontType = new JComboBox();

          //GETTING ALL AVAILABLE FONT FOMILY NAMES
        String [] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for (int i = 0; i < fonts.length; i++)
        {
            //Adding font family names to font[] array
             fontType.addItem ( fonts [i] );
        }
        //Setting maximize size of the fontType ComboBox
        fontType.setMaximumSize( new Dimension ( 170, 30 ));
        mainToolbar.add( fontType );
        mainToolbar.addSeparator();

        //Adding Action Listener on fontType JComboBox

        fontType.addActionListener(new ActionListener()
        {
                public void actionPerformed(ActionEvent ev)
                {
                    //Getting the selected fontType value from ComboBox
                    String p = fontType.getSelectedItem().toString();
                    //Getting size of the current font or text
                    int s = textArea.getFont().getSize();
                    textArea.setFont( new Font( p, Font.PLAIN, s));
                }
        });

        //FONT FAMILY SETTINGS SECTION END


        //FONT SIZE SETTINGS START

        fontSize = new JComboBox();

            for( int i = 5 ; i <= 100 ; i++)
            {
                fontSize.addItem( i );
            }
        fontSize.setMaximumSize( new Dimension( 70,30 ));
        mainToolbar.add( fontSize );

        fontSize.addActionListener(new ActionListener()
        {
                public void actionPerformed(ActionEvent ev)
                {
                   String sizeValue = fontSize.getSelectedItem().toString();
                    int sizeOfFont = Integer.parseInt( sizeValue );
                    String fontFamily = textArea.getFont().getFamily();

                    Font font1 = new Font( fontFamily , Font.PLAIN , sizeOfFont );
                    textArea.setFont( font1 );

                }
        });
        //FONT SIZE SETTINGS SECTION END
    }

    // Make the TextArea available to the autocomplete handler
    protected JTextArea getEditor() {
        return textArea;
    }

    // Enable autocomplete option
    public void enableAutoComplete(File file) {
        if (hasListener) {
            textArea.getDocument().removeDocumentListener(autocomplete);
            hasListener = false;
        }

        ArrayList<String> arrayList;
        String[] list = kw.getSupportedLangage();

        for (int i = 0; i < list.length; i++) {
            if (file.getName().endsWith(list[i])) {
                switch (i) {
                    case 0:
                        String[] jk = kw.getcssKeywords();
                        arrayList = kw.setKeywords(jk);
                        autocomplete = new AutoComplete(this, arrayList);
                        textArea.getDocument().addDocumentListener(autocomplete);
                        hasListener = true;
                        break;
                    case 1:
                        String[] ck = kw.gethtmlKeywords();
                        arrayList = kw.setKeywords(ck);
                        autocomplete = new AutoComplete(this, arrayList);
                        textArea.getDocument().addDocumentListener(autocomplete);
                        hasListener = true;
                        break;
                }
            }
        }
    }
    
    
public  void getcode1() 
{
String line;
line=textArea.getText();
int l,i,ec=0,pc=0,l2,flag=0,j=0;

l2= line.length();
int a[]=new int[14];
for(i=0;i<=13;i++)
{
a[i]=0;

}



try
{
FileWriter fw1= new FileWriter("Text1.txt");
fw1.write("    =======###################################======== \n ");
fw1.write("    \n=======VALIDATOR REPORT ALL THE TAGS USED======== \n ");
fw1.write("  \n  =======###################################========  \n");

for(i=0;i<l2;i++)
{
        
            
if(line.charAt(i)=='<')
{
if(line.charAt(i+1)=='h' && line.charAt(i+2)=='t'  && line.charAt(i+3)=='m' && line.charAt(i+4)=='l' && line.charAt(i+5)=='>' && l2>=5)
{pc++;a[0]=1;}

else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='h' && line.charAt(i+3)=='t' && line.charAt(i+4)=='m' && line.charAt(i+5)=='l' && line.charAt(i+6)=='>' && l2>=6 && a[0]==1)
{pc++;a[0]=0;}

else if (line.charAt(i+1)=='h' && line.charAt(i+2)=='e' && line.charAt(i+3)=='a' && line.charAt(i+4)=='d' && line.charAt(i+5)=='>' && l2>=5)
{pc++;a[1]=1;}

else if (line.charAt(i+1)=='/' && line.charAt(i+2)=='h' &&   l2>=6 && line.charAt(i+3)=='e' && line.charAt(i+4)=='a' && line.charAt(i+5)=='d' && line.charAt(i+6)=='>' && a[1]==1)
{pc++;a[1]=0;}


else if (line.charAt(i+1)=='h' && line.charAt(i+2)=='r' && line.charAt(i+3)=='>')
pc++;

else if (line.charAt(i+1)=='h' && line.charAt(i+2)>48 && line.charAt(i+2)<54 && line.charAt(i+3)=='>')
pc++;


else if(line.charAt(i+1)=='p' && line.charAt(i+2)=='>' )
{pc++; a[2]=1;}

else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='p' && line.charAt(i+3)=='>'&& a[2]==1 && l2>=3 )
{pc++; a[2]=0;}



else if(line.charAt(i+1)=='i' && line.charAt(i+2)=='>' )
{pc++;a[3]=1;}


else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='i' && line.charAt(i+3)=='>' && a[3]==1 )
{pc++; a[3]=0;}


else if(line.charAt(i+1)=='b' && line.charAt(i+2)=='>' && a[12]==1 )
{pc++;a[4]=1;}


else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='b' && line.charAt(i+3)=='>' && a[4]==1 && l2>=3)
{pc++; a[4]=0;}


else if(line.charAt(i+1)=='a' && line.charAt(i+2)=='>' && l2>=2)
{pc++;a[5]=1;}


else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='a' && line.charAt(i+3)=='>' && a[5]==1 && l2>=3)
{pc++; a[5]=0;}


else if(line.charAt(i+1)=='u' && line.charAt(i+2)=='l' &&  line.charAt(i+3)=='>' && l2>=3)
{pc++;a[6]=1;}


else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='u' &&  line.charAt(i+3)=='l' &&  line.charAt(i+4)=='>' && a[6]==1 && l2>=4)
{pc++;a[6]=0;}


else if(line.charAt(i+1)=='l' && line.charAt(i+2)=='i' &&  line.charAt(i+3)=='>' && l2>=3)
{pc++;a[7]=1;}



else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='l' &&  line.charAt(i+3)=='i' &&  line.charAt(i+4)=='>' && a[7]==1 && l2>=4)
{pc++;a[7]=0;}




else if(line.charAt(i+1)=='i' && line.charAt(i+2)=='m' &&  line.charAt(i+3)=='g' &&  line.charAt(i+4)=='>'    && l2>=4)
{pc++;a[8]=1;}



else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='i' &&  line.charAt(i+3)=='m' &&  line.charAt(i+4)=='g' &&  a[8]==1 && line.charAt(i+5)=='>' && l2>=5    )
{pc++;a[8]=0;}



else if(line.charAt(i+1)=='d' && line.charAt(i+2)=='i' &&  line.charAt(i+3)=='v' &&  line.charAt(i+4)=='>'    && l2>=4)
{pc++;a[9]=1;}


else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='d' &&  line.charAt(i+3)=='i' &&  line.charAt(i+4)=='v' &&  line.charAt(i+5)=='>'   &&  a[9]==1 && l2>=5 )
{pc++;a[9]=0;}




else if(line.charAt(i+1)=='s' && line.charAt(i+2)=='e' && line.charAt(i+3)=='c' && line.charAt(i+4)=='t' &&  line.charAt(i+5)=='i'&&  line.charAt(i+6)=='o' &&  line.charAt(i+7)=='n'&&  line.charAt(i+8)=='>'   && l2>=8)
{pc++;a[10]=1;}


else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='s' && line.charAt(i+3)=='e' && line.charAt(i+4)=='c' && line.charAt(i+5)=='t' &&  line.charAt(i+6)=='i'&&  line.charAt(i+7)=='o' &&  line.charAt(i+8)=='n'&&  line.charAt(i+9)=='>'  && a[10]==1 && l2>=9)
{pc++;a[10]=0;}



else if(line.charAt(i+1)=='e' && line.charAt(i+2)=='m' &&  line.charAt(i+3)=='>' && l2>=3)
{pc++;a[11]=1;}



else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='e' && line.charAt(i+3)=='m' &&  line.charAt(i+4)=='>' && a[11]==1 && l2>=4)
{pc++;a[11]=0;}



else if( line.charAt(i+1)=='t' && line.charAt(i+2)=='a' &&  line.charAt(i+3)=='b' &&  line.charAt(i+4)=='l' &&  line.charAt(i+5)=='e' &&  line.charAt(i+6)=='>' && l2>=6  )
{pc++;a[12]=0;}


else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='t' && line.charAt(i+3)=='a' &&  line.charAt(i+4)=='b' &&  line.charAt(i+5)=='l' &&  line.charAt(i+6)=='e' &&  line.charAt(i+7)=='>' && a[12]==1 && l2>=7)
{pc++;a[12]=0;}

else if( line.charAt(i+1)=='b' && line.charAt(i+2)=='o' &&  line.charAt(i+3)=='d' &&  line.charAt(i+4)=='y' &&  line.charAt(i+5)=='>'  && l2>=5  )
{pc++;a[13]=0;}


else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='b' && line.charAt(i+3)=='o' &&  line.charAt(i+4)=='d' &&  line.charAt(i+5)=='y' &&  line.charAt(i+6)=='y' && a[13]==1 && l2>=6)
{pc++;a[13]=0;}



else
{
j=i;
ec++; 
fw1.write("Probable error----   ");
while(line.charAt(j)!='>' && l2>j)
{
fw1.write(line.charAt(j));
j++;

}
fw1.write(line.charAt(j));
fw1.write("\n");
flag=1;
}
  
 }
 
 
 }





fw1.write("\n=========================================================\n");
fw1.write("\n TOTAL positive responses------  ");
fw1.write(Integer.toString(pc));
fw1.write("\n");
fw1.write("\n Total probable errors-------  ");
fw1.write(Integer.toString(ec));
fw1.write("\n=========================================================\n");


fw1.close();

}




catch(Exception e){System.out.println(e);}
 
 validate1();



}


public void validate1()
{
String line;
line=textArea.getText();
int l,i,c=0,pc=0,l2,flag=0,j=0,c2=0;

l2= line.length();
int a[]=new int[13];
for(i=0;i<=12;i++)
{
a[i]=0;

}



try
{
FileWriter fw1= new FileWriter("SUMMARY.txt");
fw1.write("    =======###################################======== \n ");
fw1.write("    \n======= ALL THE TAGS USED + SUMMARY of those tags======== \n ");
fw1.write("  \n  =======###################################========  \n");

for(i=0;i<l2;i++)
{
        
            
if(line.charAt(i)=='<')
{
c2++;

if(line.charAt(i+1)=='h' && line.charAt(i+2)=='t'  && line.charAt(i+3)=='m' && line.charAt(i+4)=='l' && line.charAt(i+5)=='>' && l2>=5)
{
c++;
fw1.write("<html>=------------------ Defines an HTML document \n ");
}

else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='h' && line.charAt(i+3)=='t' && line.charAt(i+4)=='m' && line.charAt(i+5)=='l' && line.charAt(i+6)=='>' && l2>=6 )
{
fw1.write("</html>=------------------ Defines an end of HTML document \n ");
c++;
}

else if(line.charAt(i+1)=='b' && line.charAt(i+2)=='o'  && line.charAt(i+3)=='d' && line.charAt(i+4)=='y' && line.charAt(i+5)=='>' && l2>=5)
{
c++;
fw1.write("<body>=------------------ The <body> tag defines the document's body.The <body> element contains all the contents of an HTML document, such as text, hyperlinks, images, tables, lists, etc. \n ");
}

else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='b' && line.charAt(i+3)=='o' && line.charAt(i+4)=='d' && line.charAt(i+5)=='y' && line.charAt(i+6)=='>' && l2>=6 )
{
fw1.write("</body>=------------------ End of body \n ");
c++;
}





else if (line.charAt(i+1)=='h' && line.charAt(i+2)=='e' && line.charAt(i+3)=='a' && line.charAt(i+4)=='d' && line.charAt(i+5)=='>' && l2>=5)
{
fw1.write("<head>=------------------ Defines information about the document \n ");
c++;
}

else if (line.charAt(i+1)=='/' && line.charAt(i+2)=='h' &&   l2>=6 && line.charAt(i+3)=='e' && line.charAt(i+4)=='a' && line.charAt(i+5)=='d' && line.charAt(i+6)=='>' && a[1]==1)
{
fw1.write("</head>=------------------ End of head tag  \n ");
c++;
}


else if (line.charAt(i+1)=='h' && line.charAt(i+2)=='r' && line.charAt(i+3)=='>')
{
fw1.write("<hr>=------------------  a self closing tag-Defines a thematic change in the content   \n ");
c++;
}

else if (line.charAt(i+1)=='h' && line.charAt(i+2)>48 && line.charAt(i+2)<54 && line.charAt(i+3)=='>')
{
fw1.write("<h1 to 6>=------------------  a self closing tag- defines heading  \n ");
c++;
}


else if(line.charAt(i+1)=='p' )
{
fw1.write("<p>=------------------  Defines a paragraph  \n ");
c++;
}

else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='p' && line.charAt(i+3)=='>'&& a[2]==1 && l2>=3 )
{
fw1.write("</p>=------------------  Defines Ending of a paragraph  \n ");
c++;
}



else if(line.charAt(i+1)=='i' && line.charAt(i+2)=='>' )
{
c++;
fw1.write("<i>=------------------  	Defines a part of text in an alternate voice or mood   \n ");

}


else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='i' && line.charAt(i+3)=='>' && a[3]==1 )
{
c++;
fw1.write("</i>=------------------  	Ending tag of <i> \n ");

}


else if(line.charAt(i+1)=='b' && line.charAt(i+2)=='>' && a[12]==1 )
{
fw1.write("<b>=------------------  	Defines a bold character or a group of bold characters   \n ");

c++;
}


else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='b' && line.charAt(i+3)=='>' && a[4]==1 && l2>=3)
{
fw1.write("<b>=------------------  	Defines the END of a bold character or a group of bold characters   \n ");
c++;
}


else if(line.charAt(i+1)=='a'  && l2>=2)
{
fw1.write("<a>=------------------  Defines a hyperlink  \n ");
c++;

}


else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='a' && line.charAt(i+3)=='>' && a[5]==1 && l2>=3)
{
c++;
fw1.write("</a>=------------------  Ends a hyperlink  \n ");
}


else if(line.charAt(i+1)=='u' && line.charAt(i+2)=='l'  && l2>=3)
{
c++;
fw1.write("<ul>=------------------  The <ul> tag defines an unordered (bulleted) list. Use the <ul> tag together with the <li> tag to create unordered lists.  \n ");

}


else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='u' &&  line.charAt(i+3)=='l' &&  line.charAt(i+4)=='>' && a[6]==1 && l2>=4)
{
c++;
fw1.write("</ul>=------------------  finishes/ marks the end of unordered list  \n ");
}


else if(line.charAt(i+1)=='l' && line.charAt(i+2)=='i' &&  line.charAt(i+3)=='>' && l2>=3)
{
c++;
fw1.write("<li>=------------------  The <li> tag defines a list item.The <li> tag is used in ordered lists(<ol>), unordered lists (<ul>), and in menu lists (<menu>). \n ");

}



else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='l' &&  line.charAt(i+3)=='i' &&  line.charAt(i+4)=='>' && a[7]==1 && l2>=4)
{
c++;
fw1.write("</li>=------------------  ending of list element  \n ");
}




else if(line.charAt(i+1)=='i' && line.charAt(i+2)=='m' &&  line.charAt(i+3)=='g'    && l2>=4)
{
c++;
fw1.write("<img>=------------------  The <img> tag defines an image in an HTML page ALSO The <img> tag has two required attributes: src and alt.  \n ");

}



else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='i' &&  line.charAt(i+3)=='m' &&  line.charAt(i+4)=='g' &&  a[8]==1 && line.charAt(i+5)=='>' && l2>=5    )
{
c++;
fw1.write("</img>=------------------  ending of image tag  \n ");

}



else if(line.charAt(i+1)=='d' && line.charAt(i+2)=='i' &&  line.charAt(i+3)=='v' &&  line.charAt(i+4)=='>'    && l2>=4)
{
c++;
fw1.write("</div>=------------------  The <div> tag defines a division or a section in an HTML document Also The <div> tag is used to group block-elements to format them with CSS.  \n ");

}


else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='d' &&  line.charAt(i+3)=='i' &&  line.charAt(i+4)=='v' &&  line.charAt(i+5)=='>'   &&  a[9]==1 && l2>=5 )
{
c++;
fw1.write("</div>=------------------  closing of the div tag");
}




else if(line.charAt(i+1)=='s' && line.charAt(i+2)=='e' && line.charAt(i+3)=='c' && line.charAt(i+4)=='t' &&  line.charAt(i+5)=='i'&&  line.charAt(i+6)=='o' &&  line.charAt(i+7)=='n'&&  line.charAt(i+8)=='>'   && l2>=8)
{
c++;
fw1.write("<section>=------------------  Defines defines the starting of a section used  eg news section, nav section etc.  \n ");

}


else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='s' && line.charAt(i+3)=='e' && line.charAt(i+4)=='c' && line.charAt(i+5)=='t' &&  line.charAt(i+6)=='i'&&  line.charAt(i+7)=='o' &&  line.charAt(i+8)=='n'&&  line.charAt(i+9)=='>'  && a[10]==1 && l2>=9)
{
c++;
fw1.write("</section>=------------------  Defines the END of the section(part)  \n ");
}



else if(line.charAt(i+1)=='e' && line.charAt(i+2)=='m' &&  line.charAt(i+3)=='>' && l2>=3)
{
c++;
fw1.write("<em>=------------------  Tells html to   emphasize on the text (italics)   \n ");
}



else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='e' && line.charAt(i+3)=='m' &&  line.charAt(i+4)=='>' && a[11]==1 && l2>=4)
{
c++;
fw1.write("</em>=------------------  Tells html to stop  emphasize on the text (italics)  \n ");
}



else if( line.charAt(i+1)=='t' && line.charAt(i+2)=='a' &&  line.charAt(i+3)=='b' &&  line.charAt(i+4)=='l' &&  line.charAt(i+5)=='e' &&  line.charAt(i+6)=='>' && l2>=6  )
{
c++;
fw1.write("<table>=------------------  Defines a Table  \n ");
}


else if(line.charAt(i+1)=='/' && line.charAt(i+2)=='t' && line.charAt(i+3)=='a' &&  line.charAt(i+4)=='b' &&  line.charAt(i+5)=='l' &&  line.charAt(i+6)=='e' &&  line.charAt(i+7)=='>' && a[12]==1 && l2>=7)
{
c++;
fw1.write("</table>=------------------  Defines the End a table  \n ");
}




  
 }
 
 
 }



fw1.write("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");
fw1.write("\n TOTAL total tags recognized ------  ");
fw1.write(Integer.toString(c));
fw1.write("\n");
fw1.write("\n TOTAL total tags found including error tags and not defined tags ------  ");
fw1.write(Integer.toString(c2));
fw1.write("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");


fw1.close();
}

catch(Exception e){System.out.println(e);}



}

public void minimize()
{
int i,l;
String s2;
s2=textArea.getText();
l=s2.length();
try
{
FileWriter fw3= new FileWriter("minimized.txt");

//s2.replaceAll("\\s+","");//Regular xpression for removal of whitespaces
//if(s2.charAt(i)==' ' ||s2.charAt(i)==32 ) {continue;} could alo be used inside a loop; 

for(i=0;i<l;i++)
{

if(s2.charAt(i)==' ' || s2.charAt(i)=='\n' ) {continue;}
else
fw3.write(s2.charAt(i));
}



fw3.close();
}


catch(Exception e){System.out.println(e);}



}





    public void actionPerformed (ActionEvent e)  {
        // If the source of the event was our "close" option
        if (e.getSource() == close || e.getSource() == closeButton) {
            this.dispose(); // dispose all resources and close the application
        }
        // If the source was the "new" file option
        else if (e.getSource() == newFile || e.getSource() == newButton) {
            FEdit.clear(textArea);
        }

        // If the source was the "open" option
        else if (e.getSource() == openFile || e.getSource() == openButton) {
            JFileChooser open = new JFileChooser(); // open up a file chooser (a dialog for the user to  browse files to open)
            int option = open.showOpenDialog(this); // get the option that the user selected (approve or cancel)

            /*
             * NOTE: because we are OPENing a file, we call showOpenDialog~ if
             * the user clicked OK, we have "APPROVE_OPTION" so we want to open
             * the file
             */
            if (option == JFileChooser.APPROVE_OPTION) {
                FEdit.clear(textArea); // clear the TextArea before applying the file contents
                try {
                    File openFile = open.getSelectedFile();
                    setTitle(openFile.getName() + " | " + SimpleJavaTextEditor.NAME);
                    Scanner scan = new Scanner(new FileReader(openFile.getPath()));
                    while (scan.hasNext())
                        textArea.append(scan.nextLine() + "\n");

                    enableAutoComplete(openFile);
                } catch (Exception ex) { // catch any exceptions, and...
                    // ...write to the debug console
                    System.out.println(ex.getMessage());
                }
            }
        }
        // If the source of the event was the "save" option
        else if (e.getSource() == saveFile || e.getSource() == saveButton) {
            // Open a file chooser
            JFileChooser fileChoose = new JFileChooser();
            // Open the file, only this time we call
            int option = fileChoose.showSaveDialog(this);

            /*
             * ShowSaveDialog instead of showOpenDialog if the user clicked OK
             * (and not cancel)
             */
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    File openFile = fileChoose.getSelectedFile();
                    setTitle(openFile.getName() + " | " + SimpleJavaTextEditor.NAME);

                    BufferedWriter out = new BufferedWriter(new FileWriter(openFile.getPath()));
                    out.write(textArea.getText());
                    out.close();

                    enableAutoComplete(openFile);

                } catch (Exception ex) { // again, catch any exceptions and...
                    // ...write to the debug console
                    System.out.println(ex.getMessage());
                }
            }
        }

        // Clear File (Code)
        if (e.getSource() == clearFile || e.getSource() == clearButton) {
            FEdit.clear(textArea);
        }
        // Find
        if (e.getSource() == quickFind || e.getSource() == quickButton) {
            new Find(textArea);
        }

        
        else if (e.getSource() == vali || e.getSource() == valButton) {
                getcode1();


        }
        
         else if (e.getSource() == mini || e.getSource() == minibutton) {
                minimize();


        }
        
           // About Software
        else if (e.getSource() == aboutSoftware || e.getSource() == aboutButton) {
            new About(this).software();
        }

    }

    class SelectAllAction extends AbstractAction {
        /**
         * Used for Select All function
         */
        private static final long serialVersionUID = 1L;

        public SelectAllAction(String text, ImageIcon icon, String desc, Integer mnemonic, final JTextArea textArea) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            textArea.selectAll();
        }
    }
 
    
    }
   
