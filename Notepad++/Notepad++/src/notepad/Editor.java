/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;

/**
 *
 * @author Shreyas
 */
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import static java.awt.Color.RED;
import static java.awt.Color.red;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.awt.Font;
import java.awt.datatransfer.Clipboard;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Stack;
import javafx.scene.input.KeyCode;
import static javax.swing.JFileChooser.FILES_ONLY;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

public class Editor extends javax.swing.JFrame {

    /**
     * Creates new form Editor
     */
    
    
    
    public Editor() {
        Notepad.windowCount++;
        setTitle(title);
        initComponents();
        prev = new FixedStack<>(undomoves);
        prev.push("");
        fontdialog=new FontSelect(this);
        
        tln = new TextLineNumber(textArea);
        jScrollPane4.setRowHeaderView(tln);
        
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        Thread coloring = new Thread(new Runnable(){
            @Override
            public void run() {
                while (true) {
                    RSyntaxTextArea tmp=new RSyntaxTextArea();
                    tmp.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
                    textArea=tmp;
                }
            }
        });
        //coloring.start();
		//textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		
        Thread mouseLoc = new Thread(new Runnable(){
            @Override
            public void run(){
                while (true) {
                    if (showMouse) {
                        //System.out.println("mouse");
                        int x=java.awt.MouseInfo.getPointerInfo().getLocation().x;
                        int y=java.awt.MouseInfo.getPointerInfo().getLocation().y;
                        txtMouseX.setText("X : "+x+"");
                        txtMouseY.setText("Y : "+y+"");
                        int pos=textArea.getCaretPosition();
                        String tmp=textArea.getText();
                        if ("".equals(tmp))
                            continue;
                        int rows=0,cols=0;
                        for (int i=0;i<pos&&i<tmp.length();i++) {
                            cols++;
                            if (tmp.charAt(i)=='\n') {
                                rows++;
                                cols=0;
                            }
                        }
                        //System.out.println((rows+1)+" "+cols);
                        txtCarretX.setText("Rows : "+(rows+1)+"");
                        txtCarretY.setText("Cols :  "+cols+"");
                    }
                    else {
                        txtCarretX.setText("");
                        txtCarretY.setText("");
                        txtMouseX.setText("");
                        txtMouseY.setText("");
                    }
                }
            }
        });
        mouseLoc.start();
        
    }
    
    class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
        public MyHighlightPainter(Color color) {
            super(color);
        }
    }
    
    Highlighter.HighlightPainter myHighlightPainter =  new MyHighlightPainter(Color.red);
    public void highlight(JTextComponent textComp,String pattern) {
        removeHighlight(textComp);
        try {
            Highlighter hilite = textComp.getHighlighter();
            Document doc = textComp.getDocument();
            String text=doc.getText(0,doc.getLength());
            int pos=0;
            while ((pos=text.toUpperCase().indexOf(pattern.toUpperCase(),pos))>=0) {
                hilite.addHighlight(pos,pos+pattern.length(),myHighlightPainter);
                pos+=pattern.length();
            } 
        } catch (Exception e) {
            
        }
    }
    public void removeHighlight(JTextComponent textComp) {
        Highlighter hilite = textComp.getHighlighter();
        Highlighter.Highlight[] hilites = hilite.getHighlights();
        for (int i=0;i<hilites.length;i++) {
            if (hilites[i].getPainter() instanceof MyHighlightPainter) {
                hilite.removeHighlight(hilites[i]);
            }
        }
    }
    
