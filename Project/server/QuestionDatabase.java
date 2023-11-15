//ea377 11/15/23
package Project.server;

import Project.common.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QuestionDatabase {
    private Map<String, List<Question>> categoryQuestions;

    public QuestionDatabase() {
        this.categoryQuestions = new HashMap<>();
        initializeQuestions(); // You can populate questions in your actual implementation
    }

    private void initializeQuestions() {
        // Add sample questions to the database with categories
        addQuestion(new Question("What is the capital of France?", new String[]{"Berlin", "Madrid", "Paris", "Rome"}, 2, "Geography"));
        addQuestion(new Question("Which planet is known as the Red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Venus"}, 1, "Space"));
        // Add more questions as needed
    }

    private void addQuestion(Question question) {
        // Add a question to the corresponding category
        categoryQuestions.computeIfAbsent(question.getCategory(), k -> new ArrayList<>()).add(question);
    }

    public Question getRandomQuestion(String category) {
        if (!categoryQuestions.containsKey(category) || categoryQuestions.get(category).isEmpty()) {
            return null; // Handle the case where there are no questions in the specified category
        }

        List<Question> questionsInCategory = categoryQuestions.get(category);
        Random random = new Random();
        int randomIndex = random.nextInt(questionsInCategory.size());
        return questionsInCategory.get(randomIndex);
    }
}
