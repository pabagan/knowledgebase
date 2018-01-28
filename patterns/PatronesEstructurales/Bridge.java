import java.util.*;

interface Question {
  
    public void nextQuestion();
    public void priorQuestion();
    public void newQuestion(String q);
    public void deleteQuestion(String q);
    public void displayQuestion();
    public void displayAllQuestions();
}

class QuestionManager {
 
  protected Question questDB;
  public String catalog;

  public QuestionManager(String catalog) {
      this.catalog = catalog;
  }

  public void next() {
      questDB.nextQuestion();
  }

  public void prior() {
      questDB.priorQuestion();
  }

  public void newOne(String quest) {
      questDB.newQuestion(quest);
  }

  public void delete(String quest) {
      questDB.deleteQuestion(quest);
  }

  public void display() {
      questDB.displayQuestion();
  }

  public void displayAll() {
      System.out.println("Catálogo de preguntas: " + catalog);
      questDB.displayAllQuestions();
  }
}

class QuestionFormat extends QuestionManager {
  
    public QuestionFormat(String catalog){
        super(catalog);
    }

    public void displayAll() {
    
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~");
        super.displayAll();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}

class JavaQuestions implements Question {
  
    private List<String> questions = new ArrayList<String>();
    private int current = 0;

    public JavaQuestions() {
        questions.add("¿Qué es Java? ");
        questions.add("¿Qué es una interfaz? ");
        questions.add("¿Qué significa 'multi-plataform'? ");
        questions.add("¿Qué significa 'UFT-8'? ");
        questions.add("¿Qué significa 'abstract'? ");
        questions.add("¿Qué significa 'Thread'? ");
        questions.add("¿Qué significa 'multi-threading'? "); 
    }

    public void nextQuestion() {
        if( current <= questions.size() - 1 )
            current++;
    }

    public void priorQuestion() {
        if( current > 0 )
            current--;
    }

    public void newQuestion(String quest) {
        questions.add(quest);
    }

    public void deleteQuestion(String quest) {
        questions.remove(quest);
    }

    public void displayQuestion() {
        System.out.println( questions.get(current) );
    }

    public void displayAllQuestions() {
        for (String quest : questions) {
            System.out.println(quest);
        }
    }
}

public class Bridge {
    public static void main(String[] args) {
 
        QuestionFormat questions = new QuestionFormat("Lenguaje Java");

        questions.questDB = new JavaQuestions();
        //podemos usar otras clases de preguntas
        //questions.questDB = new CsharpQuestions();
        //questions.questDB = new CplusplusQuestions();

        questions.display();
        questions.next();
    
        questions.newOne("¿Qué es un objeto?");
        questions.newOne("¿Qué es un 'reference type'?");

        questions.displayAll();
  }
}