import javax.swing.*;
import java.util.*;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
class QuizCard{
    private String question;
    private String answer;
    public QuizCard(String q, String a){
        question=q;
        answer=a;
    }
    public String getQuestion(){
        return question;
    }
    public String getAnswer(){
        return answer;
    }
}
public class QuizCardBuilder {
    JFrame frame;
    JTextArea Question;
    JTextArea Answer;
    ArrayList<QuizCard>CardList;
    public static void main(String[] args) {
        QuizCardBuilder builder=new QuizCardBuilder();
        builder.go();

    }
   public void go(){
       frame=new JFrame("Window");
       JPanel mainPanel=new JPanel();
       Font bigFont=new Font("sanserif",Font.BOLD,24);
       JButton button=new JButton("Next question");
       button.addActionListener(new NewMenuListner());
       CardList =new ArrayList<QuizCard>();

        Question=new JTextArea(6,20);
        Question.setLineWrap(true);
        Question.setWrapStyleWord(true);
        Question.setFont(bigFont);
        JScrollPane qScroller=new JScrollPane(Question);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        Answer=new JTextArea(6,20);
        Answer.setLineWrap(true);
        Answer.setWrapStyleWord(true);
        Answer.setFont(bigFont);
        JScrollPane aScroller=new JScrollPane(Answer);
        aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JLabel qLabel=new JLabel("Question: ");
        JLabel aLabel=new JLabel("Answer: ");

        mainPanel.add(qLabel);
        mainPanel.add(qScroller);
        mainPanel.add(aLabel);
        mainPanel.add(aScroller);
        mainPanel.add(button);

        JMenuBar menuBar=new JMenuBar();
        JMenu fileMenu=new JMenu("File");
        JMenuItem saveMenu=new JMenuItem("Save");
        saveMenu.addActionListener(new SaveMenuListener());
        JMenuItem newMenu=new JMenuItem("New");
        newMenu.addActionListener(new NewMenuListner());

        fileMenu.add(newMenu);
        fileMenu.add(saveMenu);
        menuBar.add(fileMenu);

       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setJMenuBar(menuBar);
       frame.getContentPane().add(mainPanel);
       frame.setSize(500,500);
       frame.setVisible(true);
    }
    public class NextCardListener implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            QuizCard card=new QuizCard(Question.getText(), Answer.getText());
            CardList.add(card);
            clearCard();
        }
    }
    public class SaveMenuListener implements ActionListener{
        public void actionPerformed (ActionEvent ev) {
            QuizCard card=new QuizCard(Question.getText(), Answer.getText());
            CardList.add(card);
            JFileChooser fileSave=new JFileChooser();
            fileSave.showSaveDialog(frame);
            SaveFile(fileSave.getSelectedFile());
        }
    }
    public class NewMenuListner implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            CardList.clear();
            clearCard();
        }
    }
    private void clearCard(){
        Question.setText("");
        Answer.setText("");
        Question.requestFocus();
    }
    private void SaveFile(File file){
        try{
            BufferedWriter writer=new BufferedWriter(new FileWriter(file));
            for(QuizCard card:CardList){
                writer.write(card.getQuestion()+" / ");
                writer.write(card.getAnswer() + "\n");
            }
            writer.close();
        }
        catch(IOException ex){
            System.out.println("Couldn't write the cardList");
            ex.printStackTrace();
        }
    }
}