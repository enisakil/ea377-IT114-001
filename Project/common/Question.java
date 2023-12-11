//ea377 11/15/23
package Project.common;

import java.io.Serializable;

public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    private String text;
    private static String[] options;
    private int correctOptionIndex;
    private String category;

    public Question(String text, String[] options, int correctOptionIndex, String category) {
        this.text = text;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public static String[] getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public String getCategory() {
        return category;
    }
}