//    public void getCaretPixelPosition() {
//        try {
//            int cpos = textArea.getCaretPosition();
//            Font font = textArea.getStyledDocument().getFont(textArea.getStyledDocument().getLogicalStyle(cpos));
//            FontMetrics metrics = getFontMetrics(font);
//
//            int lineNum = a.getLineOfOffset(cpos);
//            int y = lineNum * metrics.getHeight();
//            int lineStart = a.getLineStartOffset(lineNum);
//            int x = metrics.stringWidth(a.getText().substring(lineStart, cpos));
//
//                        return new Point(x,y);
//
//        } catch(BadLocationException e) {}
//        return null;
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        itemPopCut = new javax.swing.JMenuItem();
        itemPopCopy = new javax.swing.JMenuItem();
        itemPopPaste = new javax.swing.JMenuItem();
        itemPopupCompile = new javax.swing.JMenuItem();
        itemPopupRun = new javax.swing.JMenuItem();
        itemPopupFind = new javax.swing.JMenuItem();
        itemPopupReplace = new javax.swing.JMenuItem();
        itemPopupRemoveHighlight = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        textArea = new RSyntaxTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        textDebug = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        txtMouseX = new javax.swing.JLabel();
        txtMouseY = new javax.swing.JLabel();
        txtCarretX = new javax.swing.JLabel();
        txtCarretY = new javax.swing.JLabel();
        btnC = new javax.swing.JRadioButton();
        btnJava = new javax.swing.JRadioButton();
        txtInput = new javax.swing.JTextField();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 30), new java.awt.Dimension(0, 30), new java.awt.Dimension(32767, 30));
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        itemNew = new javax.swing.JMenuItem();
        itemNewWindow = new javax.swing.JMenuItem();
        itemOpenFile = new javax.swing.JMenuItem();
        itemOpen = new javax.swing.JMenuItem();
        itemSave = new javax.swing.JMenuItem();
        itemSaveAs = new javax.swing.JMenuItem();
        itemExit = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        itemUndo = new javax.swing.JMenuItem();
        itemCut = new javax.swing.JMenuItem();
        itemCopy = new javax.swing.JMenuItem();
        itemPaste = new javax.swing.JMenuItem();
        itemDelete = new javax.swing.JMenuItem();
        itemFind = new javax.swing.JMenuItem();
        itemFindAll = new javax.swing.JMenuItem();
        itemFindNext = new javax.swing.JMenuItem();
        itemReplace = new javax.swing.JMenuItem();
        itemGoto = new javax.swing.JMenuItem();
        itemSelectAll = new javax.swing.JMenuItem();
        itemTime = new javax.swing.JMenuItem();
        menuFormat = new javax.swing.JMenu();
        itemFont = new javax.swing.JMenuItem();
        itemAutoBrac = new javax.swing.JCheckBoxMenuItem();
        itemRepeatHighlight = new javax.swing.JCheckBoxMenuItem();
        itemSuggest = new javax.swing.JCheckBoxMenuItem();
        itemComplete = new javax.swing.JMenuItem();
        menuRun = new javax.swing.JMenu();
        itemCompile = new javax.swing.JMenuItem();
        itemRun = new javax.swing.JMenuItem();
        itemStop = new javax.swing.JMenuItem();
        menuView = new javax.swing.JMenu();
        itemMouse = new javax.swing.JCheckBoxMenuItem();
        itemLineNumber = new javax.swing.JCheckBoxMenuItem();
        menuHelp = new javax.swing.JMenu();
        itemViewHelp = new javax.swing.JMenuItem();
        itemAbout = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        itemPopCut.setText("Cut");
        itemPopCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPopCutActionPerformed(evt);
            }
        });
        jPopupMenu1.add(itemPopCut);

        itemPopCopy.setText("Copy");
        itemPopCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPopCopyActionPerformed(evt);
            }
        });
        jPopupMenu1.add(itemPopCopy);

        itemPopPaste.setText("Paste");
        itemPopPaste.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                itemPopPasteMouseReleased(evt);
            }
        });
        itemPopPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPopPasteActionPerformed(evt);
            }
        });
        jPopupMenu1.add(itemPopPaste);

        itemPopupCompile.setText("Compile");
        itemPopupCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPopupCompileActionPerformed(evt);
            }
        });
        jPopupMenu1.add(itemPopupCompile);

        itemPopupRun.setText("Run");
        itemPopupRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPopupRunActionPerformed(evt);
            }
        });
        jPopupMenu1.add(itemPopupRun);

        itemPopupFind.setText("Find");
        itemPopupFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPopupFindActionPerformed(evt);
            }
        });
        jPopupMenu1.add(itemPopupFind);

        itemPopupReplace.setText("Replace");
        itemPopupReplace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPopupReplaceActionPerformed(evt);
            }
        });
        jPopupMenu1.add(itemPopupReplace);

        itemPopupRemoveHighlight.setText("Remove Highlight");
        itemPopupRemoveHighlight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPopupRemoveHighlightActionPerformed(evt);
            }
        });
        jPopupMenu1.add(itemPopupRemoveHighlight);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        jTree1.setModel(new FileSystemModel(new File("C:\\Users\\ssony\\Desktop")));
        jTree1.setMaximumSize(new java.awt.Dimension(200, 64));
        jTree1.setPreferredSize(new java.awt.Dimension(100, 64));
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTree1);

        jSplitPane1.setLeftComponent(jScrollPane2);

        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });

        textArea.setColumns(20);
        textArea.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        textArea.setRows(5);
        textArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                textAreaMouseReleased(evt);
            }
        });
        textArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textAreaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textAreaKeyTyped(evt);
            }
        });
        jScrollPane4.setViewportView(textArea);

        textDebug.setEditable(false);
        textDebug.setColumns(20);
        textDebug.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        textDebug.setForeground(new java.awt.Color(255, 51, 51));
        textDebug.setRows(5);
        textDebug.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textDebugMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(textDebug);

        jSeparator1.setCursor(new java.awt.Cursor(java.awt.Cursor.N_RESIZE_CURSOR));
        jSeparator1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jSeparator1MouseDragged(evt);
            }
        });

        txtMouseX.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        txtMouseX.setText("0");

        txtMouseY.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        txtMouseY.setText("0");

        txtCarretX.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        txtCarretX.setText("Rows : 0");

        txtCarretY.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        txtCarretY.setText("Cols : 0");

        btnC.setSelected(true);
        btnC.setText("C/C++");
        btnC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCActionPerformed(evt);
            }
        });

        btnJava.setText("Java");
        btnJava.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJavaActionPerformed(evt);
            }
        });

        txtInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInputActionPerformed(evt);
            }
        });
        txtInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtInputKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
            .addComponent(jScrollPane3)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 110, Short.MAX_VALUE)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(txtMouseX, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMouseY, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCarretX, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCarretY, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnJava)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtInput, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnJava)
                        .addComponent(btnC)
                        .addComponent(txtCarretY, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCarretX, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMouseY, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMouseX, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jSplitPane1.setRightComponent(jPanel2);

        jTabbedPane1.addTab("tab1", jSplitPane1);

        menuFile.setText("File");

        itemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        itemNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/new.png"))); // NOI18N
        itemNew.setText("New");
        itemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNewActionPerformed(evt);
            }
        });
        menuFile.add(itemNew);

        itemNewWindow.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        itemNewWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/newWindow.png"))); // NOI18N
        itemNewWindow.setText("New Window");
        itemNewWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNewWindowActionPerformed(evt);
            }
        });
        menuFile.add(itemNewWindow);

        itemOpenFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/treeFile.png"))); // NOI18N
        itemOpenFile.setText("Open File");
        itemOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemOpenFileActionPerformed(evt);
            }
        });
        menuFile.add(itemOpenFile);

        itemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        itemOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/open.png"))); // NOI18N
        itemOpen.setText("Open ..");
        itemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemOpenActionPerformed(evt);
            }
        });
        menuFile.add(itemOpen);

        itemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        itemSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save.png"))); // NOI18N
        itemSave.setText("Save");
        itemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSaveActionPerformed(evt);
            }
        });
        menuFile.add(itemSave);

        itemSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        itemSaveAs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/saveAs.png"))); // NOI18N
        itemSaveAs.setText("Save As ..");
        itemSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSaveAsActionPerformed(evt);
            }
        });
        menuFile.add(itemSaveAs);

        itemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        itemExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exit.png"))); // NOI18N
        itemExit.setText("Exit");
        itemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemExitActionPerformed(evt);
            }
        });
        menuFile.add(itemExit);

        jMenuBar1.add(menuFile);

        menuEdit.setText("Edit");

        itemUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        itemUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/undo.png"))); // NOI18N
        itemUndo.setText("Undo");
        itemUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemUndoActionPerformed(evt);
            }
        });
        menuEdit.add(itemUndo);

        itemCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        itemCut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cut.png"))); // NOI18N
        itemCut.setText("Cut");
        itemCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCutActionPerformed(evt);
            }
        });
        menuEdit.add(itemCut);

        itemCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        itemCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/copy.png"))); // NOI18N
        itemCopy.setText("Copy");
        itemCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCopyActionPerformed(evt);
            }
        });
        menuEdit.add(itemCopy);

        itemPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        itemPaste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/paste.png"))); // NOI18N
        itemPaste.setText("Paste");
        itemPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPasteActionPerformed(evt);
            }
        });
        menuEdit.add(itemPaste);

        itemDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        itemDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete.png"))); // NOI18N
        itemDelete.setText("Delete");
        itemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemDeleteActionPerformed(evt);
            }
        });
        menuEdit.add(itemDelete);

        itemFind.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        itemFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/find.png"))); // NOI18N
        itemFind.setText("Find ..");
        itemFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemFindActionPerformed(evt);
            }
        });
        menuEdit.add(itemFind);

        itemFindAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        itemFindAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/findNext.png"))); // NOI18N
        itemFindAll.setText("Find All");
        itemFindAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemFindAllActionPerformed(evt);
            }
        });
        menuEdit.add(itemFindAll);

        itemFindNext.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        itemFindNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/findNext.png"))); // NOI18N
        itemFindNext.setText("Find Next");
        itemFindNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemFindNextActionPerformed(evt);
            }
        });
        menuEdit.add(itemFindNext);

        itemReplace.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        itemReplace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/replace.png"))); // NOI18N
        itemReplace.setText("Replace ..");
        itemReplace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemReplaceActionPerformed(evt);
            }
        });
        menuEdit.add(itemReplace);

        itemGoto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        itemGoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/goto.png"))); // NOI18N
        itemGoto.setText("Go To");
        itemGoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGotoActionPerformed(evt);
            }
        });
        menuEdit.add(itemGoto);

        itemSelectAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        itemSelectAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/selectAll.png"))); // NOI18N
        itemSelectAll.setText("Select All");
        itemSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSelectAllActionPerformed(evt);
            }
        });
        menuEdit.add(itemSelectAll);

        itemTime.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        itemTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/date.png"))); // NOI18N
        itemTime.setText("Time/Date");
        itemTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemTimeActionPerformed(evt);
            }
        });
        menuEdit.add(itemTime);

        jMenuBar1.add(menuEdit);

        menuFormat.setText("Format");

        itemFont.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/font.png"))); // NOI18N
        itemFont.setText("Font");
        itemFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemFontActionPerformed(evt);
            }
        });
        menuFormat.add(itemFont);

        itemAutoBrac.setSelected(true);
        itemAutoBrac.setText("Auto Bracket Completion");
        itemAutoBrac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAutoBracActionPerformed(evt);
            }
        });
        menuFormat.add(itemAutoBrac);

        itemRepeatHighlight.setText("Repeating words Highlight");
        itemRepeatHighlight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRepeatHighlightActionPerformed(evt);
            }
        });
        menuFormat.add(itemRepeatHighlight);

        itemSuggest.setSelected(true);
        itemSuggest.setText("Auto Completion");
        menuFormat.add(itemSuggest);

        itemComplete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE, java.awt.event.InputEvent.CTRL_MASK));
        itemComplete.setText("Auto Complete");
        itemComplete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCompleteActionPerformed(evt);
            }
        });
        menuFormat.add(itemComplete);

        jMenuBar1.add(menuFormat);

        menuRun.setText("Run");

        itemCompile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.CTRL_MASK));
        itemCompile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/compile.png"))); // NOI18N
        itemCompile.setText("Compile");
        itemCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCompileActionPerformed(evt);
            }
        });
        menuRun.add(itemCompile);

        itemRun.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        itemRun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/run.png"))); // NOI18N
        itemRun.setText("Run");
        itemRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRunActionPerformed(evt);
            }
        });
        menuRun.add(itemRun);

        itemStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/stop.png"))); // NOI18N
        itemStop.setText("Stop");
        itemStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemStopActionPerformed(evt);
            }
        });
        menuRun.add(itemStop);

        jMenuBar1.add(menuRun);

        menuView.setText("View");

        itemMouse.setText("Show Mouse Coordinates");
        itemMouse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMouseActionPerformed(evt);
            }
        });
        menuView.add(itemMouse);

        itemLineNumber.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        itemLineNumber.setSelected(true);
        itemLineNumber.setText("Show Line Numbers");
        menuView.add(itemLineNumber);

        jMenuBar1.add(menuView);

        menuHelp.setText("Help");

        itemViewHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/help.png"))); // NOI18N
        itemViewHelp.setText("View Help");
        itemViewHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemViewHelpActionPerformed(evt);
            }
        });
        menuHelp.add(itemViewHelp);

        itemAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/about.png"))); // NOI18N
        itemAbout.setText("About");
        itemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAboutActionPerformed(evt);
            }
        });
        menuHelp.add(itemAbout);

        jMenuBar1.add(menuHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        setSize(new java.awt.Dimension(751, 579));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void itemNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemNewActionPerformed
        // TODO add your handling code here:
        if (change) {
            int option=JOptionPane.showConfirmDialog(null,"Do you want to save changes to "+title);
            if (option==0) {
                // Yes
                if (title.equals(defaultTitle)) {
                    saveAs();
                }
                else {
                    save();
                }
                title=defaultTitle;
                setTitle(title);
                textArea.setText("");
            }
            else if (option==1) {
                // No
                title=defaultTitle;
                setTitle(title);
                textArea.setText("");
            }
        }
        else {
            title=defaultTitle;
            setTitle(title);
            textArea.setText("");
        }
    }//GEN-LAST:event_itemNewActionPerformed

    private void itemOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemOpenActionPerformed
        // TODO add your handling code here:
        openFile();
    }//GEN-LAST:event_itemOpenActionPerformed

    private void itemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSaveActionPerformed
        // TODO add your handling code here:
        if (title.equals(defaultTitle)) {
            saveAs();
        }
        else {
            save();
        }
    }//GEN-LAST:event_itemSaveActionPerformed

    private void itemSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSaveAsActionPerformed
        // TODO add your handling code here:
        saveAs();
    }//GEN-LAST:event_itemSaveAsActionPerformed

    private void itemNewWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemNewWindowActionPerformed
        // TODO add your handling code here:
        new Editor().setVisible(true);
    }//GEN-LAST:event_itemNewWindowActionPerformed

    private void itemUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemUndoActionPerformed
        // TODO add your handling code here:
        if (prev.size()<=0)
            return;
        String txt=prev.pop();
        textArea.setText(txt);
        change=true;
    }//GEN-LAST:event_itemUndoActionPerformed

    private void itemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemExitActionPerformed
        // TODO add your handling code here:
        if (change) {
            int option=JOptionPane.showConfirmDialog(null,"Do you want to save changes to "+title);
            if (option==0) {
                // Yes
                if (title.equals(defaultTitle)) {
                    saveAs();
                }
                else {
                    save();
                }
                exit();
            }
            else if (option==1) {
                exit();
            }
        }
        else {
            exit();
        }
    }//GEN-LAST:event_itemExitActionPerformed

    private void itemFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemFindActionPerformed
        // TODO add your handling code here:
        find(0,true,false); // start from begining find an new word and don't consider case sensitive
    }//GEN-LAST:event_itemFindActionPerformed

    private void itemCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCopyActionPerformed
        // TODO add your handling code here:
        clipboard=textArea.getSelectedText();
    }//GEN-LAST:event_itemCopyActionPerformed

    private void itemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemDeleteActionPerformed
        // TODO add your handling code here:
        prev.push(textArea.getText());
        textArea.replaceSelection("");
    }//GEN-LAST:event_itemDeleteActionPerformed

    private void itemCutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCutActionPerformed
        // TODO add your handling code here:
        prev.push(textArea.getText());
        clipboard=textArea.getSelectedText();
        textArea.replaceSelection("");
    }//GEN-LAST:event_itemCutActionPerformed

    private void itemPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPasteActionPerformed
        // TODO add your handling code here:
        prev.push(textArea.getText());
        textArea.insert(clipboard,textArea.getCaretPosition());
    }//GEN-LAST:event_itemPasteActionPerformed

    private void itemSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSelectAllActionPerformed
        // TODO add your handling code here:
        textArea.selectAll();
    }//GEN-LAST:event_itemSelectAllActionPerformed

    private void itemFindNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemFindNextActionPerformed
        // TODO add your handling code here:
        find(start+1,false,false); // continue from before and its not a new word and don't consider case sensitive
    }//GEN-LAST:event_itemFindNextActionPerformed

    private void itemReplaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemReplaceActionPerformed
        // TODO add your handling code here:
        callReplaceDialog();
    }//GEN-LAST:event_itemReplaceActionPerformed

    private void itemGotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGotoActionPerformed
        // TODO add your handling code here:
        String tmp=JOptionPane.showInputDialog("Go to Line no.");
        try {
            int line=Integer.valueOf(tmp);
            String data=textArea.getText();
            int len=data.length();
            int cnt=1;
            int i;
            for (i=0;i<len;i++) {
                char c=data.charAt(i);
                if (cnt==line)
                    break;
                if (c=='\n')
                    cnt++;
            }
            System.out.println(cnt);
            textArea.setCaretPosition(i);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Wrong Input");
        }
    }//GEN-LAST:event_itemGotoActionPerformed

    private void itemTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemTimeActionPerformed
        // TODO add your handling code here:
        prev.push(textArea.getText());
        textArea.insert(time(),textArea.getCaretPosition());
    }//GEN-LAST:event_itemTimeActionPerformed

    private void itemFontActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemFontActionPerformed
        // TODO add your handling code here:
        callFontSelectDialog();
    }//GEN-LAST:event_itemFontActionPerformed

    private void itemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAboutActionPerformed
        // TODO add your handling code here:
        try {
            File infoPage = new File(getClass().getResource("/Webpage/index.html").getFile());
            String path=infoPage.toString();
            if (path.indexOf('.')!=path.lastIndexOf('.')) {
                // .jar file
                int a=path.indexOf('.');
                String name="Notepad";
                path=path.substring(0,a-name.length())+"index.html";
            }
            //String path="C:\\Users\\ssony\\Documents\\NetBeansProjects\\Notepad\\src\\Webpage\\index.html";
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+path);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Error opening");
        }
    }//GEN-LAST:event_itemAboutActionPerformed

    private void itemViewHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemViewHelpActionPerformed
        // TODO add your handling code here:
        new About(this).setVisible(true);
    }//GEN-LAST:event_itemViewHelpActionPerformed

    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        // TODO add your handling code here:
        Jtreevar=jTree1.getSelectionPath().toString().replaceAll("[\\[\\]]","").replace(", ","\\");
    }//GEN-LAST:event_jTree1MouseClicked

    private void itemOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemOpenFileActionPerformed
        // TODO add your handling code here:
        try {
            File Selection  = new File(Jtreevar);
            if (Selection.exists()) {
                System.out.println(Jtreevar);
                filePath=Jtreevar;
                File openFile = new File(filePath);
                FileReader fr = new FileReader(openFile);
                BufferedReader br = new BufferedReader(fr);
                textArea.read(br, null);
                
//                if (Desktop.isDesktopSupported()) {
//                    Desktop.getDesktop().open(Selection);
//                }
//                else {
//                    JOptionPane.showMessageDialog(this,"awt Desktop not supported","Error",JOptionPane.INFORMATION_MESSAGE);
//                }
            }
            else {
                JOptionPane.showMessageDialog(this,"awt Desktop not supported","Error",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_itemOpenFileActionPerformed

    private void itemCompileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCompileActionPerformed
        // TODO add your handling code here:
        long t1=System.currentTimeMillis();
        inFile=filePath.substring(filePath.lastIndexOf('\\')+1); // File name with extension
        outFile=inFile.substring(0,inFile.lastIndexOf('.')); // file name without extension
        fileDir=filePath.substring(0,filePath.lastIndexOf('\\')); // file path without actual file
        
        //String ext=filePath.substring(filePath.lastIndexOf('.'));
        //pb = new ProcessBuilder("cmd", "/C", "g++ " + "\"" + filepath2 + "\\" + name + "\"" + " -o \"" + name2+"\"");
        
        try {
            ProcessBuilder pb;
            if (cpp) {
                pb = new ProcessBuilder("cmd", "/C", "gcc " + "\"" + fileDir + "\\" +inFile+ "\"" + " -o \"" + outFile+"\"");
                pb.directory(new File(fileDir));
            }
            else {
                pb = new ProcessBuilder("cmd","/C","javac "+filePath);
                fileDir="C:\\Program Files\\Java\\jdk1.8.0_111\\bin";
                pb.directory(new File(fileDir));
            }
            
            Process p = pb.start();
            textDebug.setText("");
            p.waitFor();
            int x = p.exitValue();
            if (x == 0) {
                long t2=System.currentTimeMillis();
                //JOptionPane.showMessageDialog(null,"Compilation Completed !");
                textDebug.append("Done compiling\n");
                textDebug.append("Compilation Time : "+((t2-t1)/1000.0)+" sec\n");
            }
            else
            {
                BufferedReader r = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                String out;
                while ((out = r.readLine()) != null)
                {
                    String msg=out + System.getProperty("line.separator");
                    System.out.println(msg);
                    System.out.println("Compiler : "+out);
                    textDebug.append(out+"\n");
                }
                long t2=System.currentTimeMillis();
                textDebug.append("Compilation Time : "+((t2-t1)/1000.0)+" sec\n");
            }
        }
        catch (Exception e) {
            System.out.println("Error ");
        }
        
    }//GEN-LAST:event_itemCompileActionPerformed

    private void textDebugMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textDebugMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_textDebugMouseClicked

    private void itemRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemRunActionPerformed
        // TODO add your handling code here:
        save();
        Thread compileRun = new Thread(new Runnable(){
            @Override
            public void run() {
                inFile=filePath.substring(filePath.lastIndexOf('\\')+1);
                outFile=inFile.substring(0,inFile.lastIndexOf('.'));
                fileDir=filePath.substring(0,filePath.lastIndexOf('\\'));
                //String ext=filePath.substring(filePath.lastIndexOf('.'));
                //pb = new ProcessBuilder("cmd", "/C", "g++ " + "\"" + filepath2 + "\\" + name + "\"" + " -o \"" + name2+"\"");
                try {
                    //ProcessBuilder pb = new ProcessBuilder("cmd", "/C", outFile);
                    ProcessBuilder pb;
                    if (cpp) {
                        pb = new ProcessBuilder("cmd","/c",outFile);
                        pb.directory(new File(fileDir));
                    }
                    else {
                        pb = new ProcessBuilder("cmd","/c","java "+outFile);
                        //fileDir="C:\\Program Files\\Java\\jdk1.8.0_111\\bin";
                        pb.directory(new File(fileDir));
                    }
                    Process p = pb.start();
                    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    //PrintWriter pw = new PrintWriter(p.getOutputStream());
                    String inp=txtInput.getText()+"\n";
                    
                    byte buffer[] = inp.getBytes();//new byte[100];
                    //buffer=new String("5\n").getBytes();
                    OutputStream os =(p.getOutputStream());
                    os.write(buffer,0,buffer.length);
                    os.flush();
                    String str;
                    textDebug.setText("");
                    long t1=System.currentTimeMillis();
                    while ((str=br.readLine())!=null) {
                        System.out.println(str);
                        textDebug.append(str+"\n");
                        if (terminate) {
                            break;
                        }
                        try {
                            ;//Thread.sleep(10);
                        } catch (Exception e) {
                            ;
                        }
                    }
                    long t2=System.currentTimeMillis();
                    System.out.println("show");
                    textDebug.append("\nRun completed !\n");
                    textDebug.append("Elapsed Time : "+((t2-t1)/1000.0)+" sec\n");
                    terminate=false;
                    //System.out.println("");
                    //textDebug.setText("");
                    p.waitFor();
                    int x = p.exitValue();
                    if (x == 0) {
                        JOptionPane.showMessageDialog(null,"Run Completed !");
                    }
                    else
                    {
                        System.out.println("Displaying something");
                        BufferedReader r = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                        String out;
                        while ((out = r.readLine()) != null)
                        {
                            String msg=out + System.getProperty("line.separator");
                            System.out.println(msg);
                            System.out.println("Compiler : "+out);
                            textDebug.append(out+"\n");
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println("Error ");
                }
            }
        });
        compileRun.start();
    }//GEN-LAST:event_itemRunActionPerformed

    private void itemMouseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMouseActionPerformed
        // TODO add your handling code here:
        showMouse=itemMouse.isSelected();
    }//GEN-LAST:event_itemMouseActionPerformed

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jPanel2MouseDragged

    private void jSeparator1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSeparator1MouseDragged
        // TODO add your handling code here:
        //System.out.println("Dragged");
        textDebug.append(evt.getX()+" "+evt.getY()+"\n");
        ;//Hello
        //jSeparator1.setLocation(jSeparator1.getX(),evt.getY());
        int y=java.awt.MouseInfo.getPointerInfo().getLocation().y;
        jScrollPane4.setSize(jScrollPane4.getWidth(),y-150);
        //jScrollPane3.setLocation(jScrollPane3.getX(),y-jPanel2.getY()-100);
        
        //jScrollPane3.setSize(jScrollPane3.getWidth(),jScrollPane3.getY()-evt.getY());
    }//GEN-LAST:event_jSeparator1MouseDragged

    private void txtInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInputActionPerformed

    private void itemStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemStopActionPerformed
        // TODO add your handling code here:
        terminate=true;
    }//GEN-LAST:event_itemStopActionPerformed

    private void txtInputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInputKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtInputKeyTyped

    private void itemPopPasteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemPopPasteMouseReleased
        // TODO add your handling code here:]
        
    }//GEN-LAST:event_itemPopPasteMouseReleased

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formMouseReleased

    private void textAreaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textAreaMouseReleased
        // TODO add your handling code here:
        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(this,evt.getX(),evt.getY());
        }
        //System.out.println("Right Mouse ");
    }//GEN-LAST:event_textAreaMouseReleased

    private void itemPopCutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPopCutActionPerformed
        // TODO add your handling code here:
        prev.push(textArea.getText());
        clipboard=textArea.getSelectedText();
        textArea.replaceSelection("");
    }//GEN-LAST:event_itemPopCutActionPerformed

    private void itemPopCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPopCopyActionPerformed
        // TODO add your handling code here:
        clipboard=textArea.getSelectedText();
    }//GEN-LAST:event_itemPopCopyActionPerformed

    private void itemPopPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPopPasteActionPerformed
        // TODO add your handling code here:
        prev.push(textArea.getText());
        textArea.insert(clipboard,textArea.getCaretPosition());
    }//GEN-LAST:event_itemPopPasteActionPerformed

    private void itemPopupCompileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPopupCompileActionPerformed
        // TODO add your handling code here:
        long t1=System.currentTimeMillis();
        inFile=filePath.substring(filePath.lastIndexOf('\\')+1); // File name with extension
        outFile=inFile.substring(0,inFile.lastIndexOf('.')); // file name without extension
        fileDir=filePath.substring(0,filePath.lastIndexOf('\\')); // file path without actual file
        
        //String ext=filePath.substring(filePath.lastIndexOf('.'));
        //pb = new ProcessBuilder("cmd", "/C", "g++ " + "\"" + filepath2 + "\\" + name + "\"" + " -o \"" + name2+"\"");
        
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "gcc " + "\"" + fileDir + "\\" +inFile+ "\"" + " -o \"" + outFile+"\"");
            pb.directory(new File(fileDir));
            Process p = pb.start();
            textDebug.setText("");
            p.waitFor();
            int x = p.exitValue();
            if (x == 0) {
                long t2=System.currentTimeMillis();
                //JOptionPane.showMessageDialog(null,"Compilation Completed !");
                textDebug.append("Done compiling\n");
                textDebug.append("Compilation Time : "+((t2-t1)/1000.0)+" sec\n");
            }
            else
            {
                BufferedReader r = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                String out;
                while ((out = r.readLine()) != null)
                {
                    String msg=out + System.getProperty("line.separator");
                    System.out.println(msg);
                    System.out.println("Compiler : "+out);
                    textDebug.append(out+"\n");
                }
                long t2=System.currentTimeMillis();
                textDebug.append("Compilation Time : "+((t2-t1)/1000.0)+" sec\n");
            }
        }
        catch (Exception e) {
            System.out.println("Error ");
        }
    }//GEN-LAST:event_itemPopupCompileActionPerformed

    private void itemPopupRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPopupRunActionPerformed
        // TODO add your handling code here:
        Thread compileRun = new Thread(new Runnable(){
            @Override
            public void run() {
                inFile=filePath.substring(filePath.lastIndexOf('\\')+1);
                outFile=inFile.substring(0,inFile.lastIndexOf('.'));
                fileDir=filePath.substring(0,filePath.lastIndexOf('\\'));
                //String ext=filePath.substring(filePath.lastIndexOf('.'));
                //pb = new ProcessBuilder("cmd", "/C", "g++ " + "\"" + filepath2 + "\\" + name + "\"" + " -o \"" + name2+"\"");
                try {
                    //ProcessBuilder pb = new ProcessBuilder("cmd", "/C", outFile);
                    ProcessBuilder pb = new ProcessBuilder("cmd","/c",outFile);
                    pb.directory(new File(fileDir));
                    Process p = pb.start();
                    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    //PrintWriter pw = new PrintWriter(p.getOutputStream());
                    String inp=txtInput.getText()+"\n";
                    
                    byte buffer[] = inp.getBytes();//new byte[100];
                    //buffer=new String("5\n").getBytes();
                    OutputStream os =(p.getOutputStream());
                    os.write(buffer,0,buffer.length);
                    os.flush();
                    String str;
                    textDebug.setText("");
                    long t1=System.currentTimeMillis();
                    while ((str=br.readLine())!=null) {
                        System.out.println(str);
                        textDebug.append(str+"\n");
                        if (terminate) {
                            break;
                        }
                        try {
                            ;//Thread.sleep(10);
                        } catch (Exception e) {
                            ;
                        }
                    }
                    long t2=System.currentTimeMillis();
                    System.out.println("show");
                    textDebug.append("\nRun completed !\n");
                    textDebug.append("Elapsed Time : "+((t2-t1)/1000.0)+" sec\n");
                    terminate=false;
                    //System.out.println("");
                    //textDebug.setText("");
                    p.waitFor();
                    int x = p.exitValue();
                    if (x == 0) {
                        JOptionPane.showMessageDialog(null,"Run Completed !");
                    }
                    else
                    {
                        System.out.println("Displaying something");
                        BufferedReader r = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                        String out;
                        while ((out = r.readLine()) != null)
                        {
                            String msg=out + System.getProperty("line.separator");
                            System.out.println(msg);
                            System.out.println("Compiler : "+out);
                            textDebug.append(out+"\n");
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println("Error ");
                }
            }
        });
        compileRun.start();
    }//GEN-LAST:event_itemPopupRunActionPerformed

    private void itemPopupFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPopupFindActionPerformed
        // TODO add your handling code here:
        find(0,true,false);
    }//GEN-LAST:event_itemPopupFindActionPerformed

    private void itemPopupReplaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPopupReplaceActionPerformed
        // TODO add your handling code here:
        callReplaceDialog();
    }//GEN-LAST:event_itemPopupReplaceActionPerformed

    private void textAreaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textAreaKeyTyped
        // TODO add your handling code here:
        change=true;
        Thread trie = new Thread(new Runnable(){
            @Override
            public void run() {
                
            }
        });
        trie.start();
        
        Thread highRepeat = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    
                }
                if (!Character.isLetter(evt.getKeyChar())) {
                   removeHighlight(textArea);
                }
                else {
                    String str=textArea.getText().substring(0,textArea.getCaretPosition());
                    int index=str.lastIndexOf(" ");
                    if (index!=-1) {
                        String repeat=str.substring(index+1,textArea.getCaretPosition());
                        if (!repeat.equals("")) {
                            if (repeatHighlight)
                                highlight(textArea,repeat);
                        }

                        //System.out.println(repeat);
                    }
                }
                
            }
        });
        highRepeat.start();
        
        if (autoMatch&&(evt.getKeyChar()=='{'||evt.getKeyChar()=='('||evt.getKeyChar()=='['||evt.getKeyChar()=='\'')||evt.getKeyChar()=='\"') {
            Thread autoCom = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        
                        char brac = evt.getKeyChar();
                        switch (brac) {
                            case '{':
                                brac='}';
                                break;
                            case '(':
                                brac=')';
                                break;
                            case '[':
                                brac=']';
                                break;
                        }
                        Thread.sleep(50);
                        textArea.insert(brac+"",textArea.getCaretPosition());
//                        String tmp=textArea.getText();
//                        int index=textArea.getCaretPosition();
//                        //String auto=tmp.substring(0,index)+brac+tmp.substring(index);
//                        
//                        System.out.println("index "+index);
//                        //textArea.setCaretPosition(2);
//                        textArea.setText(auto);
                    }
                    catch (Exception e) {

                    }
                }
            });
            Thread setCur = new Thread();
            autoCom.start();
            
            
            
            //prev.push(textArea.getText());
            
        }
        Thread indent = new Thread(new Runnable() {
            @Override
            public void run() {
                if (evt.getKeyChar()=='}') {
                    System.out.println("indent");
                    int cnt=0;
                    String tmp=textArea.getText();
                    int index=textArea.getCaretPosition();
                    String space="";
                    System.out.println(tmp.substring(0,index));
                    for (int i=0;i<index;i++) {
                        if (tmp.charAt(i)=='{')
                            cnt++;
                        if (tmp.charAt(i)=='}')
                            cnt--;
                    }
                    //cnt-=1;
                    if (cnt<0)
                        cnt=0;
                    for (int i=0;i<cnt;i++)
                        space=space+"    ";
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        
                    }
                    textArea.insert("\n"+space,index-1);
                    
                    textArea.setCaretPosition(index+cnt*4);
                }
                if (evt.getKeyChar()=='\n') {
                    int cnt=0;
                    String tmp=textArea.getText();
                    int index=textArea.getCaretPosition();
                    String space="";
                    for (int i=0;i<index;i++) {
                        if (tmp.charAt(i)=='{')
                            cnt++;
                        if (tmp.charAt(i)=='}')
                            cnt--;
                    }
                    if (cnt<0)
                        cnt=0;
                    for (int i=0;i<cnt;i++)
                        space=space+"    ";
                    textArea.insert(space,index);
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        
                    }
                    textArea.setCaretPosition(index+cnt*4);
                }
            }
        });
        indent.start();
    }//GEN-LAST:event_textAreaKeyTyped

    private void textAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textAreaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER||evt.getKeyCode()==KeyEvent.VK_SPACE) {
            String txt=textArea.getText();
            prev.push(txt);
            if (suggest) {
                int index=txt.lastIndexOf(' ');
                if (index==-1)
                    index=txt.lastIndexOf('\n');
                if (index==-1)
                    index=0;
                dict.addWord(txt.substring(index));
            }
            
        }
       
    }//GEN-LAST:event_textAreaKeyPressed

    private void itemAutoBracActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAutoBracActionPerformed
        // TODO add your handling code here:
        autoMatch=itemAutoBrac.isSelected();
    }//GEN-LAST:event_itemAutoBracActionPerformed

    private void itemFindAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemFindAllActionPerformed
        // TODO add your handling code here:
        String txt=JOptionPane.showInputDialog("Find");
        if (txt.equals("")) {
            JOptionPane.showMessageDialog(null,"Enter valid string");
            return;
        }
        highlight(textArea,txt);
    }//GEN-LAST:event_itemFindAllActionPerformed

    private void itemPopupRemoveHighlightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPopupRemoveHighlightActionPerformed
        // TODO add your handling code here:
        removeHighlight(textArea);
    }//GEN-LAST:event_itemPopupRemoveHighlightActionPerformed

    private void itemRepeatHighlightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemRepeatHighlightActionPerformed
        // TODO add your handling code here:
        repeatHighlight=itemRepeatHighlight.isSelected();
    }//GEN-LAST:event_itemRepeatHighlightActionPerformed

    private void btnCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCActionPerformed
        // TODO add your handling code here:
        btnC.setSelected(true);
        btnJava.setSelected(false);
        cpp=true;
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
    }//GEN-LAST:event_btnCActionPerformed

    private void btnJavaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJavaActionPerformed
        // TODO add your handling code here:
        btnC.setSelected(false);
        btnJava.setSelected(true);
        cpp=false;
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
    }//GEN-LAST:event_btnJavaActionPerformed

    private void itemCompleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCompleteActionPerformed
        // TODO add your handling code here:
        String txt=textArea.getText();
        int index=txt.lastIndexOf(' ');
        if (index==-1)
            index=0;
        String text=txt.substring(0,index);
        String search=txt.substring(index+1);
        String str[]=txt.split(" ");
        dict.delete();
        for (int i=0;i<str.length;i++)
            dict.addWord(str[i]);
        dict.getSuggestion(search);
        dict.show(500,300);
    }//GEN-LAST:event_itemCompleteActionPerformed
    
    public void followSuggestion(String w) {
        System.out.println("follow "+w);
        String txt=textArea.getText();
        int index=txt.lastIndexOf(' ');
        if (index==-1)
            index=0;
        textArea.select(index,txt.length());
        textArea.replaceSelection(" "+w);
    }
    
    private void saveAs() {
        FileDialog fd = new FileDialog(Editor.this,"Save",FileDialog.SAVE);
        fd.show();
        if (fd.getFile()!=null) {
            title=fd.getFile();
            dir=fd.getDirectory();
            file=dir+title;
            System.out.println(file);
            if (file.indexOf('.')==-1) {
                file=file+".c";
                title=title+".c";
            }
            filePath=file;
            textArea.requestFocus();
            save();
        }
    }
    
    private void save() {
        setTitle(title);
        try
        {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter p = new PrintWriter(bw);
            String data[]=textArea.getText().split("\n");
            for (String data1 : data) {
                p.println(data1);
            }
            p.flush();
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println("Writing error");
        } 
        change=false;
    }
    
    private void openFile() {
//        title=fd.getFile();
//            dir=fd.getDirectory();
//            file=dir+title;
//            System.out.println(file);
//            if (file.indexOf('.')==-1) {
//                file=file+".c";
//                title=title+".c";
//            }
//            filePath=file;
//            textArea.requestFocus();
//            save();
        try{
            JFileChooser fileChooser = new JFileChooser("C:\\Users\\ssony\\Desktop");
            fileChooser.setFileSelectionMode(FILES_ONLY);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("c", "c"));
            fileChooser.setAcceptAllFileFilterUsed(true);
            int re = fileChooser.showOpenDialog(this);
            
            if(re == JFileChooser.APPROVE_OPTION){
                File openFile = fileChooser.getSelectedFile();
                FileReader fr = new FileReader(openFile);
                BufferedReader br = new BufferedReader(fr);
                String tmp=openFile.toString();
                int index=tmp.lastIndexOf('\\');
                title=tmp.substring(index+1);
                dir=tmp.substring(0,index+1);
                file=dir+title;
                filePath=file;
                setTitle(title);
                textArea.read(br, null);
                br.close();
                autoInsertTime();
                
            }
        }
        catch(Exception e){
            
        } 
    }
    
    private void find(int startIndex,boolean newWord,boolean caseSensitive) {
        if (newWord)
            findText=JOptionPane.showInputDialog("Find");
        if (findText!=null&&findText.equals("")) {
            JOptionPane.showMessageDialog(null,"Enter something to Find");
            return;
        }
        String find=findText;
        String data=textArea.getText();
        if (!caseSensitive) {
            find=find.toLowerCase();
            data=data.toLowerCase();
        }
        int len=data.length();
        start=startIndex;
        end=start;
        for (int i=start;i<len;i++) {
            char c=data.charAt(i);
            if (c==find.charAt(0)) {
                start=i;
                boolean flag=true;
                for (int j=0;j<find.length();j++,i++) {
                    if (i>=len||data.charAt(i)!=find.charAt(j)) {
                        flag=false;
                        break;
                    }
                }
                if (flag) {
                    end=i;
                    break;
                }
            }
        }
        if (start>=end) {
            if (startIndex!=0) {
                find(0,false,caseSensitive);
            }
            else {
                JOptionPane.showMessageDialog(null,findText+" Not Found");
                start=textArea.getCaretPosition();
                end=start;
            }
        }
        textArea.select(start, end);
    }
    
    private void callReplaceDialog() {
        new FindReplace(this).setVisible(true);
    }
    
    public void replace(boolean all,boolean caseSensitive) {
        prev.push(textArea.getText());
        String data=textArea.getText();
        if  (!caseSensitive) {
            data=data.toLowerCase();
            findText=findText.toLowerCase();
        }
        int len=data.length();
        for (int i=0;i<len;i++) {
            char c=data.charAt(i);
            if (c==findText.charAt(0)) {
                start=i;
                boolean flag=true;
                for (int j=0;j<findText.length();j++,i++) {
                    if (i>=len||data.charAt(i)!=findText.charAt(j)) {
                        flag=false;
                        break;
                    }
                }
                if (flag) {
                    end=i;
                    textArea.select(start,end);
                    textArea.replaceSelection(replaceText);
                    data=textArea.getText();
                    len=data.length();
                    i=start+replaceText.length()-1;
                    change=true;
                    if (!all)
                        break;
                }
            }
        }
    }
    
    private void autoInsertTime() {
        if (textArea.getText().startsWith(".LOG")) {
            textArea.insert("\n"+time(),textArea.getText().length());
        }
    }
    
    private String time() {
        String DATE_FORMAT_NOW="HH:mm dd-MM-yyyy";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        change=true;
        return sdf.format(cal.getTime());
    }
    
    private void callFontSelectDialog() {
        fontdialog.setVisible(true);
    }
    
    public void setTextFont(Font font) {
        textArea.setFont(font);
        textArea.setForeground(textColor);
    }
    
    private void exit() {
        this.setVisible(false);
        Notepad.windowCount--;
        if (Notepad.windowCount<=0) {
            System.exit(0);
        }
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Editor().setVisible(true);
            }
        });
    }

    private boolean change=false;
    private String file="";
    String filePath="";
    private String dir="";
    private final String defaultTitle="Untitled";
    private String title=defaultTitle;
    File Filename2;
    Clipboard clip = getToolkit().getSystemClipboard();
    private String clipboard;
    private FixedStack<String> prev;
    private final int undomoves=100;
    private int start,end;
    public String findText;
    public String replaceText;
    public Color textColor;
    private final FontSelect fontdialog;
    int indentTab;
    int tabSpace=4;
    String Jtreevar;
    
    String inFile;
    String outFile;
    String fileDir;
    
    TextLineNumber tln;// = new TextLineNumber(textArea);
    //scrollPane.setRowHeaderView( tln );
    
    boolean showMouse;
    boolean terminate;
    boolean autoMatch=true;
    boolean repeatHighlight=false;
    boolean cpp=true;
    Dictionary dict = new Dictionary(this);
    boolean suggest=false;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton btnC;
    private javax.swing.JRadioButton btnJava;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JMenuItem itemAbout;
    private javax.swing.JCheckBoxMenuItem itemAutoBrac;
    private javax.swing.JMenuItem itemCompile;
    private javax.swing.JMenuItem itemComplete;
    private javax.swing.JMenuItem itemCopy;
    private javax.swing.JMenuItem itemCut;
    private javax.swing.JMenuItem itemDelete;
    private javax.swing.JMenuItem itemExit;
    private javax.swing.JMenuItem itemFind;
    private javax.swing.JMenuItem itemFindAll;
    private javax.swing.JMenuItem itemFindNext;
    private javax.swing.JMenuItem itemFont;
    private javax.swing.JMenuItem itemGoto;
    private javax.swing.JCheckBoxMenuItem itemLineNumber;
    private javax.swing.JCheckBoxMenuItem itemMouse;
    private javax.swing.JMenuItem itemNew;
    private javax.swing.JMenuItem itemNewWindow;
    private javax.swing.JMenuItem itemOpen;
    private javax.swing.JMenuItem itemOpenFile;
    private javax.swing.JMenuItem itemPaste;
    private javax.swing.JMenuItem itemPopCopy;
    private javax.swing.JMenuItem itemPopCut;
    private javax.swing.JMenuItem itemPopPaste;
    private javax.swing.JMenuItem itemPopupCompile;
    private javax.swing.JMenuItem itemPopupFind;
    private javax.swing.JMenuItem itemPopupRemoveHighlight;
    private javax.swing.JMenuItem itemPopupReplace;
    private javax.swing.JMenuItem itemPopupRun;
    private javax.swing.JCheckBoxMenuItem itemRepeatHighlight;
    private javax.swing.JMenuItem itemReplace;
    private javax.swing.JMenuItem itemRun;
    private javax.swing.JMenuItem itemSave;
    private javax.swing.JMenuItem itemSaveAs;
    private javax.swing.JMenuItem itemSelectAll;
    private javax.swing.JMenuItem itemStop;
    private javax.swing.JCheckBoxMenuItem itemSuggest;
    private javax.swing.JMenuItem itemTime;
    private javax.swing.JMenuItem itemUndo;
    private javax.swing.JMenuItem itemViewHelp;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuFormat;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenu menuRun;
    private javax.swing.JMenu menuView;
    private RSyntaxTextArea textArea;
    private javax.swing.JTextArea textDebug;
    private javax.swing.JLabel txtCarretX;
    private javax.swing.JLabel txtCarretY;
    private javax.swing.JTextField txtInput;
    private javax.swing.JLabel txtMouseX;
    private javax.swing.JLabel txtMouseY;
    // End of variables declaration//GEN-END:variables
}

class FixedStack<T> extends Stack<T> {
    private final int maxSize;
    public FixedStack(int size) {
        super();
        this.maxSize=size;
    }
    @Override
    public T push(T object) {
        while (this.size()>=maxSize) {
            this.remove(0);
        }
        return super.push(object);
    }
}