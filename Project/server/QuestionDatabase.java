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
        initializeQuestions();
    }
//ea377 11/21/22
    private void initializeQuestions() {
        // Add sample questions to the database with categories
        addQuestion(new Question("What is the capital of France?", new String[]{"Berlin", "Madrid", "Paris", "Rome"}, 2, "Geography: Europe"));
        addQuestion(new Question("Which planet is known as the Red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Venus"}, 1, "Space"));
        addQuestion(new Question("What is the capital of New York", new String[]{"New York City", "Little Rock", "Rochester", "Albany"}, 3, "Geography: America"));
        addQuestion(new Question("In which sport would you perform a slam dunk?", new String[]{"Soccer", "Basketball", "Tennis", "Golf"}, 1, "Sports"));
        addQuestion(new Question("In which year did Christopher Columbus reach the Americas?", new String[]{"1492", "1520", "1607", "1620"}, 0, "History"));



        // Add more questions as needed
    }

    private void addQuestion(Question question) {
        // Add a question to the corresponding category
        categoryQuestions.computeIfAbsent(question.getCategory(), k -> new ArrayList<>()).add(question);
    }
//ea377 11/15/23
    public String getRandomCategory() {
        if (categoryQuestions.isEmpty()) {
            return null; // Handle the case where there are no categories
        }

        List<String> categories = new ArrayList<>(categoryQuestions.keySet());
        Random random = new Random();
        int randomIndex = random.nextInt(categories.size());
        return categories.get(randomIndex);
    }

    public Question getRandomQuestion(String category) {
        if (categoryQuestions.containsKey(category)) {
            List<Question> questionsInCategory = categoryQuestions.get(category);

            if (!questionsInCategory.isEmpty()) {
                Random random = new Random();
                int randomIndex = random.nextInt(questionsInCategory.size());
                return questionsInCategory.get(randomIndex);
            } else {
                // Handle the case where there are no questions in the specified category
                return null;
            }
        } else {
            // Handle the case where the category is not found
            return null;
        }
    }

    }
