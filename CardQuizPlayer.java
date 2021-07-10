import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
public class CardQuizPlayer{
    JFrame frame;
    ArrayList<QuizCard>S;
    JTextArea Display;
    JTextArea Answer;
    QuizCard currnetCard;
    JButton button;
    boolean isShowAnswer;
    int currentCardIndex;
    public static void main(String[] args) {
        CardQuizPlayer gui=new CardQuizPlayer();
        gui.go();
    }
    void go(){
        frame=new JFrame("Player Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel=new JPanel();
        Font bigFont=new Font("sanserif",Font.BOLD,24);

        Display=new JTextArea(10,20);
        Display.setLineWrap(true);
        Display.setEditable(true);
        Display.setFont(bigFont);

        JScrollPane dScroller=new JScrollPane(Display);
        dScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        dScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(dScroller);

        button=new JButton("Next question");
        button.addActionListener(new NextCardListener());
        panel.add(button);

        JMenuBar menuBar=new JMenuBar();
        JMenu FileMenu=new JMenu("File");
        JMenuItem LoadMenuItem=new JMenuItem("Load card set");
        FileMenu.add(LoadMenuItem);
        menuBar.add(FileMenu);
        LoadMenuItem.addActionListener(new NextCardListener());
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER,panel);
        frame.setSize(500,500);
        frame.setVisible(true);
    }
    public class NextCardListener implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            if(isShowAnswer){
                Display.setText(currnetCard.getAnswer());
                button.setText("Next card");
                isShowAnswer=false;
            }else{
                if(currentCardIndex<S.size()){
                    showNextCard();
                }else{
                    Display.setText("There was no last card");
                    button.setEnabled(false);   
                }
            }
        }
    }
    public class OpenMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            JFileChooser fileOpen=new JFileChooser();
            fileOpen.showOpenDialog(frame);
           LoadFile(fileOpen.getSelectedFile());
        }
    }
    public void LoadFile(File file){
        S=new ArrayList<QuizCard>();
        try{
            BufferedReader reader=new BufferedReader(new FileReader(file));
            String line=null;
            while((line=reader.readLine())!=null){
                makeCard(line);
            }
            reader.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        showNextCard();
    }
    private void makeCard(String LineToPrase){
        String []result=LineToPrase.split("/");
        QuizCard card=new QuizCard(result[0], result[1]);
        S.add(card);
        System.out.println("Make a new card");
    }
    private void showNextCard(){
        currnetCard=S.get(currentCardIndex);
        currentCardIndex++;
        Display.setText(currnetCard.getQuestion());
        button.setText("Show Answer");
        isShowAnswer=true;
    }
}